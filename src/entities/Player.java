package entities;

import java.util.ArrayList;
import java.util.List;

import model.Consumable;
import model.Inventory;
import model.Skill;
import model.Skill.SkillType;

public class Player extends Entity{
	public enum PlayerClass {
		WARRIOR,MAGE,ASSASSIN,RANGER
	}
	
	
	
	private PlayerClass pClass;
	private Inventory inventory;
	private int level;
	private int xp;
	private int xpToNextLevel;
	private int mana;
	private int maxMana;
	private int goldAmmount;
	private List<Skill> skills;
	
	public Player(String  n, PlayerClass pc) {
		super(n,0,0,0);
		
		this.level = 1;
		this.xp = 0;
		this.xpToNextLevel = 100;
		this.maxMana = 20;
		this.mana = maxMana;
		
		this.pClass = pc;		
		this.inventory = new Inventory();
		this.skills = new ArrayList<>();
		unlockStartingSkills();
		assignStats();
	}
	
	public void setPlayerClass(PlayerClass pc) {
		this.pClass = pc;
	}
	
	public PlayerClass getPlayerClass() {
		return pClass;
	}
	
	public int getCurrentXP() {
		return xp;
	}
	
	public int getXPToNextLevel() {
		return xpToNextLevel;
	}
	
	public void gainXp(int number) {
		xp += number;
		while (xp > xpToNextLevel) {
			xp -= xpToNextLevel;
			levelUp();
		}
	}
	
	public int getGold() {
		return goldAmmount;
	}
	
	public void addGold(int ammount) {
		this.goldAmmount += ammount;
	}
	
	public int getCurrentMana() {
		return mana;
	}
	public int getMaxMana() {
		return maxMana;
	}
	
	private void levelUp() {
		level++;
		xpToNextLevel += 50;
		
		maxHP += 10;
		currentHP = maxHP;
		
		maxMana += 5;
		mana = maxMana;
		
		System.out.println("You leveled up to level " + level + "!");
		unlockSkillsForLevel();
	}
	
	
	private void unlockStartingSkills() {
		switch(pClass) {
		case WARRIOR:
			skills.add(new Skill("Heavy Strike","Wind up a powerful hit, dealing moderate damage.", 5 , 1 , 8, 1, SkillType.DAMAGE));
			break;
		
		case MAGE:
			skills.add(new Skill("FireBall", "Cast a simple fire spell, dealing moderate damage", 5, 1, 10, 1, SkillType.DAMAGE));
			break;
		
		case ASSASSIN:
			skills.add(new Skill("Flash Strike", "Perform a fast attack, dealing moderate damage.", 5, 1, 4, 1, SkillType.DAMAGE));
			break;
		case RANGER:
			skills.add(new Skill("Pierceing Shot", "You notch up a focused shot, dealing moderate damage.", 5, 1, 6, 1, SkillType.DAMAGE));
			break;
		}
	}
	
	
	private void unlockSkillsForLevel() {
		switch (pClass) {
		case ASSASSIN:
			unlockSkillsIfEligible(new Skill("Poison Shiv",
					"A shiv strike coated in poison. Chance to do poison damage.",
					10, 3, 5, 1.2, SkillType.POISONSHIV));
			
			unlockSkillsIfEligible(new Skill("Execute",
					"Opportunity attack. If the enemy is low enough (30%), it executes them directly.",
					15, 5, 25, 0.8, SkillType.EXECUTE));
			break;
			
		case WARRIOR:
			unlockSkillsIfEligible(new Skill("Shield Bash",
					"Bash the enemy with your shield, increase defense by 25% for one turn.",
					8, 3, 8, 1, SkillType.SHIELDBASH));
			
			unlockSkillsIfEligible(new Skill("Enrage",
					"Your feral instinct kicks in, you deal  immense damage, but are more vulnerable to attacks.",
					15, 5, 30, 1.5, SkillType.ENRAGE));
			break;
			
		case MAGE:
			unlockSkillsIfEligible(new Skill("Heal Spell",
					"Your radiance spell helps close your wounds.",
					10, 3, 15, 1.2, SkillType.HEALSPELL));
			
			unlockSkillsIfEligible(new Skill("Lightning Storm",
					"You cast a storm of lightning, has a chance to decrease enemy attack power.",
					15, 5, 19, 1.2, SkillType.LIGHTNINGSTORM));
			
			break;
			
		case RANGER:
			unlockSkillsIfEligible(new Skill(
			        "Marked Shot",
			        "Mark the enemy, making your next attacks deal increased damage.",
			        10, 3, 8, 1.0, SkillType.MARKEDSHOT));

			unlockSkillsIfEligible(new Skill(
			        "Trap",
			        "Set a trap that damages and has a chance to stop the enemy's next attack.",
			        15, 5, 15, 1, SkillType.TRAP));
			break;    
		}
		
	}
	
	private void unlockSkillsIfEligible(Skill skill) {
		if (level >= skill.getRequiredLevel() && !hasSkill(skill.getName())) {
			skills.add(skill);
			System.out.println("You unlocked a new skill: " + skill.getName());
		}
	}
	
	private boolean hasSkill(String SkillName) {
		for (Skill skill : skills) {
			if (skill.getName().equals(SkillName)) {
				return true;
			}
		}
		return false;
	}
	
	public void setAttack(int attack) {
		this.attack = attack;
	}
	
	public void setDefense(int defense) {
		this.defense = defense;
	}
	
	
	public void spendGold(int goldCost) {
		goldAmmount -= goldCost;
	}
	public void spendMana(int manaCost) {
		mana -= manaCost;
	}
	
	
	public void assignStats() {
		switch (pClass){
		
		
		case WARRIOR:
				maxHP = 100;
				currentHP = maxHP;
				attack = 20;
				defense = 10;
				mana = 10;
				break;
		
		case MAGE:
			maxHP = 75;
			currentHP = maxHP;
			attack = 10;
			defense = 5;
			mana = 20;
			break;
			
		case ASSASSIN:
			maxHP = 80;
			currentHP = maxHP;
			attack = 15;
			defense = 5;
			mana = 15;
			break;
		
		case RANGER:
			maxHP = 90;
			currentHP = maxHP;
			attack = 15;
			defense = 15;
			mana = 15;
			break;
				
		}
																					
		}
	
	public Inventory getInventory() {
		return inventory;
	}
	
	public void useConsumable(Consumable item) {
		
		switch (item.getType()) {
		case HEALTH:
			heal(item.getAmount());
			System.out.println("Healed for " + item.getAmount());
			System.out.println("Player HP: ( " + getCurrentHP() + "/" + getHP() +" )");
			break;
		
		case MANA:
			restoreMana(item.getAmount());
			System.out.println("Restored " + item.getAmount() + " mana");
			System.out.println("Player Mana: ( " + getCurrentMana() + "/" + getMaxMana() +" )");
			break;
		}
		
		inventory.removeItem(item);
	}
	
	
	public void restoreMana(int ammount) {
		mana = mana + ammount;
		if (mana > maxMana) {
			mana = maxMana;
		}
	}
	
	public List<Skill> getSkills(){
		return skills;
	}
	
	public void showSkills() {
		if (skills.isEmpty()) {
			System.out.println("No skills available.");
			return;
		}else {
			for (int i = 0; i < skills.size(); i++) {
			System.out.println((i+1) + " ." + skills.get(i).getName() + " - " + skills.get(i).getDescription());
			}
		}
		
	}
		
}
