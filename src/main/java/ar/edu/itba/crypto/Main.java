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
import ar.edu.itba.crypto.utils.ParserConfig;
import ar.edu.itba.crypto.utils.SteganographyAlgorithm;
import javafx.util.Pair;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.math.BigInteger;
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


        /**
        Pair<byte[], byte[]> pair = OpenSSL.EVP_BytesToKey("margarita".getBytes(), 32, 16);


        System.out.println(new BigInteger(1, pair.getKey()).toString(16));
        System.out.println(new BigInteger(1, pair.getValue()).toString(16));

        CipherConfig config = new CipherConfig(BlockMode.CBC, EncryptAlgorithm.AES128, "password".getBytes());
        StegMessage ms = new StegEncriptedMessage(config, new StegPlainMessage("SARASA".getBytes(), ".exe"));
        byte[] message = "SARASA".getBytes();
        byte[] encrupt = config.encrypt(message);
        System.out.println(new String( encrupt));
        byte[] decypt = config.decrypt(encrupt);
        System.out.println(new String( decypt));
        **/

        ParserConfig parserConfig = new ParserConfig(true,true,"resources/ladoLSB4aes256cbc.bmp","","","outenc.pdf",SteganographyAlgorithm.LSB4, EncryptAlgorithm.AES256, BlockMode.CBC,"secreto") ;
        Stenographer sten = getStenographer(parserConfig);
        if(!parserConfig.isExtract()){
            hide(sten,parserConfig);
        } else {
            System.out.println("EXTRACT");
            extract(sten, parserConfig);
        }




    }

    public static void hide(Stenographer sten, ParserConfig parserConfig){
        byte[] messageToHide = FileLoader.GetFileBytes(parserConfig.getHidePath());
        StegMessage stegMessage = new StegPlainMessage(messageToHide, parserConfig.getHideExtension());
        PlainBMPImage image = FileLoader.read(parserConfig.getInPath());

        if (parserConfig.encrypts()){
            CipherConfig cipherConfig =  new CipherConfig(parserConfig.getMode(),parserConfig.getEncription(),parserConfig.getPassword().getBytes());
            stegMessage = new StegEncriptedMessage(cipherConfig,(StegPlainMessage)stegMessage);
        }

        sten.insertInto(image, stegMessage);
        FileLoader.SaveFile(parserConfig.getOutPath(),image.imageData);
        System.out.println(new String(image.imageData, StandardCharsets.UTF_8));

    }

    public static void extract(Stenographer sten, ParserConfig parserConfig){
        PlainBMPImage alteredImage = FileLoader.read(parserConfig.getInPath());
        byte[] hiddenFileData = sten.removeFrom(alteredImage,parserConfig.isEncrypts());
        FileLoader.SaveFile(parserConfig.getOutPath(), hiddenFileData);

        if(parserConfig.isEncrypts()){
            System.out.println("ENCRYPTED");
            CipherConfig cipherConfig = new CipherConfig(parserConfig.getMode(),parserConfig.getEncription(),parserConfig.getPassword().getBytes());
            hiddenFileData = cipherConfig.decrypt(hiddenFileData);
        }

        //System.out.println(new String(hiddenFileData, StandardCharsets.UTF_8));
    }

    public static void hideAndSearch(Stenographer sten, ParserConfig parserConfig){

        PlainBMPImage bytes = FileLoader.read(parserConfig.getInPath());
        StegMessage message = new StegPlainMessage("sarasasdasdasdsasa".getBytes(), ".html");
        sten.insertInto(bytes, message);
        byte[] answer = sten.removeFrom(bytes, false);
        System.out.println(new String(answer, StandardCharsets.UTF_8));

    }

    public static Stenographer getStenographer(ParserConfig parserConfig){
        Stenographer stenographer;
        if (parserConfig.getSteg() == SteganographyAlgorithm.LSBE){
            stenographer = new LSBEnhanced();
        }else if (parserConfig.getSteg() == SteganographyAlgorithm.LSB1){
            stenographer = new LSBN(1);
        }else {
            stenographer = new LSBN(4);
        }
        return stenographer;
    }
}
