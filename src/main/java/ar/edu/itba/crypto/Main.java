package ar.edu.itba.crypto;

import ar.edu.itba.crypto.encryption.BlockMode;
import ar.edu.itba.crypto.encryption.CipherConfig;
import ar.edu.itba.crypto.encryption.EncryptAlgorithm;
import ar.edu.itba.crypto.encryption.OpenSSL;
import ar.edu.itba.crypto.engine.FileLoader;
import ar.edu.itba.crypto.model.image.PlainBMPImage;
import ar.edu.itba.crypto.model.steg.StegEncriptedMessage;
import ar.edu.itba.crypto.model.steg.StegMessage;
import ar.edu.itba.crypto.model.steg.StegPlainMessage;
import ar.edu.itba.crypto.steganographer.LSBEnhanced;
import ar.edu.itba.crypto.steganographer.LSBN;
import ar.edu.itba.crypto.steganographer.Stenographer;
import ar.edu.itba.crypto.utils.BitManipulation;
import javafx.util.Pair;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

import static java.lang.System.exit;
import static java.lang.System.setOut;

/**
 * Hello world!
 *
 */
public class Main
{
    public static void main( String[] args ) {

        CipherConfig config = new CipherConfig(BlockMode.CBC, EncryptAlgorithm.AES128, "password".getBytes());
        StegMessage ms = new StegEncriptedMessage(config, new StegPlainMessage("SARASA".getBytes(), ".exe"));
        byte[] message = "SARASA".getBytes();
        byte[] encrupt = config.encrypt(message);
        System.out.println(new String( encrupt));
        byte[] decypt = config.decrypt(encrupt);
        System.out.println(new String( decypt));




    }

    public static void hideAndSearch(Stenographer sten){
        PlainBMPImage bytes = FileLoader.read("resources/lado.bmp");
        StegMessage message = new StegPlainMessage("sarasasdasdasdsasa".getBytes(), ".html");
        sten.insertInto(bytes, message);
        byte[] answer = sten.removeFrom(bytes, false);
        System.out.println(new String(answer, StandardCharsets.UTF_8));

    }
}
