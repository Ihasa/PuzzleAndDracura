package puzzlefield;
public class FieldDrawer{
	public void draw(FieldState fieldState){
		writeConsole(fieldState);
	}

	protected void writeConsole(FieldState fieldState) {
		//�R���\�[���o�͂���̂�
		System.out.println("y\\x �Z���O�l��\n");
		for(int y = 0; y < fieldState.height; y++){
			System.out.print(y + "   ");
			for(int x = 0; x < fieldState.width; x++){
				char c;
				switch(fieldState.get(x, y)){
					case RED:
						c = '��';
						break;
					case GREEN:
						c = '��';
						break;
					case BLUE:
						c = '��';
						break;
					case YELLOW:
						c = '��';
						break;
					case BLACK:
						c = '��';
						break;
					case PINK:
						c = '��';
						break;
					default:
						c = '�Q';
				}
				System.out.print(c);
			}
			System.out.println("");
		}
		System.out.println("");
	}
}
