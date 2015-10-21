package puzzlefield;

import java.awt.Graphics;
import java.awt.Panel;

public class StringDrawer {
	private Panel panel;
	private int x;
	private int y;
	public StringDrawer(Panel p, int ix, int iy){
		panel = p;
		x = ix;
		y = iy;
	}
	public void draw(String str){
		Graphics g = panel.getGraphics();
		g.clearRect(x, y, 10 * str.length(), 10);
		g.drawString(str, x, y + 10);
	}
}
