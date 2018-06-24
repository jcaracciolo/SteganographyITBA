package ar.edu.itba.crypto.model.steg;

import ar.edu.itba.crypto.encryption.CipherConfig;
import ar.edu.itba.crypto.utils.BitManipulation;

public class StegEncriptedMessage extends StegMessage {

    byte[] toStegMessage;

    public StegEncriptedMessage(CipherConfig config, StegPlainMessage message){
        byte[] plainStegMessage = message.toStegMessage;
        byte[] encrypted = config.encrypt(plainStegMessage);
        toStegMessage = new byte[4 + encrypted.length];
        System.arraycopy(BitManipulation.toBytes(encrypted.length), 0, toStegMessage, 0, 4);
        System.arraycopy(encrypted, 0, toStegMessage, 4, encrypted.length);
    }
}
