package rooms;

public class Room {
	public enum RoomType {
		COMBAT,EVENT,BOSS,MERCHANT
	}
	
	
	
	protected String name;
	protected String description;
	protected RoomType type;
	protected boolean completed;
	
	
	public Room (String n, String d, RoomType t) {
		this.name = n;
		this.description = d;
		this.type = t;
		this.completed = false;
	}
	
	
	public String getName() {
		return name;
	}
	
	
	public String getDescription() {
		return description;
	}
	
	public RoomType  getType() {
		return type;
	}

	
	public void setCompleted(boolean c) {
		this.completed = c;

	}
	
	public boolean isCompleted() {
		return completed;
	
	}
	
	
}
