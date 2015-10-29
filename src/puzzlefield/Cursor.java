package puzzlefield;

public class Cursor implements Cloneable{
	public int x = -1;
	public int y = -1;
	public boolean selected = false;
	public void move(int dx, int dy){
		x += dx;
		y += dy;
	}
	public void select(int sx, int sy){
		x = sx;
		y = sy;
		selected = true;
	}
	public void release(){
		selected = false;
		x = -1;
		y = -1;
	}
	public Object clone(){
		Cursor c = new Cursor();
		c.x = x;
		c.y = y;
		c.selected = selected;
		return c;
	}
}
