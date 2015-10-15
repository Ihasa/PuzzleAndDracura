package puzzlefield;
import java.util.ArrayList;

public class Field{
	private int width;
	private int height;
	private Block[][] blocks;
	private Cursor cursor;
	
	public Field(){ this(6, 5); }
	public Field(int w, int h){
		width = w;
		height = h;
		blocks = new Block[height][width];
		init();
		cursor = new Cursor();
	}
	private void init(){
		//初期状態ではつながらないようにする
		for(int y = 0; y < height; y++){
			for(int x = 0; x < width; x++){
				//blocks[y][x] = getRandomBlock();
				if(x < 2 && y < 2){
					blocks[y][x] = new Block(BlockColor.getRandomColor());
				}
				else{
					BlockColor colorUp = BlockColor.EMPTY;
					BlockColor colorLeft = BlockColor.EMPTY;

					if(y >= 2){
						colorUp = blocks[y-1][x].color;
					}

					if(x >= 2){
						colorLeft = blocks[y][x - 1].color;
					}
					blocks[y][x] = new Block(BlockColor.getRandomColor(colorUp, colorLeft));
				}
			}
		}
	}

	private Block getRandomBlock(){
		return new Block((int)(Math.random() * 6));
	}

	public FieldState getFieldState(){
		FieldState state = new FieldState(width, height, (Cursor)cursor.clone());
		for(int y = 0; y < height; y++){
			for(int x = 0; x < width; x++){
				state.set(blocks[y][x].color, x, y);
			}
		}
		return state;
	}

	private void checkEraceableH(int x, int y, int count){
		if(x == width - 1 || blocks[y][x].color != blocks[y][x + 1].color){
			if(count >= 3){
				for(int i = 0; i < count; i++){
					blocks[y][x - i].eraseable = true;
					//blocks[y][x - i].color = BlockColor.EMPTY;
				}
			}
			count = 1;
		}else if(blocks[y][x].color == blocks[y][x + 1].color){
			count++;
		}
		
		if(x < width - 1)
			checkEraceableH(x + 1, y, count);
	}
	private void checkEraceableV(int x, int y, int count){
		if(y == height - 1 || blocks[y][x].color != blocks[y + 1][x].color){
			if(count >= 3){
				for(int i = 0; i < count; i++){
					blocks[y - i][x].eraseable = true;
					//blocks[y][x - i].color = BlockColor.EMPTY;
				}
			}
			count = 1;
		}else if(blocks[y][x].color == blocks[y + 1][x].color){
			count++;
		}
		
		if(y < height - 1)
			checkEraceableV(x, y + 1, count);
	}

	public boolean checkEraceable(){
		//横に調べる
		for(int y = 0; y < height; y++){
			checkEraceableH(0, y, 1);
		}

		//縦に調べる
		for(int x = 0; x < width; x++){
			checkEraceableV(x, 0, 1);
		}
		
		return existsEraseable();
	}
	private boolean existsEraseable(){
		for(int y = 0; y < height; y++){
			for(int x = 0; x < width; x++){
				if(blocks[y][x].eraseable)
					return true;
			}
		}
		return false;
	}
	
	//つながってるブロックを削除
	public void eraseAll(){
		//まとめて消す
		for(int y = 0; y < height; y++){
			for(int x = 0; x < width; x++){
				if(blocks[y][x].eraseable){
					blocks[y][x].color = BlockColor.EMPTY;
					blocks[y][x].eraseable = false;
				}
			}
		}
	}
	
	private ErasedBlockInfo[] ebi;
	public void erase(){
		ArrayList<ErasedBlockInfo> result = new ArrayList<ErasedBlockInfo>();
		for(int y = 0; y < height; y++){
			for(int x = 0; x < width; x++){
				if(blocks[y][x].eraseable){
					ErasedBlockInfo ebi = new ErasedBlockInfo(blocks[y][x].color, 0);
					eraseSub(x,y,ebi);
					result.add(ebi);
				}
			}
		}
		ebi = result.toArray(new ErasedBlockInfo[0]);
	}
	
