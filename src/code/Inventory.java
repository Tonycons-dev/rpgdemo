package code;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class Inventory {

    private static final Item[] items;
    private static final Image openInventory;
    private static final Image itemCursor;
    private static final Image itemSlot;
    private static final Image selectCursor;
    private static boolean up = false;
    private static boolean down = false;
    private static boolean left = false;
    private static boolean right = false;
    private static boolean enter = false;
    private static int selected = 0;
    private static Item selectedItem = null;
    private static int invSlot = 0;
    private static int oldSlot = 0;
    private static boolean showCursor1 = false;
    private static int cursorX = 128;
    private static int cursorY = 128;
    private static int x1 = 0;
    private static int y1 = 0;
    private static int x2 = 0;
    private static int y2 = 0;
    private static Image cursorImage;


    static {
        items = new Item[19];
        openInventory = new ImageIcon(Sprite.folder+"OpenInventory.png").getImage();
        itemCursor = new ImageIcon(Sprite.folder+"ItemCursor.png").getImage();
        itemSlot = new ImageIcon(Sprite.folder+"ItemSlot.png").getImage();
        selectCursor = new ImageIcon(Sprite.folder+"SelectCursor.png").getImage();
        cursorImage = itemCursor;
    }

    /**
     * Performs the Item's behavior for an item in given slot
     * @see Item useItem
     */
    public static void useItem(double xp, double yp, int w, int h, double dr, byte slot) {
        if (slot == 1 && items[slot] != null) {
            items[slot].useItem(xp, yp, w, h, dr);
        }
        else if (slot == 0 && items[slot] != null) {
            items[slot].useItem(xp, yp, w, h, dr);
        }
    }

    /**
     * Update cursor
     */
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
                if(items[invSlot] != null) {
                    selectedItem = items[invSlot];
                    oldSlot = invSlot;
                    items[invSlot] = null;
                }
                x1 = cursorX;
                y1 = cursorY;
                cursorImage = selectCursor;
                showCursor1 = true;
            } else {
                //The second time enter is pressed, select where to move the item
                //Returns cursor to normal
                if(selectedItem != null) {
                    items[invSlot] = selectedItem;
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

        int xp = 128;
        int yp = 128;

        for(int index = 0; index<18; index++) {
            xp = 128 + (index % 6)*64;
            yp = 128 + (index / 6)*64;

            buffer.drawImage(itemSlot, xp, yp, null);
            if (items[index] != null) {
                buffer.drawImage(items[index].getImage(), xp + 5, yp + 5, null);
            }
        }

        //Drawing cursors
        if(showCursor1) {
            buffer.drawImage(selectCursor, x1, y1, null);
        }
        buffer.drawImage(cursorImage, cursorX, cursorY, null);
    }

    /**
     * Responds to a keyboard key press
     */
    public static void keyPressed(KeyEvent e) {
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

    /**
     * Change the item in a given slot
     * TODO: Refactor
     */
    public static void newItem(int type, int slot) {

        switch (type) {
            case 2:
                items[slot] = new ItemCrossbow(-50, -50);
                break;
            default:
                items[slot] = new ItemSword(-50, -50);
                break;
        }
    }

    public static Item getItem(int index) {
        return items[index];
    }
}
