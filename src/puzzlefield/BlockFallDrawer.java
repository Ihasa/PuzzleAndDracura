package puzzlefield;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class BlockFallDrawer extends AppletFieldDrawer {
	public BlockFallDrawer(Applet app, int bWidth, int bHeight) {
		super(app, bWidth, bHeight);
	}

	@Override
	protected void drawAllBlocks(Graphics g, FieldState fState) {
		for(int y = 0; y < fState.height; y++){
			for(int x = 0; x < fState.width; x++){
				drawBlock(g, x, y, fState);
			}
		}
	}
	private void drawBlock(Graphics g, int x, int y, FieldState fState){
		BlockColor color = fState.get(x, y);
		Cursor cursor = fState.getCursor();
		if(cursor.selected && cursor.x == x && cursor.y == y){
			g.setColor(Color.MAGENTA);
			g.fillRect(x * blockWidth, y * blockHeight, blockWidth, blockHeight);
		}
		drawBlockImage(g,color,x * blockWidth,y * blockHeight,blockWidth,blockHeight);
	}

	private Animation th;
	@Override
	protected void drawUpdatedBlocks(Graphics g, FieldState current, FieldState prev, FieldIndex[] diff) {
		int[][] falls = createFallMap(prev);
		th = new Animation((frame, allFrame)->{
			float rate = (float)frame / allFrame;

			for(int x = 0; x < current.width; x++){
				for(int y = current.height - 1; y >= 0; y--){
					if(falls[y][x] > 0){
						g.clearRect(x * blockWidth, 0, blockWidth, blockHeight * (y + 1));
						break;
					}
				}
			}
			for(FieldIndex idx : diff){
				int fallHeight = (int)(rate * falls[idx.y][idx.x] * blockHeight);
				BlockColor color = current.get(idx.x,idx.y);
				drawBlockImage(g,color,idx.x * blockWidth, (int)(idx.y * blockHeight - fallHeight), blockWidth,blockHeight);
			}
		},600);
		th.start();
		try {
			th.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	protected FieldIndex[] getDiff(FieldState current, FieldState prev){
		ArrayList<FieldIndex> res = new ArrayList<FieldIndex>();
		for(int x = 0; x < current.width; x++){
			for(int y = current.height - 1; y >= 0; y--){
				if(prev.get(x, y) == BlockColor.EMPTY){
					for(int i = 0; i <= y; i++){
						res.add(new FieldIndex(x,i));
					}
					break;
				}
			}
		}
		return res.toArray(new FieldIndex[0]);
	}
	private int countFall(FieldState fState, int x, int y){
		int res = 0;
		for(int iy = y; iy >= 0; iy--){
			if(fState.get(x, iy) == BlockColor.EMPTY){
				res++;
			}else{
				break;
			}
		}
		return res;
	}
	private int[][] createFallMap(FieldState fState){
		int[][] res = new int[fState.height][fState.width];
		for(int x = 0; x < fState.width; x++){
			for(int y = fState.height - 1; y >= 0; y--){
				int iy = y - res[y][x];
				if(iy >= 0 && fState.get(x, iy) == BlockColor.EMPTY){
					int f = countFall(fState,x,iy);
					for(int iy2 = y; iy2 >= 0; iy2--){
						res[iy2][x] += f;
					}
				}
			}
		}
//			int fall = 0;
//			int from = fState.height - 1;
//			for(int y = fState.height - 1; y >= 0; y--){
//				BlockColor color = fState.get(x, y);
//				if(color == BlockColor.EMPTY){
//					fall++;
//				}else if(fall > 0){
//					for(int iy = from ; iy > y; iy--){
//						res[iy][x] = fall;
//					}
//					from = y;
//				}else{
//					from = y - 1;
//				}
//			}
//			if(fall > 0){
//				for(int iy = from; iy >= 0; iy--){
//					res[iy][x] = fall;
//				}
//			}
//		}
		for(int y = 0; y < fState.height; y++){
			for(int x = 0; x < fState.width; x++){
				System.out.print(res[y][x] + " ");
			}
			System.out.println();
		}
		return res;
	}
}
