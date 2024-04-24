package code;

import javax.swing.*;

public class ItemCrossbow extends Item {

	private static final ImageIcon imgCrossbowShooting;
	private static final ImageIcon imgCrossbow;

	static {
		imgCrossbowShooting = new ImageIcon(Sprite.folder + "Crossbow.gif");
		imgCrossbow = new ImageIcon(Sprite.folder + "Crossbow.png");
	}

	public ItemCrossbow(int x, int y) {
		super(x, y);
		meleeDamage = 1;
		useImage(imgCrossbow);
	}

	@Override
	public void useItem(double xp, double yp, int w, int h, double dr) {
		useImage(imgCrossbowShooting);
		x = xp + 24 * Math.cos(Math.toRadians(dr)); 
		y = yp + 24 * Math.sin(Math.toRadians(dr));
		direction = dr;
	}
}
