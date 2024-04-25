package code;

import javax.swing.*;

public class ParticleSpark extends Particle{

	private static final ImageIcon sparkParticle;
	private static final ImageIcon sparkParticle2;
	private static final ImageIcon sparkParticle3;
	private static final ImageIcon sparkParticle4;
	private static final ImageIcon sparkParticle5;

	static {
		sparkParticle = new ImageIcon(folder + "sparkparticle.png");
		sparkParticle2 = new ImageIcon(folder + "sparkparticle2.png");
		sparkParticle3 = new ImageIcon(folder + "sparkparticle3.png");
		sparkParticle4 = new ImageIcon(folder + "sparkparticle4.png");
		sparkParticle5 = new ImageIcon(folder + "sparkparticle5.png");
	}

	/**
	 * @see Particle constructor
	 */
	public ParticleSpark(int x, int y, int lifespan) {
		super(x, y, lifespan);
		useImage(sparkParticle);
		this.lifespan = lifespan;
	}

	/**
	 * @see Particle update
	 */
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
