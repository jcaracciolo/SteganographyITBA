package ar.edu.itba.crypto.steganographer;

import ar.edu.itba.crypto.model.image.PlainBMPImage;

public class LSBN extends Stenographer {

    private int n;

    public LSBN(int n){
        this.n = n;
    }

    @Override
    int getBitsPerComponent() {
        return n;
    }

    @Override
    int getNextIndex(int lastIndex, PlainBMPImage image) {
        return lastIndex+1;
    }

}
