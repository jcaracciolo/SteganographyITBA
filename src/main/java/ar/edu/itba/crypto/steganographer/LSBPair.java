package ar.edu.itba.crypto.steganographer;

import ar.edu.itba.crypto.model.image.PlainBMPImage;

import static java.lang.System.exit;

public class LSBPair extends Stenographer {

    @Override
    int getBitsPerComponent() {
        return 2;
    }

    @Override
    int getNextIndex(int lastIndex, PlainBMPImage image) { return lastIndex+2; }

}
