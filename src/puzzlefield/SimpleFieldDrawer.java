package puzzlefield;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;

public class SimpleFieldDrawer extends AppletFieldDrawer{
	public SimpleFieldDrawer(Applet app, int bWidth, int bHeight) {
		super(app, bWidth, bHeight);
	}
	
	protected void drawAllBlocks(Graphics g, FieldState fState) {
		for(int y = 0; y < fState.height; y++){
			for(int x = 0; x < fState.width; x++){
				drawBlock(g, x, y, fState);
			}
		}
	}

	protected void drawUpdatedBlocks(Graphics g, FieldState current, FieldState prev, FieldIndex[] diff) {
		for(FieldIndex index : diff){
			int x = index.x;
			int y = index.y;
			g.clearRect(x * blockWidth, y * blockHeight, blockWidth, blockHeight);
			drawBlock(g, x, y, current);
		}
	}

	private void drawBlock(Graphics g, int x, int y, FieldState fState){
		BlockColor color = fState.get(x, y);
//		Cursor cursor = fState.getCursor();
//		if(cursor.selected && cursor.x == x && cursor.y == y){
//			g.setColor(Color.MAGENTA);
//			g.fillRect(x * blockWidth, y * blockHeight, blockWidth, blockHeight);
//		}
		drawBlockImage(g,color,x * blockWidth,y * blockHeight,blockWidth,blockHeight);
		
	}
}
