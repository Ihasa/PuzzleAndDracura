package puzzlefield;
public class Block{
	public BlockColor color;
	public boolean eraseable = false;
	public Block(int ci){
		this(BlockColor.valueOf(ci));
	}
	public Block(BlockColor c){
		color = c;
	}
}
