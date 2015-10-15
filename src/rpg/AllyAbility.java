package rpg;

public class AllyAbility {
	private int hitPoint;
	private int attack;
	private int heal;
	private Skill skill;
	private LeaderSkill leaderSkill;
	
	public AllyAbility(int hp, int atta, int hea){
		this(hp,atta,hea,new Skill(), new LeaderSkill());
	}
	public AllyAbility(int hp, int atta, int hea, Skill ski, LeaderSkill lSki){
		hitPoint = hp;
		attack = atta;
		heal = hea;
		skill = ski;
		leaderSkill = lSki;
	}
	public int getHP(){ return hitPoint; }
	public int getAttack(){ return attack; }
	public int getHeal(){ return heal; }
	public Skill getSkill(){ return skill; }
	public LeaderSkill getLeaderSkill(){ return leaderSkill; }
}
