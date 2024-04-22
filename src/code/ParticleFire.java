package code;

import javax.swing.*;

public class ParticleFire extends Particle {

	private static final ImageIcon fireParticle;
	private static final ImageIcon fireParticle2;
	private static final ImageIcon fireParticle3;
	private static final ImageIcon fireParticle4;
	private static final ImageIcon fireParticle5;

	static {
		fireParticle = new ImageIcon(folder + "fireparticle.png");
		fireParticle2 = new ImageIcon(folder + "fireparticle2.png");
		fireParticle3 = new ImageIcon(folder + "fireparticle3.png");
		fireParticle4 = new ImageIcon(folder + "fireparticle4.png");
		fireParticle5 = new ImageIcon(folder + "fireparticle5.png");
	}

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
