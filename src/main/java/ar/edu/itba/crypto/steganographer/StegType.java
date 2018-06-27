package ar.edu.itba.crypto.steganographer;

public enum StegType {
    LSB1(new LSBN(1)),
    LSB4(new LSBN(4)),
    LSBE(new LSBEnhanced());

    public Stenographer stenographer;
    StegType(Stenographer stenographer) {
        this.stenographer = stenographer;
    }
}
