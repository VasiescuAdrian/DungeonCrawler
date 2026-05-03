package model;


import entities.Entity;


public class Skill {
	public enum SkillType{
			DAMAGE,POISONSHIV,EXECUTE,SHIELDBASH,ENRAGE,HEALSPELL,LIGHTNINGSTORM,MARKEDSHOT,TRAP,WEAKEN,BONEPRISON
		}
		
	
	
	private String name;
	private String description;
	private int manaCost;
	private int requiredLevel;
	private int baseDamage;
	private double scaling;
	private SkillType type;
	
	
	public Skill(String n, String d, int mc, int rl, int bdmg, double sc, SkillType t) {
		
		
		this.name = n;
		this.description = d;
		this.manaCost = mc;
		this.requiredLevel = rl;
		this.baseDamage = bdmg;
		this.scaling = sc;
		this.type = t;
	}
	
	public String getName() {
		return name;
	}
	public String getDescription() {
		return description;
	}
	public int getManaCost() {
		return manaCost;
	}
	public int getRequiredLevel() {
		return requiredLevel;
	}
	public int getBaseDamage() {
		return baseDamage;
	}
	public double getScaling() {
		return scaling;
	}
	public SkillType getType() {
		return type;
	}
	
	public int calculateDamage(Entity attacker, Entity target) {
		int rawDamage = (int) Math.round(baseDamage + attacker.getAttack() * scaling);
		int finalDamage = rawDamage - target.getDefense();
		
		return  Math.max(1, finalDamage);
	}
	
	
}
