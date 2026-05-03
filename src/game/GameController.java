package game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import combat.CombatManager;
import combat.EnemyFactory;
import combat.SkillHandler;
import combat.StatusEffect;
import combat.StatusEffect.StatusEffectType;

import entities.Enemy;
import entities.Merchant;
import entities.Player;
import entities.Player.PlayerClass;

import model.Consumable;
import model.Inventory;
import model.Item;
import model.Skill;
import model.Consumable.ConsumableType;

import rooms.BossRoom;
import rooms.CombatRoom;
import rooms.Dungeon;
import rooms.DungeonFloor;
import rooms.DungeonGenerator;
import rooms.EventRoom;
import rooms.Room;
import rooms.EventRoom.EventType;
import rooms.MerchantRoom;

public class GameController {


	// CORE FIELDS

	private Player player;
	private Dungeon dungeon;
	private Scanner scanner;
	private Random random;
	private EnemyFactory enemyFactory = new EnemyFactory();

	public enum CombatResult {
		PLAYER_WON,
		PLAYER_LOST,
		PLAYER_ESCAPED
	}

	public GameController() {
		this.scanner = new Scanner(System.in);
		this.random = new Random();
	}


	// GAME START

	public void startGame() {
		setupGame();
		gameLoop();
	}

	private void setupGame() {
		configurePlayer();

		DungeonGenerator generator = new DungeonGenerator(enemyFactory);

		List<DungeonFloor> floors = new ArrayList<>();
		floors.add(generator.generateRandomFloor(1));

		dungeon = new Dungeon(floors);
		player.gainXp(1000);
	}

	private void gameLoop() {
		while (player.isAlive()) {
			Room currentRoom = dungeon.getCurrentRoom();
			if (currentRoom == null) {
				break;
			}

			handleRoom(currentRoom);

			if (!player.isAlive()) {
				System.out.println("Game over..");
				break;
			}

			if (!currentRoom.isCompleted()) {
				continue;
			}

			if (dungeon.hasNextRoom()) {
				dungeon.moveToNextRoom();
				System.out.println("Moving to next room...");
			} else {

				System.out.println("Floor cleared!");

				if (dungeon.hasNextFloor()) {
					dungeon.moveToNextFloor();
					System.out.println("Descending to the next floor...");
					continue;
				} else {
					System.out.println("Dungeon Cleared!");
					System.out.println("Victory!");
					break;
				}
			}
		}
	}


	// PLAYER CONFIG

	private String choosePlayerName() {

		while (true) {
			System.out.println("Choose your character's name:");
			String name = scanner.nextLine().trim();

			if (name.length() > 15) {
				System.out.println("Name is too long, try another one. Limit is 15 characters");
				continue;
			} else if (name.length() < 3) {
				System.out.println("Name is too short. Must be at least 3 characters.");
				continue;
			} else if (name.isEmpty()) {
				System.out.println("Name cannot be blank empty.");
				continue;
			}
			return name;
		}
	}

	private void configurePlayer() {
		String name = choosePlayerName();

		PlayerClass type = null;

		while (type == null) {

			System.out.println("Choose what class you want to pick:");
			System.out.println("1.Warrior (High damage, weakness to attacks when powered up.)");
			System.out.println("2.Mage (Good damage, debuffs enemies and mantains overall control.)");
			System.out.println("3.Assassin (Medium damage, way more rewarding for tactical plays, higher chance to escape combat.)");
			System.out.println("4.Ranger (Balanced damage, more of a passive/punishing style.)");

			int choice = readInt();

			switch (choice) {
			case 1:
				type = PlayerClass.WARRIOR;
				break;

			case 2:
				type = PlayerClass.MAGE;
				break;

			case 3:
				type = PlayerClass.ASSASSIN;
				break;

			case 4:
				type = PlayerClass.RANGER;
				break;

			default:
				System.out.println("Invalid choice, try again.");
				break;
			}
		}

		player = new Player(name, type);
	}


	// ROOM HANDLING

	private void handleRoom(Room room) {
		System.out.println("You entered:" + room.getName());
		System.out.println(room.getDescription());

		if (room instanceof MerchantRoom) {
			handleMerchantRoom((MerchantRoom) room);
			return;
		}

		if (room instanceof BossRoom) {
			handleBossRoom((BossRoom) room);
			return;
		}

		if (room instanceof CombatRoom) {
			handleCombatRoom((CombatRoom) room);
			return;
		}

		if (room instanceof EventRoom) {
			handleEventRoom((EventRoom) room);
		}
	}


	// INPUT

	private int readInt() {
		while (!scanner.hasNextInt()) {
			System.out.println("Invalid input. Enter a number.");
			scanner.next();
		}
		return scanner.nextInt();
	}


	// INVENTORY / SKILLS

	private void showInventory() {
		player.getInventory().showInventory();
	}

