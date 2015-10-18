package rpg;

public class Dangeon {
	private Battle[] battles;
	private int battleNum;
	private String name;
	public Dangeon(String name, Battle[] iniBattles){
		this.name = name;
		battles = iniBattles;
		battleNum = battles.length;
	}
	public Battle getBattle(int index){
		return battles[index];
	}
	public int getBattleNum(){ return battleNum; }
		
	public String toString(){
		return name + "(" + battleNum + "ƒoƒgƒ‹)";
	}
}
