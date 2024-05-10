package code;

import javax.swing.*;

public class ItemKey extends Item {

    public ItemKey(int x, int y) {
        super(x, y);
        loadImage("Key.png");
    }
    @Override
    public void useItem(double xp, double yp, int w, int h, double dr) {
        //fill in
    }

    @Override
    public int getCoinValue() {
        return 250;
    }
}
