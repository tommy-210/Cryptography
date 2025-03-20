package Methods;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import Constants.Const;
import GUI.EncryptGUI;

public class Controller {
    private EncryptGUI encryptGui;
    private EncryptionASE encryptionASE = new EncryptionASE();
    private EncryptionRSA encryptionRSA = new EncryptionRSA();
    private ImageEncryption imageEncryption = new ImageEncryption();
    
    public Controller(EncryptGUI encryptGui) {
        this.encryptGui = encryptGui;
    }

    public void confirm(boolean IS_ENCRYPTION, int methods, String key1, String key2, String message, boolean exportKey) {
        //if input isn't valide return
        if(!checkConfirm(methods, key1, key2, message)) return;
        //choose encryptio or decryption
        if(IS_ENCRYPTION) {
            encryption(methods, key1, key2, message);
        }else {
            decryption(methods, key1, key2, message);
        }
        //export key
        if(exportKey) {
            exportKeys(methods, key1, key2);
        }
    }

    private void encryption(int methods, String key1, String key2, String message) {
        switch (methods) {
            case 0: //AES
                String msgAES = encryptionASE.encrypt(key1, key2, message);
                updateTextArea(msgAES);
                break;
            case 1: //RSA
                String msgRSA = encryptionRSA.encryption(key2, message);
                updateTextArea(msgRSA);
                break;
            case 2: //IMAGE
                BufferedImage image = imageEncryption.loadImage(message);
                String encryptImage = imageEncryption.encryptImage(image, key1);
                updateTextArea(encryptImage);
                createFile("Encrypt Image (" + (int)(Math.random() * 9999) + ").txt", encryptImage);
                break;
        }
    }

    private void decryption(int methods, String key1, String key2, String encryptMessage) {
        switch (methods) {
            case 0: //AES
                String msgAES = encryptionASE.decrypt(key1, key2, encryptMessage);
                updateTextArea(msgAES);
                break;
            case 1: //RSA
                String msgRSA = encryptionRSA.decryption(key1, encryptMessage);
                updateTextArea(msgRSA);
                break;
            case 2: //IMAGE
                BufferedImage image = imageEncryption.decryptImage(encryptMessage, key1);
                imageEncryption.exportImage(image, "Decrypt Image (" + (int)(Math.random() * 9999) + ").png");
                updateTextArea("Decryption sucefully!");
                break;
        }
    }
    
    private void updateTextArea(String message) {
        encryptGui.getTextArea().setText(message);
    }

    private boolean checkConfirm(int methods, String key1, String key2, String message) {
        //check all camp
        //methods must be select
        if(methods == -1) return false;
        //key1 must be insert
        if(key1 == null) return false;
        //message must be insert
        if(message == null) return false;
        //check for image method
        if(key2 == null && methods != 2) return false;
        return true;
    }

    private void exportKeys(int methods, String key1, String key2) {
        int id = (int)(Math.random() * 9999);
        String keys = key1 + "\n" + key2;
        switch (methods) {
            case 0: //AES
                createFile("Secret Key - IV (" + id + ").txt", keys);
                break;
            case 1: //RSA
                createFile("Private - Public Keys (" + id + ").txt", keys);
                break;
            case 2: //Image
                createFile("Secret Key - Img (" + id + ").txt", key1);
                break;
        }
    }

    private void createFile(String path, String msg) {
        try {
            File file = new File(Const.FILE_CHOOSER_DIRECTORY_PATH + path);
            if(!file.createNewFile()) return;
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(msg.getBytes());
            fileOutputStream.flush();
            file.setReadOnly();
            fileOutputStream.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void generateKey(int method) {
        if(method == -1) return;
        switch (method) {
            case 0: //AES
                String[] aes = encryptionASE.generateKeys();
                encryptGui.getFirstKeyField().setText(aes[0]);
                encryptGui.getSecondKeyField().setText(aes[1]);
                break;
            case 1: //RSA
                String[] rsa = encryptionRSA.generateKeys();
                encryptGui.getFirstKeyField().setText(rsa[0]);
                encryptGui.getSecondKeyField().setText(rsa[1]);
                break;
            case 2: //image
                String key = imageEncryption.generateKeyString();
                encryptGui.getFirstKeyField().setText(key);
                break;
        }
    }

    public void openTextFile() {
        //get file path
        String path = fileChooser();
        if(path == null) return;
        //convert file to message
        String message = fileToMessage(path);
        if(message == null) return;
        //update gui
        encryptGui.getTextArea().setText(message);
        System.out.println("Message: " + message);
    }

    private String fileToMessage(String path) {
        try {
            FileInputStream fis = new FileInputStream(new File(path));
            String message = new String(fis.readAllBytes());
            fis.close();
            return message;
        }catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public void openKeyFile() {
        //open dialog for choose file
        String path = fileChooser();
        if(path == null) return;
        //convert keys file to string array contains keys 
        String[] keys = keyFileToText(path);
        if(keys == null) return;
        //update gui
        encryptGui.getFirstKeyField().setText(keys[0]);
        encryptGui.getSecondKeyField().setText(keys[1]);
        encryptGui.setInput(false, false);
        System.out.println("keys: " + keys[0] + "\n" + keys[1]);
    }

    private String[] keyFileToText(String path) {
        String[] result = new String[2];
        try {
            BufferedReader br = new BufferedReader(new FileReader(new File(path)));
            for(int i = 0; i < result.length; i++) {
                result[i] = br.readLine();
            }
            br.close();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private String fileChooser() {
        JFileChooser fileChooser = new JFileChooser(Const.FILE_CHOOSER_DIRECTORY_PATH);
        fileChooser.setFileFilter(new FileNameExtensionFilter("TXT", "txt"));
        int response = fileChooser.showOpenDialog(encryptGui);
        File selectFile = fileChooser.getSelectedFile();
        //check if file selected is correct
        if(response == JFileChooser.APPROVE_OPTION && selectFile != null) {
            return selectFile.getAbsolutePath();
        }else return null;
    }
}
