package rpg;

import java.util.Scanner;
public class RPG {
	public static void main(String[] args){
		Monster[] monsters = new Monster[]{
			new Monster("�����C���i�ӂ��j", Attribute.FIRE),
			new Monster("�����C���i�󕠁j", Attribute.WOOD),
			new Monster("�����C���i��Áj", Attribute.WATER),
			new Monster("�C���Ȃ�Ƃ�����", Attribute.LIGHT),
			new Monster("�h���Ȃ�Ƃ�����", Attribute.DARK),
			new Monster("�z���X��y", Attribute.FIRE),
				
			new Monster("�ق̂��", Attribute.FIRE),
			new Monster("������", Attribute.WOOD),
			new Monster("������", Attribute.WATER),
			new Monster("�Ђ����", Attribute.LIGHT),
			new Monster("�łт��", Attribute.DARK),
						
			new Monster("�ł��ق̂��", Attribute.FIRE),
			new Monster("�E�b�h�f�r��", Attribute.WOOD),
			new Monster("�u���[�S�[����", Attribute.WATER)
		};
		
		EnemyAbility weakEnemy = new EnemyAbility(250, 100, 0, 4);
		EnemyAbility normalEnemy = new EnemyAbility(500, 200, 30, 3);
		EnemyAbility rapidEnemy = new EnemyAbility(400, 120, 20, 1);
		EnemyAbility strongEnemy = new EnemyAbility(1500, 500, 100, 6);
		
		AllyAbility balance = new AllyAbility(300, 200, 100);
		AllyAbility attack = new AllyAbility(200, 300, 80);
		AllyAbility hp = new AllyAbility(400, 250, 5);
		AllyAbility heal = new AllyAbility(150, 150, 250);
		AllyAbility[] abilities = new AllyAbility[] {balance, attack, hp, heal};
		
		Party party = new Party();
		for(int i = 0; i < 6; i++){
			party.setMonster(new Ally(monsters[i], abilities[i % 4]), i);
		}
		Player player = new Player(party);

		
		Enemy[] weaks = new Enemy[5];
		for(int i = 0; i < 5; i++){
			weaks[i] = new Enemy(monsters[i + 6], weakEnemy);
		}
		Enemy boss1 = new Enemy(monsters[11], normalEnemy);
		Enemy boss2 = new Enemy(monsters[12], rapidEnemy);
		Enemy boss3 = new Enemy(monsters[13], strongEnemy);
				
		Battle[] battle = new Battle[]{
			new Battle(player, weaks[0]),
			new Battle(player, weaks[1], weaks[3]),
			new Battle(player, weaks[2], weaks[4]),
			new Battle(player, boss1, boss2),
			new Battle(player, boss3)
		};
		Dangeon dangeon = new Dangeon("�e�X�g�_���W����", battle);
		int battleIndex = 0;
		int battleNum = dangeon.getBattleNum();
		Battle currentBattle = dangeon.getBattle(0);
		System.out.println(dangeon.toString());
		
		Scanner s = new Scanner(System.in);
		while(true){
			System.out.println(currentBattle.toString());
			CommandList command = getCommand(s.nextLine());
			currentBattle.playerTurn(command);
			if(currentBattle.won()){
				battleIndex++;
				if(battleIndex == battleNum){
					System.out.println("YOU WIN!!");
					break;
				}else{
					System.out.println("Battle " + battleIndex + "/" 
							+battleNum + "clear!");
				
					currentBattle = dangeon.getBattle(battleIndex);
				}
			}else{
				currentBattle.enemyTurn();
				if(currentBattle.lose()){
					System.out.println("YOU LOSE...");
					break;
				}
			}
		}
		s.close();
	}
	static CommandList getCommand(String str){
		CommandList commands = new CommandList();
		String[] commandStr = str.split(" ");
		for(int i = 0; i+1 < commandStr.length; i+=2){
			String attStr = commandStr[i];
			String powerStr = commandStr[i + 1];
			float power = Float.parseFloat(powerStr);
			
			Command cmd;
			switch(attStr){
			case "fire":
				cmd = new AttackCommand(Attribute.FIRE, power);
				break;
			case "wood":
				cmd = new AttackCommand(Attribute.WOOD, power);
				break;
			case "water":
				cmd = new AttackCommand(Attribute.WATER, power);
				break;
			case "light":
				cmd = new AttackCommand(Attribute.LIGHT, power);
				break;
			case "dark":
				cmd = new AttackCommand(Attribute.DARK, power);
				break;
			case "heal":
				cmd = new HealCommand(power);
				break;
			default:
				cmd = new EmptyCommand();
			}
			commands.add(cmd);
		}
		return commands;
	}
}
