package code;

import java.awt.*;
import java.util.Arrays;
import java.util.List;
import com.google.gson.*;


public class Room {


    // This must match the file structure exactly.
    public static class Descriptor {

        private int width;
        private int height;
        private int[] layout;
        private int[] tiles;
        private int[][] entities;
    }

    /**
     * Constructs a room from a level string.
     * @param str Level string
     */
    public Room(String str) {

        var gson = new Gson();
        Descriptor d = gson.fromJson(str, Descriptor.class);

        System.out.println("Descriptor:");
        System.out.println(d.width);
        System.out.println(d.height);
        System.out.println(Arrays.toString(d.tiles));
        System.out.println(Arrays.deepToString(d.entities));
    }

    /**
     * Returns the size of the room.
     */
    public Dimension getSize() {
        return null;
    }

    /**
     * Returns a list of the room IDs that can be accessed through this room.
     */
    public List<Integer> getAccessibleRooms() {
        return null;
    }

    /**
     * Returns a list of the entities that generate in this level, at their starting locations.
     */
    public List<Entity> getEntities() {
        return null;
    }

    /**
     * Returns the tile layout of this level.
     */
    public List<Tile> getTiles() {
        return null;
    }


    /**
     * Returns the room id. This is an integer that corresponds to the file name.
     */
    public int getID() {
        return -1;
    }
}
