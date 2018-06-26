package ar.edu.itba.crypto.utils;

import ar.edu.itba.crypto.encryption.BlockMode;
import ar.edu.itba.crypto.encryption.EncryptAlgorithm;

public class ParserConfig {

    boolean encrypts;
    boolean extract;
    String hidePath;
    String hideExtension;
    String inPath;
    String outPath;
    SteganographyAlgorithm steg;

    EncryptAlgorithm encription;
    BlockMode mode;
    String password;

    public boolean isEncrypts() {
        return encrypts;
    }

    public String getHidePath() {
        return hidePath;
    }

    public String getHideExtension() {
        return hideExtension;
    }

    public ParserConfig(boolean encrypts, boolean extract, String inPath, String hidePath, String hideExtension, String outPath, SteganographyAlgorithm steg, EncryptAlgorithm encription, BlockMode mode, String password) {
        this.encrypts = encrypts;
        this.extract = extract;
        this.hidePath = hidePath;
        this.inPath = inPath;
        this.outPath = outPath;
        this.steg = steg;
        this.encription = encription;
        this.mode = mode;
        this.password = password;
    }

    public boolean isExtract() {
        return extract;
    }

    public String getInPath() {
        return inPath;
    }

    public String getOutPath() {
        return outPath;
    }

    public SteganographyAlgorithm getSteg() {
        return steg;
    }

    public EncryptAlgorithm getEncription() {
        return encription;
    }

    public BlockMode getMode() {
        return mode;
    }

    public String getPassword() {
        return password;
    }

    public boolean encrypts() { return encrypts; }
}
