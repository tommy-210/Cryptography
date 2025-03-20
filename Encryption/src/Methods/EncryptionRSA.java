package Methods;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;
import javax.crypto.Cipher;
import Constants.Const;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.security.KeyFactory;

public class EncryptionRSA {

    //endode
    private String encode(byte[] data) {
        return new String(Base64.getEncoder().encode(data));
    }
    //decode
    private byte[] decode(String data) {
        return Base64.getDecoder().decode(data);
    }

    public String[] generateKeys() {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(Const.RSA_METHODS);
            keyPairGenerator.initialize(Const.RSA_KEY_LEN);
            KeyPair KeyPair = keyPairGenerator.generateKeyPair();
            String[] keys = {encode(KeyPair.getPrivate().getEncoded()), encode(KeyPair.getPublic().getEncoded())};
            return keys;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    //init Public key from string
    private PublicKey initPublicFromStrings(String PUBLIC_KEY_STRING){
        try{
            X509EncodedKeySpec keySpecPublic = new X509EncodedKeySpec(decode(PUBLIC_KEY_STRING));
            KeyFactory keyFactory = KeyFactory.getInstance(Const.RSA_METHODS);
            return keyFactory.generatePublic(keySpecPublic);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //init Private key from string
    private PrivateKey initPrivateFromStrings(String PRIVATE_KEY_STRING){
        try{
            PKCS8EncodedKeySpec keySpecPrivate = new PKCS8EncodedKeySpec(decode(PRIVATE_KEY_STRING));
            KeyFactory keyFactory = KeyFactory.getInstance(Const.RSA_METHODS);
            return keyFactory.generatePrivate(keySpecPrivate);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //encryption method with string
    public String encryption(String PUBLIC_KEY_STRING, String message) {
        try {
            PublicKey publicKey = initPublicFromStrings(PUBLIC_KEY_STRING);
            Cipher cipher = Cipher.getInstance(Const.RSA_METHODS1);
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] encrypted = cipher.doFinal(message.getBytes());
            return encode(encrypted);
        }catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //decryption method with string
    public String decryption(String PRIVATE_KEY_STRING, String encryptedMessage) {
        try {
            PrivateKey privateKey = initPrivateFromStrings(PRIVATE_KEY_STRING);
            Cipher cipher = Cipher.getInstance(Const.RSA_METHODS1);
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] decryptMessage = cipher.doFinal(decode(encryptedMessage));
            return new String(decryptMessage);
        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
