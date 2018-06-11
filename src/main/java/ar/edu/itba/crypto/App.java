package ar.edu.itba.crypto;


import ar.edu.itba.crypto.utils.ConsoleValues;
import ar.edu.itba.crypto.utils.InputParser;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        String[] arguments = {"embed","-in", "arg", "-p", "file", "-out", "arg", "-steg", "arg"};
        InputParser input = new InputParser(arguments);
        ConsoleValues values = input.parse();
    }
}
