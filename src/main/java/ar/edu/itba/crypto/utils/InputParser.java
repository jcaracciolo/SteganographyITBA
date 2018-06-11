package ar.edu.itba.crypto.utils;

import org.apache.commons.cli.*;


import java.io.File;

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

    /*Placeholders*/
    private File input = null;
    private File bitmapFile = null;
    private File output = null;




    public InputParser(String[] args) {
        this.args = args;

        options.addOption(helpShortArg, helpArg, false, "show help");
        options.addOption(null, embed, false, "Indica que se va a ocultar la información.");
        options.addOption(null, in, true, "Archivo que se va a ocultar.");
        options.addOption(null, p, true, "Archivo bmp que será el portador.");
        options.addOption(null, out, true, "Archivo bmp de salida, es decir, el archivo bitmapfile con la informacion de file incrustada.");
        options.addOption(null, steg, true, "algoritmo de estenografiado: LSB de 1 bit, LSB de 4 bits, LSB Enhanced");
        /*Opcionales*/
        options.addOption(null, a, true, "aes128 | aes192 | aes256 | des");
        options.addOption(null, m, true, "ecb | cfb | ofb | cbc");
        options.addOption(null, pass, true, "Password de encripción.");
    }

    public ConsoleValues parse() {
        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = null;
        ConsoleValues values = null;
        try {
            cmd = parser.parse(options, args);

            if(cmd.hasOption(helpShortArg)) {
                showHelp();
                exit(0);
            }


            if(!cmd.hasOption(in)) throw new IllegalStateException("in: No file specified. Use -h,--help for more information");
            String fileName = cmd.getOptionValue(in);
            if(!cmd.hasOption(p)) throw new IllegalStateException("p: No bitmap specified. Use -h,--help for more information");
            String bitmap = cmd.getOptionValue(p);
            if(!cmd.hasOption(out)) throw new IllegalStateException("out: No bitmapFile specified. Use -h,--help for more information");
            String outputFile = cmd.getOptionValue(out);
            if(!cmd.hasOption(steg)) throw new IllegalStateException("Steg: No arguments specified. Use -h,--help for more information");
            String stegArgs = cmd.getOptionValue(steg);

            values = new ConsoleValues(input,bitmapFile,output,steg,a,m,pass);


        } catch (Exception e) {
            System.err.println("An error was found: " + e.getMessage());
            exit(-1);
        }

        return values;
    }

    private void showHelp() {
        HelpFormatter formater = new HelpFormatter();
        formater.printHelp("Main", options);
    }
}
