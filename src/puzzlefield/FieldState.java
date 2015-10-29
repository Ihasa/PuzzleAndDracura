package puzzlefield;

import java.util.ArrayList;
import java.util.List;

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
	public FieldIndex[] getDiff(FieldState f2){
		List<FieldIndex> res = new ArrayList<FieldIndex>();
		
		for(int y = 0; y < height; y++){
			for(int x = 0; x < width; x++){
				if(stateChanged(f2, x, y)){
					res.add(new FieldIndex(x,y));
				}
			}
		}
		
		return res.toArray(new FieldIndex[0]);
	}
	private boolean stateChanged(FieldState state2, int x, int y){
		Cursor c1 = getCursor();
		Cursor c2 = state2.getCursor();
		return get(x, y) != state2.get(x, y) || 
				(c1.x == x && c1.y == y && (c2.x != x || c2.y != y)) ||
				(c2.x == x && c2.y == y && (c1.x != x || c1.y != y));
	}
}
