package code;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

@SuppressWarnings("unused")
public class Menu{
	
	protected static final String folder = "src/Textures/";
	
	private static final Color boxColor = new Color(58, 112, 184);
	private static final Color lineColor = new Color(180, 155, 29);
	
	private final static ImageIcon i1 = new ImageIcon(folder+"MenuTopLeft.png");
	private final static ImageIcon i2 = new ImageIcon(folder+"MenuTopRight.png");
	private final static ImageIcon i3 = new ImageIcon(folder+"MenuBottomLeft.png");
	private final static ImageIcon i4 = new ImageIcon(folder+"MenuBottomRight.png");
	private final static ImageIcon i5 = new ImageIcon(folder+"ClosedInventory.png");
	
	private final static Image menuTopLeft = i1.getImage();
	private final static Image menuTopRight = i2.getImage();
	private final static Image menuBottomLeft = i3.getImage();
	private final static Image menuBottomRight = i4.getImage();
	private final static Image closedInventory = i5.getImage();
	
	private static int[] wordWrap(int xp, int yp, int x1, int y1, int x2, int y2, 
					     Graphics2D buffer, String[] word, FontMetrics metrics) 
	{
		int size = metrics.stringWidth("A");
		for(String s : word) 
		{
			if(xp + metrics.stringWidth(s) > x2 - 10) {
				xp = x1 + 16;
				yp += 15;
			}
			if(yp > y2 - 5)
				break;
			
			buffer.drawString(s, xp, yp);
			xp+= metrics.stringWidth(s);
			
			buffer.drawString(" ", xp, yp);
			xp += size;
		}
		
		return new int[] {xp, yp};
	}
	
	public static void showDialogBox(Graphics2D buffer, int x, int y, int width, int height, String text)
	{	
		Color oldColor = buffer.getColor();
		buffer.setColor(Color.BLACK);
		buffer.fillRect(x, y, width, height);
		buffer.setColor(boxColor);
		buffer.fillRect(x + 1, y + 1, width - 1, height - 1);
		buffer.setColor(lineColor);
		int x1 = x + 5; int y1 = y + 5; 
		int x2 = (x + width) - 5; int y2 = (y + height) - 5;
		
		buffer.drawLine(x1, y1, x2, y1);
		buffer.drawLine(x2, y1, x2, y2);
		buffer.drawLine(x2, y2, x1, y2);
		buffer.drawLine(x1, y2, x1, y1);
		
		buffer.drawImage(menuTopLeft, x, y, null);
		buffer.drawImage(menuTopRight, (x + width) - 20, y, null);
		buffer.drawImage(menuBottomLeft, x, (y + height) - 20, null);
		buffer.drawImage(menuBottomRight, (x + width) - 20, (y + height) - 20, null);
		
		buffer.setColor(Color.BLACK);
		
		int xp = x1 + 16;
		int yp = y1 + 24;
		
		Font font = buffer.getFont();
		FontMetrics metrics = buffer.getFontMetrics(font);

		int size = metrics.stringWidth("A");
		for(String line : text.split("\n"))
		{
			for (String s : line.split(" ")) {
				if(xp + metrics.stringWidth(s) > x2 - 10) {
					xp = x1 + 16;
					yp += 15;
				}
				if(yp > y2 - 5)
					break;

				buffer.drawString(s, xp, yp);
				xp += metrics.stringWidth(s);

				buffer.drawString(" ", xp, yp);
				xp += size;
			}
			xp = x1 + 16;
			yp += 15;
		}
		buffer.setColor(oldColor);
	}
	
//	public static void showDialogBox(int x, int y, int width, int height,
//	Graphics2D buffer, String arg1, Color c1, String arg2, Color c2, String arg3, Color c3)
//	{
//		Color oldColor = buffer.getColor();
//
//		buffer.setColor(Color.BLACK);
//		buffer.fillRect(x, y, width, height);
//		buffer.setColor(boxColor);
//		buffer.fillRect(x + 1, y + 1, width - 1, height - 1);
//		buffer.setColor(lineColor);
//
//		int x1 = x + 5;
//		int y1 = y + 5;
//		int x2 = (x + width) - 5;
//		int y2 = (y + height) - 5;
//
//		buffer.drawLine(x1, y1, x2, y1);
//		buffer.drawLine(x2, y1, x2, y2);
//		buffer.drawLine(x2, y2, x1, y2);
//		buffer.drawLine(x1, y2, x1, y1);
//
//		buffer.drawImage(menuTopLeft, x, y, null);
//		buffer.drawImage(menuTopRight, (x + width) - 20, y, null);
//		buffer.drawImage(menuBottomLeft, x, (y + height) - 20, null);
//		buffer.drawImage(menuBottomRight, (x + width) - 20, (y + height) - 20, null);
//
//		buffer.setColor(c1);
//		String[] word1 = arg1.split(" ");
//
//		int xp = x1 + 16;
//		int yp = y1 + 24;
//
//		Font font = buffer.getFont();
//		FontMetrics metrics = buffer.getFontMetrics(font);
//
//		int[] newPositions = wordWrap(xp, yp, x1, y1, x2, y2, buffer, word1, metrics);
//		buffer.setColor(c2);
//
//		String[] word2 = arg2.split(" ");
//		xp = x1 + 16;
//		yp = newPositions[1] + 16;
//
//		newPositions = wordWrap(xp, yp, x1, y1, x2, y2, buffer, word2, metrics);
//		buffer.setColor(c3);
//
//		String[] word3 = arg3.split(" ");
//		xp = x1 + 16;
//		yp = newPositions[1] + 16;
//		wordWrap(xp, yp, x1, y1, x2, y2, buffer, word3, metrics);
//
//		buffer.setColor(oldColor);
//	}
	
	public static void showHealthBar(Player player, Graphics2D buffer) 
	{
		Color oldColor = buffer.getColor();
		
		buffer.setColor(Color.BLACK);
		buffer.fillRect(512, 448, 100, 10);
		buffer.setColor(Color.RED);
		
		double k = player.getHP();
		double n = player.getMaxHP();
		
		double percent = k / n;
		
		buffer.fillRect(512, 448, (int)(100 * percent), 10);
		buffer.setColor(oldColor);
	}
	
	public static Image closedInventory() {
		return closedInventory;
	}
	
	public static int getX() {
		return 0;
	}
	
	public static int getY() {
		return 384;
	}
	
	public static boolean isVisible() {
		return true;
	}
}