package code;

import javax.swing.ImageIcon;

public abstract class Particle extends Sprite {
	
	protected int lifespan;
	protected float velY;
	protected float velX;

	
	public Particle(int x, int y, int lifespan) {
		super(x, y);
		this.lifespan = lifespan;
	}
	
	public static Particle NewParticle(int x, int y, int lifespan, int type) {
		switch(type) {
		case 0:
			
			return new ParticleFire(x,  y, lifespan);
		
		case 1:
			
			return new ParticleIce(x, y, 60);
			
		case 2:
			
			return new ParticleSpark(x, y, 30);
			
		default:
			
			return new ParticleFire(x,  y, lifespan);	
		}
	}
	
	public Particle() {
		super(200, 200);
		
		this.lifespan = 100;
	}
	
	public abstract void update();
	
	public boolean dead() {
		if(lifespan < 0)
			return true;
		else
			return false;
	}
}
