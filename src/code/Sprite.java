package code;

import java.awt.Image;
import java.awt.Rectangle;
import javax.swing.ImageIcon;

public class Sprite {
	
	protected double direction;
	protected double rotationSpeed;
	protected double moveSpeed;
	protected int width;
	protected int height;
	protected int solid;
	protected double x;
	protected double y;
	protected double moveX;
	protected double moveY;
	protected double oldX;
	protected double oldY;
	protected boolean visible;
	protected boolean colliding;
	
	protected Image image;
	
	public static final String folder = "src/Textures/";


	/**
	 * Constructs a Sprite from XY coordinates.
	 */
	public Sprite(int x, int y) {
		this.x = x;
		this.y = y;
		visible = true;
	}

	/**
	 * Changes the Image to another existing Image.
	 */
	protected void useImage(ImageIcon ii) {
		image = ii.getImage();
		width = image.getWidth(null);
		height = image.getHeight(null);
	}

	/**
	 * Change the Image.
	 * @param imageName Path relative to Textures directory.
	 */
	protected void loadImage(String imageName) {
		ImageIcon imageIcon = new ImageIcon(folder+imageName);
		useImage(imageIcon);
	}

	/**
	 * Returns the Image assigned to this sprite.
	 */
	public Image getImage() {
		return image;
	}

	/**
	 * Returns the direction (angle of rotation).
	 */
	public double getDirection() {
		return direction;
	}

	/**
	 * Returns the width.
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * Returns the height.
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * Returns the X coordinate.
	 */
	public double getX() {
		return x;
	}

	/**
	 * Returns the Y coordinate.
	 */
	public double getY() {
		return y;
	}

	/**
	 * Set coordinates
	 * @param x X coordinate
	 * @param y Y coordinate
	 */
	public void setPosition(double x, double y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Set x coordinate
	 */
	public void setX(double x) {
		this.x = x;
	}

	/**
	 * Set y coordinate
	 */
	public void setY(double y) {
		this.y = y;
	}

	/**
	 * Returns if the sprite is visible.
	 */
	public boolean isVisible() {
		return visible;
	}

	/**
	 * Returns true if the sprite has collision.
	 */
	public boolean isSolid() {
        return solid != 0;
	}

	/**
	 * Returns the bounding area.
	 */
	public Rectangle getBounds(){
		return new Rectangle((int)x, (int)y, width, height);
	}

	/**
	 * Sets the sprite visible.
	 */
	public void setVisible(Boolean visible) {
		this.visible = visible;
	}
		
}
