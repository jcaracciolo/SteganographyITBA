package ar.edu.itba.crypto.utils;

import org.apache.commons.cli.*;

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



    public InputParser(String[] args) {
        this.args = args;

        options.addOption(helpShortArg, helpArg, false, "show help");
        options.addOption(null, embed, false, "Embed");
        options.addOption(null, in, true, "Missing file");
        options.addOption(null, p, true, "Missing bitmap");
        options.addOption(null, out, true, "Missing bitmapFile");
        options.addOption(null, steg, true, "Missing arguments");
        options.addOption(null, a, true, "Missing arguments");
        options.addOption(null, m, true, "Missing arguments");
        options.addOption(null, pass, true, "Missing argument");
    }

    public ConsoleArguments parse() {
        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = null;
        ConsoleArguments arguments = null;
        try {
            cmd = parser.parse(options, args);

            if(cmd.hasOption(helpShortArg)) {
                showHelp();
                exit(0);
            }

            if(cmd.hasOption(embed)){
              //TODO: embed
            }

            if(!cmd.hasOption(in)) throw new IllegalStateException("No file specified. Use -h,--help for more information");
            String fileName = cmd.getOptionValue(in);
            if(!cmd.hasOption(p)) throw new IllegalStateException("No bitmap specified. Use -h,--help for more information");
            String bitmap = cmd.getOptionValue(p);
            if(!cmd.hasOption(out)) throw new IllegalStateException("No bitmapFile specified. Use -h,--help for more information");
            String bitmapFile = cmd.getOptionValue(out);
            if(!cmd.hasOption(steg)) throw new IllegalStateException("No arguments specified. Use -h,--help for more information");
            String stegArgs = cmd.getOptionValue(steg);
            if(!cmd.hasOption(a)) throw new IllegalStateException("No arguments specified. Use -h,--help for more information");
            String aArgs = cmd.getOptionValue(a);
            if(!cmd.hasOption(m)) throw new IllegalStateException("No arguments specified. Use -h,--help for more information");
            String mArgs = cmd.getOptionValue(m);
            if(!cmd.hasOption(pass)) throw new IllegalStateException("No arguments specified. Use -h,--help for more information");
            String password = cmd.getOptionValue(pass);



            arguments = new ConsoleArguments(embed,in,p,out,steg,a,m,pass);


        } catch (Exception e) {
            System.err.println("An error was found: " + e.getMessage());
            exit(-1);
        }

        return arguments;
    }

    private void showHelp() {
        HelpFormatter formater = new HelpFormatter();
        formater.printHelp("Main", options);
    }
}
