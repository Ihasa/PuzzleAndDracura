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
	public boolean equals(Object obj){
		if(obj instanceof FieldState)
			return false;
		FieldState ope = (FieldState)obj;
		for(int y = 0; y < height; y++){
			for(int x = 0; x < width; x++){
				if(ope.field[y][x] != field[y][x])
					return false;
			}
		}
		return true;
	}
}
