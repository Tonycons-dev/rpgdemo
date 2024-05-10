package code;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.Serial;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.Timer;
import com.google.gson.*;

public class Main extends JPanel implements ActionListener {
	
	@Serial
	private static final long serialVersionUID = 1L;
	private final int scaledWidth;
    private final int scaledHeight;
	private final int borderX;
    private final int borderY;
	private boolean ingame;
	private final Timer timer;
	private Player player;
	private final BufferedImage bufferImage;
	private final Graphics2D buffer;
	private final Color background;
	private Room room;

	// True if a fade transition is going on.
	private boolean fading;

	// The current level of transparency in the fade transition
	private float fadeTransparency;

	private Stack<Dialogue> dialogues = new Stack<>();

	private static class StartLocation {
		private String start;
		private int[] position;
	};

	public void restartGame() throws IOException {

		ingame = true;

		dialogues.clear();

		var start = new Gson().fromJson(
				Files.readString(Path.of(System.getProperty("user.dir") + "/src/Maps/start.json")), StartLocation.class);

		player = new Player(start.position[0], start.position[1]);
		room = new Room(Files.readString(Path.of(System.getProperty("user.dir") + "/src/Maps/" + start.start + ".json")));

		Inventory.reset();
		Inventory.addItem(1, 0);
	}

	public Main(int scaleWidth, int scaleHeight, int screenWidth, int screenHeight) throws IOException {
		addKeyListener(new Adapter());

		background = new Color(87, 86, 84);
		setBackground(Color.BLACK);
		setFocusable(true);

		//Initialize graphics
		bufferImage = new BufferedImage(640, 480, BufferedImage.TYPE_INT_RGB);
		buffer = bufferImage.createGraphics();
		buffer.setColor(background);
		buffer.fillRect(0, 0, 640, 480);

		restartGame();

        timer = new Timer(15, this);
		timer.start();

		scaledWidth = scaleWidth;
		scaledHeight = scaleHeight;

		borderX = (screenWidth - scaleWidth) / 2;
		borderY = (screenHeight - scaleHeight) / 2;
	}

	public void tutorial()
	{
		startDialogue("Tutorial");
	}

	private void renderTiles() {
		AffineTransform oldTransform = buffer.getTransform();

		for(Tile tile : room.getTiles()) {
			if (tile == null)
				continue;
			//Draw tiles with offset
			buffer.drawImage(tile.getImage(), (int)tile.getX(), (int)tile.getY(), this);
		}
		buffer.setTransform(oldTransform);
	}

	private void renderEntities() {
		AffineTransform oldTransform = buffer.getTransform();

		for (Entity entity : room.getEntities()) {
			buffer.setTransform(oldTransform);

			buffer.rotate(Math.toRadians(entity.getDirection()),
					entity.getX() + entity.getWidth() / 2.0,
					entity.getY() + entity.getHeight() / 2.0);

			buffer.drawImage(entity.getImage(), (int)entity.getX(), (int)entity.getY(), this);
			buffer.setTransform(oldTransform);
		}
	}

	private void renderPlayer() {
		if (!player.isVisible())
			return;

		AffineTransform oldTransform = buffer.getTransform();

		for (int i = 0; i < 2; i++) {
			if (player.usedItemSlot() != i || Inventory.getItem(i) == null)
				continue;

			Item t = Inventory.getItem(i);

			buffer.rotate(Math.toRadians(t.getDirection()),
				t.getX() + t.getWidth() / 2.0, t.getY() + t.getHeight() / 2.0);

			buffer.drawImage(t.getImage(), (int)t.getX(), (int)t.getY(), this);
			buffer.setTransform(oldTransform);
		}

		// Rotate and draw player

		buffer.rotate(Math.toRadians(player.getDirection()),
				player.getX() + player.getWidth() / 2.0,
				player.getY() + player.getHeight() / 2.0);

		buffer.drawImage(player.getImage(),
				(int)player.getX(), (int)player.getY(), this);

		buffer.setTransform(oldTransform);
	}

	private void renderParticles() {
		ParticleGenerator.update(buffer);
	}

