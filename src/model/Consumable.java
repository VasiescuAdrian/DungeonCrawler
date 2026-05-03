package model;

public class Consumable extends Item {
	public enum ConsumableType{
		HEALTH, MANA
	}
	
	private ConsumableType type;
	private int ammount;

	
	public Consumable(String n, String d,  int ammount ,ConsumableType type ) {
		super(n,d);		
		this.ammount = ammount;
		this.type = type;
		
	}
	
	
	public ConsumableType getType() {
        return type;
    }
	
	
	public int getAmount() {
		return ammount;
	}
	
}
