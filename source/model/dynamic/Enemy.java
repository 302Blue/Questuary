package model.dynamic;

import java.awt.Toolkit;

import model.DynamicObject;

/**
 * @author Andrew Baldwin, Matt Billone, David Chan, Akash Sharma, Vineeth Gutta
 */

public abstract class Enemy extends DynamicObject {

	// *************************************************
	// Fields
	String name;
	boolean killable = false;
	private boolean dead = false;
	boolean hasAttacked = false;
	int health;
	int damage;
	int bound1;
	int bound2;

	private double screenWidth = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	private double screenHeight = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
	private double screenRatio = screenHeight / screenWidth;
	
	// *************************************************
	// Methods

	//only damage player once 
	public void damage(Player p) {
		if(!hasAttacked)
		{
			p.setHealth(p.getHealth() - damage);
			System.out.println("Player Health: " + p.getHealth());
		}
	}
	
	protected double getScreenWidth() {
		return screenWidth;
	}
	
	protected double getScreenHeight() {
		return screenHeight;
	}
	
	protected double getScreenRatio() {
		return screenRatio;
	}
	
	public int getBound1() {
		return bound1;
	}
	
	public int getBound2() {
		return bound2;
	}
	
	public boolean getHasAttacked() {
		return hasAttacked;
	}
	
	public boolean isKillable() {
		return killable;
	}
	
	public boolean isDead() {
		return dead;
	}
	
	public String getName() {
		return name;
	}
	
	//set damage
	public void setDamage(int d) {
		damage = d;
	}
	
	public void setBoundary(int x1, int x2)
	{
		bound1 = x1;
		bound2 = x2;
	}
	
	public void setHasAttacked(boolean b) {
		hasAttacked = b;
	}
	
	
	public void setKillable(boolean b) {
		killable = b;
	}
	
	public void setIsDead() {
		dead = true;
	}
	
	protected void setName(String n)
	{
		name = n;
	}

}
