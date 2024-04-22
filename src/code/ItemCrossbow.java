package code;

public class ItemCrossbow extends Item {

	public ItemCrossbow(int x, int y) {
		super(x, y);
		// TODO Auto-generated constructor stub
	}

	public void useItem(double xp, double yp, int w, int h, double dr) 
	{
		image = crossbow;
		x = xp + 24 * Math.cos(Math.toRadians(dr)); 
		y = yp + 24 * Math.sin(Math.toRadians(dr));
		direction = dr;
		getImageDimensions();
	}
}
