package code;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.ImageObserver;

public class LevelMap {

    private static final ImageIcon img = new ImageIcon(Sprite.folder + "legend.png");

    private static boolean showingMap = false;

    public static void keyPressed(KeyEvent e)
    {
        if (e.getKeyCode() == KeyEvent.VK_M) {
            //toggle the map
            showingMap = !showingMap;
        }
    }

    public static void render(Graphics2D buffer, ImageObserver observer)
    {
        AffineTransform oldTransform = buffer.getTransform();

        AffineTransform x = new AffineTransform(oldTransform);
        x.scale(0.3, 0.3);
        buffer.setTransform(x);

        if (showingMap)
            buffer.drawImage(img.getImage(), 320, 168, observer);

        buffer.setTransform(oldTransform);
    }
}
