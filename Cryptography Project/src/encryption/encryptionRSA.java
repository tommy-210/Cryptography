import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;
import javax.crypto.Cipher;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.security.KeyFactory;

public class encryptionRSA {

    // int KEY_SIZE = 1024;
    private PublicKey PublicKey;
    private PrivateKey PrivateKey;

    encryptionRSA() {

    }

    //endode
    private static String encode(byte[] data) {
        return Base64.getEncoder().encodeToString(data);
    }
    //decode
    private static byte[] decode(String data) {
        return Base64.getDecoder().decode(data);
    }

    public void init(int KEY_SIZE) {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(KEY_SIZE);
            KeyPair KeyPair = keyPairGenerator.generateKeyPair();
            this.PublicKey = KeyPair.getPublic();
            this.PrivateKey = KeyPair.getPrivate();
        } catch (NoSuchAlgorithmException e) {e.printStackTrace();}
    }

    //init Public key from string
    public void initPublicFromStrings(String PUBLIC_KEY_STRING){
        try{
            X509EncodedKeySpec keySpecPublic = new X509EncodedKeySpec(decode(PUBLIC_KEY_STRING));
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            this.PublicKey = keyFactory.generatePublic(keySpecPublic);
        }catch (Exception ignored){}
    }

    //init Private key from string
    public void initPrivateFromStrings(String PRIVATE_KEY_STRING){
        try{
            PKCS8EncodedKeySpec keySpecPrivate = new PKCS8EncodedKeySpec(decode(PRIVATE_KEY_STRING));
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            this.PrivateKey = keyFactory.generatePrivate(keySpecPrivate);
        }catch (Exception e){e.printStackTrace();}
    }

    public void deleteKey() {
        this.PublicKey = null;
        this.PrivateKey = null;
    }

    //encryption method with string
    public String encryption(String message) throws Exception {
        byte[] messageToBytes = message.getBytes();
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, PublicKey);
        byte[] encryptedBytes = cipher.doFinal(messageToBytes);
        return encode(encryptedBytes);
    }

    //decryption method with string
    public String decryption(String encryptedMessage) throws Exception {
        byte[] encryptedBytes = decode(encryptedMessage);
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.DECRYPT_MODE, PrivateKey);
        byte[] decryptedMessage = cipher.doFinal(encryptedBytes);
        return new String(decryptedMessage);
    }

    public void exportKey() throws IOException{
        String privateAndPublicKey = new String("Private Key:" + encode(PrivateKey.getEncoded()) + "\nPublic Key:" + encode(PublicKey.getEncoded()));
        File file = new File(".\\output\\Private - Public Key.txt");
        file.createNewFile();
        try(FileOutputStream fileOutputStream = new FileOutputStream(file)) {
            fileOutputStream.write(privateAndPublicKey.getBytes());
            fileOutputStream.flush();
            fileOutputStream.close();
            file.setReadOnly();
        }
    }
}
