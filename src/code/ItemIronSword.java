package code;

public class ItemIronSword extends Item
{
	private int wOffset = -90;

	/**
	 * @see Item constructor
	 */
	public ItemIronSword(int x, int y) {
		super(x, y);
		loadImage("ItemBasicSword.png");

		meleeDamage = 5;
		invTime = 20;
	}

	/**
	 * @see Item useItem
	 */
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

	@Override
	public int getCoinValue()
	{
		return 100;
	}
}