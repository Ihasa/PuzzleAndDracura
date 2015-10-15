package rpg;

//敵モンスター
public class Enemy {
	private Monster monster;
	private EnemyAbility ability;
	private int currentTurn;
	private int currentHP;
	public Enemy(Monster m, EnemyAbility abi){
		monster = m;
		ability = abi;
		currentTurn = abi.getAttackTurn();
		currentHP = ability.getHP();
	}
	public Monster getMonster(){ return monster; }
	public int getAttack(){ return ability.getAttack(); }
	public int getCurrentTurn(){ return currentTurn; }
	public int getCurrentHP(){ return currentHP; }
	
	public void damage(AttackInfo aInfo){
		int dmg = aInfo.damage;
		//属性の組み合わせでダメージ変化
		Attribute attackAtt = aInfo.attribute;
		Attribute enemyAtt = monster.attribute;
		if(attackAtt.isCriticalTo(enemyAtt)){
			dmg *= 2;
		}else if(enemyAtt.isTolerantTo(attackAtt)){
			dmg /= 2;
		}
		dmg -= ability.getDefence();
		if(dmg <= 0){
			dmg = 1;
		}
		currentHP -= dmg;
	}
	
	public boolean dead(){
		return currentHP <= 0;
	}
	public boolean nextTurn(){
		currentTurn--;
		if(currentTurn == 0){
			currentTurn = ability.getAttackTurn();
			return true;
		}
		return false;
	}
}
