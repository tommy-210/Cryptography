package Methods;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.imageio.ImageIO;

import Constants.Const;

public class ImageEncryption {

    public String generateKeyString() {
        byte[] key = Base64.getEncoder().encode(generateKey().getEncoded());
        return new String(key);
    }

    private SecretKey generateKey() {
        try {
            //generate key for encryption
            KeyGenerator keyGen = KeyGenerator.getInstance("AES");
            keyGen.init(Const.AES_T_LEN);
            return keyGen.generateKey();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    public BufferedImage loadImage(String path) {
        try {
            //load image from file path
            return ImageIO.read(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private byte[] convertImageToBytes(BufferedImage image) {
        try {
            //convert image into array of bytes
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, "png", baos);
            return baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private BufferedImage convertBytesToImage(byte[] bytes) {
        try {
            ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
            return ImageIO.read(bais);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private byte[] decode(byte[] data){
        return Base64.getDecoder().decode(data);
    }

    public String encryptImage(BufferedImage image, String key) {
        try {
            SecretKey secretKey = new SecretKeySpec(decode(key.getBytes()), "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            //convert image to array of bytes and encrypt it
            byte[] imageBytes = convertImageToBytes(image);
            byte[] encryptImageBytes = cipher.doFinal(imageBytes);
            //return a string contains encrypt image
            return Base64.getEncoder().encodeToString(encryptImageBytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public BufferedImage decryptImage(String base64EncryptImage, String key) {
        try {
            SecretKey secretKey = new SecretKeySpec(decode(key.getBytes()), "AES");
            //inizialized cipher
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
    
            //get string of image encrypt and transform to decrypt
            byte[] encryptBytes = Base64.getDecoder().decode(base64EncryptImage);
            byte[] decryptBytes = cipher.doFinal(encryptBytes);
        
            //return decrypt image
            return convertBytesToImage(decryptBytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void exportImage(BufferedImage image, String path) {
        try {
            //create image on pc
            ImageIO.write(image, "png", new File("src\\example\\" + path));
        } catch (IOException e) {
            e.printStackTrace();
        }
    } 
}