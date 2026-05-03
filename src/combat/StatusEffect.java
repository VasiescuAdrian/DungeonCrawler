package combat;

public class StatusEffect {
	public enum StatusEffectType {
		POISON,
		STUN,
		BURN, 
		MARKED,
		SHIELDED,
		WEAKEN,
		ENRAGED,
		DEFENDING
		
	}
	
	private StatusEffectType type;
	private int duration;	
	private int value;
	
	public StatusEffect(StatusEffectType t, int d, int v) {
		this.type = t;
		this.duration = d;
		this.value = v;
	
	}
	
	public StatusEffectType getType() {
		return type;
	}
	
	public int getDuration() {
		return duration;
	}
	
	
	public int getValue() {
		return value;
	}
	
	
	public void reduceDuration() {
		duration--;
	}
	
	public boolean isExpired() {
		return duration <= 0;
	}
	
}
