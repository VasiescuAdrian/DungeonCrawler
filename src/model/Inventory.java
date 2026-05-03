package model;

import java.util.ArrayList;
import java.util.List;




public class Inventory {
	private List<Item> items;
	
	public Inventory() {
		this.items = new ArrayList<>();
	}
	
	public void addItem(Item i) {
		items.add(i);
	}
	
	public void removeItem(Item i) {
		items.remove(i);
	}
	
	
	public List<Item> getItems(){
		return items;
		
	}
	
	public boolean isEmpty() {
		return items.isEmpty();
	}
	
	
	public void showInventory() {
		if (items.isEmpty()) {
			System.out.println("Inventory is empty.");
			return;
		} else {
			for (int i = 0; i < items.size(); i++) {
				System.out.println((i+1)+ " ." + items.get(i).getName() + "-" + items.get(i).getDescription());
			}
		}
	}

}
