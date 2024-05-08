package code;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import javax.swing.JPanel;
import javax.swing.Timer;

public class Main extends JPanel implements ActionListener
{
	
private static final long serialVersionUID = 1L;
	
private final int PLAYER_X = 32;
private final int PLAYER_Y = 288;
private final int DELAY = 15;
	
private int zone;
private int initialX;
private int initialY;
private int offsetX; 
private int offsetY;
private int scaledWidth, scaledHeight;
private int borderX, borderY;
	
private boolean[] loadedRooms;
private boolean ingame;
private boolean isTransitioning;
private boolean hLineCrossed;
private boolean vLineCrossed;
	
private Timer timer;
private Player player;
private BufferedImage bufferImage;
private Graphics2D buffer;
private Color background;
		
private List<Tile> tiles;
private List<Entity> entities;


public Main(int scaleWidth, int scaleHeight, int screenWidth, int screenHeight) 
{
	//Initialize arrays
	Tilemaps.init();
	addKeyListener(new Adapter());
		
	background = new Color(87, 86, 84);
	setBackground(Color.BLACK);
	setFocusable(true);
		
	//Initialize graphics
	bufferImage = new BufferedImage(640, 480, BufferedImage.TYPE_INT_RGB);
	buffer = bufferImage.createGraphics();
	buffer.setColor(background);
	buffer.fillRect(0, 0, 640, 480);
		
	ingame = true; zone = 128;
	loadedRooms = new boolean[256];
	isTransitioning = false;
		
	player = new Player(PLAYER_X, PLAYER_Y);
	initialX = PLAYER_X; 
	initialY = PLAYER_Y;
		
	Inventory.addItem(1, 0);
	Inventory.addItem(2, 1);
		
	timer = new Timer(DELAY, this);
	timer.start();

	scaledWidth = scaleWidth; 
	scaledHeight = scaleHeight;
	
	borderX = (screenWidth - scaleWidth) / 2;
	borderY = (screenHeight - scaleHeight) / 2;
	
	tiles = new ArrayList<>();
	player.setScrollX(0);
	player.setScrollY(0);
	
	Tilemaps.clearMap();
	
	for(int i : Tilemaps.getConnectedRooms(zone)) 
	{
		int x = 0, y = 0;

		int diff = zone - i; 
		int sign = diff < 0 ? -1 : 1;
		
		offsetY = 352 * -1 * (diff / 16);
		offsetX = 640 * (sign * (diff % 16));
		
		if(offsetX != 0 && offsetY != 0)
			player.setScrollingMode(3);
		else if(offsetX != 0)
			player.setScrollingMode(1);
		else if(offsetY != 0)
			player.setScrollingMode(2);
		else 
			player.setScrollingMode(0);
		
		for(int k : Tilemaps.loadMap(i)) 
		{
			if(x == 640) {
				x = 0; 
				y += 32;
			}
			if(k != 0)
				tiles.add(new Tile(offsetX + x, offsetY + y, k));
			else
				tiles.add(null);
			
			x += 32; 
		}
		
		loadedRooms[i] = true;
	}

	entities = new ArrayList<>();
	for(int i : Entitymaps.getConnectedMap(zone)) 
	{
		int diff = zone - i;
		int sign = diff < 0 ? -1 : 1;
			
		offsetY = 352 * -1 * (diff / 16);
		offsetX = 640 * (sign * (diff % 16));
			
		for(int[] k : Entitymaps.getemap(zone)) {
			entities.add(Entity.NewEntity
			(offsetX + k[0], offsetY + k[1], 
			k[2], k[3], (double)k[4], k[5], k[6]));
		} 
	}
}
	
@Override
public void paintComponent(Graphics g) 
{
	super.paintComponent(g);
		
	if(ingame) {
		AffineTransform oldTransform = buffer.getTransform();
		
	if(isTransitioning) 
	{
		//Reset map states
		tiles.clear();
		player.setScrollX(0);
		player.setScrollY(0);
		
		//used to restore previous rotations
		buffer.setTransform(oldTransform);
		Tilemaps.clearMap();
		
		for(int i : Tilemaps.getConnectedRooms(zone)) 
		{
			int x = 0, y = 0;
				
			int diff = zone - i;
			int sign = diff < 0 ? -1 : 1;
			
			offsetY = 352 * -1 * (diff / 16);
			offsetX = 640 * (sign * (diff % 16));
			
			if(offsetX != 0 && offsetY != 0)
				player.setScrollingMode(3);
			else if(offsetX != 0)
				player.setScrollingMode(1);
			else if(offsetY != 0)
				player.setScrollingMode(2);
			else
				player.setScrollingMode(0);

			for(int k : Tilemaps.loadMap(i)) 
			{
				if(x == 640) {
					x = 0; 
					y += 32;
				}
				if(k != 0)
					tiles.add(new Tile(offsetX + x, offsetY + y, k));
				else
					tiles.add(null);
				
				x += 32; 
			}
			loadedRooms[i] = true;
		}
		entities.clear();
		
		for(int i : Entitymaps.getConnectedMap(zone)) 
		{

			int diff = zone - i;
			int sign = diff < 0 ? -1 : 1;
			
			offsetY = 352 * -1 * (diff / 16);
			offsetX = 640 * (sign * (diff % 16));
			
			System.out.println(zone);
			
			for(int[] k : Entitymaps.getemap(zone)) {
				entities.add(Entity.NewEntity
				(offsetX + k[0], offsetY + k[1], 
				k[2], k[3], (double)k[4], k[5], k[6]));
			} 
		}
		isTransitioning = false;
	}
	else 
	{	
		if(player.isScrollingEnabled()) {
			for(Tile tile : tiles) {
				//Draw tiles with offset
				if(tile != null)
				buffer.drawImage(tile.getImage(), 
				(int)(player.getScrollX()) + tile.getTX(), 
				(int)(player.getScrollY()) + tile.getTY(), this);
			} 
		} 
		else {
			for(Tile tile : tiles) {
				//Draw tiles without offset
			if(tile != null)
			buffer.drawImage(tile.getImage(), 
					tile.getTX(), 
					tile.getTY(), this);
			}
		}
	}

	buffer.setTransform(oldTransform); 
	
	if(player.isVisible()) 
	{
		//Draw on screen the first item 
		//in the player's slot when used

		if(player.usedItemSlot() == 0 && Inventory.getItem(0) != null)
		{
			Item t = Inventory.getItem(0);

			buffer.rotate(
				Math.toRadians(t.getDirection()),
				t.getX() + t.getWidth() / 2.0,
				t.getY() + t.getHeight() / 2.0);

			buffer.drawImage(t.getImage(), (int)t.getX(), (int)t.getY(), this);
		}
		
		buffer.setTransform(oldTransform);

		if(player.usedItemSlot() == 1 && Inventory.getItem(1) != null)
		{
			Item t = Inventory.getItem(1);
			
			buffer.rotate(
				Math.toRadians(t.getDirection()),
				t.getX() + t.getWidth() / 2.0,
				t.getY() + t.getHeight() / 2.0);
					
			buffer.drawImage(t.getImage(), (int)t.getX(), (int)t.getY(), this);
		}
		
		buffer.setTransform(oldTransform);
		
		//Rotate and draw player
		
		buffer.rotate(Math.toRadians(player.getDirection()), 
				player.getX() + player.getWidth() / 2.0,
				player.getY() + player.getHeight() / 2.0);
		
		buffer.drawImage(player.getImage(), 
				(int)player.getX(), (int)player.getY(), this);
	}
	
	Rectangle playerHitbox = player.getBounds();
	
	for(Entity entity : entities) 
	{ 
		buffer.setTransform(oldTransform);
		
		buffer.rotate(Math.toRadians(entity.getDirection()),
		player.getScrollX() + entity.getX() + entity.getWidth() / 2.0,
		player.getScrollY() + entity.getY() + entity.getHeight() / 2.0);
			
		buffer.drawImage(entity.getImage(), 
				(int)(player.getScrollX() + entity.getX()), 
		   		(int)(player.getScrollY() + entity.getY()), this);
		
		buffer.setTransform(oldTransform);
		Rectangle entityHitbox = entity.getTBounds
				((int)(entity.getX() + player.getScrollX()),
				 (int)(entity.getY()+player.getScrollY()));
		
		player.setHit(false);
		if(playerHitbox.intersects(entityHitbox)) {
			
			if(entity.getAggro() == 1) {
				//If hostile, deal damage
				player.takeContactDamageFrom(entity);
			}
			else {
				if(player.isInteracting()) {
				//If friendly, provide dialogue
				Dialogue dialogue = Entity.getDialogue(entity.getDialogNumber());
				//dialogue.showDialog(player, buffer, 204, 111, 200, 130);
				}
			}
		}
		if(player.isUsingItem()) {
			
			//Check if the item is hitting the entity
			Item item = Inventory.getItem(player.usedItemSlot());
			Rectangle itemHitbox = item.getBounds();
			
			if(itemHitbox.intersects(entityHitbox)) {
				
				entity.damage(entity.getHP() - item.getMeleeDamage(), item,
						player.getScrollX(), player.getScrollY());

				if (entity.isDead())
					player.addCoins(entity.getCoinValue());
			}
		}
	}	
	
	buffer.setTransform(oldTransform);
	ParticleGenerator.update(buffer);
	
	if(Menu.isVisible()) 
	{
		//Draw GUI
		buffer.drawImage(Menu.closedInventory(), 
			(int)Menu.getX(), (int)Menu.getY(), this);
		
		if(Inventory.getItem(0) != null)
			buffer.drawImage(Inventory.getItem(0).getImage(), 80, 432, this);
		
		if(Inventory.getItem(1) != null)
			buffer.drawImage(Inventory.getItem(1).getImage(), 130, 432, this);
			
		buffer.setColor(Color.BLACK);
		
		//Draw debug statistics
		buffer.drawString("Zone: "+ zone, 500, 416);
		buffer.drawString("Item Slot: "+ player.usedItemSlot(), 500, 432);
		buffer.drawString("Tiles: "+ tiles.size(), 560, 416);
		buffer.drawString("Vertical Line Crossed: "+ vLineCrossed, 300, 416);
		buffer.drawString("Horizontal Line Crossed: "+ hLineCrossed, 300, 432);
		buffer.drawString("Loaded Rooms: ", 300, 448);
		
		Menu.showHealthBar(player, buffer);
		int x = 400, y = 448;
		
		for(int i = 0; i<256; i++) { 
			if(loadedRooms[i]) {
				if(x > 430) {x = 400; y += 12;}
				buffer.drawString(Integer.toString(i), x, y);
				x += 30;
			}	
		}
		
		if(player.isInventoryOpen())
			Inventory.updateGUI(buffer);
	}
	
	//Render buffer
	buffer.setTransform(oldTransform);
	g.drawImage(bufferImage, borderX, borderY, scaledWidth, scaledHeight, null);
	g.dispose();
	
	}
	Toolkit.getDefaultToolkit().sync();
}
	
	
@Override
public void actionPerformed(ActionEvent e) 
{
	//Update things at the interval of a timer,
	//before they are drawn.
	buffer.setColor(background);
	buffer.fillRect(0, 0, 640, 480);
	checkIngameState();
	checkCollisions();
	updatePlayer();
	updateEntities();
	updateItem();
	repaint();
}
	
private void checkIngameState() {
	if(!ingame)
		timer.stop();
}
	
public void checkCollisions() 
{
	Rectangle playerHitbox = player.getBounds();
		
	for(Tile tile : tiles) {	
		
	if(tile != null)
	if(tile.getTX() > -1 && tile.getTX() < 640) 
	{
			
		Rectangle tileHitbox = tile.getTBounds(player.getScrollX(), player.getScrollY());
			
		//This could be changed later to 
		//"if (tile.emitsParticles = true), generator.addParticle(tile.getparticle)
			
		if(tile.getType() == 11) {
			ParticleGenerator.add(ParticleFire.class, (int)(tile.getTX() + player.getScrollX()), (int)(tile.getTY() + player.getScrollY()), 1);
		}
			
		if(playerHitbox.intersects(tileHitbox) && tile.isSolid()) 
		{
			if(tile.getType()==13) 
			{
				for(int i = 0; i < 256; i++)
					loadedRooms[i] = false;
				
				//Calculate how many rooms the player traveled before touching a door
				double yd = (player.getY()-player.getScrollY()-initialY) / 352;
				int n1 = (int)yd;
				zone += 16 * n1; 
				double xd = (player.getX()-player.getScrollX()-initialX) / 640;
				int n2 = (int)xd;
				
				zone += n2;
				zone -= 16;
				
				initialX = (int) player.getX();
				initialY = 300;
				
				player.setPosition(player.getX(), 300);
				isTransitioning = true;
			}
			else if(tile.getType()==16) 
			{
				for(int i = 0; i < 256; i++)
					loadedRooms[i] = false;
				
				//Calculate how many rooms the player traveled before touching a door
				double yd = (player.getY()-player.getScrollY()-initialY) / 352;
				int n1 = (int)yd;
				zone += 16 * n1; 
				double xd = (player.getX()-player.getScrollX()-initialX) / 640;
				int n2 = (int)xd;
				
				zone += n2;
				zone += 16;
				
				initialX = (int) player.getX();
				initialY = 50;
				
				player.setPosition(player.getX(), 50);
				isTransitioning = true;
			}
			else if(tile.getType()==17) 
			{
				for(int i = 0; i < 256; i++)
					loadedRooms[i] = false;
				
				//Calculate how many rooms the player traveled before touching a door
				double yd = (player.getY()-player.getScrollY()-initialY) / 352;
				int n1 = (int)yd;
				zone += 16 * n1; 
				double xd = (player.getX()-player.getScrollX()-initialX) / 640;
				int n2 = (int)xd;
				
				zone += n2;
				zone++;
				
				initialX = 100;
				initialY = (int) player.getY();
				
				player.setPosition(100, player.getY());
				isTransitioning = true;
			}
			else if(tile.getType()==18) 
			{
				for(int i = 0; i<256; i++)
					loadedRooms[i] = false;
				
				//Calculate how many rooms the player traveled before touching a door
				double d = (player.getY()-player.getScrollY()-initialY) / 352;
				int n = (int)d; 
				zone += 16 * n; 
				double xd = (player.getX()-player.getScrollX()-initialX) / 640;
				int n2 = (int)xd;
				
				zone += n2;
				zone--;
				
				initialX = 450;
				initialY = (int) player.getY();
				
				player.setPosition(450, player.getY());
				isTransitioning = true;
			}
		else
			player.collideWithTile();
		}
	else
		player.endTileCollision();	
	}
	}
}
	
private void updatePlayer() 
{
	if(player.isVisible())
		player.move();
		
	if(!player.isEnterAllowed())
		player.setEnterKey(false);
		
	//Determine when to start scrolling
	vLineCrossed = (
		   player.getX() > player.getScrollX()+initialX + 144 
		|| player.getX() < player.getScrollX()+initialX - 144);

	hLineCrossed = (
		   player.getY() > player.getScrollY()+initialY + 144 
		|| player.getY() < player.getScrollY()+initialY - 144);
		
	player.setHLine(hLineCrossed);
	player.setVLine(vLineCrossed);
		
	player.regen();
}
	

private void updateEntities() 
{
	for(ListIterator<Entity> entityIterator = entities.listIterator(); entityIterator.hasNext();) 
	{
		Entity entity = entityIterator.next();
		if(!isTransitioning) 
		{
			int currentRoomX = 0 + (int)(Math.floor(entity.getX() / 640));
			int currentRoomY = 0 + (int)(Math.floor(entity.getY() / 352));
				
			double localX = entity.getX();
			double localY = entity.getY();
				
			if(localX > 640)
				localX = localX % 640;

			else if(localX < 0)
				localX = (localX % 640) + 640;
				
			if(localY > 352)
				localY = localY % 352;

			else if(localY < 0)
				localY = (localY % 352) + 352;
				
			int location = (int)(( localX / 32)) + 20*(int)( localY /32);
			Tile tile = new Tile((int)(entity.getX()), (int)(entity.getY()), 
						
			Tilemaps.getMapIndex
				(zone + (currentRoomX + (16*currentRoomY)), location));
					
			if(tile != null) 
			{
				if(tile.isSolid())
					entity.collideWithTile();
				else
					entity.setColliding(false);
			}
				
			entity.performAI(
					player.getX()-player.getScrollX(), 
				player.getY()-player.getScrollY(), player.getDirection());
				
		}
		if(entity.isDead())
			entityIterator.remove();
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
			player.keyPressed(e);
			Inventory.keyPressed(e);
		}
	}
}
