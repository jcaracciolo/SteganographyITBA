package ar.edu.itba.crypto;

import ar.edu.itba.crypto.engine.FileLoader;
import ar.edu.itba.crypto.model.image.PlainBMPImage;
import ar.edu.itba.crypto.model.steg.StegMessage;
import ar.edu.itba.crypto.model.steg.StegPlainMessage;
import ar.edu.itba.crypto.steganographer.LSBN;
import ar.edu.itba.crypto.utils.BitManipulation;

import java.nio.charset.StandardCharsets;

/**
 * Hello world!
 *
 */
public class Main
{
    public static void main( String[] args ) {
        System.out.println("SADAS".getBytes());
        PlainBMPImage bytes = FileLoader.read("resources/lado.bmp");

        LSBN lsbn1 = new LSBN(2);
        StegMessage message = new StegPlainMessage("sarasasdasdasdsasa".getBytes(), ".html");
        lsbn1.insertInto(bytes, message);
        byte[] answer = lsbn1.removeFrom(bytes, false);
        System.out.println(new String(answer, StandardCharsets.UTF_8));


    }
}
