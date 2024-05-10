package code;

import javax.swing.ImageIcon;

public class EntityGuard extends Entity{

	private static final ImageIcon guardIdle;
	private static final ImageIcon guardMoving;


	static {
		guardIdle = new ImageIcon(Sprite.folder + "GuardIdle.png");
		guardMoving = new ImageIcon(Sprite.folder + "GuardMoving.gif");
	}

	/**
	 * @see Entity constructor
	 */
	public EntityGuard(int x, int y, int aggro, double direction, int dNum, int hp) {
		super(x, y, aggro, direction, dNum, hp);
		useImage(guardIdle);
		moveSpeed = 1;
		dmg = 15;
	}


	/**
	 * @see Entity whenInteracted
	 */
	@Override
	public void whenInteracted() {

	}

	/**
	 * @see Entity performAI
	 */
	@Override
	public void performAI(double targetX, double targetY, double targetDirection) {
		//A basic behavior where the entity moves towards the player.
		
		//If this check isn't there, it will be a "ghost"
		if (colliding)
			return;

        if (aggro == 0) {
            useImage(guardIdle);
			return;
        }
		useImage(guardMoving);

		double xp = targetX - x;
		double yp = targetY - y;

		direction = (Math.atan2(yp, xp) * (180.0 / Math.PI)) + 90.0;
		double hyp = Math.sqrt(xp * xp + yp * yp);
		xp /= hyp;
		yp /= hyp;

		x += xp * moveSpeed;
		y += yp * moveSpeed;
		oldX = xp * moveSpeed * -1.0;
		oldY = yp * moveSpeed * -1.0;
    }

	@Override
	public int getCoinValue()
	{
		return 100;
	}
}
