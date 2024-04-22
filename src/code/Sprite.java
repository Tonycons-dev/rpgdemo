package code;

import java.awt.Image;
import java.awt.Rectangle;
import javax.swing.ImageIcon;

public class Sprite {
	
	protected double direction;
	protected double rotationSpeed;
	protected double moveSpeed;
	protected int entityType;
	protected int width;
	protected int height;
	protected int solid;
	protected int aggro;
	protected int type;
	//precise coordinates
	protected double x;
	protected double y;
	//coordinates for tiles
	protected int tx;
	protected int ty;
	protected double moveX;
	protected double moveY;
	protected double oldX;
	protected double oldY;
	protected boolean visible;
	protected boolean colliding;
	
	protected Image image;
	
	protected static final String folder = "src/Textures/";
	
	//Default Constructor (Stores x and y as double)
	public Sprite(int x, int y) {
		this.x = x; this.y = y;
		visible = true;
	}
	
	//Entity Constructor (Stores x and y as double)
	public Sprite(int x, int y, int type, int aggro, double direction) {
		this.x = x; this.y = y;
		this.aggro = aggro;
		this.direction = direction;
		this.type = type;
		visible = true;
	}
	//Tile Constructor (Stores x and y as integer)
	public Sprite(int tx, int ty, int type) {
		this.tx = tx; this.ty = ty;
		this.type = type;
		visible = true;
	}
	
	protected void createImageIcon(ImageIcon ii, String name)
	{
		ii = new ImageIcon(folder+name);
	}
	
	protected void useImage(ImageIcon ii)
	{	
		image = ii.getImage();
	}
	
	protected void loadImage(String imageName) {
		ImageIcon imageIcon = new ImageIcon(folder+imageName);
		image = imageIcon.getImage();	
	}
		
	protected void getImageDimensions() {
		width = image.getWidth(null);
		height = image.getHeight(null);
	}
	
	public Image getImage() {
		return image;
	}
	
	public double getDirection() {
		return direction;}
	public int getWidth() {
		return width;}
	public int getHeight() {
		return height;}
	public double getX() {
		return x;}
	public double getY() {
		return y;}
	public int getTX() {
		return tx;}
	public int getTY() {
		return ty;}
	
	public boolean isVisible() {
		return visible;
	}
	
	public boolean isSolid() {
		if(solid == 0) return false;
		else return true;
	}
	
	public Rectangle getBounds(){
		return new Rectangle((int)x, (int)y, width, height);
	}
	
	//Used to get the bounds of a sprite when offset
	public Rectangle getTBounds(double sx, double sy) {
		return new Rectangle((int)sx + tx, (int)sy + ty, width, height); 
	}
	
	public void setVisible(Boolean visible) {
		this.visible = visible;
	}
		
}
