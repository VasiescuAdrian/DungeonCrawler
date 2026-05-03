package combat;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import combat.StatusEffect.StatusEffectType;
import entities.Enemy;
import entities.Entity;
import entities.Player;
import entities.Player.PlayerClass;
import game.GameController.CombatResult;
import model.Consumable;
import model.Consumable.ConsumableType;
import model.Skill;

public class CombatManager {
	
	
	
	
	private Player player;
	private Enemy enemy;
	private Random random;
	
	public CombatManager(Player p, Enemy e) {
		this.player = p;
		this.enemy = e;
		this.random = new Random();
	}
	
	
	
	public void startPlayerTurn() {
		applyStatusEffect(player);
		
	}
	
	public void enemyTurn() {
		checkPhaseTransition();
		
		boolean stunned = hasEffect(enemy, StatusEffectType.STUN);
		applyStatusEffect(enemy);
		
		if (!enemy.isAlive()) {
			handleEnemyDeath();
			return;
		}
		
		if (stunned) {
			System.out.println(enemy.getName()  + " has been stunned and can't attack.");
			return;
		}
		
		Skill skill = chooseEnemyAction();
		
		if (skill != null) {
			SkillHandler handler = new SkillHandler(enemy, player);
			handler.useSkill(skill);
		} else {
			enemyAttack();
		}
		
		if (!player.isAlive() || !enemy.isAlive()) {
	        handleEnemyDeath();
	    }
		
		
		
	}
	
	private boolean enemyDeathHandled = false;
	
	private void handleEnemyDeath() {
		if (enemyDeathHandled) return;

	    enemyDeathHandled = true;
	    
	    int gold = enemy.getGoldReward();
	    player.addGold(gold);

	    System.out.println("You gained +" + gold + " gold!");

	    dropLoot();
		
	}
	
	
	private void dropLoot() {
		int roll = random.nextInt(100);

	    if (roll < 25) {
	        player.getInventory().addItem(
	            new Consumable("Health Potion", "Restores 30 HP",30, ConsumableType.HEALTH)
	        );
	        System.out.println("Health Potion dropped!");
	    }
	    else if (roll < 50) {
	        player.getInventory().addItem(
	            new Consumable("Mana Potion", "Restores 25 Mana",25, ConsumableType.MANA)
	        );
	        System.out.println("Mana Potion dropped!");
	    }
	}
	
	
	private Skill chooseEnemyAction() {

	    boolean phaseTwo = isPhaseTwo();

	    List<Skill> skills = enemy.getSkillsForPhase(phaseTwo);

	    if (skills == null || skills.isEmpty()) return null;

	    int roll = random.nextInt(100);

	    if (!phaseTwo) {
	        if (roll < 40) return null;
	    } else {

	        if (roll < 20) return null;
	    }

	    List<Skill> valid = new ArrayList<>();

	    for (Skill skill : skills) {
	        switch(skill.getType()) {

	            case WEAKEN:
	                if (!hasEffect(player, StatusEffectType.WEAKEN))
	                    valid.add(skill);
	                break;

	            case BONEPRISON:
	                if (!hasEffect(player, StatusEffectType.STUN))
	                    valid.add(skill);
	                break;

	            default:
	                valid.add(skill);
	                break;
	        }
	    }

	    if (valid.isEmpty()) return null;

	    return valid.get(random.nextInt(valid.size()));
	}
	
	
	private int applyPlayerDamageModifiers(int rawDamage) {
		int damage = rawDamage;
		
		if (hasEffect(player, StatusEffectType.ENRAGED)) {
			damage = (int) (damage * 1.5);
		}
		if (hasEffect(enemy, StatusEffectType.MARKED)){
			damage = (int) (damage * 1.25);
		}
		if (hasEffect(player, StatusEffectType.WEAKEN)) {
			damage = (int) (damage * 0.75);
		}
		
		damage -= enemy.getDefense();
		
		return Math.max(1, damage);
	}
	
	private int applyEnemyDamageModifiers(int rawDamage) {
		int damage = rawDamage;
		
		if (hasEffect(enemy, StatusEffectType.WEAKEN)) {
			damage = (int) (damage * 0.75);
		}
		
		if (hasEffect(player, StatusEffectType.SHIELDED)) {
			damage = (int) (damage * 0.75);
		}
		
		if (hasEffect(player, StatusEffectType.ENRAGED)) {
			damage = (int) (damage * 1.25);
		}
		if (hasEffect(player, StatusEffectType.DEFENDING)) {
			damage = (int) (damage * 0.85);
		}
		
		damage -= player.getDefense();
		
		return Math.max(1, damage);
	}
	
	
	
