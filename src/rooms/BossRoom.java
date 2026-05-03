package rooms;

import entities.Enemy;

public class BossRoom extends CombatRoom {
	
	
	public BossRoom(String name, String description, Enemy boss) {
		super(name, description, boss);
	}
}
