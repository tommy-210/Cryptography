package Methods;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import Constants.Const;
import java.util.Base64;

public class EncryptionASE {

    public String[] generateKeys() {
        try {
            KeyGenerator generator = KeyGenerator.getInstance(Const.AES_METHODS);
            generator.init(Const.AES_KEY_LEN);
            SecretKey secretKey = generator.generateKey();
            Cipher cipher = Cipher.getInstance(Const.AES_METHODS1);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            String[] keys = {new String(encode(secretKey.getEncoded())), new String(encode(cipher.getIV()))};
            return keys;
        }catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //encrypt method with string of message
    public String encrypt(String key, String iv, String message) {
        try {
            byte[] IV = decode(iv.getBytes());
            SecretKey secretKey = new SecretKeySpec(decode(key.getBytes()), Const.AES_METHODS);
            Cipher encryptionCipher = Cipher.getInstance(Const.AES_METHODS1);
            GCMParameterSpec spec = new GCMParameterSpec(Const.AES_T_LEN, IV);
            encryptionCipher.init(Cipher.ENCRYPT_MODE, secretKey, spec);
            byte[] encryptedBytes = encryptionCipher.doFinal(message.getBytes());
            return new String(encode(encryptedBytes));
        }catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //decrypt method with string of message
    public String decrypt(String key, String iv, String encryptedMessage) {
        try {
            byte[] IV = decode(iv.getBytes());
            SecretKey secretKey = new SecretKeySpec(decode(key.getBytes()), Const.AES_METHODS);
            byte[] message = decode(encryptedMessage.getBytes());
            Cipher decryptionCipher = Cipher.getInstance(Const.AES_METHODS1);
            GCMParameterSpec spec = new GCMParameterSpec(Const.AES_T_LEN, IV);
            decryptionCipher.init(Cipher.DECRYPT_MODE, secretKey, spec);
            return new String(decryptionCipher.doFinal(message));
        }catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private byte[] encode(byte[] data){
        return Base64.getEncoder().encode(data);
    }

    private byte[] decode(byte[] data) {
        return Base64.getDecoder().decode(data);
    }
}
