package entities;

import java.util.ArrayList;
import java.util.List;

import model.Skill;

public class Enemy extends Entity {
	private int xpReward;
	private int skillCooldown;
	private List<Skill> skills;
	private boolean isBoss;
	private List<Skill> phase1Skills;
	private List<Skill> phase2Skills;
	private int goldReward;

	
	
	
	public Enemy(String  n, int mhp,int at, int def, int xpreward, int goldReward) {
		super(n,mhp,at,def);
		this.xpReward = xpreward;
		this.skillCooldown = 0;
		this.skills = new ArrayList<>();
		this.goldReward = goldReward;
	}
	
	public int getGoldReward() {
	    return goldReward;
	}
	
	
	public void setBoss(boolean boss) {
	    this.isBoss = boss;
	}
	
	public boolean isBoss() {
		return isBoss;
	}

	public void setPhase1Skills(List<Skill> skills) {
	    this.phase1Skills = skills;
	}

	public void setPhase2Skills(List<Skill> skills) {
	    this.phase2Skills = skills;
	}
	
	public List<Skill> getSkillsForPhase(boolean phaseTwo) {
	    if (!isBoss) return skills; 

	    return phaseTwo ? phase2Skills : phase1Skills;
	}
	
	
	public int getXpReward() {
		return xpReward;
	}
	
	
	public int getSkillCooldown() {
		return skillCooldown;
	}
	
	public void setSkillCooldown(int cooldown) {
		this.skillCooldown = cooldown;
	}
	
	public List<Skill> getSkills(){
		return skills;
	}
	
	public void setSkills(List<Skill> skills) {
		this.skills = skills;
	}
	
}
