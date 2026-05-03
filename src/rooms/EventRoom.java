package rooms;

public class EventRoom extends Room {
	
	public enum EventType {
	ABANDONED_ALTAR,
	SUSPICIOUS_CORPSE,
	CHEST_ROOM,
	ANCIENT_SHRINE,
	BROKEN_WAGON
}
	
	
	private EventType type;

	public EventRoom(String n, String d, EventType e) {
		super(n, d, RoomType.EVENT);
		this.type = e;		
	}
	
	public EventType getEventType() {
		return type;
	}
	
	
}
