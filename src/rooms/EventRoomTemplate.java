package rooms;

import rooms.EventRoom.EventType;

public class EventRoomTemplate {

	    String name;
	    String description;
	    EventType type;

	    public EventRoomTemplate(String n, String d, EventType t) {
	        this.name = n;
	        this.description = d;
	        this.type = t;
	    }
	}

