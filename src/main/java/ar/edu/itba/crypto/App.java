package ar.edu.itba.crypto;


import ar.edu.itba.crypto.engine.FileLoader;
import ar.edu.itba.crypto.model.image.PlainBMPImage;
import ar.edu.itba.crypto.model.steg.StegEncriptedMessage;
import ar.edu.itba.crypto.model.steg.StegMessage;
import ar.edu.itba.crypto.model.steg.StegPlainMessage;
import ar.edu.itba.crypto.utils.ConsoleValues;
import ar.edu.itba.crypto.utils.InputParser;
import ar.edu.itba.crypto.utils.ParserConfig;
import javafx.util.Pair;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;


public class App 
{
    public static void main( String[] args )
    {
        String image = "resources/grupo11/lima.bmp";


        String[] option1 = { "-steg", "LSB1"};
        String[] option2 = { "-steg", "LSB4"};
        String[] option3 = { "-steg", "LSBE"};

        String[][] options = { option1, option2, option3 };

        String[] alg1 = { "-a", "aes128"};
        String[] alg2 = { "-a", "aes192"};
        String[] alg3 = { "-a", "aes256"};
        String[] alg4 = { "-a", "des"};

        String[][] algs = {alg1, alg2, alg3, alg4};

        String[] block1 = { "-m", "ecb"};
        String[] block2 = { "-m", "cfb"};
        String[] block3 = { "-m", "ofb"};
        String[] block4 = { "-m", "cbc"};

        String[][] blocks = {block1, block2, block3, block4};

        String[] baseArguments =  {"-extract", "-p", image, "-out", "secret5", "-pass", "solucion"};

        for(String[] option: options) {
            String[] arguments = concat(baseArguments, option);
            try {
                actualMain(arguments);
                System.out.println(toStringAr(arguments));

//                System.exit(0);
            }catch (Exception e) {
                System.out.println(e);
            }
        }

        for(String[] option: options) {
            for(String[] algo: algs) {
                for (String[] mode : blocks) {

                    String[] arguments = concat(baseArguments, option, algo, mode);
                    try {
                        actualMain(arguments);
                        System.out.println(toStringAr(arguments));

//                        System.exit(0);
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                }
            }
        }


    }

    public static void actualMain(String[] arguments) {
        InputParser input = new InputParser(arguments);
        ParserConfig config = input.parse();
        if(config.isExtract()) {
            extract(config);
        }else {
            hide(config);
        }
    }

    public static void extract(ParserConfig parserConfig){
        PlainBMPImage alteredImage = FileLoader.read(parserConfig.getBmpPath());
        Pair<byte[],String> hiddenFileData = parserConfig.getSteg().stenographer.removeFrom(alteredImage,parserConfig.getCipherConfig());
        FileLoader.SaveFile(parserConfig.getOutPath() + hiddenFileData.getValue(), hiddenFileData.getKey());
    }

    public static void hide(ParserConfig parserConfig){
        byte[] message = FileLoader.GetFileBytes(parserConfig.getInPath());
        StegMessage stegMessage = new StegPlainMessage(message, parserConfig.getExtension());

        PlainBMPImage image = FileLoader.read(parserConfig.getBmpPath());
        if (parserConfig.encrypts()){
            stegMessage = new StegEncriptedMessage(parserConfig.getCipherConfig(),(StegPlainMessage)stegMessage);
        }

        parserConfig.getSteg().stenographer.insertInto(image, stegMessage);
        FileLoader.SaveFile(parserConfig.getOutPath(),image.imageData);
    }


    public static String[] concat(String[]... arrays) {
        int length = 0;
        for (String[] array : arrays) {
            length += array.length;
        }
        String[] result = new String[length];
        int pos = 0;
        for (String[] array : arrays) {
            for (String element : array) {
                result[pos] = element;
                pos++;
            }
        }
        return result;
    }

    public static String toStringAr(String[] strings) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < strings.length; i++) {
            builder.append(strings[i]);
            builder.append(" ");
        }
        return builder.toString();
    }
}
