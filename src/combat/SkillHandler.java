package combat;

import java.util.Random;

import combat.StatusEffect.StatusEffectType;
import entities.Entity;
import entities.Player;
import model.Skill;

public class SkillHandler {
	private Entity caster;
	private Entity target;
	private Random random;
	
	
	
	public SkillHandler(Entity caster, Entity target) {
		this.caster = caster;
		this.target = target;
		this.random = new Random();
	}
	
	public boolean useSkill(Skill skill) {
		if ( caster instanceof Player) {
			Player player = (Player) caster;
			
			if (player.getCurrentMana() < skill.getManaCost()) {
			System.out.println("Not enough mana to perform skill.");
			return false;
		}
		
			player.spendMana(skill.getManaCost());
			
		}
		
		
		
		
		switch (skill.getType()) {
		case DAMAGE:
			useDamage(skill);
			break;
		
		case EXECUTE:
			useExecute(skill);
			break;
		
		case HEALSPELL:
			useHealSpell(skill);
			break;
		
		case POISONSHIV:
			usePoisonShiv(skill);
			break;
		
		case MARKEDSHOT:
			useMarkedShot(skill);
			break;
		
		case SHIELDBASH:
			useShieldBash(skill);
			break;
		
		case ENRAGE:
			useEnrage(skill);
			break;
		
		case TRAP:
			useTrap(skill);
			break;
		
		case LIGHTNINGSTORM:
			useLightningStorm(skill);
			break;
			
		case WEAKEN:
			useWeaken(skill);
			break;
			
		case BONEPRISON:
			useBonePrison(skill);
			break;
		}
		
		
		return true;				
	}
	

	
	
	private void useDamage(Skill skill){

	    int damage = skill.calculateDamage(caster, target);
	    target.takeDamage(damage);

	    System.out.println(caster.getName() + " used " + skill.getName() +
	        " on " + target.getName() + " dealing " + damage + " damage. " +
	        "( " + target.getCurrentHP() + " / " + target.getHP() + " )");
	}
	
	
	private void useBonePrison(Skill skill) {

		
		int damage = skill.calculateDamage(caster, target);
		target.takeDamage(damage);
		System.out.println(caster.getName() + " used " + skill.getName() + 
				" on " + target.getName());
		
		target.addStatusEffect(new StatusEffect(StatusEffectType.STUN, 2 , 0));
		System.out.println(target.getName() + " has been trapped!");
		
	}
	
	private void useWeaken(Skill skill) {

		
		int damage = skill.calculateDamage(caster, target);
		target.takeDamage(damage);
		System.out.println(caster.getName() + " used " + skill.getName() + 
				" on " + target.getName());
		target.addStatusEffect(new StatusEffect(StatusEffectType.WEAKEN, 2 , 0));
		System.out.println(target.getName() + " was weakened!");
	}
	
	private void usePoisonShiv(Skill skill) {


	    int damage = skill.calculateDamage(caster, target);
	    target.takeDamage(damage);

	    System.out.println(caster.getName() + " used " + skill.getName() +
	        " on " + target.getName() + " dealing " + damage + " damage.");

	    if (random.nextBoolean()) {
	        target.addStatusEffect(new StatusEffect(StatusEffectType.POISON, 3, 5));
	        System.out.println(target.getName() + " was poisoned!");
	    }
	}
	
	private void useHealSpell(Skill skill) {


	    int heal = skill.calculateDamage(caster, target);
	    caster.heal(heal);

	    System.out.println(caster.getName() + " healed for " + heal +
	        " ( " + caster.getCurrentHP() + " / " + caster.getHP() + " )");
	}
	
	private void useExecute(Skill skill) {


	    double hpPercent = (double) target.getCurrentHP() / target.getHP();

	    if (hpPercent < 0.30) {
	        target.takeDamage(target.getCurrentHP());
	        System.out.println(caster.getName() + " executed " + target.getName() + "!");
	    } else {
	        int damage = skill.calculateDamage(caster, target);
	        target.takeDamage(damage);

	        System.out.println("Execute failed. Target too healthy.");
	        System.out.println(caster.getName() + " dealt " + damage + " damage.");
	    }
	}
	
	private void useMarkedShot(Skill skill) {

		int damage = skill.calculateDamage(caster,target);
		target.takeDamage(damage);
		System.out.println("You used " + skill.getName() + " on " + target.getName() + " dealing " + damage + " damage." + " ( " + target.getCurrentHP() + " / " + target.getHP() + " )");
		target.addStatusEffect(new StatusEffect(StatusEffectType.MARKED, 2, 0));
		System.out.println(target.getName() + " has been marked!");
	}
	
	private void useShieldBash(Skill skill) {

	    int damage = skill.calculateDamage(caster, target);
	    target.takeDamage(damage);

	    caster.addStatusEffect(new StatusEffect(StatusEffectType.SHIELDED, 2, 0));

	    System.out.println(caster.getName() + " used " + skill.getName() + " dealing " + damage + " damage." + " ( " + target.getCurrentHP() + " / " + target.getHP() + " )");
	    System.out.println("Defense increased!");
	}
	
	
	private void useEnrage(Skill skill) {


	    int damage = skill.calculateDamage(caster, target);
	    target.takeDamage(damage);

	    caster.addStatusEffect(new StatusEffect(StatusEffectType.ENRAGED, 3, 0));
	    System.out.println(caster.getName() + " used " + skill.getName() + " dealing " + damage + " damage." + " ( " + target.getCurrentHP() + " / " + target.getHP() + " )");
	    System.out.println(caster.getName() + " is enraged!");
	}
	
	private void useTrap(Skill skill) {

	    int damage = skill.calculateDamage(caster, target);
	    target.takeDamage(damage);
	    System.out.println(caster.getName() + " used " + skill.getName() + " dealing " + damage + " damage." + " ( " + target.getCurrentHP() + " / " + target.getHP() + " )");
	    if (random.nextBoolean()) {
	        target.addStatusEffect(new StatusEffect(StatusEffectType.STUN, 1, 0));
	        System.out.println(target.getName() + " is stunned!");
	    } else {
	        System.out.println(target.getName() + " avoided the trap.");
	    }
	}
	
	private void useLightningStorm(Skill skill) {

	    int damage = skill.calculateDamage(caster, target);
	    target.takeDamage(damage);
	    System.out.println(caster.getName() + " used " + skill.getName() + " dealing " + damage + " damage." + " ( " + target.getCurrentHP() + " / " + target.getHP() + " )");

	    target.addStatusEffect(new StatusEffect(StatusEffectType.WEAKEN, 3, 0));

	    System.out.println(target.getName() + " is weakened!");
	}
	

	
}
