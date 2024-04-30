package code;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class TestRoom {


    public static void main(String[] args) throws IOException {
        var str = new String(Files.readAllBytes(Path.of("Maps/map_094.txt")));
        var room = new Room(str);

        System.out.println(room.getSize());
    }
}
