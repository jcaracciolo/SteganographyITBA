package ar.edu.itba.crypto.utils;

import ar.edu.itba.crypto.encryption.BlockMode;
import ar.edu.itba.crypto.encryption.EncryptAlgorithm;
import ar.edu.itba.crypto.steganographer.StegType;
import org.apache.commons.cli.*;


import java.util.Arrays;
import java.util.function.Supplier;

import static java.lang.System.exit;

public class InputParser {

    private String[] args;
    private Options options = new Options();

    private static final String helpShortArg = "h";
    private static final String helpArg = "help";

    private static final String embed = "embed";
    private static final String in = "in";
    private static final String p = "p";
    private static final String out = "out";
    private static final String steg = "steg";
    private static final String a = "a";
    private static final String m = "m";
    private static final String pass = "pass";
    private static final String extract = "extract";


    private String stegArgs;
    private String password;
    private String encryptMethod;
    private String encryptAlgorithm;

    /*ParserConfig parameters*/
    private StegType stegParam;
    private EncryptAlgorithm encryptParam;
    private BlockMode blockMode;
    private String bitmap;
    private String fileName;
    private String outputName;
    private Boolean isExtract = false;
    private Boolean isEncrypt = false;


    public InputParser(String[] args) {
        this.args = args;

        options.addOption(helpShortArg, helpArg, false, "show help");
        options.addOption(null, embed, false, "Indica que se va a2 ocultar la información.");
        options.addOption(null, extract, false, "Indica que se va a2 extraer informaciòn");
        options.addOption(null, in, true, "Archivo que se va a2 ocultar.");
        options.addOption(null, p, true, "Archivo bmp que será el portador.");
        options.addOption(null, out, true, "Archivo bmp de salida, es decir, el archivo bitmapfile con la informacion de file incrustada.");
        options.addOption(null, steg, true, "algoritmo de estenografiado: LSB de 1 bit, LSB de 4 bits, LSB Enhanced");
        /*Opcionales*/
        options.addOption(null, a, true, "aes128 | aes192 | aes256 | des");
        options.addOption(null, m, true, "ecb | cfb | ofb | cbc");
        options.addOption(null, pass, true, "Password de encripción.");
    }

    public ParserConfig parse() {
        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = null;
        ConsoleValues values = null;
        try {
            cmd = parser.parse(options, args);

            if (cmd.hasOption(helpShortArg)) {
                showHelp();
                exit(0);
            }

            if (!cmd.hasOption(embed) && !cmd.hasOption(extract)){
                throw new IllegalStateException("No action specified. Use -h,--help for more information");
            } else {
                if(cmd.hasOption(embed) && cmd.hasOption(extract)){
                    throw new IllegalStateException("Cannot perform 2 actions at once");
                }
                if(cmd.hasOption(embed)) {
                    isExtract = false;
                    //Mandatory parameters
                    if (!cmd.hasOption(in))
                        throw new IllegalStateException("in: No file specified. Use -h,--help for more information");
                    fileName = cmd.getOptionValue(in);
                    if (!cmd.hasOption(p))
                        throw new IllegalStateException("p: No bitmap specified. Use -h,--help for more information");
                    bitmap = cmd.getOptionValue(p);
                    if (!cmd.hasOption(out))
                        throw new IllegalStateException("out: No bitmapFile specified. Use -h,--help for more information");
                    outputName = cmd.getOptionValue(out);
                    if (!cmd.hasOption(steg))
                        throw new IllegalStateException("Steg: No arguments specified. Use -h,--help for more information");
                    stegArgs = cmd.getOptionValue(steg);
                }else {
                    isExtract = true;
                    //Invalid parameters
                    if (cmd.hasOption(in))
                        throw new IllegalStateException("Invalid parameter -in for extract action");

                    //Mandatory parameters
                    if (!cmd.hasOption(p))
                        throw new IllegalStateException("p: No bitmap specified. Use -h,--help for more information");
                    bitmap = cmd.getOptionValue(p);
                    if (!cmd.hasOption(out))
                        throw new IllegalStateException("out: No bitmapFile specified. Use -h,--help for more information");
                    String outputFile = cmd.getOptionValue(out);
                    if (!cmd.hasOption(steg))
                        throw new IllegalStateException("Steg: No arguments specified. Use -h,--help for more information");
                    stegArgs = cmd.getOptionValue(steg);
                }
            }
            outputName = cmd.getOptionValue(out);
            String stegArgs = cmd.getOptionValue(steg).toUpperCase();


            Supplier<IllegalStateException> suplier = () ->
                     new IllegalStateException("Steg: Invalid arguments specified. Use -h,--help for more information");
            stegParam = Arrays.stream(StegType.values()).filter(s -> s.name.equals(stegArgs) ).findAny().orElseThrow(suplier);


            if(cmd.hasOption(a)){
                isEncrypt = true;
                encryptMethod = cmd.getOptionValue(a).toLowerCase();
                switch (encryptMethod){
                    case "aes128":
                        encryptParam = EncryptAlgorithm.AES128;
                        break;
                    case "aes192":
                        encryptParam = EncryptAlgorithm.AES192;
                        break;
                    case "aes256":
                        encryptParam = EncryptAlgorithm.AES256;
                        break;
                    case "des":
                        encryptParam = EncryptAlgorithm.DES;
                        break;
                    default:
                        throw new IllegalStateException("a2: Invalid arguments for encryption. Use -h,--help for more information");
                }
            }else{
                encryptParam = EncryptAlgorithm.AES128;
            }
            if(cmd.hasOption(m)){
                encryptAlgorithm = cmd.getOptionValue(m).toLowerCase();
                switch (encryptAlgorithm) {
                    case "ecb":
                        blockMode = BlockMode.ECB;
                        break;
                    case "cfb":
                        blockMode = BlockMode.CFB;
                        break;
                    case "ofb":
                        blockMode = BlockMode.OFB;
                        break;
                    case "cbc":
                        blockMode = BlockMode.CBC;
                        break;
                    default:
                        throw new IllegalStateException("m: Invalid arguments for encryption algorithm. Use -h,--help for more information");
                }
            }else{
                blockMode = BlockMode.CBC;
            }
            if(cmd.hasOption(pass)){
                password = cmd.getOptionValue(pass);
            }


        } catch (Exception e) {
            System.err.println("An error was found: " + e.getMessage());
            exit(-1);
        }

        ParserConfig parserConfig = new ParserConfig(
                isExtract,
                isEncrypt,
                fileName,
                bitmap,
                outputName,
                stegParam,
                encryptParam,
                blockMode,
                password) ;


        return parserConfig;
    }

    private void showHelp() {
        HelpFormatter formater = new HelpFormatter();
        formater.printHelp("Main", options);
    }
}
