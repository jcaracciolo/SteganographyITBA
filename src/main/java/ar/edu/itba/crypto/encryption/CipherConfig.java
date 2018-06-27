package ar.edu.itba.crypto.encryption;

import javafx.util.Pair;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import static java.lang.System.exit;
import static java.lang.System.setErr;

public class CipherConfig {

    private Cipher cipher;
    private int keySize;
    private int ivSize;
    private byte[] password;
    private BlockMode mode;
    private EncryptAlgorithm algorithm;

    public CipherConfig(BlockMode mode, EncryptAlgorithm algorithm, byte[] password){
        keySize = algorithm.keySize;
        ivSize = algorithm.ivSize;
        this.mode = mode;
        this.algorithm = algorithm;
        this.password = password;


    }

    private void setupCipher(int encryptionMode) throws InvalidAlgorithmParameterException, InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException {
        Pair<byte[], byte[]> pair = OpenSSL.EVP_BytesToKey(password,keySize,ivSize);
        byte[] keyBytes = pair.getKey();
        byte[] ivBytes = pair.getValue();

        SecretKeySpec key = new SecretKeySpec(keyBytes, algorithm.sslName);
        IvParameterSpec iv = new IvParameterSpec(ivBytes);
        cipher = Cipher.getInstance(algorithm.sslName + "/" + mode.sslName + "/PKCS5Padding");
        cipher.init(encryptionMode, key, iv);
    }

    public byte[] encrypt(byte[] message){
        try {
            setupCipher(Cipher.ENCRYPT_MODE);
            return cipher.doFinal(message);
        }catch (Exception e){
            System.err.println("The encryption parameters must be wrong");
            exit(-1);
        }

        return new byte[0];
    }

    public byte[] decrypt(byte[] message){
        try {
            setupCipher(Cipher.DECRYPT_MODE);
            return cipher.doFinal(message);
        }catch (Exception e){
            System.err.println("The encryption parameters must be wrong");
            exit(-1);
        }

        return new byte[0];
    }

}
