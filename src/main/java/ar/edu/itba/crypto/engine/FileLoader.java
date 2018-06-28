package ar.edu.itba.crypto.engine;

import ar.edu.itba.crypto.model.image.PlainBMPImage;
import com.sun.xml.internal.fastinfoset.tools.FI_DOM_Or_XML_DOM_SAX_SAXEvent;

import java.io.*;
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

    public static byte[] GetFileBytes(String filename) {
        try {
            Path path = Paths.get(filename);
            return Files.readAllBytes(path);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
         e.printStackTrace();
        }

        return null;
    }

    public static void SaveFile (String filename, byte[] fileData) {

        File file = new File(filename);

        try {
            OutputStream os = new FileOutputStream(file);
            os.write(fileData);
            os.close();
        } catch (FileNotFoundException e) {
            System.err.println("File Not Found");
        } catch (IOException e) {
            System.err.println("IO Exception");
        }
    }
}
