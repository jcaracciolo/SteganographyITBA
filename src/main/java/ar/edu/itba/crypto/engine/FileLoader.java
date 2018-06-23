package ar.edu.itba.crypto.engine;

import ar.edu.itba.crypto.model.image.PlainBMPImage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileLoader {

    public static PlainBMPImage read(String filename) {
        try {
            Path path = Paths.get(filename);
            return new PlainBMPImage(Files.readAllBytes(path));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
