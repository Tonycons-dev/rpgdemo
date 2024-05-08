package code;

public class Key extends Item {
    public Key(int x, int y) {
        super(x, y);
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
