package code;

public class ItemCrossbow extends Item {

	public ItemCrossbow(int x, int y) {
		super(x, y);
		meleeDamage = 0;
		loadImage("Crossbow.png");
	}

	@Override
	public void useItem(double xp, double yp, int w, int h, double dr) {
		x = xp + 24 * Math.cos(Math.toRadians(dr)); 
		y = yp + 24 * Math.sin(Math.toRadians(dr));
		direction = dr;
	}
}
