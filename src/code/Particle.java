package code;

public abstract class Particle extends Sprite {
	
	protected int lifespan;
	protected float velY;
	protected float velX;

	public abstract void update();

	/**
	 * Particle Constructor
	 * @param x X coordinate
	 * @param y Y coordinate
	 * @param lifespan Number of ticks particle will be active
	 */
	public Particle(int x, int y, int lifespan) {
		super(x, y);
		this.lifespan = lifespan;
	}

	/**
	 * Returns true if the time limit has expired for particle's existence
	 */
	public boolean isDead() {
        return lifespan < 0;
	}
}
