package rooms;

import java.util.List;

public class DungeonFloor {
	private int floorNumber;
	private List<Room> rooms;
	private int currentRoomIndex;
	
	
	public DungeonFloor(int floornumber, List<Room> rooms) {
		this.floorNumber = floornumber;
		this.rooms = rooms;
		this.currentRoomIndex = 0;
	}
	
	public int getFloorNumber() {
		return floorNumber;
	}
	
	public Room getCurrentRoom() {
		if (currentRoomIndex < rooms.size()) {
			return rooms.get(currentRoomIndex);
		}
		
		return null;
	}
	
	
	public boolean hasNextRoom() {
		return currentRoomIndex < rooms.size() -1;
	}
	
	public void moveToNextRoom() {
		if(hasNextRoom()) {
			currentRoomIndex++;
		}
	}
	
	public boolean isFinished() {
		return currentRoomIndex >= rooms.size() - 1 && getCurrentRoom().isCompleted();
	}
}
