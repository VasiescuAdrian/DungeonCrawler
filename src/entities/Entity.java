package entities;

import java.util.ArrayList;
import java.util.List;

import combat.StatusEffect;

public class Entity {
	protected String name;
	protected int maxHP;
	protected int currentHP;
	protected int attack;
	protected int defense;
	private List<StatusEffect> effects = new ArrayList<>();
	
	public Entity(String  n, int mhp,int at, int def) {
		this.name = n;
		this.maxHP = mhp;
		this.currentHP = mhp;
		this.attack = at;
		this.defense = def;
	}
	
	
	public int getAttack() {
		return attack;
	}
	public int getCurrentHP() {
		return currentHP;
	}
	public int getDefense() {
		return defense;
	}
	
	public String getName() {
		return name;
	}
	
	public int getHP() {
		return maxHP;
	}
	

	public void addStatusEffect(StatusEffect status) {
		effects.add(status);
	}
	
	public List<StatusEffect> getStatusEffects(){
		return effects;
	}
	
	public int takeDamage(int dmg) {
		int damageTaken = Math.min(currentHP, dmg);
		currentHP -= damageTaken;
		return damageTaken;
		
		
	}
	
	public boolean isAlive() {
		return currentHP > 0;
	}
	
	public void heal(int heal) {
		currentHP = currentHP + heal;
		if (currentHP > maxHP) {
			currentHP = maxHP;
		}
	}
	
	
	
	
	
	
}
