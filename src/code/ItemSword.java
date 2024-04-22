package code;

import javax.swing.*;

public class ItemSword extends Item
{
	private int wOffset = -90;

	public ItemSword(int x, int y) {
		super(x, y);
		loadImage("ItemBasicSword.png");

		meleeDamage = 3;
		invTime = 20;
	}

	@Override
	public void useItem(double xp, double yp, int w, int h, double dr) {
		direction = dr + wOffset;
		
		knockbackX = 24 * Math.cos(Math.toRadians(direction));
		knockbackY = 24 * Math.sin(Math.toRadians(direction));
		
		x = xp + knockbackX; 
		y = yp + knockbackY;
		
		wOffset += 10;
		if(wOffset > 90)
			wOffset = -90;
	}
}