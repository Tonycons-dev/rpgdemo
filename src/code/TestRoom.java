package code;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class TestRoom {


    public static void main(String[] args) throws IOException {
        var str = Files.readString(Path.of(System.getProperty("user.dir") + "/src/Maps/F.json"));
        var room = new Room(str);
    }
}