	public void playerAttack() {
		int damage = applyPlayerDamageModifiers(player.getAttack());
		if (!player.isAlive() || !enemy.isAlive()) return;
		
		
		enemy.takeDamage(damage);
		System.out.println(player.getName() + " attacks " + enemy.getName() + " dealing " + damage + " damage. " + "( " + enemy.getCurrentHP() + " / " + enemy.getHP() + " ) HP" );
				
		if (!enemy.isAlive()) {
			handleEnemyDeath();
		}
				
	}
	
	

	
	public CombatResult getCombatResult() {
		if (playerWon()) {
			return CombatResult.PLAYER_WON;
		} else {
			return CombatResult.PLAYER_LOST;
		}
	}
	
	
	public boolean tryEscape() {
		int chance = 30;
		if (player.getPlayerClass() == PlayerClass.ASSASSIN) {
			chance += 20;
		}
		
		int roll = random.nextInt(100);
		
		if (chance > roll) {
			System.out.println("You successfully escaped the room.");
			return true;
		} else {
			System.out.println("Your efforts were in vain. You cannot escape your fate.");
			return false;
		}
	}
	

	
	private boolean isPhaseTwo() {
		return enemy.getCurrentHP() <= enemy.getHP() * 0.5;
	}
	
	private boolean phaseTriggered = false;
	
	private void checkPhaseTransition() {
		if (!enemy.isBoss()) return;
		
		if (!phaseTriggered && isPhaseTwo()) {
			phaseTriggered = true;
			
			System.out.println("The ground cracks...");
			System.out.println(enemy.getName() + " screams loudly and goes feral!");
			
			enemy.addStatusEffect(new StatusEffect(StatusEffectType.ENRAGED, 999, 0));
		}
	}
	
	
	
	
	
	public void printCombatState() {
		System.out.println("Player HP: " + player.getCurrentHP());
		System.out.println("Enemy HP: " + enemy.getCurrentHP());
	}
	
	
	public void enemyAttack() {
		int damage = applyEnemyDamageModifiers(enemy.getAttack());
		if (enemy.isAlive() && player.isAlive()) {
		player.takeDamage(damage);
		System.out.println(enemy.getName() + " attacks " + player.getName() + " dealing " + damage + " damage. " + " ( " + player.getCurrentHP() + " / " + player.getHP() + " ) HP");
	}
	}

	
	public boolean isCombatOver() {
		return !player.isAlive() || !enemy.isAlive();
	}
	
	
	public boolean playerWon() {
		return player.isAlive() && !enemy.isAlive();
	}
	
	
	public Player getPlayer() {
		return player;
	}
	
	public Enemy getEnemy() {
		return enemy;
	}
	
	
	public void applyStatusEffect(Entity entity) {
		for (StatusEffect effect : entity.getStatusEffects()) {
			switch(effect.getType()) {
			case POISON:
				entity.takeDamage(effect.getValue());
				System.out.println(entity.getName() + " took " + effect.getValue() + " poison damage." + " ( " + entity.getCurrentHP() + " / " + entity.getHP() + " )");
				break;
			
			case STUN:
				break;
			
			case SHIELDED:
				System.out.println(entity.getName() + " is still shielded.");
				break;
			
			case BURN:
				entity.takeDamage(effect.getValue());
				System.out.println(entity.getName() + " took " + effect.getValue() + " burning damage." + " ( " + entity.getCurrentHP() + " / " + entity.getHP() + " )");
				break;
			
			case MARKED:
				System.out.println(entity.getName() + " is  marked.");
				break;
				
			case ENRAGED:
				System.out.println(entity.getName() + " is  enraged.");
				break;
			
			case WEAKEN:
				System.out.println(entity.getName() + " is  weakened.");
				break;
				
			case DEFENDING:
				System.out.println(entity.getName() + " is defending.");
			}
			
			effect.reduceDuration();
		}
		entity.getStatusEffects().removeIf(effect -> effect.isExpired());
	}
	
	public boolean isStunned(Entity entity) {
		for (StatusEffect effect : entity.getStatusEffects()) {
			if (effect.getType() == StatusEffectType.STUN) {
				return true;
			}
		}
		return false;
	}
	
	
	public boolean hasEffect(Entity entity, StatusEffectType type) {
		for (StatusEffect effect : entity.getStatusEffects()) {
			if (effect.getType() == type) {
				return true;
			}
		}
		return false;
	}
	
	


}
