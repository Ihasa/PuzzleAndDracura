package puzzlefield;
import java.applet.Applet;
import java.awt.Graphics;

public class StringDrawer {
	private Applet applet;
	private int x;
	private int y;
	public StringDrawer(Applet app, int ix, int iy){
		applet = app;
		x = ix;
		y = iy;
	}
	public void draw(String str){
		Graphics g = applet.getGraphics();
		g.clearRect(x, y, 10 * str.length(), 10);
		g.drawString(str, x, y + 10);
	}
}
