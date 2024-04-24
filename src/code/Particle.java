package code;

public abstract class Particle extends Sprite {
	
	protected int lifespan;
	protected float velY;
	protected float velX;

	public abstract void update();

	public Particle(int x, int y, int lifespan) {
		super(x, y);
		this.lifespan = lifespan;
	}

	public boolean isDead() {
        return lifespan < 0;
	}
}
