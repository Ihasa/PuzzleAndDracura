package puzzlefield;
import java.util.Scanner;

public class PuzzleConsole{
	static void update(Field f, FieldDrawer drawer){
		int combo = 0;
		while(f.checkEraceable()){
			f.erase();
			ErasedBlockInfo[] info = f.getEbi();
			combo += f.getCombo();
			for(ErasedBlockInfo ebi : info){
				System.out.println("(" + ebi.color + " * " + ebi.blockNum + ")");
			}
			System.out.println(combo + " Combo!!");
			drawer.draw(f.getFieldState());
			f.drop();
			f.fill();
			drawer.draw(f.getFieldState());
		}
	}
	public static void main(String[] args){		
		Field f = new Field();
		FieldDrawer fDrawer = new FieldDrawer();
		fDrawer.draw(f.getFieldState());
		try{
			Scanner s = new Scanner(System.in);
			String str;
			while(true){
				System.out.println("select index...");
				str = s.nextLine();
				String[] arg = str.split(" ");
				int x = Integer.parseInt(arg[0]);
				int y = Integer.parseInt(arg[1]);
				if(!f.select(x,y))
					continue;
				System.out.println("input commands...");
				System.out.println("move    : l,r,u,d");
				System.out.println("release : e");
				while(!(str = s.nextLine()).equals("e")){
					arg = str.split(" ");
					for(String cmd : arg){
						switch(cmd){
						case "l":
							f.moveLeft();
							break;
						case "r":
							f.moveRight();
							break;
						case "u":
							f.moveUp();
							break;
						case "d":
							f.moveDown();
							break;
						}
					}
					fDrawer.draw(f.getFieldState());
					System.out.println("current : " + f.getCursorX() + ", " + f.getCursorY());
				}
				f.release();
				update(f, fDrawer);
			}
		}catch(Exception e){
			
		}
	}
}

