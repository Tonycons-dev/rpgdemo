package code;

public class ParticleFire extends Particle{
	
	public ParticleFire(int x, int y, int lifespan) {
		super(x, y, lifespan);
		useImage(fireParticle);
		this.lifespan = lifespan;
	}
	
	@Override
	public void update() {
		
		velY += (float)(2*(Math.random())-1);
		y += velY;
		velX += (float)(2*(Math.random())-1);
		x += velX;
		
		lifespan--;
		
		if(lifespan < 13) {
			useImage(fireParticle3);
		}
		if(lifespan < 8) {
			useImage(fireParticle5);
		}
		
	}

}
