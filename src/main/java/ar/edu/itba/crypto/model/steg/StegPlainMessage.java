package ar.edu.itba.crypto.model.steg;

import ar.edu.itba.crypto.utils.BitManipulation;

import java.util.Arrays;

public class StegPlainMessage extends StegMessage {

    public StegPlainMessage(byte[] message, String extension) {
        toStegMessage = new byte[4 + message.length + extension.length() + 1];
        System.arraycopy(BitManipulation.toBytes(message.length), 0, toStegMessage, 0, 4);
        System.arraycopy(message, 0, toStegMessage, 4, message.length);
        System.arraycopy(extension.getBytes(), 0, toStegMessage, 4 + message.length, extension.length());
        toStegMessage[toStegMessage.length - 1] = 0;
    }

}
