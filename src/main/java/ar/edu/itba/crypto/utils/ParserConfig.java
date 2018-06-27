package ar.edu.itba.crypto.utils;

import ar.edu.itba.crypto.encryption.BlockMode;
import ar.edu.itba.crypto.encryption.CipherConfig;
import ar.edu.itba.crypto.encryption.EncryptAlgorithm;
import ar.edu.itba.crypto.steganographer.StegType;

public class ParserConfig {

    boolean encrypts;
    boolean extract;
    String hidePath;
    String hideExtension;
    String inPath;
    String outPath;
    StegType steg;

    EncryptAlgorithm encription;
    BlockMode mode;
    String password;

    CipherConfig cipherConfig = null;

    public boolean isEncrypts() {
        return encrypts;
    }

    public String getHidePath() {
        return hidePath;
    }

    public String getHideExtension() {
        return hideExtension;
    }

    public ParserConfig(boolean encrypts, boolean extract, String inPath, String hidePath, String hideExtension, String outPath, StegType steg, EncryptAlgorithm encription, BlockMode mode, String password) {
        this.encrypts = encrypts;
        this.extract = extract;
        this.hidePath = hidePath;
        this.inPath = inPath;
        this.outPath = outPath;
        this.steg = steg;
        this.encription = encription;
        this.mode = mode;
        this.password = password;
        if(isEncrypts()) {
            if(this.password != null) {
                this.cipherConfig = new CipherConfig(mode, encription, password.getBytes());
            }
        }
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

    public StegType getSteg() {
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

    public CipherConfig getCipherConfig() {
        return cipherConfig;
    }
}
