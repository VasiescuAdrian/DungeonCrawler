package combat;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import entities.Enemy;
import model.Skill;
import model.Skill.SkillType;

public class EnemyFactory {
	private Random random;
	
	public enum BossType{
		BONE_KING
	}
	
	public EnemyFactory() {
		this.random = new Random();
		
	}
	
	private int generateXpValue(int floorLevel) {
		int minXpValue = 5 + (floorLevel -1) * 10;
		int maxXpValue = 10 + (floorLevel -1) * 20;
		
		return minXpValue + random.nextInt(maxXpValue - minXpValue + 1);
	}
	
	private int generateGoldValue(int floorLevel) {
		int minGold = 4 + (floorLevel -1) * 10;
		int maxGold = 15 + (floorLevel - 1) * 10;
		
		return minGold + random.nextInt(maxGold - minGold + 1);
	}
	
	
	public Enemy createBoneKing(int floorLevel) {

	    Enemy boss = new Enemy(
	        "Bone King",
	        scaleHp(100, floorLevel),
	        scaleAttack(20, floorLevel),
	        scaleDefense(10, floorLevel),
	        generateXpValue(floorLevel) * 6,
	        generateGoldValue(floorLevel) * 4
	    );
	    boss.setBoss(true);

	    List<Skill> phase1 = new ArrayList<>();
	    phase1.add(new Skill("Bone Pierce","...", 0, 1, 17, 1.2, SkillType.DAMAGE));
	    phase1.add(new Skill("Curse", "...", 0, 1, 0, 1, SkillType.WEAKEN));
	    phase1.add(new Skill("Bone Prison", "...", 0, 1, 0, 1, SkillType.BONEPRISON));

	    List<Skill> phase2 = new ArrayList<>();
	    phase2.add(new Skill("Curse", "...", 0, 1, 0, 1, SkillType.WEAKEN));
	    phase2.add(new Skill("Bone Crush", "...", 0, 1, 25, 1.4, SkillType.DAMAGE));
	    phase2.add(new Skill("Death Slam", "...", 0, 1, 40, 1.8, SkillType.DAMAGE));

	    boss.setPhase1Skills(phase1);
	    boss.setPhase2Skills(phase2);

	    return boss;
	}
	
	
	
	
	
	
	public Enemy createEnemy(String type, int floorLevel) {
		switch (type) {
		case "Carnivorous Plant":
			return new Enemy(
					"Carnivorous Plant",
					scaleHp(25,floorLevel),
					scaleAttack(8, floorLevel),
					scaleDefense(10, floorLevel),
					generateXpValue(floorLevel),
					generateGoldValue(floorLevel)
					);
			
		case "Ghoul":
			return new Enemy(
					"Ghoul",
					scaleHp(30,floorLevel),
					scaleAttack(10, floorLevel),
					scaleDefense(5, floorLevel),
					generateXpValue(floorLevel),
					generateGoldValue(floorLevel)
					
					);
			
		case "Mimic":
			return new Enemy(
					"Mimic",
					scaleHp(25,floorLevel),
					scaleAttack(15,floorLevel),
					scaleDefense(7,floorLevel),
					generateXpValue(floorLevel),
					generateGoldValue(floorLevel)
					);
			
		case "Ogre":
			return new Enemy(
					"Ogre",
					scaleHp(35,floorLevel),
					scaleAttack(20,floorLevel),
					scaleDefense(8,floorLevel),
					generateXpValue(floorLevel),
					generateGoldValue(floorLevel)
					);
		case "Evil Spirit":
			return new Enemy(
					"Evil Spirit",
					scaleHp(20,floorLevel),
					scaleAttack(25,floorLevel),
					scaleDefense(5,floorLevel),
					generateXpValue(floorLevel),
					generateGoldValue(floorLevel)
					);
		case "Disturbed Skeleton":
			return new Enemy(
					"Disturbed Skeleton",
					scaleHp(30,floorLevel),
					scaleAttack(15,floorLevel),
					scaleDefense(9,floorLevel),
					generateXpValue(floorLevel),
					generateGoldValue(floorLevel)
					);
		
		default:
			return new Enemy(
					"Unknown Creature",
					scaleHp(25,floorLevel),
					scaleAttack(10,floorLevel),
					scaleDefense(6,floorLevel),
					generateXpValue(floorLevel),
					generateGoldValue(floorLevel)
					);
		}
	}
	
	
	
	private int randomBetween(int min, int max) {
		return min + random.nextInt(max - min + 1);
	}
	
	
	private int scaleHp(int baseHP, int floorLevel) {
		int minHpValue = baseHP + (floorLevel -1) * 10;
		int maxHpValue = baseHP + (floorLevel -1) * 20;
		
		return randomBetween(minHpValue,maxHpValue);
	}
	
	private int scaleAttack(int baseAttack, int floorLevel) {
		int minAttack = baseAttack + (floorLevel -1) *3;
		int maxAttack = baseAttack + (floorLevel -1) *6;
		
		return randomBetween(minAttack, maxAttack);
	}
	
	private int scaleDefense(int baseDefense, int floorLevel) {
		int minDefense = baseDefense + (floorLevel -1) * 2;
		int maxDefense = baseDefense + (floorLevel -1) * 5;
		
		return randomBetween(minDefense, maxDefense);
	}
	
	
	
	
	
}
