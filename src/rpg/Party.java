package rpg;

public class Party {
	private Ally[] monsters;
	private static final int MONSTER_MAX = 6;
	
	public Party(){
		monsters = new Ally[MONSTER_MAX];
	}
	
	public Ally[] getMonsters(){
		return monsters;
	}
	public Ally getMonster(int idx){
		if(idx < 0 || idx > MONSTER_MAX - 1)
			return null;
		return monsters[idx];
	}
	
	public void setMonster(Ally m, int idx){
		if(idx < 0 || idx > MONSTER_MAX - 1)
			return;
		monsters[idx] = m;
	}
	
	public int getMaxHP(){
		int hp = 0;
		for(Ally ally : monsters){
			if(ally != null){
				hp += ally.getAbility().getHP();
			}
		}
		return hp;
	}
	
	public LeaderSkill getLeaderSkill(){
		return monsters[0].getLeaderSkill();
	}
	
	public LeaderSkill getFriendSkill(){
		return monsters[MONSTER_MAX - 1].getLeaderSkill();
	}
}
