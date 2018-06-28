package ar.edu.itba.crypto.utils;

import java.io.File;

public class ConsoleValues {

    private File inputFile;
    private File bitmapFile;
    private File outputFile;
    private String steg;
    private String a;
    private String m;
    private String pass;

    public ConsoleValues(File inputFile, File bitmapFile, File outputFile, String steg, String a, String m, String pass) {
        this.inputFile = inputFile;
        this.bitmapFile = bitmapFile;
        this.outputFile = outputFile;
        this.steg = steg;
        this.a = a;
        this.m = m;
        this.pass = pass;

    }


}
