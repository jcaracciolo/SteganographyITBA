package ar.edu.itba.crypto.steganographer;

import ar.edu.itba.crypto.model.image.PlainBMPImage;

public class LSBBig extends Stenographer {

    @Override
    int getBitsPerComponent() {
        return 6;
    }

    @Override
    int getNextIndex(int lastIndex, PlainBMPImage image) {
        return lastIndex+1;
    }
}
