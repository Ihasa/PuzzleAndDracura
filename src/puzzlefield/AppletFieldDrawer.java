package puzzlefield;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Panel;

public class AppletFieldDrawer{
	private static Image[] img;
	private int blockWidth;
	private int blockHeight;
	private Panel panel;
	
	public AppletFieldDrawer(Panel p, int bWidth, int bHeight){
		panel = p;
		blockWidth = bWidth;
		blockHeight = bHeight;
		
		if(img == null){
			String[] imgNames = new String[]{
					"ball_red.gif", "ball_green.gif", "ball_blue.gif",
					"ball_yellow.gif", "ball_black.gif", "ball_pink.gif"
				};
			img = new Image[imgNames.length];
			int i = 0;
			for(String name : imgNames){
				img[i++] = loadImage("img/" + name);
			}
		}
	}
	
	private Image loadImage(String name) {
		try {
		    java.net.URL url = getClass().getResource(name);
		    return panel.createImage((java.awt.image.ImageProducer) url.getContent());
		} catch (Exception ex) {
		    return null;
		}
	}
	
	private FieldState prevState = null;
	public void draw(FieldState fState, boolean repaint){
		//super.draw(fState);
		Graphics g = panel.getGraphics();
		if(g == null){
			return;
		}
		//g.clearRect(0, 0, blockWidth * fState.width, blockHeight * fState.height);
		for(int y = 0; y < fState.height; y++){
			for(int x = 0; x < fState.width; x++){
				//Å‰‚ârepaint–½—ßŽž‚Í‘S•”•`‰æ
				if(repaint || prevState == null){
					drawBlock(g, x, y, fState);
				}else{
					//·•ª‚ª‚ ‚Á‚½ê‡‚Ì‚Ý•`‰æ
					if(stateChanged(fState, x, y)){
						g.clearRect(x * blockWidth, y * blockHeight, blockWidth, blockHeight);
						drawBlock(g, x, y, fState);
					}
				}
			}
		}
		prevState = fState;
	}
	private void drawBlock(Graphics g, int x, int y, FieldState fState){
		BlockColor color = fState.get(x, y);
		Cursor cursor = fState.getCursor();
		if(color != BlockColor.EMPTY){
			if(cursor.selected && cursor.x == x && cursor.y == y){
				g.setColor(Color.MAGENTA);
				g.fillRect(x * blockWidth, y * blockHeight, blockWidth, blockHeight);
			}
			int imgIndex = color.ordinal();
			g.drawImage(img[imgIndex], x * blockWidth, y * blockHeight, blockWidth, blockHeight, panel);
		}
	}
	private boolean stateChanged(FieldState current, int x, int y){
		Cursor curCursor = current.getCursor();
		Cursor prevCursor = prevState.getCursor();
		return current.get(x, y) != prevState.get(x, y) || 
				(curCursor.x == x && curCursor.y == y) ||
				(prevCursor.x == x && prevCursor.y == y);
	}
}
