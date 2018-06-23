package ar.edu.itba.crypto.steganographer;

import ar.edu.itba.crypto.model.image.PlainBMPImage;
import ar.edu.itba.crypto.model.steg.StegMessage;
import ar.edu.itba.crypto.utils.BitManipulation;

import java.util.stream.Stream;

public class LSBN extends Stegranographer {

    private int n;
    private static int BITS_FOR_SIZE = 32;

    public LSBN(int n){
        this.n = n;
    }

    public void insertInto(PlainBMPImage original, StegMessage message) {
        for (int i = 0; i < original.componentsSize() && !message.isDone(); i++) {
            byte originalByte = original.component(i);
            original.imageData[original.indexOfComponent(i)] =
                    BitManipulation.replace(originalByte, message.getNextBits(n), n);
        }
    }

    public byte[] removeFrom(PlainBMPImage altered, boolean isEncripted) {
        int componentsForSize = BITS_FOR_SIZE/n;
        int componentsForByte = 8/n;

        int size = 0;
        for (int i = 0; i < componentsForSize; i++) {
            size <<= n;
            size += BitManipulation.getNBits(altered.component(i), altered.getBitsPerComponent()-n, n);
        }

        byte[] answer = new byte[size];
        int messageByte = 0;
        for (int i = 0; i < size*componentsForByte;) {
            answer[messageByte] <<= n;
            answer[messageByte] |= BitManipulation.getNBits(altered.component(i + componentsForSize), altered.getBitsPerComponent()-n, n);
            i++;
            if(i%componentsForByte==0) {
                messageByte++;
            }
        }

        StringBuilder str = new StringBuilder();
        if(!isEncripted) {
            byte b = 0;
            int baseForExtension = size*componentsForByte + componentsForSize;
            int extension = 0;
            do {
                for (int i = 0; i <componentsForByte; i++) {
                    b<<=n;
                    b|= BitManipulation.getNBits(altered.component(i + extension + baseForExtension), altered.getBitsPerComponent()-n, n);
                }

                str.append((char)b);
                extension+=componentsForByte;
            }while(b!=(byte)0);
        }



        return BitManipulation.concat(answer, str.toString().getBytes());
    }
}
