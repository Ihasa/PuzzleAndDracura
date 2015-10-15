package rpg;

//‚¨’‡ŠÔƒ‚ƒ“ƒXƒ^[
public class Ally {
	private Monster monster;
	private AllyAbility ability;
	private int currentAttack;
	public Ally(Monster m, AllyAbility abi){
		monster = m;
		ability = abi;
		currentAttack = 0;
	}
	public Monster getMonster(){ return monster; }
	public AllyAbility getAbility(){ return ability; }
	public LeaderSkill getLeaderSkill(){
		return ability.getLeaderSkill();
	}
	public void resetPower(){
		currentAttack = 0;
	}
	public void chargePower(float power){
		currentAttack += (int)(power * ability.getAttack());
	}
	public AttackInfo getAttackInfo(){
		return new AttackInfo(monster.attribute, currentAttack);
	}
	public boolean isAttributeOf(Attribute att){
		return monster.attribute == att;
	}
	public boolean charged(){ return currentAttack > 0; }
}
