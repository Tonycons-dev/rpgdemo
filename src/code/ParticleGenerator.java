package code;

import java.util.List;
import java.awt.Graphics2D;
import java.util.LinkedList;
import java.util.Iterator;

public class ParticleGenerator {


	// REVISED: LinkedList is better because it allows fast removal of elements.
	private static final List<Particle> particles = new LinkedList<>();
	
	public static void add(int x, int y, int amount, int type) {
		
		for(int i = 0; i < amount; i++) {
			particles.add(Particle.NewParticle
				(x+(int)(20 * (Math.random())), y+(int)(20 * (Math.random())), 15, type));
		}
	}
	
	public static void update(Graphics2D buffer) {
		Iterator<Particle> it = particles.iterator();

		while(it.hasNext()) {
			Particle p = it.next();

			if (p.dead()) {
				it.remove();
			}
			else {
				p.update();
				buffer.drawImage(p.getImage(), (int)p.getX(), (int)p.getY(), null);
			}
		}
	}
}