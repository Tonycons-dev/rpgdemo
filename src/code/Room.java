package code;

import java.awt.*;
import java.util.List;

public class Room {

    /**
     * Constructs a room from a level string.
     * @param str Level string
     */
    public Room(String str) {

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
