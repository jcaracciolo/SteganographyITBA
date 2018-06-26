package ar.edu.itba.crypto.encryption;

public enum EncryptAlgorithm {
    AES128("aes128", "AES", 128, 128),
    AES192("aes192", "AES", 192, 128),
    AES256("aes256", "AES", 256, 128),
    DES("des", "DES", 64, 64);

    public String name;
    public String sslName;
    public int keySize;
    public int ivSize;

    EncryptAlgorithm(String name, String sslName, int bitsKeySize, int bitsIvSize) {
        this.name = name;
        this.sslName = sslName;
        this.keySize = bitsKeySize/8;
        this.ivSize = bitsIvSize/8;
    }
}
