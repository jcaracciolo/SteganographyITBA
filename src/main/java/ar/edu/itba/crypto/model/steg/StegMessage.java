package ar.edu.itba.crypto.model.steg;

import ar.edu.itba.crypto.utils.BitManipulation;

public abstract class StegMessage {

    byte[] toStegMessage;

    private int bitIndex = 0;

    public byte getNextBits(int n) {
        int byteIndex = bitIndex/8;
        int currentBit = bitIndex%8;
        bitIndex+=n;
        byte ans = BitManipulation.getNBits(toStegMessage[byteIndex],currentBit, n);
        return ans;
    }

    public boolean isDone() {
        return bitIndex >= toStegMessage.length*8;
    }

}
