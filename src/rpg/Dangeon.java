package rpg;

public class Dangeon {
	private Battle[] battles;
	private int battleNum;
	private Battle currentBattle;
	private int currentBattleIndex;
	
	public Dangeon(Battle[] iniBattles){
		battles = iniBattles;
		currentBattle = battles[0];
		currentBattleIndex = 0;
		battleNum = battles.length;
	}
	
	public int getCurrentBattleIndex(){ return currentBattleIndex; }
	public int getBattleNum(){ return battleNum; }
	
	public void playerTurn(CommandList command){
		currentBattle.playerTurn(command);
	}
	
	public void enemyTurn(){
		currentBattle.enemyTurn();
	}
	
	public boolean wonBattle(){
		return currentBattle.won();
	}
	public boolean lose(){
		return currentBattle.lose();
	}
	public void nextBattle(){
		currentBattleIndex++;
		currentBattle = battles[currentBattleIndex];
	}
	
	public boolean cleared(){
		return (currentBattleIndex == battleNum - 1) && 
				currentBattle.won();
	}
	
	public String toString(){
		String str = "Battle " + (currentBattleIndex + 1) + "/" + battleNum + "\n";
		return str + currentBattle.toString();
	}
}
