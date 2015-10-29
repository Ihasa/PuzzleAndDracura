package puzzlefield;
import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

public abstract class AppletFieldDrawer{
	protected static Image[] img;
	protected int blockWidth;
	protected int blockHeight;
	protected Applet applet;
	
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
			
	//事後条件：描画後のフィールドがfStateに基づいたものになっていること
	private FieldState prevState = null;
	public final void draw(FieldState fState, boolean repaint){
//		//super.draw(fState);
//		if(th != null){
//			//アニメーション中断して前の状態を描ききる
//			
//		}
//		th = new AnimationThread(fState, prevState != null ? prevState : fState, blockWidth, blockHeight, applet, img);
//		th.start();
//		try {
//			th.join();
//		} catch (InterruptedException e) {
//			System.out.println("interrupt!!");
//		}
		Graphics g = applet.getGraphics();
		if(g == null){
			return;
		}
		//g.clearRect(0, 0, blockWidth * fState.width, blockHeight * fState.height);
		
		//最初やrepaint命令時は全部描画
		if(repaint || prevState == null){
			drawAllBlocks(g, fState);
		}else{
			FieldIndex[] diff = fState.getDiff(prevState);
			if(diff.length > 0){//差分があった場合に差分があったところのみ描画
				drawUpdatedBlocks(g, fState, prevState, diff);
			}
		}
		prevState = fState;
	}
	abstract protected void drawAllBlocks(Graphics g, FieldState current);
	abstract protected void drawUpdatedBlocks(Graphics g, FieldState current, FieldState prev, FieldIndex[] diff);
	
	protected void drawBlockImage(Graphics g, BlockColor color, int x, int y, int width, int height){
		if(color == BlockColor.EMPTY)
			return;
		int imgIndex = color.ordinal();
		g.drawImage(img[imgIndex], x, y, width, height, applet);		
	}
}
