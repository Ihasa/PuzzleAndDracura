package rpg;

public class EnemyAbility {
	private int hitPoint;
	private int attack;
	private int defence;
	private int attackTurn;
	public EnemyAbility(int hp, int att, int def, int turn){
		hitPoint = hp;
		attack = att;
		defence = def;
		attackTurn = turn;
	}
	public int getHP(){ return hitPoint; }
	public int getAttack(){ return attack; }
	public int getDefence(){ return defence; }
	public int getAttackTurn(){ return attackTurn; }
}
