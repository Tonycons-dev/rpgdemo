package code;

public class ItemSword extends Item
{
	public ItemSword(int x, int y) 
	{
		super(x, y);
		meleeDamage = 3;
		invTime = 20;
	}
	
	int wOffset = -90;
	public void useItem(double xp, double yp, int w, int h, double dr) 
	{
		image = basicSword;

		direction = dr + wOffset;
		
		knockbackX = 24 * Math.cos(Math.toRadians(direction));
		knockbackY = 24 * Math.sin(Math.toRadians(direction));
		
		x = xp + knockbackX; 
		y = yp + knockbackY;
		
		wOffset+=10;
		if(wOffset > 90)
			wOffset = -90;
		
		getImageDimensions();
	}
}