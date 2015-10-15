package puzzlefield;
enum BlockColor{
	RED, GREEN, BLUE, YELLOW, BLACK, PINK, EMPTY;
	
	public static BlockColor valueOf(int val){
		BlockColor c;
		switch(val){
			case 0:
				c = BlockColor.RED;
				break;
			case 1:
				c = BlockColor.GREEN;
				break;
			case 2:
				c = BlockColor.BLUE;
				break;
			case 3:
				c = BlockColor.YELLOW;
				break;
			case 4:
				c = BlockColor.BLACK;
				break;
			case 5:
				c = BlockColor.PINK;
				break;
			default:
				c = BlockColor.EMPTY;
		}
		return c;
	}
	public static BlockColor getRandomColor(BlockColor... excepts){
		java.util.ArrayList<BlockColor> list = new java.util.ArrayList<BlockColor>();
		for(int i = 0; i < 6; i++){
			list.add(BlockColor.valueOf(i));
		}
		
		for(BlockColor ec : excepts){
			list.remove(ec);
		}
		return list.get((int)(list.size() * Math.random()));
	}
}
