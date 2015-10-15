package rpg;

//敵・味方関係なく共通したモンスターの情報
public class Monster {
	public Attribute attribute;
	public String name;
	//public Image image;
	public Monster(String name, Attribute attribute){
		this.name = name;
		this.attribute = attribute;
		//this.image = image;
	}
}
