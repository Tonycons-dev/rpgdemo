package code;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;


public class Player extends Sprite {

	private static final ImageIcon playerIdle;
	private static final ImageIcon playerMoving;
	private static final ImageIcon playerAttack;
	private static final ImageIcon playerMovingAttack;
	private byte item;
	private boolean interact;
	private boolean moving;
	private boolean forward;
	private boolean backward;
	private boolean use;
	private boolean left;
	private boolean right;
	private boolean strafe;
	private boolean inventoryOpen;
	private boolean upArrow;
	private boolean downArrow;
	private boolean enterKey;
	private boolean allowEnter;
	private double scrollX;
	private double scrollY;
	private int scrollingMode;
	private int hp;
	private int maxHp;
	private int invframe;
	private boolean hit;
	private boolean hScrollEnabled;
	private boolean vScrollEnabled;
	private boolean hLineCrossed;
	private boolean vLineCrossed;
	private int coins;


	static {
		playerIdle = new ImageIcon(folder+"Player_Idle.png");
		playerMoving = new ImageIcon(folder+"Player_Moving.gif");
		playerAttack = new ImageIcon(folder+"Player_Attack.png");
		playerMovingAttack = new ImageIcon(folder+"Player_Attack_Moving.gif");
	}

	/**
	 * Player constructor
	 * @param x X coordinate
	 * @param y Y coordinate
	 */
	public Player(int x, int y) {
		super(x, y);

		colliding      = false;
		moving 	       = false;
		direction      = 0;
		moveSpeed      = 1.5;
		rotationSpeed  = 3;
		item           = 0;
		hp             = 50;
		maxHp          = 50;
		invframe       = 100;
		hit            = false;
		use            = false;
		interact       = false;
		hScrollEnabled = false;
		vScrollEnabled = false;
		hLineCrossed   = false;
		vLineCrossed   = false;
		scrollX        = 0;
		scrollY        = 0;
		upArrow        = false;
		downArrow      = false;
		enterKey       = false;
		coins = 0;
		useImage(playerIdle);
	}

	/**
	 * Calculates the position of the player for the next frame.
	 */
	public void move() {
		if(moving) {
			if(forward && !colliding) {
				moveX = moveSpeed * Math.cos(Math.toRadians(direction));
				moveY = moveSpeed * Math.sin(Math.toRadians(direction));

				oldX = moveX * -1.0;
				oldY = moveY * -1.0;

				if(hScrollEnabled && vLineCrossed)
					scrollX -= moveX;
				else
					x += moveX;

				if(vScrollEnabled && hLineCrossed)
					scrollY -= moveY;
				else
					y += moveY;
			}
			if(backward && !colliding) {
				moveX = moveSpeed * Math.cos(Math.toRadians(direction)) * -1.0;
				moveY = moveSpeed * Math.sin(Math.toRadians(direction)) * -1.0;

				oldX = moveX * -1.0;
				oldY = moveY * -1.0;

				if(hScrollEnabled && vLineCrossed)
					scrollX -= moveX;
				else
					x += moveX;

				if(vScrollEnabled && hLineCrossed)
					scrollY -= moveY;
				else
					y += moveY;
			}
			if(left && strafe && !forward && !backward)
			{
				moveX = moveSpeed * Math.cos(Math.toRadians(direction + 90)) * -1.0;
				moveY = moveSpeed * Math.sin(Math.toRadians(direction + 90)) * -1.0;

				oldX = moveX * -1.0;
				oldY = moveY * -1.0;

				if(hScrollEnabled && vLineCrossed)
					scrollX -= moveX;
				else
					x += moveX;
				if(vScrollEnabled && hLineCrossed)
					scrollY -= moveY;
				else
					y += moveY;
			}
			else if (left)
			{
				if(direction == 360)
					direction = 0;
				else
					direction -= rotationSpeed;
			}
			if(right && strafe && !forward && !backward) {
					moveX = moveSpeed * Math.cos(Math.toRadians(direction - 90)) * -1.0;
					moveY = moveSpeed * Math.sin(Math.toRadians(direction - 90)) * -1.0;

					oldX = moveX * -1.0;
					oldY = moveY * -1.0;

					if(hScrollEnabled && vLineCrossed)
						scrollX -= moveX;
					else
						x += moveX;

					if(vScrollEnabled && hLineCrossed)
						scrollY -= moveY;
					else
						y += moveY;
			}
			else if (right) {
				if(direction == 360)
					direction = 0;
				else
					direction += rotationSpeed;
			}
			if(use)
				useImage(playerMovingAttack);
			else
				useImage(playerMoving);
		}

		if(!left && !right && !forward && !backward)
		{
			if(use)
				useImage(playerAttack);
			else
				useImage(playerIdle);
		}
	}

	/**
	 * Updates player state based on keyboard key pressed.
	 */
	public void keyPressed(KeyEvent e)
	{
		// FIXED: Upgraded to switch statements
		switch (e.getKeyCode()) {
		case KeyEvent.VK_W:
			forward = true;
			moving = true;
			break;

		case KeyEvent.VK_S:
			backward = true;
			moving = true;
			break;

		case KeyEvent.VK_D:
			right = true;
			moving = true;
			break;

		case KeyEvent.VK_A:
			left = true;
			moving = true;
			break;

		case KeyEvent.VK_F:
			interact = true;
			break;

		case KeyEvent.VK_SPACE:
			item = 0;
			use = true;
			break;

		case KeyEvent.VK_SHIFT:
			item = 1;
			use = true;
			break;

		case KeyEvent.VK_ESCAPE:
			inventoryOpen = true;
			break;

		case KeyEvent.VK_UP:
			upArrow = true;
			break;

		case KeyEvent.VK_DOWN:
			downArrow = true;
			break;

		case KeyEvent.VK_ENTER:
			if (allowEnter)
				enterKey = true;
			break;

		case KeyEvent.VK_CONTROL:
			strafe = true;
			break;
		}
	}

