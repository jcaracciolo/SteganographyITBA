package ar.edu.itba.crypto;

import ar.edu.itba.crypto.encryption.BlockMode;
import ar.edu.itba.crypto.encryption.CipherConfig;
import ar.edu.itba.crypto.encryption.EncryptAlgorithm;
import ar.edu.itba.crypto.engine.FileLoader;
import ar.edu.itba.crypto.model.image.PlainBMPImage;
import ar.edu.itba.crypto.model.steg.StegEncriptedMessage;
import ar.edu.itba.crypto.model.steg.StegMessage;
import ar.edu.itba.crypto.model.steg.StegPlainMessage;
import ar.edu.itba.crypto.steganographer.LSBEnhanced;
import ar.edu.itba.crypto.steganographer.LSBN;
import ar.edu.itba.crypto.steganographer.Stenographer;
import ar.edu.itba.crypto.utils.ParserConfig;
import ar.edu.itba.crypto.steganographer.StegType;
import javafx.util.Pair;
import sun.jvm.hotspot.opto.Block;

import java.nio.charset.StandardCharsets;

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
        /*for (StegType type : StegType.values()){
            for (EncryptAlgorithm alg: EncryptAlgorithm.values()) {
                for (BlockMode mode: BlockMode.values()) {
                    try {
                        System.out.println(type + "  " + alg + "  " + mode);
                        ParserConfig parserConfig = new ParserConfig(
                                true,
                                true,
                                "resources/buenosaires.bmp",
                                "","",
                                "buenosaires",
                                type,
                                alg,
                                mode,
                                "solucion") ;
                        if(!parserConfig.isExtract()){
                            hide(parserConfig);
                        } else {
                            extract(parserConfig);
                        }
                    }catch (Exception e) {

                    }
                }
            }
        }*/
        /*ParserConfig parserConfig = new ParserConfig(
                true,
                true,
                "resources/silenceout.wmv",
                "","",
                "final",
                StegType.LSBE,
                EncryptAlgorithm.AES256,
                BlockMode.CFB,
                "solucion") ;
        if(!parserConfig.isExtract()){
            hide(parserConfig);
        } else {
            extract(parserConfig);
        }*/

        bypassImage();


    }

    public static void bypassImage() {
        CipherConfig cipherConfig = new CipherConfig(BlockMode.CFB,EncryptAlgorithm.AES256, "solucion".getBytes());
        byte[] video = FileLoader.GetFileBytes("resources/silenceout.wmv");
        System.out.println(video.length);
        System.out.println("------------------------");
        cipherConfig.decrypt(video);
        System.out.println(video);

    }

    public static void hide(ParserConfig parserConfig){
        byte[] messageToHide = FileLoader.GetFileBytes(parserConfig.getHidePath());
        StegMessage stegMessage = new StegPlainMessage(messageToHide, parserConfig.getHideExtension());
        PlainBMPImage image = FileLoader.read(parserConfig.getInPath());

        if (parserConfig.encrypts()){
            stegMessage = new StegEncriptedMessage(parserConfig.getCipherConfig(),(StegPlainMessage)stegMessage);
        }

        parserConfig.getSteg().stenographer.insertInto(image, stegMessage);
        FileLoader.SaveFile(parserConfig.getOutPath(),image.imageData);
        System.out.println(new String(image.imageData, StandardCharsets.UTF_8));

    }

    public static void extract(ParserConfig parserConfig){
        PlainBMPImage alteredImage = FileLoader.read(parserConfig.getInPath());
        Pair<byte[],String> hiddenFileData = parserConfig.getSteg().stenographer.removeFrom(alteredImage,parserConfig.getCipherConfig());
        FileLoader.SaveFile(parserConfig.getOutPath() + hiddenFileData.getValue(), hiddenFileData.getKey());
    }

//    public static void hideAndSearch(Stenographer sten, ParserConfig parserConfig){
//
//        PlainBMPImage bytes = FileLoader.read(parserConfig.getInPath());
//        StegMessage message = new StegPlainMessage("sarasasdasdasdsasa".getBytes(), ".html");
//        sten.insertInto(bytes, message);
//        byte[] answer = sten.removeFrom(bytes, false);
//        System.out.println(new String(answer, StandardCharsets.UTF_8));
//
//    }
}
