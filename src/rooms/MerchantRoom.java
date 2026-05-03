package rooms;

import entities.Merchant;

public class MerchantRoom extends Room {

	private Merchant merchant;
	
	public MerchantRoom(String n, String d, RoomType t, Merchant m) {
		super(n,d,t);
		this.merchant = m;
		
	}
	
	
	public Merchant getMerchant() {
		return merchant;
	}
	
}
