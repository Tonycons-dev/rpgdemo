package code;

public class EntityGuard extends Entity{

	public EntityGuard(int x, int y, int aggro, double direction, int dNum, int hp) {
		super(x, y, aggro, direction, dNum, hp);
		loadImage("GuardIdle.png");
		moveSpeed = 1;
	}
	
	public EntityGuard(int x, int y) {
		super(x, y, 0, 0, 0, 30);
		aggro = 1;
		direction = 0.0;
		inv = 100;
		moveSpeed = 1;
		loadImage("GuardIdle.png");
	}
	
	public void performAI(double targetX, double targetY, double targetDirection) {
		//A basic behavior where the entity moves towards the player.
		
		//If this check isn't there, it will be a "ghost"
		if(!colliding) {
			
		if(aggro == 0) {
			loadImage("GuardIdle.png");
			getImageDimensions();
		}
		else {
			
			loadImage("GuardMoving.gif");
			getImageDimensions(); 
			
			double xp = targetX - x;
			double yp = targetY - y;
			
			direction = (Math.atan2(yp, xp) * (180.0/Math.PI)) + 90.0;
			double hyp = Math.sqrt(xp * xp + yp * yp);
			xp /= hyp;
			yp /= hyp;
			
			x += xp * moveSpeed;
			y += yp * moveSpeed;
			oldX = xp * moveSpeed * -1.0; oldY = yp * moveSpeed * -1.0;
		}
		}
	}

}
