package code;

import java.util.LinkedList;
import java.util.List;

public class Shop {
    private static List<Item> items;

    public Shop() {
        items = new LinkedList<>();
        //items.add(new ItemIronSword());
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
