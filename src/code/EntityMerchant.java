package code;

import javax.swing.*;

public class EntityMerchant extends Entity {

    private static final ImageIcon merchant = new ImageIcon(Sprite.folder + "EntityMerchant.png");

    /**
     * @see Entity constructor
     */
    public EntityMerchant(int x, int y, int aggro, double direction, int dNum, int hp) {
        super(x, y, aggro, direction, dNum, hp);
        useImage(merchant);
    }

    @Override
    public void whenInteracted()
    {
        if(aggro == 0)
            Main.startDialogue("ShopDialogue");
    }

    @Override
    public void performAI(double targetX, double targetY, double targetDirection) {
        if (aggro != 0)
            Main.startDialogue("KilledMerchantDialogue");
    }

    @Override
    public int getCoinValue() {
        return 0;
    }
}
