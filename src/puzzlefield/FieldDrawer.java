package puzzlefield;
public class FieldDrawer{
	public void draw(FieldState fieldState){
		writeConsole(fieldState);
	}

	protected void writeConsole(FieldState fieldState) {
		//コンソール出力するのみ
		System.out.println("y\\x 〇一二三四五\n");
		for(int y = 0; y < fieldState.height; y++){
			System.out.print(y + "   ");
			for(int x = 0; x < fieldState.width; x++){
				char c;
				switch(fieldState.get(x, y)){
					case RED:
						c = '火';
						break;
					case GREEN:
						c = '木';
						break;
					case BLUE:
						c = '水';
						break;
					case YELLOW:
						c = '光';
						break;
					case BLACK:
						c = '闇';
						break;
					case PINK:
						c = '回';
						break;
					default:
						c = '＿';
				}
				System.out.print(c);
			}
			System.out.println("");
		}
		System.out.println("");
	}
}
