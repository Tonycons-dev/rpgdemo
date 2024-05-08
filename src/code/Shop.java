package code;

import java.util.LinkedList;
import java.util.List;

public class Shop {
    private static List<Item> items;
    private static Item is = new ItemIronSword(-50, -50);
    private static Item cb = new ItemCrossbow(-50, -50);

    public Shop() {
        items = new LinkedList<>();
        items.add(cb);
        items.add(is);
    }

    public static boolean buyItem(int i) {
        if (Player.trySubtractCoins(items.get(i).getCoinValue())) {
            return true;
        }
        return false;
    }
    public static Item returnItem(int i)
    {
        return items.get(i);
    }
}
