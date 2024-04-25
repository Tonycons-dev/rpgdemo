package code;

public abstract class Entity extends Sprite {
		
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

	/**
	 * Entity constructor
	 * @param x X coordinate
	 * @param y y coordinate
	 * @param aggro Hostility state
	 * @param direction Angle of rotation
	 * @param dNum Dialogue ID it references
	 * @param hp Health points
	 */
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

	/**
	 * Instantiate a derived class Entity with parameters
	 * TODO: Refactor
	 */
	public static Entity NewEntity(int x, int y, int type, int aggro, double direction, int dNum, int hp) 
	{
		switch(type) {
		case 1:
			return new EntityGuard(x, y, aggro, direction, dNum, hp);
		default:
			return new EntityGuard(x, y, aggro, direction, dNum, hp);
		}
	}


	/**
	 * Perform movement towards a target
	 * @param targetX target X coordinate
	 * @param targetY target Y coordinate
	 * @param targetDirection angle towards target position
	 */
	public abstract void performAI(double targetX, double targetY, double targetDirection);

	/**
	 * Sets the health points
	 */
	public void setHP(int amount) {
		hp = amount;
	}

	/**
	 * Deals damage to the Entity
	 * TODO: Refactor
	 * @param amount health points after damage
	 * @param item Item dealing the damage
	 * @param offsetX ?
	 * @param offsetY ?
	 */
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
			ParticleGenerator.add(ParticleSpark.class, (int)(x + offsetX), (int)(y + offsetY), 3);
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
			ParticleGenerator.add(ParticleIce.class, (int)(x + offsetX), (int)(y + offsetY), 10);
		}
	}

	/**
	 * Responds to a tile collision.
	 */
	public void collideWithTile() {
		colliding = true;
		x += oldX;
		y += oldY;
	}

	/**
	 * Set collision state
	 */
	public void setColliding(boolean b) {
		colliding = b;
	}

	/**
	 * @return the hostility level
	 */
	public int getAggro() {
		return aggro;
	}

	/**
	 * @return the current number of health points
	 */
	public int getHP() {
		return hp;
	}

	/**
	 * @return Dialogue ID
	 */
	public int getDialogNumber() {
		return dNum;
	}

	/**
	 * @return Contact damage this entity deals
	 */
	public int getDamage() {
		return dmg;
	}

	/**
	 * @return True if the entity is dead (health is 0)
	 */
	public boolean isDead() {
		return dead;
	}

	/**
	 * @param index index
	 * @return A segment of dialogue this entity uses
	 */
	public static Dialogue getDialogue(int index) {
		return dialogs[index];
	}
}
