package ar.edu.itba.crypto;


import ar.edu.itba.crypto.utils.InputParser;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        String[] argumentos = {"-h"};
        InputParser input = new InputParser(argumentos);
        input.parse();
    }
}
