package ar.edu.itba.crypto.steganographer;

import ar.edu.itba.crypto.model.image.PlainBMPImage;

import static java.lang.System.exit;

public class LSBEnhanced extends Stenographer {

    @Override
    int getBitsPerComponent() {
        return 1;
    }

    @Override
    int getNextIndex(int lastIndex, PlainBMPImage image) {
        int index = lastIndex+1;
        while(image.component(index) < (byte)254) {
            index++;
            if(index>=image.imageData.length) {
                System.err.println("Reached the end of the image file, processing could not be completed");
                exit(-1);
            }
        }

        return index;
    }
}
