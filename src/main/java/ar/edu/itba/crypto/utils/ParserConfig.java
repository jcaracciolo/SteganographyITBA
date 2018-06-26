package ar.edu.itba.crypto.utils;

public class ParserConfig {

    boolean extract;
    String hidePath;
    String inPath;
    String outPath;
    SteganographyAlgorithm steg;

    EncryptionAlgorithm encription;
    EncryptionMode mode;
    String password;

    public ParserConfig(boolean extract, String inPath, String hidePath, String outPath, SteganographyAlgorithm steg, EncryptionAlgorithm encription, EncryptionMode mode, String password) {
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

    public EncryptionAlgorithm getEncription() {
        return encription;
    }

    public EncryptionMode getMode() {
        return mode;
    }

    public String getPassword() {
        return password;
    }
}
