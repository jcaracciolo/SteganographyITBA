package ar.edu.itba.crypto.encryption;

import ar.edu.itba.crypto.utils.BitManipulation;
import javafx.util.Pair;

import javax.crypto.Cipher;
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
        for (int i = 0; ans.length<16; i++) {
            ans = BitManipulation.concat(ans, key_derive(digest, i, data));
        }

        byte[] key = new byte[keySize];
        byte[] iv = new byte[ivSize];

        System.arraycopy(ans, 0,key, 0, keySize);
        System.arraycopy(ans, keySize,iv, 0, ivSize);
        return new Pair<>(key, iv);
    }

    static private byte[] key_derive(MessageDigest digest, int i, byte[] data){
        if(i==0) { return new byte[0]; }
        return BitManipulation.concat(recHash(digest, key_derive(digest,i-1,data).length, data), data);
    }

    static private byte[] recHash(MessageDigest digest, int i, byte[] data){
        if(i<=1){
            return digest.digest(data);
        } else {
            return digest.digest(recHash(digest, i-1, data));
        }
    }
}
