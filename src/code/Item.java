package code;

import java.awt.event.KeyEvent;
import java.awt.Graphics2D;
import java.awt.Image;
import javax.swing.ImageIcon;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public abstract class Item extends Sprite{
	
	private int[] equippedItems = new int[2];
	private static Item[] inventoryItems = new Item[19];
	
	private static final ImageIcon i1 = new ImageIcon(folder+"OpenInventory.png");
	private static final ImageIcon i2 = new ImageIcon(folder+"ItemCursor.png");
	private static final ImageIcon i3 = new ImageIcon(folder+"ItemSlot.png");
	private static final ImageIcon i4 = new ImageIcon(folder+"SelectCursor.png");
	private static final ImageIcon i5 = new ImageIcon(folder+"ItemBasicSword.png");
	private static final ImageIcon i6 = new ImageIcon(folder+"Crossbow.png");
	
	protected static final Image openInventory = i1.getImage();
	protected static final Image itemCursor = i2.getImage();
	protected static final Image itemSlot = i3.getImage();
	protected static final Image selectCursor = i4.getImage();
	protected static final Image basicSword = i5.getImage();
	protected static final Image crossbow = i6.getImage();
	
	private static Image cursorImage = itemCursor;
	
	private static boolean up = false;
	private static boolean down = false;
	private static boolean left = false;
	private static boolean right = false;
	private static boolean enter = false;
	private static boolean showCursor1 = false;
	private static boolean showCursor2 = false;
	
	private static Item selectedItem = null;
	
	private static int selected = 0;
	private static int invSlot = 0;
	private static int oldSlot = 0;
	private static int cursorX = 128;
	private static int cursorY = 128;
	private static int x1 = 0;
	private static int y1 = 0;
	private static int x2 = 0;
	private static int y2 = 0;
	
	protected int meleeDamage;
	protected int rangedDamage;
	protected int invTime;
	protected double knockbackX;
	protected double knockbackY;
	
	public static void NewItem(int type, int slot) {
		
		switch(type) {
		case 1:
			inventoryItems[slot] = new ItemSword(-50, -50);
			inventoryItems[slot].image = basicSword;
			break;
		case 2:
			inventoryItems[slot] = new ItemCrossbow(-50, -50);
			inventoryItems[slot].image = crossbow;
			break;
		default:
			inventoryItems[slot] = new ItemSword(-50, -50);
			inventoryItems[slot].image = basicSword;
			break;
		}
	}
	
	
	public Item(int x, int y) {
		super(x, y);
	}
	
	public abstract void useItem(double xp, double yp, int w, int h, double dr);
	
	public static void use(double xp, double yp, int w, int h, double dr, byte slot) {
	if(slot == 1 && getInventoryItem(slot) != null) { 
		inventoryItems[slot].useItem(xp, yp, w, h, dr);
	}
	else if(slot == 0 && getInventoryItem(slot) != null) {
		inventoryItems[slot].useItem(xp, yp, w, h, dr);
	}
	}
	
	public static void updateCursor() {
		if(up && cursorY > 128) {
			cursorY -= 64;
			invSlot -= 6;
			up = false;
		}
		if(down && cursorY < 256) {
			cursorY += 64;
			invSlot += 6;
			down = false;
		}
		if(left && cursorX > 128) {
			cursorX -= 64;
			invSlot--;
			left = false;
		}
		if(right && cursorX < 448) {
			cursorX += 64;
			invSlot++;
			right = false;
		}
		if(enter) {
			//Perform item selection
			selected++;
			if(selected == 1) {
				//The first time enter is pressed, select an item
				if(inventoryItems[invSlot] != null) {
				selectedItem = inventoryItems[invSlot];
				oldSlot = invSlot;
				
				inventoryItems[invSlot] = null;
				
				}
				x1 = cursorX;
				y1 = cursorY;
				cursorImage = selectCursor;
				showCursor1 = true;
			} else {
				//The second time enter is pressed, select where to move the item
				//Returns cursor to normal
				if(selectedItem != null) {
				inventoryItems[invSlot] = selectedItem;
				//inventoryItems[oldSlot] = null;
				}
				cursorImage = itemCursor;
				showCursor1 = false;
				selected = 0;
			}
			enter = false;
		}
	}
	
	public static void updateGUI(Graphics2D buffer) {
		//Drawing inventory slots
		buffer.drawImage(openInventory, 64, 16, null);
		
		int xp = 128; int yp = 128;
		for(int index = 0; index<18; index++) {
			xp = 128 + (index % 6)*64;
			yp = 128 + (index / 6)*64;
			buffer.drawImage(itemSlot, xp, yp, null);
			if(inventoryItems[index] != null) {
			buffer.drawImage(inventoryItems[index].getImage(), xp + 5, yp + 5, null);
			}
		}
		
		//Drawing cursors
		if(showCursor1) {
			buffer.drawImage(selectCursor, x1, y1, null);
		}
		buffer.drawImage(cursorImage, cursorX, cursorY, null);
	}
	
	public static void keyPressed(KeyEvent e) {
		//Buttons must be pressed repeatedly to move cursor
		int key = e.getKeyCode();
		if(key == KeyEvent.VK_UP && up) {
			up = true;
		}
		if(key == KeyEvent.VK_DOWN && down) {
			down = true;
		}
		if(key == KeyEvent.VK_LEFT && left) {
			left = true;
		}
		if(key == KeyEvent.VK_RIGHT && right) {
			right = true;
		}
		if(key == KeyEvent.VK_ENTER && enter) {
			enter = true;
		}
	}
	
	public static void keyReleased(KeyEvent e) {
		//Buttons must be pressed repeatedly to move cursor
		int key = e.getKeyCode();
		if(key == KeyEvent.VK_UP && !up) {
			up = true;
		}
		if(key == KeyEvent.VK_DOWN && !down) {
			down = true;
		}
		if(key == KeyEvent.VK_LEFT && !left) {
			left = true;
		}
		if(key == KeyEvent.VK_RIGHT && !right) {
			right = true;
		}
		if(key == KeyEvent.VK_ENTER && !enter) {
			enter = true;
		}
	}
	
	public static Item getInventoryItem(int index) {
		return inventoryItems[index];
	}
	
	public static boolean upPressed() {
		return up;
	}
	
	public static boolean downPressed() {
		return down;
	}
	
	public static boolean enterPressed() {
		return enter;
	}
	
	public int getMeleeDamage() {
		return meleeDamage;
	}
	
	public int getRangedDamage() {
		return rangedDamage;
	}
	
	public int getInvTime() {
		return invTime;
	}
	
	public double getKnockbackX() {
		return knockbackX;
	}
	
	public double getKnockbackY() {
		return knockbackY;
	}
}
