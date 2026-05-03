package entities;

import model.Consumable;
import model.Consumable.ConsumableType;

public class Merchant {
	private int wheaponCost = 20;
	private int armorCost = 20;
	private int potionCost = 10;
	
	
	public int getArmorCost() {
		return armorCost;
	}
	
	public int getWheaponCost() {
		return wheaponCost;
	}
	
	public int getPotionCost() {
		return potionCost;
	}
	
	
	
	public void upgradeWheapon(Player player) {
		if (player.getGold() < wheaponCost) {
			System.out.println("Not enough gold.");
			return;
		}
		
		player.spendGold(wheaponCost);
		player.setAttack(player.getAttack() + 3);
		
		wheaponCost += 10;
		
		System.out.println("Wheapon Upgraded!");
		
		
	}
	
	public void buyHealthPotion(Player player) {
        if (player.getGold() < potionCost) {
            System.out.println("Not enough gold.");
            return;
        }

        player.spendGold(potionCost);
        player.getInventory().addItem(new Consumable("Health Potion","A medium size health potion, restores 30 health.", 30, ConsumableType.HEALTH));

        System.out.println("Bought Health Potion!");
    }

	 public void buyManaPotion(Player player) {
	        if (player.getGold() < potionCost) {
	            System.out.println("Not enough gold.");
	            return;
	        }

	        player.spendGold(potionCost);
	        player.getInventory().addItem(new Consumable("Mana Potion", "A medium size mana potion, restores 15 mana.", 15, ConsumableType.MANA));

	        System.out.println("Bought Mana Potion!");
	    }
	
	
	public void upgradeArmor(Player player) {
		if (player.getGold() < armorCost) {
			System.out.println("Not enough gold.");
			return;
		}
		
		player.spendGold(armorCost);
		player.setDefense(player.getDefense() + 3);
		
		armorCost += 10;
		
		System.out.println("Armor upgraded!");
	}
	
	
	
}
