package ar.edu.itba.crypto;


import ar.edu.itba.crypto.utils.ConsoleValues;
import ar.edu.itba.crypto.utils.InputParser;
import ar.edu.itba.crypto.utils.ParserConfig;


public class App 
{
    public static void main( String[] args )
    {
        String[] arguments = {"-embed","-in", "arg","-p", "file", "-out", "arg", "-steg", "LSB1", "-a","aes128"};
        InputParser input = new InputParser(arguments);
        ParserConfig values = input.parse();
        /*System.out.println(values.getEncription());
        System.out.println(values.getHidePath());
        System.out.println(values.getInPath());
        System.out.println(values.getMode());
        System.out.println(values.getPassword());
        System.out.println(values.getSteg());
        System.out.println(values.getHideExtension());
        System.out.println(values.getOutPath());*/
    }
}
