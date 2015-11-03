package puzzlefield;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;

public class BlockEraceDrawer extends AppletFieldDrawer {

	public BlockEraceDrawer(Applet app, int bWidth, int bHeight) {
		super(app, bWidth, bHeight);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void drawAllBlocks(Graphics g, FieldState fState) {
		for(int y = 0; y < fState.height; y++){
			for(int x = 0; x < fState.width; x++){
				drawBlock(g, x, y, fState);
			}
		}
	}
	private void drawBlock(Graphics g, int x, int y, FieldState fState){
		BlockColor color = fState.get(x, y);
		Cursor cursor = fState.getCursor();
		if(cursor.selected && cursor.x == x && cursor.y == y){
			g.setColor(Color.MAGENTA);
			g.fillRect(x * blockWidth, y * blockHeight, blockWidth, blockHeight);
		}
		drawBlockImage(g,color,x * blockWidth,y * blockHeight,blockWidth,blockHeight);
	}
	private Animation th;
	
	@Override
	protected void drawUpdatedBlocks(Graphics g, FieldState current, FieldState prev, FieldIndex[] diff) {
		for(ErasedBlockInfo ebi : current.ebi){
			th = new Animation( (frame,allFrame)->{
				Color ord = g.getColor();
				g.setColor(new Color(255,255,255,255 - (int)(255 * frame / (float)allFrame)));
				for(int i = 0; i < ebi.blockNum; i++){
					FieldIndex idx = ebi.getIndex(i);
					BlockColor color = prev.get(idx.x, idx.y);
					
					g.clearRect(idx.x * blockWidth, idx.y * blockHeight, blockWidth, blockHeight);
					
					drawBlockImage(g,color,idx.x * blockWidth, idx.y * blockHeight, blockWidth, blockHeight);
					g.fillRect(idx.x * blockWidth, idx.y * blockHeight, blockWidth, blockHeight);					
				}
				g.setColor(ord);
			},300);
			th.start();
			try {
				th.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
