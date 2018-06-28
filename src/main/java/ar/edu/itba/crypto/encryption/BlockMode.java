package ar.edu.itba.crypto.encryption;

public enum BlockMode {
    ECB("ecb","ECB", "PKCS5Padding"),
    CFB("cfb","CFB", "NoPadding"),
    OFB("ofb","OFB", "NoPadding"),
    CBC("cbc","CBC", "NoPadding");

    String name;
    String sslName;
    String padding;

    BlockMode(String name, String sslName, String padding) {
        this.name = name;
        this.sslName = sslName;
        this.padding = padding;
    }
}
