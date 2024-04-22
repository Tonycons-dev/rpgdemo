package code;

public abstract class Item extends Sprite {

	protected int meleeDamage;
	protected int rangedDamage;
	protected int invTime;
	protected double knockbackX;
	protected double knockbackY;

	
	public Item(int x, int y) {
		super(x, y);
	}
	
	public abstract void useItem(double xp, double yp, int w, int h, double dr);

	
	public int getMeleeDamage() {
		return meleeDamage;
	}
	
	public int getRangedDamage() {
		return rangedDamage;
	}
	
	public int getInvTime() {
		return invTime;
	}
	
	public double getKnockbackX() {
		return knockbackX;
	}
	
	public double getKnockbackY() {
		return knockbackY;
	}
}
