package puzzlefield;
public class FieldState{
	public int width;
	public int height;
	private BlockColor[][] field;
	private Cursor cursor;
	public FieldState(int w, int h,Cursor c){
		field = new BlockColor[h][w];
		width = w;
		height = h;
		cursor = c;
	}
	public void set(BlockColor c, int x, int y){
		field[y][x] = c;
	}
	public BlockColor get(int x, int y){
		return field[y][x];
	}
	public Cursor getCursor(){
		return cursor;
	}
}