	private void renderMenu() {
		//AffineTransform oldTransform = buffer.getTransform();

		if (!Menu.isVisible())
			return;

		buffer.drawImage(Menu.closedInventory(), Menu.getX(), Menu.getY(), this);

		if(Inventory.getItem(0) != null)
			buffer.drawImage(Inventory.getItem(0).getImage(), 80, 432, this);

		if(Inventory.getItem(1) != null)
			buffer.drawImage(Inventory.getItem(1).getImage(), 130, 432, this);

		buffer.setColor(Color.BLACK);

		buffer.drawString("WASD: Move: ", 300, 416);
		buffer.drawString("SPACE: Item 1", 300, 432);
		buffer.drawString("SHIFT: Item 2", 300, 448);
		buffer.drawString("E: Inventory ", 396, 416);
		buffer.drawString("F: Interact ", 396, 432);
		buffer.drawString("G: Guide", 396, 448);
		buffer.drawString("Coins: " + player.getCoins(),  512, 416);

		Menu.showHealthBar(player, buffer);

		if(player.isInventoryOpen())
			Inventory.updateGUI(buffer);

		//buffer.setTransform(oldTransform);
	}


	@Override
	public void paintComponent(Graphics g) {
		//super.paintComponent(g);
		buffer.setColor(background);
		buffer.fillRect(0, 0, 640, 480);

		var g2d = (Graphics2D)g;

		if (!ingame) {
			Toolkit.getDefaultToolkit().sync();
			return;
		}
		if (fading) {
			player.stopAnimating();
			// Advance the fade transition
			g2d.setComposite(AlphaComposite.getInstance(
					AlphaComposite.SRC_OVER, fadeTransparency));

			fadeTransparency += 0.01f;
			if (fadeTransparency >= 0.3f) {
				fadeTransparency = 1.0f;
				fading = false;
				g2d.setComposite(AlphaComposite.getInstance(
						AlphaComposite.SRC_OVER, 1.0f));
			} else {
				// Paint recursively until the fade transition is over
				repaint();
			}
		}
		AffineTransform oldTransform = buffer.getTransform();
		AffineTransform scrollOffset = new AffineTransform(oldTransform);

		if (room.getSize().width > 18 && player.getX() > 288) {
			scrollOffset.translate(288 + player.getDeltaX(), 0);
			buffer.setTransform(scrollOffset);
		}
		if (room.getSize().height > 12 && player.getY() > 192) {

			var yy = 192 - player.getY();
			if (yy < -(room.getSize().height * 16))
				yy = -(room.getSize().height * 16);

			scrollOffset.translate(0, yy);
			buffer.setTransform(scrollOffset);
		}
		renderTiles();
		renderPlayer();
		renderEntities();
		renderParticles();
		renderDialogues();
		LevelMap.render(buffer, this);

		// We don't want the menu to get scrolled
		buffer.setTransform(oldTransform);
		renderMenu();

		g2d.drawImage(bufferImage, borderX, borderY, scaledWidth, scaledHeight, null);
		Toolkit.getDefaultToolkit().sync();
		System.gc(); // reduces lag??
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (fading)
			return;

		checkIngameState();
		checkCollisions();

		if (dialogues.empty())
			updatePlayer();

		updateEntities();
		updateItem();
		repaint();
	}

