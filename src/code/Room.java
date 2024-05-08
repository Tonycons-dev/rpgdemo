package code;

import java.awt.*;
import java.util.*;
import com.google.gson.*;


public class Room {

    private record TileCoordinate(int x, int y) {}

    private final Map<TileCoordinate, Tile> tiles = new HashMap<>();
    private final Collection<Entity> entities = new LinkedList<>();
    private final Map<TileCoordinate, String> doors = new HashMap<>();
    private final Map<TileCoordinate, Dimension> doorExitPositions = new HashMap<>();
    private final Dimension size = new Dimension();


    // This must match the file structure exactly.
    public static class Descriptor {

        private int width;
        private int height;
        private int[] layout;
        private int[] tiles;
        private int[][] entities;
        private String[] doors;
    }

    /**
     * Constructs a room from a level string.
     * @param str Level string
     */
    public Room(String str) {
        var gson = new Gson();
        Descriptor d = gson.fromJson(str, Descriptor.class);
        this.size.width = d.width;
        this.size.height = d.height;

        int row = 0;
        int col = 0;
        int door = 0;
        for (int id : d.tiles) {
            if (row > d.width + 1) {
                col++;
                row = 0;
            }
            int x = row * 32;
            int y = col * 32;

            if (Tile.isDoor(id) && d.doors != null && door < d.doors.length) {
                doors.put(new TileCoordinate(x, y), d.doors[door++]);
            }
            if (id != 0) {
                tiles.put(new TileCoordinate(x, y), new Tile(x, y, id));
            }
            row++;
        }
        for (int[] p : d.entities) {
            entities.add(Entity.newEntity(p[0], p[1], p[2], p[3], p[4], p[5], p[6]));
        }
    }

//    @Override
//    public String toString() {
//        var sb = new StringBuilder();
//    }

    /**
     * Returns the size of the room.
     */
    public Dimension getSize() {
        return this.size;
    }

    /**
     * Returns a list of the entities that generate in this level, at their starting locations.
     */
    public Collection<Entity> getEntities() {
        return entities;
    }

    /**
     * Returns the tile layout of this level.
     */
    public Collection<Tile> getTiles() {
        return tiles.values();
    }

    public Tile getTileAt(int x, int y) {
        return tiles.get(new TileCoordinate(x, y));
    }

    public Tile getTileAt(double x, double y) {
        return tiles.get(new TileCoordinate((int)x, (int)y));
    }

    /**
     * Returns the name of the Room that a door leads to.
     * Returns null if there is no door or tile at (x, y)
     */
    public String getNextRoomName(double x, double y) {
        Tile tile = getTileAt(x, y);

        if (tile == null || !Tile.isDoor(tile.getType()))
            return null;

        return doors.get(new TileCoordinate((int)x, (int)y));
    }

    /**
     * If there is a door at (x, y), returns the location that
     * player will start from after entering that room.
     */
    public Dimension getDoorExitPosition(double x, double y) {
        return null;
        // still not sure if i should do this
    }
}
