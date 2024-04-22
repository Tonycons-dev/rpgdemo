package code;

public abstract class Entity extends Sprite{
		
	protected int aggro;
	protected int dNum;
	protected String dialog;
	
	protected int hp;
	protected int inv;
	protected int dmg;
	
	protected int invframe = 0;
	protected boolean dead;
	
	//Dialogues that entities reference
	private static Dialogue[] dialogs = {
		new Dialogue("src/Dialogues/Dialog1.txt")
	};
	
	public Entity(int x, int y, int aggro, double direction, int dNum, int hp) 
	{
		super(x, y);
		oldX = 0;
		oldY = 0;
		dmg = 5;
		this.aggro = aggro;
		this.dNum = dNum;
		this.direction = direction;
		this.hp = hp;
		inv = 100;
	}
	
	
	public static Entity NewEntity(int x, int y, int type, int aggro, double direction, int dNum, int hp) 
	{
		switch(type) {
		case 1:
			return new EntityGuard(x, y, aggro, direction, dNum, hp);
		default:
			return new EntityGuard(x, y, aggro, direction, dNum, hp);
		}
	}
	
	//Each entity can provide their own implementation of this method
	public abstract void performAI(double targetX, double targetY, double targetDirection);
	
	public void setHP(int amount) {
		hp = amount;
	}
	
	public void damage(int amount, Item item, double offsetX, double offsetY) 
	{
		aggro = 1;
		moveSpeed = 1;
		//How rapidly entities are damaged is based upon the speed of the weapon,
		//If they are being hit by a fast weapon they will have less invincibility frames.
		
		if(invframe == 0) {
			setHP(amount);
			invframe = item.getInvTime();
			//Spawns particles on death
			Generator.addParticle((int)(x + offsetX), (int)(y + offsetY), 3, 2);
		}
		else {
			invframe--;
			if(!colliding) {
				oldX = item.getKnockbackX() / 12.0 * -1.0;
				oldY = item.getKnockbackY() / 12.0 * -1.0;
			x += item.getKnockbackX() / 12.0;
			y += item.getKnockbackY() / 12.0;
			}
		}
		if(hp < 1) {
			dead = true;
			Generator.addParticle((int)(x + offsetX), (int)(y + offsetY), 10, 1);
		}
	}
	
	public void collideWithTile() {
		colliding = true;
		x += oldX;
		y += oldY;
	}
	
	public void setColliding(boolean b) {
		colliding = b;
	}
	
	public int getAggro() {
		return aggro;
	}
	
	public int getHP() {
		return hp;
	}
	
	public int getDialogNumber() {
		return dNum;
	}
	
	public int getDamage() {
		return dmg;
	}
	
	public boolean isDead() {
		return dead;
	}
	
	public static Dialogue getDialogue(int index) {
		return dialogs[index];
	}
}
