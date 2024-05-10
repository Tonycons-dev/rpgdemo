package code;

import java.util.LinkedList;
import java.util.List;

public class Shop {
    private static final List<Item> items;

    static {
        items = new LinkedList<>();
        items.add(new ItemCrossbow(0, 0));
        items.add(new ItemIronSword(0, 0));
        items.add(new ItemKey(0, 0));
    }

    /**
     * Attempts to buy an item
     * @param player Player instance
     * @param i Index of item in the shop
     * @return True if there are enough coins to buy it
     */
    public static boolean buyItem(Player player, int i) {
        //i -= 1;
        if (player.trySubtractCoins(items.get(i).getCoinValue())) {
            //add to inventory
            Inventory.addItem(items.get(i));
            //items.remove(i);
            return true;
        }
        return false;
    }
}