	private void eraseSub(int x, int y, ErasedBlockInfo ebi){
		blocks[y][x].eraseable = false;
		ebi.blockNum++;
		if(x < width - 1  && blocks[y][x+1].eraseable && blocks[y][x].color == blocks[y][x + 1].color){
			eraseSub(x+1,y,ebi);
		}
		if(x > 0 && blocks[y][x - 1].eraseable && blocks[y][x].color == blocks[y][x - 1].color){
			eraseSub(x-1,y,ebi);
		}
		if(y < height - 1 && blocks[y + 1][x].eraseable && blocks[y][x].color == blocks[y + 1][x].color){
			eraseSub(x,y+1,ebi);
		}
		if(y > 0 && blocks[y - 1][x].eraseable && blocks[y][x].color == blocks[y - 1][x].color){
			eraseSub(x,y-1,ebi);
		}
		
		blocks[y][x].color = BlockColor.EMPTY;
	}
	
	public int getCombo(){ return ebi.length; }
	public ErasedBlockInfo[] getEbi(){ return ebi; }
	
	//空白がある場合にブロックを落下させる
	public void drop(){
		for(int x = 0; x < width; x++){
			for(int y = 0; y < height - 1; y++){
				if(blocks[y + 1][x].color == BlockColor.EMPTY){
					for(int y2 = y + 1; y2 > 0; y2--){
						blocks[y2][x].color = blocks[y2-1][x].color;
						blocks[y2-1][x].color = BlockColor.EMPTY;
					}
				}
			}
		}
	}

	//空白をランダムで埋める
	public void fill(){
		for(int y = 0; y < height; y++){
			for(int x = 0; x < width; x++){
				if(blocks[y][x].color == BlockColor.EMPTY){
					blocks[y][x] = getRandomBlock();
				}
			}
		}
	}
	
	public boolean select(int x, int y){
		if(!cursor.selected && x >= 0 && x < width && y >= 0 && y < height){
			cursor.select(x, y);
			return true;
		}
		return false;
	}
	public boolean isSelected(){
		return cursor.selected;
	}
	public void release(){
		cursor.release();
	}
	
	private void swap(Block b1, Block b2){
		BlockColor c = b1.color;
		b1.color = b2.color;
		b2.color = c;
	}
	public void moveUp(){
		if(!cursor.selected || cursor.y == 0)
			return;
		
		swap(blocks[cursor.y][cursor.x], blocks[cursor.y - 1][cursor.x]);
		cursor.move(0, -1);
	}
	public void moveDown(){
		if(!cursor.selected ||cursor.y == height - 1)
			return;
		
		swap(blocks[cursor.y][cursor.x], blocks[cursor.y + 1][cursor.x]);
		cursor.move(0, 1);		
	}
	public void moveLeft(){
		if(!cursor.selected || cursor.x == 0)
			return;
		
		swap(blocks[cursor.y][cursor.x], blocks[cursor.y][cursor.x - 1]);
		cursor.move(-1, 0);
	}
	public void moveRight(){
		if(!cursor.selected || cursor.x == width - 1)
			return;
		
		swap(blocks[cursor.y][cursor.x], blocks[cursor.y][cursor.x + 1]);
		cursor.move(1, 0);
	}
	public int getCursorX(){return cursor.x;}
	public int getCursorY(){return cursor.y;}
	
	public void blockChange(BlockColor from, BlockColor to){
		if(from == to || from == BlockColor.EMPTY || to == BlockColor.EMPTY)
			return;
		for(int y = 0; y < height; y++){
			for(int x = 0; x < width; x++){
				if(blocks[y][x].color == from)
					blocks[y][x].color = to;
			}
		}
	}
	public void blockChange(BlockColor[] from, BlockColor to){
		if(from.length == 0 || to == BlockColor.EMPTY)
			return;
		
		for(int y = 0; y < height; y++){
			for(int x = 0; x < width; x++){
				for(BlockColor c : from){
					if(blocks[y][x].color == c){
						blocks[y][x].color = to;
						break;
					}
				}
			}
		}
	}
	public void blockChange(BlockColor[] from, BlockColor[] to){
		if(from.length == 0 || to.length == 0)
			return;
		
		for(int y = 0; y < height; y++){
			for(int x = 0; x < width; x++){
				for(BlockColor c : from){
					if(blocks[y][x].color == c){
						int idx = (int)(Math.random() * to.length);
						blocks[y][x].color = to[idx];
						break;
					}
				}
			}
		}
	}
}
