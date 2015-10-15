package rpg;

import java.util.ArrayList;
import java.util.List;

public class Battle {
	private Player player;
	private List<Enemy> enemies;
	public Battle(Player p, Enemy... iniEnemies){
		player = p;
		enemies = new ArrayList<Enemy>();
		for(Enemy e : iniEnemies){
			enemies.add(e);
		}
	}
	
	public void playerTurn(CommandList c){
		//Command c = player.getCommand();
		int heal = 0;
		player.resetAttackInfo();
		for(Command cm : c.commands){
			if(cm instanceof AttackCommand){
				player.addAttackInfo((AttackCommand)cm);
			}else if(cm instanceof HealCommand){
				heal += (int)(player.getHealPower() * cm.power);
			}
		}
		for(AttackInfo ai : player.getAttackInfo()){
			Enemy target = enemies.get(getTarget(ai));
			target.damage(ai);
			//if(target.dead())
			//	enemies.remove(target);
		}	
		enemies.removeIf((enemy)->{return enemy.dead();});
		if(heal > 0)
			player.heal(heal);
		
	}
	
	private int getTarget(AttackInfo aInfo){
		return (int)(enemies.size() * Math.random());
	}
	
	public void enemyTurn(){
		for(Enemy e : enemies){
			if(e.nextTurn()){
				player.damage(e.getAttack());
			}
		}
	}
	
	public boolean won(){
		return enemies.size() == 0;
	}
	
	public boolean lose(){
		return player.dead();
	}
	
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("ƒvƒŒƒCƒ„[HP : " + player.getCurrentHP() + "/" + player.getMaxHP());
		sb.append("\nEnemies : \n");
		for(Enemy e : enemies){
			sb.append('\t');
			Monster m = e.getMonster();
			sb.append(m.name + "\n\t\t");
			sb.append("HP : " + e.getCurrentHP() + "\n\t\t");
			sb.append("‚ ‚Æ : " + e.getCurrentTurn() + "\n\n");
		}
		return sb.toString();
	}
}
