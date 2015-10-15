package rpg;

public class AttackCommand extends Command{
	public Attribute attribute;
	public AttackCommand(Attribute att, float pow){
		super(pow);
		attribute = att;
	}
}
