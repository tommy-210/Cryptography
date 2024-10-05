import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class encryptionASE {
    private SecretKey key;
    private byte[] IV;
    private int T_LEN = 128;

    encryptionASE() {

    }

    public void init(int KEY_SIZE) throws Exception{
        KeyGenerator generator = KeyGenerator.getInstance("AES");
        generator.init(KEY_SIZE);
        this.key = generator.generateKey();
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        this.IV = cipher.getIV();
    }

    public void initFromStrings(String secretKey, String IV){
        this.key = new SecretKeySpec(decode(secretKey), "AES");
        this.IV = decode(IV);
    }

    public void deleteKey() {
        this.key = null;
        this.IV = null;
    }

    //encrypt method with string of message
    public String encrypt(String message, int KEY_SIZE) throws Exception{
        byte[] messageInByte = message.getBytes();
        Cipher encryptionCipher = Cipher.getInstance("AES/GCM/NoPadding");
        GCMParameterSpec spec = new GCMParameterSpec(T_LEN, IV);
        encryptionCipher.init(Cipher.ENCRYPT_MODE, key, spec);
        byte[] encryptedBytes = encryptionCipher.doFinal(messageInByte);
        return encode(encryptedBytes);
    }

    //decrypt method with string of message
    public String decrypt(String encryptedMessage, int KEY_SIZE) throws Exception{
        byte[] messageInByte = decode(encryptedMessage);
        Cipher decryptionCipher = Cipher.getInstance("AES/GCM/NoPadding");
        GCMParameterSpec spec = new GCMParameterSpec(T_LEN, IV);
        decryptionCipher.init(Cipher.DECRYPT_MODE, key, spec);
        byte[] decryptedBytes = decryptionCipher.doFinal(messageInByte);
        return new String(decryptedBytes);
    }

    private String encode(byte[] data){
        return Base64.getEncoder().encodeToString(data);
    }

    private byte[] decode(String data) {
        return Base64.getDecoder().decode(data.getBytes());
    }

    public void exportKeys() throws IOException{
        String keyAndIV = new String(encode(key.getEncoded()) + "\n" + encode(IV));
        File file = new File(".\\output\\Secret Key - IV.txt");
        file.createNewFile();
        try(FileOutputStream fileOutputStream = new FileOutputStream(file)) {
            fileOutputStream.write(keyAndIV.getBytes());
            fileOutputStream.flush();
            file.setReadOnly();
            fileOutputStream.close();
        }
    }
}
