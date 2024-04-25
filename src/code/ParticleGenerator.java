package code;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.awt.Graphics2D;
import java.util.LinkedList;
import java.util.Iterator;

public class ParticleGenerator {


	// REVISED: LinkedList is better because it allows fast removal of elements.
	private static final List<Particle> particles = new LinkedList<>();

	/**
	 * Creates a particle with a random lifetime.
	 * @param c Particle class
	 * @param x X coordinate to spawn particle
	 * @param y Y coordinate to spawn particle
	 * @param amount Number of particles
	 */
	public static <T extends Particle> void add(Class<T> c, int x, int y, int amount) {
		for(int i = 0; i < amount; i++) {
			try {
				particles.add(c.getDeclaredConstructor(int.class, int.class, int.class)
						.newInstance(x+(int)(20 * (Math.random())), y+(int)(20 * (Math.random())), (int)(24 * Math.random())));
			} catch (NoSuchMethodException | InstantiationException |
					 IllegalAccessException | InvocationTargetException ignored) {}
		}
	}

	/**
	 * Renders the active particles.
	 */
	public static void update(Graphics2D buffer) {
		Iterator<Particle> it = particles.iterator();

		while(it.hasNext()) {
			Particle p = it.next();

			if (p.isDead()) {
				it.remove();
			}
			else {
				p.update();
				buffer.drawImage(p.getImage(), (int)p.getX(), (int)p.getY(), null);
			}
		}
	}
}