package puzzlefield;
import java.applet.Applet;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class PuzzleApplet extends Applet implements MouseListener, MouseMotionListener{
	private static final int BLOCK_WIDTH = 64;
	private static final int BLOCK_HEIGHT = 64;
	private static final int FIELD_GRIDS_X = 6;
	private static final int FIELD_GRIDS_Y = 5;
	private static final int FIELD_WIDTH = BLOCK_WIDTH * FIELD_GRIDS_X;
	private static final int FIELD_HEIGHT = BLOCK_HEIGHT * FIELD_GRIDS_Y;
	
	private Field field;
	private AppletFieldDrawer moveDrawer;
	private AppletFieldDrawer fallDrawer;
	private AppletFieldDrawer eraceDrawer;
	private AppletMessageBox msgBox;
	
	private int currentMouseGridX = -1;
	private int currentMouseGridY = -1;
	public PuzzleApplet(){
	    setSize(FIELD_WIDTH, FIELD_HEIGHT);
	    
	    field = new Field(FIELD_GRIDS_X, FIELD_GRIDS_Y);
	    
	    moveDrawer = new BlockMoveDrawer(this,BLOCK_WIDTH, BLOCK_HEIGHT);
	    fallDrawer = new SimpleFieldDrawer(this,BLOCK_WIDTH,BLOCK_HEIGHT);
	    eraceDrawer = new SimpleFieldDrawer(this,BLOCK_WIDTH,BLOCK_HEIGHT);
	    msgBox = new AppletMessageBox(this, 0, 0);
	    
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
		moveDrawer.draw(field.getFieldState());
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
		FieldState current;
		FieldState prev;
		while(field.checkEraceable()){
			prev = field.getFieldState();
			field.erase();
			current = field.getFieldState();
			
			eraceDrawer.draw(current, prev, false);
			
			combo += field.getCombo();
			msgBox.draw(combo + " Combo");
			
			prev = current;
			field.drop();
			field.fill();
			current = field.getFieldState();

			fallDrawer.draw(current, prev, false);			
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
			System.out.println("YEY:" + gridX + ", " + gridY);
			setCurrentMouseGrid(gridX, gridY);
		}
	}
	
	@Override
	public void mouseDragged(MouseEvent me) {
		int mouseX = me.getX();
		int mouseY = me.getY();
		int gridX = getGridX(mouseX);
		int gridY = getGridY(mouseY);
		
		int dx = gridX - currentMouseGridX;
		int dy = gridY - currentMouseGridY;
		if(field.isSelected()){
			if(dx != 0 || dy != 0){
				FieldState prev = field.getFieldState();
				
				if(dx == -1)
					field.moveLeft();
				else if(dx == 1)
					field.moveRight();
				
				if(dy == -1)
					field.moveUp();
				else if(dy == 1)
					field.moveDown();
				
				FieldState current = field.getFieldState();
				
				moveDrawer.draw(current, prev, false);
				
				System.out.println("moved");
			}		
		}
		setCurrentMouseGrid(gridX, gridY);
		
		if(dx != 0 || dy != 0){
			System.out.println("dx = " + dx + ", dy = " + dy);
		}
	}
	
	@Override
	public void mouseReleased(MouseEvent arg0) {
		if(field.isSelected()){
			field.release();
			eraceBlocks();
		}
		System.out.println("released");
	}
	
	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
