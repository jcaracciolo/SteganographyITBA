package ar.edu.itba.crypto.steganographer;

public enum StegType {
    LSB1("LSB1", new LSBN(1)),
    LSB4("LSB4", new LSBN(4)),
    LSBE("LSBE", new LSBEnhanced()),
    LSBPair("LSBPAIR", new LSBPair()),
    LSBBig("LSBBIG", new LSBBig()),
    LSBRED("LSBRED", new LSBRed()),
    LSBGREEN("LSBGREEN", new LSBGreen()),
    LSBBLUE("LSBBLUE", new LSBBlue());

    public Stenographer stenographer;
    public String name;
    StegType(String name, Stenographer stenographer) {
        this.name = name;
        this.stenographer = stenographer;
    }
}
