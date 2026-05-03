package rooms;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import combat.EnemyFactory;
import rooms.EventRoom.EventType;
import rooms.Room.RoomType;
import entities.Enemy;
import entities.Merchant;

public class DungeonGenerator {
	private Random random;
    private EnemyFactory enemyFactory;
    private List<CombatRoomTemplate> combatTemplates;
    private List<EventRoomTemplate> eventTemplates;
    private Merchant merchant = new Merchant();

    public DungeonGenerator(EnemyFactory enemyFactory) {
        this.random = new Random();
        this.enemyFactory = enemyFactory;
        initTemplates();
    }
    
    
    private void initTemplates() {
        combatTemplates = List.of(
            new CombatRoomTemplate(
                "Dangling Gardens",
                "A garden full of exotic plants...",
                "Carnivorous Plant"
            ),
            new CombatRoomTemplate(
                "Forgotten Graveyard",
                "A dark graveyard filled with bones...",
                "Ghoul"
            ),
            new CombatRoomTemplate(
                "Dark Cave",
                "A deep dark cave...",
                "Ogre"
            ),
            new CombatRoomTemplate(
                "Lost City",
                "An ancient civilization...",
                "Evil Spirit"
            )
        );

        eventTemplates = List.of(
            new EventRoomTemplate(
                "Abandoned Altar",
                "You see an altar covered in vines...",
                EventType.ABANDONED_ALTAR
            ),
            new EventRoomTemplate(
                "Chest Room",
                "A glowing chest awaits...",
                EventType.CHEST_ROOM
            )
        );
    }
    
    
    
    
    public DungeonFloor generateRandomFloor(int floorLevel) {
    	List<Room> rooms = new ArrayList<>();

        int totalRooms = 15;
        int merchantIndex = totalRooms / 2;

        for (int i = 0; i < totalRooms; i++) {

            if (i == totalRooms - 1) {
                rooms.add(new BossRoom(
                    "Boss Chamber",
                    "A terrifying presence awaits...",
                    enemyFactory.createBoneKing(floorLevel)
                ));

            } else if (i == merchantIndex) {
                rooms.add(new MerchantRoom(
                    "Merchant Room",
                    "A shady trader appears...",
                    RoomType.MERCHANT,
                    merchant
                ));

            } else {
                if (random.nextBoolean()) {
                    rooms.add(generateCombatRoom(floorLevel));
                } else {
                    rooms.add(generateEventRoom());
                }
            }
        }

        return new DungeonFloor(floorLevel, rooms);
    }
    
    
    private CombatRoomTemplate lastCombatTemplate = null;

    private Room generateCombatRoom(int floorLevel) {
        CombatRoomTemplate t;

        if (combatTemplates.size() > 1) {
            do {
                t = combatTemplates.get(random.nextInt(combatTemplates.size()));
            } while (t == lastCombatTemplate);
        } else {
            t = combatTemplates.get(0);
        }

        lastCombatTemplate = t;

        Enemy enemy = enemyFactory.createEnemy(t.enemyType, floorLevel);

        return new CombatRoom(t.name, t.description, enemy);
    }
    
    private EventRoomTemplate lastEventTemplate = null;

    private Room generateEventRoom() {
        EventRoomTemplate t;

        if (eventTemplates.size() > 1) {
            do {
                t = eventTemplates.get(random.nextInt(eventTemplates.size()));
            } while (t == lastEventTemplate);
        } else {
            t = eventTemplates.get(0);
        }

        lastEventTemplate = t;

        return new EventRoom(t.name, t.description, t.type);
    }
    
}
