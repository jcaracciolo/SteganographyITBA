package ar.edu.itba.crypto.encryption;

public enum BlockMode {
    ECB("ecb","ECB"),
    CFB("cfb","CFB"),
    OFB("ofb","OFB"),
    CBC("cbc","CBC");

    String name;
    String sslName;
    BlockMode(String name, String sslName) {
        this.name = name;
        this.sslName = sslName;
    }
}
