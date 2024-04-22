package code;

import javax.swing.*;

public class ParticleIce extends Particle{

	private static final ImageIcon iceParticle = new ImageIcon(folder + "iceparticle.png");
	private static final ImageIcon iceParticle2 = new ImageIcon(folder + "iceparticle2.png");

	public ParticleIce(int x, int y, int lifespan) {
		super(x, y, lifespan);
		useImage(iceParticle);
		this.lifespan = lifespan;
	}
	
	@Override
	public void update() {
		
		velY += (float)((2*(Math.random())-1)/3.0f);
		y += velY;
		velX += (float)((2*(Math.random())-1)/3.0f);
		x += velX;
		
		lifespan--;
		
		if(lifespan < 13) {
			useImage(iceParticle2);
		}
	}

}
