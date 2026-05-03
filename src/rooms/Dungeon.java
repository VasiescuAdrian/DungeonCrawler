package rooms;
import java.util.List;

public class Dungeon {
	private List<DungeonFloor> floors;
	private int currentFloorIndex;
	
	
	public Dungeon(List<DungeonFloor> floors) {
		this.floors = floors;
		this.currentFloorIndex = 0;
	}
	
	
	public DungeonFloor getCurrentFloor() {
		if (currentFloorIndex < floors.size()) {
			return floors.get(currentFloorIndex);
		}
		return null;
	}
	
	public int getCurrentFloorNumber() {
		return getCurrentFloor().getFloorNumber();
	}
	
	
	
	public Room getCurrentRoom() {
		return getCurrentFloor().getCurrentRoom();
	}
	
	public boolean hasNextRoom() {
		return getCurrentFloor().hasNextRoom();
	}
	
	public void moveToNextRoom() {
		getCurrentFloor().moveToNextRoom();
	}
	
	public boolean hasNextFloor() {
		return currentFloorIndex < floors.size() - 1;
	}
	
	
	public void moveToNextFloor() {
		if (hasNextFloor()) {
			currentFloorIndex++;
			
		}
	}
	public boolean isFinished() {
		return currentFloorIndex >= floors.size() - 1 && getCurrentFloor().isFinished();
	}
}
