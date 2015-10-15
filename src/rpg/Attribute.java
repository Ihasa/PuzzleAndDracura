package rpg;

import java.util.HashMap;

public enum Attribute {
	FIRE, WOOD, WATER, LIGHT, DARK;
	private static HashMap<Attribute,Attribute> criticals;
	private static HashMap<Attribute, Attribute> tolerants;
	static{
		criticals = new HashMap<Attribute, Attribute>();
		criticals.put(Attribute.FIRE, Attribute.WATER);
		criticals.put(Attribute.WOOD, Attribute.FIRE);
		criticals.put(Attribute.WATER, Attribute.WOOD);
		criticals.put(Attribute.LIGHT, Attribute.DARK);
		criticals.put(Attribute.DARK, Attribute.LIGHT);
		
		
		tolerants = new HashMap<Attribute, Attribute>();
		tolerants.put(Attribute.FIRE, Attribute.WOOD);
		tolerants.put(Attribute.WOOD, Attribute.WATER);
		tolerants.put(Attribute.WATER, Attribute.FIRE);
	}
	
	public boolean isCriticalTo(Attribute att){
		return this == criticals.get(att);
	}
	
	public boolean isTolerantTo(Attribute att){
		if(att == Attribute.LIGHT || att == Attribute.DARK || 
			this ==Attribute.LIGHT || this == Attribute.DARK)
			return false;
		return att == tolerants.get(this);
	}
}
