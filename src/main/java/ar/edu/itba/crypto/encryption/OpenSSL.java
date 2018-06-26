package ar.edu.itba.crypto.encryption;

import ar.edu.itba.crypto.utils.BitManipulation;
import javafx.util.Pair;

import javax.crypto.Cipher;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Arrays;

public class OpenSSL {

    public static Pair<byte[],byte[]> EVP_BytesToKey(byte[] data, int keySize, int ivSize) {
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("SHA-256");
        }catch (Exception e){
            System.err.println("WTF");
        }

        byte[] ans = new byte[0];
        for (int i = 0; ans.length<(keySize+ivSize); i++) {
            byte[] di =  key_derive(digest, i, data);
            ans = BitManipulation.concat(ans,di);
        }

        byte[] key = new byte[keySize];
        byte[] iv = new byte[ivSize];

        System.arraycopy(ans, 0,key, 0, keySize);
        System.arraycopy(ans, keySize,iv, 0, ivSize);
        return new Pair<>(key, iv);
    }

    static private byte[] key_derive(MessageDigest digest, int i, byte[] data){
        if(i==0) { return new byte[0]; }
        return digest.digest(BitManipulation.concat(key_derive(digest, i-1, data), data));
    }
}
