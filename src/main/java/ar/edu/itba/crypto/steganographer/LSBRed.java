package ar.edu.itba.crypto.steganographer;

import ar.edu.itba.crypto.model.image.PlainBMPImage;

public class LSBRed extends Stenographer {

    @Override
    int getBitsPerComponent() {
        return 4;
    }

    @Override
    int getNextIndex(int lastIndex, PlainBMPImage image) {
        if(lastIndex == -1) {
            return 0;
        }
        return lastIndex+3;
    }

}