	private boolean useSkill(Enemy enemy) {
		List<Skill> skills = player.getSkills();

		if (skills.isEmpty()) {
			System.out.println("No skills available.");
			return false;
		}

		player.showSkills();
		System.out.println("Choose what skill to use:");

		int choice = readInt();

		if (choice < 1 || choice > skills.size()) {
			System.out.println("Invalid choice.");
			return false;
		}

		Skill selectedSkill = skills.get(choice - 1);

		SkillHandler handler = new SkillHandler(player, enemy);
		return handler.useSkill(selectedSkill);
	}

	private boolean useItem() {
		Inventory inventory = player.getInventory();

		if (inventory.isEmpty()) {
			System.out.println("Inventory is empty.");
			return false;
		}

		inventory.showInventory();

		System.out.println("Choose what item to use:");
		int choice = readInt();

		if (choice < 1 || choice > inventory.getItems().size()) {
			System.out.println("Invalid choice");
			return false;
		}

		Item item = inventory.getItems().get(choice - 1);

		if (item instanceof Consumable) {
			Consumable consumable = (Consumable) item;
			player.useConsumable(consumable);
			return true;
		} else {
			System.out.println("You cant use this item right now");
			return false;
		}
	}


	// COMBAT UI

	private void showCombatOptions() {
		System.out.println("What do you do?: ");
		System.out.println("1.Attack");
		System.out.println("2.Skills");
		System.out.println("3.Defend");
		System.out.println("4.Use item");
		System.out.println("5.Escape");
	}


	// COMBAT CORE

	private CombatResult runCombat(Enemy enemy, boolean tryEscape) {

		CombatManager cm = new CombatManager(player, enemy);

		while (!cm.isCombatOver()) {

			cm.startPlayerTurn();

			if (cm.isStunned(player)) {
				System.out.println(player.getName() + " is stunned and loses the turn!");
				cm.enemyTurn();
				cm.printCombatState();
				continue;
			}

			showCombatOptions();
			int choice = readInt();

			switch (choice) {

			case 1:
				cm.playerAttack();
				break;

			case 2:
				boolean skill = useSkill(enemy);
				if (!skill) continue;
				break;

			case 3:
				player.addStatusEffect(new StatusEffect(StatusEffectType.DEFENDING, 1, 0));
				System.out.println("You brace yourself for the next attack.");
				break;

			case 4:
				boolean used = useItem();
				if (!used) continue;
				break;

			case 5:
				if (!tryEscape) {
					System.out.println("You think you can escape from a boss fight?");
					continue;
				}

				if (cm.tryEscape()) {
					return CombatResult.PLAYER_ESCAPED;
				}
				break;

			default:
				System.out.println("Invalid choice.");
				continue;
			}

			if (!cm.isCombatOver()) {
				cm.enemyTurn();
			}

			cm.printCombatState();
		}

		return cm.getCombatResult();
	}


	// REWARDS

	private void giveRewards(Enemy enemy) {
		int xpAmmount = enemy.getXpReward();
		int gold = enemy.getGoldReward();

		player.gainXp(xpAmmount);
		player.addGold(gold);

		System.out.println("You gained " + xpAmmount + " XP and " + gold + " gold!");
	}


	// MERCHANT ROOM

	private void handleMerchantRoom(MerchantRoom room) {
		Merchant merchant = room.getMerchant();

		System.out.println("Welcome, traveller, what do you wish to attain?");

		while (true) {

			System.out.println("1. Upgrade Wheapon (" + merchant.getWheaponCost() + " gold )");
			System.out.println("2. Upgrade Armor ( " + merchant.getArmorCost() + " gold)");
			System.out.println("3. Buy health potion (" + merchant.getPotionCost() + " gold)");
			System.out.println("4. Buy mana potion (" + merchant.getPotionCost() + " gold)");
			System.out.println("5. Leave");
			System.out.println("You have: " + player.getGold() + " gold");

			int choice = readInt();

			switch (choice) {

			case 1:
				merchant.upgradeWheapon(player);
				break;

			case 2:
				merchant.upgradeArmor(player);
				break;

			case 3:
				merchant.buyHealthPotion(player);
				break;

			case 4:
				merchant.buyManaPotion(player);
				break;

			case 5:
				System.out.println("You bid farewell and leave the merchant.");
				room.setCompleted(true);
				return;

			default:
				System.out.println("Invalid choice. Try again.");
				continue;
			}
		}
	}

	
	// BOSS ROOM

	private void handleBossRoom(BossRoom room) {
		System.out.println("You feel a very powerful presence ahead.There is no escape.");

		CombatResult result = runCombat(room.getEnemy(), false);

		switch (result) {

		case PLAYER_WON:
			System.out.println("You defeated the boss, and move on fowards.");
			giveRewards(room.getEnemy());
			room.setCompleted(true);
			break;

		case PLAYER_LOST:
			System.out.println("You were slain by the boss...");
			break;

		case PLAYER_ESCAPED:
			break;
		}
	}


	// COMBAT ROOM

	private void handleCombatRoom(CombatRoom room) {

		CombatResult won = runCombat(room.getEnemy(), true);

		switch (won) {

		case PLAYER_WON:
			System.out.println("You defeated the enemy and move on fowards...");
			giveRewards(room.getEnemy());
			room.setCompleted(true);
			break;

		case PLAYER_LOST:
			System.out.println("You were defeated...");
			break;

		case PLAYER_ESCAPED:
			System.out.println("You fled from combat");
			room.setCompleted(true);
			break;
		}
	}


