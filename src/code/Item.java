package code;

public abstract class Item extends Sprite {

	protected int meleeDamage;
	protected int rangedDamage;
	protected int invTime;
	protected double knockbackX;
	protected double knockbackY;


	/**
	 * Item constructor
	 * @see Sprite constructor
	 * @param x Sprite X coordinate
	 * @param y Sprite Y coordinate
	 */
	public Item(int x, int y) {
		super(x, y);
	}

	/**
	 * Perform the item's behavior
	 * TODO: Simplify the parameters
	 */
	public abstract void useItem(double xp, double yp, int w, int h, double dr);


	/**
	 * Returns the melee (contact) damage
	 */
	public int getMeleeDamage() {
		return meleeDamage;
	}

	/**
	 * Returns the ranged damage if this item fires a projectile
	 * TODO: Make ranged weapons spawn a projectile Entity which has its own damage
	 */
	public int getRangedDamage() {
		return rangedDamage;
	}

	/**
	 * Returns the number of invulnerability frames an entity should gain if it receives damage from this item.
	 */
	public int getInvTime() {
		return invTime;
	}

	/**
	 * Returns the horizontal distance entities will be knocked back when damaged
	 */
	public double getKnockbackX() {
		return knockbackX;
	}

	/**
	 * Returns the vertical distance entities will be knocked back when damaged
	 */
	public double getKnockbackY() {
		return knockbackY;
	}
}
