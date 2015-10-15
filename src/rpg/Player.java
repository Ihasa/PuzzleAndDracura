package rpg;

import java.util.ArrayList;
import java.util.List;

//ダンジョンに潜るプレイヤー
public class Player {
	private Party party;
	private int hitPoint;
	private int maxHP;
	private int healPower;
	public Player(Party p){
		party = p;
		hitPoint = maxHP = calcMaxHP();
		healPower = 0;
		for(Ally ally : party.getMonsters()){
			if(ally != null){
				healPower += ally.getAbility().getHeal();
			}
		}
	}
	private int calcMaxHP(){
		int hp = 0;
		for(Ally ally : party.getMonsters()){
			if(ally != null){
				hp += ally.getAbility().getHP();
			}
		}
		return hp;
	}
	public int getMaxHP(){ return maxHP; }
	public int getCurrentHP(){ return hitPoint; }
	public int getHealPower(){ return healPower; }
	
	public void resetAttackInfo(){
		for(Ally ally : party.getMonsters()){
			if(ally != null){
				ally.resetPower();
			}
		}
	}
	public void addAttackInfo(AttackCommand cmd){
		Attribute att = cmd.attribute;
		for(Ally ally : party.getMonsters()){
			if(ally != null && ally.isAttributeOf(att)){
				ally.chargePower(cmd.power);
			}
		}
	}
	public AttackInfo[] getAttackInfo(){
		List<AttackInfo> info = new ArrayList<AttackInfo>();
		for(Ally ally : party.getMonsters()){
			if(ally != null && ally.charged()){
				info.add(ally.getAttackInfo());
			}
		}
		return info.toArray(new AttackInfo[0]);
	}
	public void damage(int d){
		hitPoint -= d;
	}
	public void heal(int h){
		hitPoint += h;
		if(hitPoint > maxHP)
			hitPoint = maxHP;
	}
	public boolean dead(){
		return hitPoint <= 0;
	}
	
}
