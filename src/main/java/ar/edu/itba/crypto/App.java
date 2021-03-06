package ar.edu.itba.crypto;


import ar.edu.itba.crypto.engine.FileLoader;
import ar.edu.itba.crypto.model.image.PlainBMPImage;
import ar.edu.itba.crypto.model.steg.StegEncriptedMessage;
import ar.edu.itba.crypto.model.steg.StegMessage;
import ar.edu.itba.crypto.model.steg.StegPlainMessage;
import ar.edu.itba.crypto.utils.ConsoleValues;
import ar.edu.itba.crypto.utils.InputParser;
import ar.edu.itba.crypto.utils.MyPair;
import ar.edu.itba.crypto.utils.ParserConfig;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import static java.lang.System.exit;


public class App
{
    public static void main( String[] args ) {
//        String[] arguments = {"-h","-in", "README.md","-p", "white.bmp", "-out", "arg", "-steg", "LSB1"};
        actualMain(args);
    }


    public static void testAllOptions() {
        String image = "lado.bmp";


        String[] option1 = { "-steg", "LSB1"};
        String[] option2 = { "-steg", "LSB4"};
        String[] option3 = { "-steg", "LSBE"};
        String[] option4 = { "-steg", "LSBRED"};
        String[] option5 = { "-steg", "LSBGREEN"};
        String[] option6 = { "-steg", "LSBBLUE"};
        String[] option8 = { "-steg", "LSBPAIR"};

        String[][] options = { option1, option2, option3, option4, option5, option6, option8 };

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

        String[] embedBaseArguments =  {"-embed", "-p", image, "-in", "logo.resized.resized.png", "-pass", "solucion"};

        for(String[] option: options) {
            String[] outFile = { "-out", toStrWithDots(option) + ".bmp" };
            String[] arguments = concat(embedBaseArguments, outFile, option);
            try {
                actualMain(arguments);
                System.out.println(toStringAr(arguments));
            }catch (Exception e) {
                System.out.println(e);
            }
        }

        for(String[] option: options) {
            for(String[] algo: algs) {
                for (String[] mode : blocks) {
                    String[] outFile = { "-out", toStrWithDots(concat(option, algo, mode)) + ".bmp" };
                    String[] arguments = concat(embedBaseArguments, option, algo, mode, outFile);
                    try {
                        actualMain(arguments);
                        System.out.println(toStringAr(arguments));
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                }
            }
        }

        String[] extractBaseArguments =  {"-extract", "-pass", "solucion"};


        for(String[] option: options) {
            String[] outFile = { "-out", toStrWithDots(option) + "out" };
            String[] inFile = { "-p", toStrWithDots(option) + ".bmp" };
            String[] arguments = concat(extractBaseArguments, outFile, inFile, option);
            try {
                actualMain(arguments);
                System.out.println(toStringAr(arguments));
            }catch (Exception e) {
                System.out.println(e);
            }
        }

        for(String[] option: options) {
            for(String[] algo: algs) {
                for (String[] mode : blocks) {
                    String[] outFile = { "-out", toStrWithDots(concat(option, algo, mode)) + "out" };
                    String[] inFile = { "-p", toStrWithDots(concat(option, algo, mode)) + ".bmp" };
                    String[] arguments = concat(extractBaseArguments, option, algo, mode, outFile, inFile);
                    try {
                        actualMain(arguments);
                        System.out.println(toStringAr(arguments));
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                }
            }
        }


    }

    public static void fileGenerator(int bits) {
        byte[] file = FileLoader.GetFileBytes("resources/grupo11/silence.bmp");
        int size = (int)(file.length/8*bits * 0.3);
        Random rand = new Random();
        for (int i = 56; i <size ; i++) {
            file[i]=(byte)0xFFFFFFFF;
        }
        FileLoader.SaveFile("white.bmp", Arrays.copyOf(file,size));
    }

    public static void actualMain(String[] arguments) {
        System.out.println(toStringAr(arguments));

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
        MyPair<byte[],String> hiddenFileData = null;
        try {
            hiddenFileData = parserConfig.getSteg().stenographer.removeFrom(alteredImage,parserConfig.getCipherConfig());
        }catch (ArrayIndexOutOfBoundsException a) {
            System.err.println("Encripted size must be wrong");
            exit(-1);
        }

        FileLoader.SaveFile(parserConfig.getOutPath() + hiddenFileData.getValue(), hiddenFileData.getKey());
    }

    public static void hide(ParserConfig parserConfig){
        byte[] message = FileLoader.GetFileBytes(parserConfig.getInPath());
        StegMessage stegMessage = new StegPlainMessage(message, parserConfig.getExtension());

        PlainBMPImage image = FileLoader.read(parserConfig.getBmpPath());
        if (parserConfig.encrypts()){
            stegMessage = new StegEncriptedMessage(parserConfig.getCipherConfig(),(StegPlainMessage)stegMessage);
        }

        try {
            parserConfig.getSteg().stenographer.insertInto(image, stegMessage);
        }catch (ArrayIndexOutOfBoundsException a) {
            System.err.println("File is too big");
            exit(-1);
        }
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

    public static String toStrWithDots(String[] strings) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < strings.length; i++) {
            builder.append(strings[i]);
            builder.append("-");
        }
        return builder.toString().replace("-","_");
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