	// EVENT ROOM
	
	private void showEventOptions(EventType type) {

		System.out.println("What do you do?");

		switch (type) {

		case ABANDONED_ALTAR:
			System.out.println("1. Pray");
			System.out.println("2. Scavenge");
			System.out.println("3. Leave");
			break;

		case CHEST_ROOM:
			System.out.println("1. Open the chest");
			System.out.println("2. Leave");
			break;
		}
	}

	private void handleEventRoom(EventRoom room) {

		switch (room.getEventType()) {

		case ABANDONED_ALTAR:
			showEventOptions(room.getEventType());
			handleAbandonedAltar(room);
			break;

		case CHEST_ROOM:
			showEventOptions(room.getEventType());
			handleChestRoom(room);
			break;
		}
	}


	// EVENT: ALTAR

	private void handleAbandonedAltar(EventRoom room) {

		while (!room.isCompleted() && player.isAlive()) {

			int choice = readInt();

			switch (choice) {

			case 1:
				if (random.nextBoolean()) {
					player.heal(15);
					System.out.println("As you approach, a radiant line shines toward you.You feel your wounds closing..");
				} else {
					player.takeDamage(15);
					System.out.println("As you approach, you feel uneasy, a sharp pain tugs your gut, you cough up blood.");
				}

				System.out.println("Player HP:" + player.getCurrentHP());
				room.setCompleted(true);
				break;

			case 2:
				if (random.nextBoolean()) {
					Consumable potion = new Consumable(
						"Small Healing potion",
						"A small healing potion, heals for 20 hp.",
						20,
						ConsumableType.HEALTH
					);

					player.getInventory().addItem(potion);
					System.out.println("You found an old potion..");
					room.setCompleted(true);
					break;

				} else {
					System.out.println("Your daring search disturbed the peace, bones reanimate..");

					Enemy eventEnemy = enemyFactory.createEnemy(
						"Disturbed Skeleton",
						dungeon.getCurrentFloorNumber()
					);

					CombatResult won = runCombat(eventEnemy, true);

					switch (won) {

					case PLAYER_WON:
						System.out.println("You defeated the enemy and moved on forwards...");
						giveRewards(eventEnemy);
						room.setCompleted(true);
						break;

					case PLAYER_LOST:
						System.out.println("The bones of the dead overwhelm you...");
						break;

					case PLAYER_ESCAPED:
						System.out.println("You struggle to escape, and luck was on your side...");
						room.setCompleted(true);
						break;
					}
				}
				break;

			case 3:
				System.out.println("You decide not to bother and leave the area.");
				room.setCompleted(true);
				break;

			default:
				System.out.println("Invalid choice. Try again");
				showEventOptions(room.getEventType());
			}
		}
	}


	// EVENT: CHEST

	private void handleChestRoom(EventRoom room) {

		while (!room.isCompleted() && player.isAlive()) {

			int choice = readInt();

			switch (choice) {

			case 1:
				if (random.nextBoolean()) {

					System.out.println("You found a greater healing potion!");

					Consumable potion = new Consumable(
						"Greater Healing potion",
						"Heals for 45 hp.",
						25,
						ConsumableType.HEALTH
					);

					player.getInventory().addItem(potion);
					room.setCompleted(true);
					break;

				} else {

					System.out.println("The chest started to move, it bit your hand while you reached, too fast to pull back!");

					player.takeDamage(10);
					System.out.println("Player HP: " + player.getCurrentHP());

					if (!player.isAlive()) {
						System.out.println("The mimic's bite proved fatal.");
					} else {

						Enemy eventEnemy = enemyFactory.createEnemy(
							"Mimic",
							dungeon.getCurrentFloorNumber()
						);

						CombatResult won = runCombat(eventEnemy, true);

						switch (won) {

						case PLAYER_WON:
							System.out.println("You defeated the enemy and moved on forwards...");
							giveRewards(eventEnemy);
							room.setCompleted(true);
							break;

						case PLAYER_LOST:
							System.out.println("The power of the mimic got the better of you...");
							break;

						case PLAYER_ESCAPED:
							System.out.println("You struggle to escape, and luck was on your side...");
							room.setCompleted(true);
							break;
						}
					}
				}
				break;

			case 2:
				System.out.println("As tempting as that chest looks, you dont risk it. You walk forwards, leaving the room behind.");
				room.setCompleted(true);
				break;

			default:
				System.out.println("Invalid choice. Try again");
				showEventOptions(room.getEventType());
			}
		}
	}

	// XP

	private void getVictoryXp(Enemy enemy) {
		int xpAmmount = enemy.getXpReward();
		player.gainXp(xpAmmount);

		System.out.println("You gained: " + xpAmmount + "XP!." +
			" ( " + player.getCurrentXP() + "/" + player.getXPToNextLevel() + " )");
	}
}