package ar.edu.itba.crypto.utils;

import java.nio.ByteBuffer;

public class BitManipulation {

    public static byte[] toBytes(int i){
        byte[] result = new byte[4];

        result[0] = (byte) (i >> 24);
        result[1] = (byte) (i >> 16);
        result[2] = (byte) (i >> 8);
        result[3] = (byte) (i /*>> 0*/);

        return result;
    }

    public static byte getNBits(byte c, int index, int n){
        byte left = (byte)(c << index);
        return (byte)(left >>> 8-n & ((1<<n)-1));
    }

    public static byte replace(byte originalByte, byte nextBits, Integer n) {
        int mask = ~((1<<n)-1);
        return (byte)((originalByte & mask) | nextBits);
    }

    public static byte[] concat(byte[]... arrays) {
        int length = 0;
        for (byte[] array : arrays) {
            length += array.length;
        }
        byte[] result = new byte[length];
        int pos = 0;
        for (byte[] array : arrays) {
            for (byte element : array) {
                result[pos] = element;
                pos++;
            }
        }
        return result;
    }



}
