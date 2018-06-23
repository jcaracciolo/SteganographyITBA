package ar.edu.itba.crypto.steganographer;

import ar.edu.itba.crypto.model.image.PlainBMPImage;
import ar.edu.itba.crypto.model.steg.StegMessage;
import ar.edu.itba.crypto.utils.BitManipulation;

import static java.lang.System.exit;

public abstract class Stenographer {
    private static int BITS_FOR_SIZE = 32;

    abstract int getBitsPerComponent();
    abstract int getNextIndex(int lastIndex, PlainBMPImage image);

    private int currentIndex = -1;

    private byte nextComponent(PlainBMPImage image) {
        currentIndex = getNextIndex(currentIndex, image);
        return image.component(currentIndex);
    }

    public void insertInto(PlainBMPImage original, StegMessage message) {
        currentIndex = -1;
        int n = getBitsPerComponent();

        //Go until message is done or there is no more components to hide
        for (int i = 0; i < original.componentsSize() && !message.isDone(); i++) {

            //Get byte from where to extract data
            byte originalByte = nextComponent(original);

            //Replace at nextComponentIndex
            original.imageData[original.indexOfComponent(currentIndex)] =
                    BitManipulation.replace(originalByte, message.getNextBits(n), n);
        }

        if(!message.isDone()) {
            System.err.println("There is no enough room in the photo for this message");
            exit(-1);
        }

    }

    public byte[] removeFrom(PlainBMPImage altered, boolean isEncrypted) {
        currentIndex = -1;
        //n is the amount of bits hidden in a component
        int n = getBitsPerComponent();

        //Components needed to hide the size
        int componentsForSize = BITS_FOR_SIZE/n;

        //Components needed to hide a byte
        int componentsForByte = 8/n;

        int size = 0;
        for (int i = 0; i < componentsForSize; i++) {

            //Move n to make room for the new n bits
            size <<= n;

            //Get from the nextComponent the n-last bits
            size += BitManipulation.getNBits(nextComponent(altered), altered.getBitsPerComponent()-n, n);
        }

        //Place where the message will be held
        byte[] answer = new byte[size];

        //Counter for the bytes in the answer
        int answerByte = 0;

        //Retrieve size bytes => size*componentsForByte components
        for (int i = 0; i < size*componentsForByte;) {
            //Make room for the following n bytes
            answer[answerByte] <<= n;

            //Get from the next component the n-last bits
            answer[answerByte] += BitManipulation.getNBits(nextComponent(altered), altered.getBitsPerComponent()-n, n);

            //add to answerByte if we completed a byte
            i++;
            if(i%componentsForByte==0) {
                answerByte++;
            }
        }

        //Get the extension
        StringBuilder str = new StringBuilder();
        if(!isEncrypted) {
            byte b = 0;
            do {
                //Retrieve byte by byte as we have to traverse componentsForByte components
                for (int i = 0; i <componentsForByte; i++) {
                    b<<=n;
                    b|= BitManipulation.getNBits(nextComponent(altered), altered.getBitsPerComponent()-n, n);
                }

                str.append((char)b);
            }while(b!=(byte)0);
        }

        return BitManipulation.concat(answer, str.toString().getBytes());
    }


}