	private void checkIngameState() {
		if(!ingame) {
			try {
				// TODO
				restartGame();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}
	
	public void checkCollisions()
	{
		Rectangle playerBounds = player.getBounds();

		for (Tile tile : room.getTiles()) {

			if (tile == null)
				continue;

			if (!(tile.getX() > -1 && tile.getX() < 640))
				continue;

			Rectangle tileBounds = tile.getBounds();

			if (tile.emitsParticle()) {
				ParticleGenerator.add(tile.getParticleClass(), (int)tile.getX(), (int)tile.getY(), 1);
			}
			if (!(playerBounds.intersects(tileBounds) && tile.isSolid())) {
				player.endTileCollision();
				continue;
			}
			if (tile.isDoor()) {
				String nextRoom = room.getNextRoomName(tile.getX(), tile.getY());

				if (nextRoom != null) {
					enterNewRoom(nextRoom, tile);
				}
				else {
					player.collideWithTile();
				}
			}
			else if (tile.isLockedDoor()) {
				// TODO
				player.collideWithTile();
			}
			else {
				player.collideWithTile();
			}
		}
	}

	private void enterNewRoom(String nextRoom, Tile door) {
		try {
			room = new Room(Files.readString(
					Path.of(System.getProperty("user.dir") + "/src/Maps/" + nextRoom + ".json")));
		} catch (IOException i) {
			throw new RuntimeException(i);
		}

		// The player needs to move in order to appear relative to the door he just walked in
		if (door.isUpwardDoor() || door.isDownwardDoor()) {
			player.setY(player.getY() > 192 ? 48 : 320);
		}
		else if (door.isLeftwardDoor() || door.isRightwardDoor()) {
			player.setX(player.getX() > 288 ? 48 : 544);
		}

		// Begins the fade transition
		ParticleGenerator.clear();
		fading = true;
		fadeTransparency = 0.0f;
	}
	
	private void updatePlayer() {
		if(player.isVisible())
			player.move();

		if(!player.isEnterAllowed())
			player.setEnterKey(false);

		if (player.getHP() <= 0)
			startDialogue("GameOverDialogue");

		player.regen();
	}

	private void updateEntities() {
		for(Iterator<Entity> iter = room.getEntities().iterator(); iter.hasNext();) {
			Entity entity = iter.next();

			if(entity.isDead())
				iter.remove();

			Tile tile = room.getTileAt(entity.getX(), entity.getY());

			if (tile != null && tile.isSolid())
				entity.collideWithTile();
			else
				entity.setColliding(false);

			entity.performAI(player.getX(), player.getY(), player.getDirection());

			Rectangle entityBounds = entity.getBounds();

//					((int)(entity.getX() + player.getScrollX()),
//							(int)(entity.getY()+player.getScrollY()));

			Rectangle playerBounds = player.getBounds();
			player.setHit(false);
			if(playerBounds.intersects(entityBounds)) {
				//If hostile, deal damage, else provide dialogue
				if(entity.getAggro() == 1) {
					player.takeContactDamageFrom(entity);
				}
				else if(player.isInteracting()) {
					entity.whenInteracted();
					//If friendly, provide dialogue
					//Dialogue dialogue = Entity.getDialogue(entity.getDialogNumber());
					//dialogue.showDialog(player, buffer, 204, 111, 200, 130);
				}
			}
			if(player.isUsingItem()) {
				//Check if the item is hitting the entity
				Item item = Inventory.getItem(player.usedItemSlot());

				// If there's nothing in the slot of course don't use it
				if (item == null)
					continue;

				Rectangle itemBounds = item.getBounds();

				if(itemBounds.intersects(entityBounds)) {
					entity.damage(entity.getHP() - item.getMeleeDamage(), item, 0, 0);
					if (entity.isDead())
						player.addCoins(entity.getCoinValue());
				}
			}
		}
	}

	private void updateItem()
	{
		if (player.isUsingItem()) {
			Inventory.useItem(
				player.getX(), player.getY(), player.getWidth(), player.getHeight(),
				player.getDirection(), player.usedItemSlot());
		}
		if (player.isInventoryOpen()) {
			Inventory.updateCursor();
		}
	}



	private class Adapter extends KeyAdapter
	{
		@Override
		public void keyReleased(KeyEvent e) {
			player.keyReleased(e);
		}
		@Override
		public void keyPressed(KeyEvent e) {
			try {
				if (e.getKeyCode() == KeyEvent.VK_G) {
					startDialogue("Tutorial");
				}
				player.keyPressed(e);
				Inventory.keyPressed(e);
				//LevelMap.keyPressed(e); Scrap this

				if (!dialogues.empty()) {
					dialogues.peek().keyPressed(player, e);

					if (dialogues.peek().isFinished()) {
						dialogues.pop();
					}
				}
			} catch (GameOverException gameOver) {
				ingame = false;
			}
		}
	}


	/**
	 * Initiates a dialogue.
	 * @param name File name relative to Dialogues folder
	 */
	public static void startDialogue(String name)
	{
		Main main = Driver.programMain;

		for (Dialogue d : main.dialogues) {

			// Cannot start a dialogue if it's already started
			if (d.name.equals(name)) {
				return;
			}
		}
		try {
			main.dialogues.push(new Dialogue(name, main.player,
					Files.readString(Path.of(System.getProperty("user.dir") + "/src/Dialogues/" + name + ".json"))));
		} catch (IOException e) {
			throw new RuntimeException("Dialogue not found: " + name);
		}
	}

	private void renderDialogues() {
		if (dialogues.empty())
			return;

		Dialogue d = dialogues.peek();

		var frame = d.getCurrentFrame();
		if (frame == null)
			return;

		var sb = new StringBuilder();
		sb.append(frame.text).append("\n\n");

		int i = 1;
		for (Dialogue.Descriptor.Frame.Option o : frame.options) {
			sb.append("[").append(i).append("] ").append(o.text).append("\n\n");
			i++;
		}
		Menu.showDialogBox(buffer, 64, 64, 512, 256, sb.toString());
	}
}
