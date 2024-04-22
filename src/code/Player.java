package code;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;


public class Player extends Sprite
{
		
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
	
ImageIcon playerIdle 
	= new ImageIcon(folder+"Player_Idle.png");

ImageIcon playerMoving 
	= new ImageIcon(folder+"Player_Moving.gif");

ImageIcon playerAttack 
	= new ImageIcon(folder+"Player_Attack.png");

ImageIcon playerMovingAttack 
	= new ImageIcon(folder+"Player_Attack_Moving.gif");


public Player(int x, int y) 
{
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
	
	useImage(playerIdle);
	getImageDimensions();
}
	
public void move() 
{
	if(moving){ 
	if(forward && !colliding) 
	{
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
	if(backward && !colliding) 
	{
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
	if(left) {	
	if(strafe && !forward && !backward) 
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
	else 
	{
		if(direction == 360)
			direction = 0;
		else
			direction -= rotationSpeed; 
	}
	}
	if(right) {
	if(strafe && !forward && !backward) 
	{
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
	else 
	{	
		if(direction == 360)
			direction = 0;
		else 
			direction += rotationSpeed; 
	}
	}
	if(use)
		useImage(playerMovingAttack);
	else
		useImage(playerMoving);
		getImageDimensions();
	}
	
	if(!left && !right && !forward && !backward) 
	{
		if(use)
			useImage(playerAttack);
		else
			useImage(playerIdle);
		getImageDimensions();
	}
}
	
public void keyPressed(KeyEvent e) 
{
	int key = e.getKeyCode();
	if(key == KeyEvent.VK_W) {
		forward = true;
		moving = true;
	}
		
	if(key == KeyEvent.VK_S) {
		backward = true;
		moving = true;
	}
		
	if(key == KeyEvent.VK_D) {
		right = true;
		moving = true;
	}	
		
	if(key == KeyEvent.VK_A) {
		left = true;
		moving = true;
	}
		
	if(key == KeyEvent.VK_F) {
		interact = true;
	}
		
	if(key == KeyEvent.VK_SPACE) {
		item = 0; use = true;
	}
	
	if(key == KeyEvent.VK_SHIFT) {
		item = 1; use = true;
	}
	if(key == KeyEvent.VK_ESCAPE) {
		inventoryOpen = true;
	}
	if(key == KeyEvent.VK_UP) {
		upArrow = true;
	}
	if(key == KeyEvent.VK_DOWN) {
		downArrow = true;
	}
	if(key == KeyEvent.VK_ENTER && allowEnter) {
		enterKey = true;
	}
	if(key == KeyEvent.VK_CONTROL) {
		strafe = true;
	}
	}
	
	public void keyReleased(KeyEvent e) {
	int key = e.getKeyCode();
	if(key == KeyEvent.VK_W) {
		moving = false;
		forward = false;
	}
	if(key == KeyEvent.VK_S) {
		moving = false;
		backward = false;
	}
    if(key == KeyEvent.VK_D) {
    	right = false;
    }
    if(key == KeyEvent.VK_A) {
    	left = false;
    }
	if(key == KeyEvent.VK_F) {
		interact = false;
	}
	if(key == KeyEvent.VK_SPACE) {
		item = 2; use = false;
	}
	if(key == KeyEvent.VK_SHIFT) {
		item = 2; use = false;
	}
	if(key == KeyEvent.VK_ESCAPE) {
		inventoryOpen = false;
	}
	if(key == KeyEvent.VK_UP) {
		upArrow = false;
	}
	if(key == KeyEvent.VK_DOWN) {
		downArrow = false;
	}
	if(key == KeyEvent.VK_ENTER) {
		enterKey = false;
		if(!allowEnter)
			allowEnter = true;
	}
	if(key == KeyEvent.VK_CONTROL) {
		strafe = false;
	}
	}
	
	//Same invincibility frame system
	
	public void damage(Entity entity) {
		hit = true;
		if(invframe == 0) {
			hp -= entity.getDamage();
			invframe = 100;
		}
		else {
			invframe--;
		}
		
	}
	
	//Regen while not being touched by an attack at all
	//Using invincibility frames prevents constant regen
	
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
	
	//Simple collision physics but effective for directional movement,
	//where the player moves in the opposite direction when hitting a tile
	
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
	public void setCoords(double xc, double yc) {
		x = xc; y = yc;
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
	
}
