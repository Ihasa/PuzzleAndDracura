package puzzlefield;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Panel;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class PuzzleApplet extends Panel implements MouseListener, MouseMotionListener{
	private int BLOCK_WIDTH;
	private int BLOCK_HEIGHT;
	private static final int FIELD_GRIDS_X = 6;
	private static final int FIELD_GRIDS_Y = 5;
	private int FIELD_WIDTH;
	private int FIELD_HEIGHT;
	
	private Field field;
	private AppletFieldDrawer fieldDrawer;
	private StringDrawer comboDrawer;
	
	private int currentMouseGridX = -1;
	private int currentMouseGridY = -1;
	public PuzzleApplet(){
	    this(64 * FIELD_GRIDS_X, 64 * FIELD_GRIDS_Y);
	}
	public PuzzleApplet(int width, int height){
		
		/*int blockSizeW = width / FIELD_GRIDS_X;
		int blockSizeH = height / FIELD_GRIDS_Y;
		if(blockSizeW == blockSizeH){
			BLOCK_WIDTH = BLOCK_HEIGHT = blockSizeW;
		}else if(blockSizeW > blockSizeH){
			BLOCK_WIDTH = BLOCK_HEIGHT = blockSizeH;
		}else{
			BLOCK_WIDTH = BLOCK_HEIGHT = blockSizeW;
		}*/
		BLOCK_WIDTH = BLOCK_HEIGHT = Math.min(width / FIELD_GRIDS_X, height / FIELD_GRIDS_Y);
		FIELD_WIDTH = BLOCK_WIDTH * FIELD_GRIDS_X;
		FIELD_HEIGHT = BLOCK_HEIGHT * FIELD_GRIDS_Y;

		setSize(FIELD_WIDTH, FIELD_HEIGHT);

	    field = new Field(FIELD_GRIDS_X, FIELD_GRIDS_Y);
	    fieldDrawer = new AppletFieldDrawer(this,BLOCK_WIDTH, BLOCK_HEIGHT);
	    comboDrawer = new StringDrawer(this, 0, 0);
	    
	    addMouseListener(this);
	    addMouseMotionListener(this);
	    repaint();
	}
    public Dimension getMinimumSize() {
        return new Dimension(FIELD_WIDTH, FIELD_HEIGHT);
    }

    public Dimension getPreferredSize() {
        return getMinimumSize();
    }
	
	public void paint(Graphics g){
		fieldDrawer.draw(field.getFieldState(), true);
	}
	
	/*Thread th;
	public void start(){
		th = new Thread(this);
		th.start();
	}*/
	
	private int getGridX(int mouseX){
		return (int)((mouseX / (float)FIELD_WIDTH) * FIELD_GRIDS_X);
	}
	private int getGridY(int mouseY){
		return (int)((mouseY / (float)FIELD_HEIGHT) * FIELD_GRIDS_Y);		
	}
	private void setCurrentMouseGrid(int x, int y){
		currentMouseGridX = x;
		currentMouseGridY = y;
	}
	
	private int combo = 0;
	private void eraceBlocks(){
		combo = 0;
		while(field.checkEraceable()){
			try{
				field.erase();
				fieldDrawer.draw(field.getFieldState(), false);
				combo += field.getCombo();
				comboDrawer.draw(combo + " Combo");
				Thread.sleep(1000);
			
				field.drop();
				field.fill();

				fieldDrawer.draw(field.getFieldState(), false);
				Thread.sleep(1000);
			}catch(Exception e){
			}
		}
	}
	
	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mousePressed(MouseEvent me) {
		int mouseX = me.getX();
		int mouseY = me.getY();
		int gridX = getGridX(mouseX);
		int gridY = getGridY(mouseY);
		
		if(field.select(gridX, gridY)){
			//System.out.println("YEY:" + gridX + ", " + gridY);
			setCurrentMouseGrid(gridX, gridY);
		}
	}
	
	@Override
	public void mouseDragged(MouseEvent me) {
		int mouseX = me.getX();
		int mouseY = me.getY();
		int gridX = getGridX(mouseX);
		int gridY = getGridY(mouseY);
		
		if(field.isSelected()){
			int dx = gridX - currentMouseGridX;
			int dy = gridY - currentMouseGridY;
			if(dx != 0 || dy != 0){
				if(dx == -1)
					field.moveLeft();
				else if(dx == 1)
					field.moveRight();
				
				if(dy == -1)
					field.moveUp();
				else if(dy == 1)
					field.moveDown();
				
				fieldDrawer.draw(field.getFieldState(), false);
				
				//System.out.println("moved");
			}		
		}
		setCurrentMouseGrid(gridX, gridY);
		
		/*if(dx != 0 || dy != 0){
			System.out.println("dx = " + dx + ", dy = " + dy);
		}*/
	}
	
	@Override
	public void mouseReleased(MouseEvent arg0) {
		if(field.isSelected()){
			field.release();
			eraceBlocks();
		}
		//System.out.println("released");
	}
	
	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
