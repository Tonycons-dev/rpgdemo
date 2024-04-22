package code;

public class ParticleSpark extends Particle{

	public ParticleSpark(int x, int y, int lifespan) {
		super(x, y, lifespan);
		useImage(sparkParticle);
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
			useImage(sparkParticle3);
		}
		if(lifespan < 8) {
			useImage(sparkParticle5);
		}
	}

}