	/**
	 * Updates player state based on keyboard key released.
	 */
	public void keyReleased(KeyEvent e) {

		switch (e.getKeyCode()) {
		case KeyEvent.VK_W:
			moving = false;
			forward = false;
			break;

		case KeyEvent.VK_S:
			moving = false;
			backward = false;
			break;

		case KeyEvent.VK_D:
			right = false;
			break;

		case KeyEvent.VK_A:
			left = false;
			break;

		case KeyEvent.VK_F:
			interact = false;
			break;

		case KeyEvent.VK_SPACE:
			item = 2;
			use = false;
			break;

		case KeyEvent.VK_SHIFT:
			item = 2;
			use = false;
			break;

		case KeyEvent.VK_ESCAPE:
			inventoryOpen = false;
			break;

		case KeyEvent.VK_UP:
			upArrow = false;
			break;

		case KeyEvent.VK_DOWN:
			downArrow = false;
			break;

		case KeyEvent.VK_ENTER:
			enterKey = false;
			if(!allowEnter)
				allowEnter = true;
			break;
		case KeyEvent.VK_CONTROL:
			strafe = false;
			break;
		}
	}

	/**
	 * Receives damage from an entity.
	 */
	public void takeContactDamageFrom(Entity entity) {
		hit = true;
		if(invframe == 0) {
			hp -= entity.getDamage();
			invframe = 100;
		}
		else {
			invframe--;
		}
	}

	/**
	 * 	Regen while not being touched by an attack at all
	 * 	Using invincibility frames prevents constant regen
	 */
	public void regen() {
		if(!hit) {
			
			if(invframe == 0) {
				if(hp < maxHp)
					hp++;
				invframe = 100;
			}
			else {
				invframe--;
			}
		}
	}

	/**
	 * 	Simple collision physics but effective for directional movement,
	 * 	where the player moves in the opposite direction when hitting a tile
	 */
	public void collideWithTile() {
		colliding = true;
		if(hScrollEnabled && vLineCrossed) {
			scrollX -= oldX;
		}
		else
			x += oldX;
		if(vScrollEnabled && hLineCrossed) {
			scrollY -= oldY;
		}
		else
			y += oldY;
	}
	
	public void endTileCollision() {
		colliding = false;
	}
	
	public void setHit(boolean b) {
		hit = b;
	}

	/**
	 * Set player's coordinates
	 * @param x X coordinate
	 * @param y Y coordinate
	 */
	public void setPosition(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	//Determine how the screen will scroll
	public void setScrollingMode(int sm) {
		scrollingMode = sm;
		
		switch(scrollingMode)
		{
		case 0: hScrollEnabled = false; vScrollEnabled = false;break;
		case 1: hScrollEnabled = true; vScrollEnabled = false;break;
		case 2: hScrollEnabled = false; vScrollEnabled = true;break;
		case 3: hScrollEnabled = true; vScrollEnabled = true; break;
		}
	}
	
	public void setHLine(boolean isHLineCrossed) {
		hLineCrossed = isHLineCrossed;

	}
	
	public void setVLine(boolean isVLineCrossed) {
		vLineCrossed = isVLineCrossed;

	}
	
	public void setScrollX(int sx) {
		scrollX = sx;
	}
	
	public void setScrollY(int sy) {
		scrollY = sy;
	}
	
	public void setEnterKey(boolean b) {
		enterKey = b;
	}
	
	public void allowEnterKey(boolean b) {
		allowEnter = b;
	}
	
	public boolean isScrollingEnabled() {
		if(hScrollEnabled || vScrollEnabled)
			return true;
		else
			return false;
	}
		
	public boolean isColliding() {
		return colliding;
	}
	
	public int getScrollingMode() {
		return scrollingMode;
	}
	
	public double getScrollX() {
		return scrollX;
	}
	
	public double getScrollY() {
		return scrollY;
	}
	
	public double getMoveSpeed() {
		return moveSpeed;
	}
	
	public int getHP() {
		return hp;
	}
	
	public int getMaxHP() {
		return maxHp;
	}
	
	public byte usedItemSlot() {
		return item;
	}
	
	public boolean isInventoryOpen() {
		return inventoryOpen;
	}
	
	public boolean isUsingItem() {
		return use;
	}
	
	public boolean isInteracting() {
		return interact;
	}
	
	public boolean isUpArrow() {
		return upArrow;
	}
	
	public boolean isDownArrow() {
		return downArrow;
	}
	
	public boolean isEnterKey() {
		return enterKey;
	}
	
	public boolean isEnterAllowed() {
		return allowEnter;
	}
	public int getCoins()
	{
		return coins;
	}
	public void addCoins(int a)
	{
		coins += a;
	}
	public void subtractCoins(int a)
	{
		coins -= a;
	}
}
