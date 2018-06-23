package ar.edu.itba.crypto;

import ar.edu.itba.crypto.engine.FileLoader;
import ar.edu.itba.crypto.model.image.PlainBMPImage;
import ar.edu.itba.crypto.model.steg.StegMessage;
import ar.edu.itba.crypto.model.steg.StegPlainMessage;
import ar.edu.itba.crypto.steganographer.LSBEnhanced;
import ar.edu.itba.crypto.steganographer.LSBN;
import ar.edu.itba.crypto.steganographer.Stenographer;
import ar.edu.itba.crypto.utils.BitManipulation;

import java.nio.charset.StandardCharsets;

/**
 * Hello world!
 *
 */
public class Main
{
    public static void main( String[] args ) {

        hideAndSearch(new LSBN(1));
        hideAndSearch(new LSBN(4));
        hideAndSearch(new LSBEnhanced());

    }

    public static void hideAndSearch(Stenographer sten){
        PlainBMPImage bytes = FileLoader.read("resources/lado.bmp");
        StegMessage message = new StegPlainMessage("sarasasdasdasdsasa".getBytes(), ".html");
        sten.insertInto(bytes, message);
        byte[] answer = sten.removeFrom(bytes, false);
        System.out.println(new String(answer, StandardCharsets.UTF_8));

    }
}
