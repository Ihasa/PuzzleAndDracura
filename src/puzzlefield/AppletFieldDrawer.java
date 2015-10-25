package puzzlefield;
import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

public class AppletFieldDrawer{
	private static Image[] img;
	private int blockWidth;
	private int blockHeight;
	private Applet applet;
	
	public AppletFieldDrawer(Applet app, int bWidth, int bHeight){
		applet = app;
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
		    return applet.createImage((java.awt.image.ImageProducer) url.getContent());
		} catch (Exception ex) {
		    return null;
		}
	}
			
	Thread th = null;
	//事後条件：描画後のフィールドがfStateに基づいたものになっていること
	private FieldState prevState = null;
	public void draw(FieldState fState, boolean repaint){
		//super.draw(fState);
		if(th != null){
			//アニメーション中断して前の状態を描ききる
			
		}
		th = new AnimationThread(fState, prevState != null ? prevState : fState, blockWidth, blockHeight, applet, img);
		th.start();
//		try {
//			th.join();
//		} catch (InterruptedException e) {
//			System.out.println("interrupt!!");
//		}
		prevState = fState;
//		Graphics g = applet.getGraphics();
//		if(g == null){
//			return;
//		}
//		//g.clearRect(0, 0, blockWidth * fState.width, blockHeight * fState.height);
//		
//		//最初やrepaint命令時は全部描画
//		if(repaint || prevState == null){
//			drawAllBlocks(fState, g);			
//		}else if(!fState.equals(prevState)){//差分があった場合に差分があったところのみ描画
//			drawUpdatedBlocks(fState, g);
//		}
//		prevState = fState;
	}

	private void drawAllBlocks(FieldState fState, Graphics g) {
		for(int y = 0; y < fState.height; y++){
			for(int x = 0; x < fState.width; x++){
				drawBlock(g, x, y, fState);
			}
		}
	}

	private void drawUpdatedBlocks(FieldState fState, Graphics g) {
		for(int y = 0; y < fState.height; y++){
			for(int x = 0; x < fState.width; x++){
				if(stateChanged(fState, x, y)){
					g.clearRect(x * blockWidth, y * blockHeight, blockWidth, blockHeight);
					drawBlock(g, x, y, fState);
				}
			}
		}
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
			g.drawImage(img[imgIndex], x * blockWidth, y * blockHeight, blockWidth, blockHeight, applet);
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
