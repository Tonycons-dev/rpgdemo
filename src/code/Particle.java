package code;

import javax.swing.ImageIcon;

public abstract class Particle extends Sprite {
	
	protected int lifespan;
	protected float velY;
	protected float velX;
	
	ImageIcon fireParticle = new ImageIcon(folder + "fireparticle.png");
	ImageIcon fireParticle2 = new ImageIcon(folder + "fireparticle2.png");
	ImageIcon fireParticle3 = new ImageIcon(folder + "fireparticle3.png");
	ImageIcon fireParticle4 = new ImageIcon(folder + "fireparticle4.png");
	ImageIcon fireParticle5 = new ImageIcon(folder + "fireparticle5.png");
	
	ImageIcon iceParticle = new ImageIcon(folder + "iceparticle.png");
	ImageIcon iceParticle2 = new ImageIcon(folder + "iceparticle2.png");
	
	ImageIcon sparkParticle = new ImageIcon(folder + "sparkparticle.png");
	ImageIcon sparkParticle2 = new ImageIcon(folder + "sparkparticle2.png");
	ImageIcon sparkParticle3 = new ImageIcon(folder + "sparkparticle3.png");
	ImageIcon sparkParticle4 = new ImageIcon(folder + "sparkparticle4.png");
	ImageIcon sparkParticle5 = new ImageIcon(folder + "sparkparticle5.png");
	
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
