package ar.edu.itba.crypto.steganographer;

public abstract class Stegranographer {

    private int insertingMessageByteIndex = 0;

    public void insertMessage(byte[] original, byte[] message) {
        insertingMessageByteIndex = 0;
        for (int i = 0; i < original.length; i++) {

        }
    }

//    abstract void insertBySteganographer(byte[] original, byte[] message);
//
//    abstract byte getMaskToInsert();
//


}
