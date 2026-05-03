package rooms;

import entities.Enemy;

public class CombatRoom extends Room {
	private Enemy enemy;
	
	
	
	public CombatRoom(String n, String d, Enemy e) {
		super(n,d,RoomType.COMBAT);
		this.enemy = e;
		
	}
	
	public Enemy getEnemy() {
	    return enemy;
	}
}
