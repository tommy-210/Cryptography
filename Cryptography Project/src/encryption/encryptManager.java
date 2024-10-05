import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

public class encryptManager {

    encryptionASE encryptionASE = new encryptionASE();
    encryptionRSA encryptionRSA = new encryptionRSA();

    private String messageInString;
    private int RSA_KEY_SIZE = 1024;
    private int AES_KEY_SIZE = 128;
    String[] key;
    File fileToFileMethod;

    encryptManager() {

    }

    public void cryptography(boolean isEncryptio, boolean isRSAMethod, int indexMethod, String secretKey, String IV, String message, boolean printKeys) throws Exception {
        if(isEncryptio) {
            //encryption
            encryption(isRSAMethod, indexMethod, secretKey, IV, message, printKeys);
        }else {
            //decryption
            decryption(isRSAMethod, indexMethod, secretKey, IV, message, printKeys);
        }
        this.messageInString = null;
    }

    //encryption method
    public void encryption(boolean isRSAMethod, int indexMethod, String secretKey, String IV, String message, boolean printKeys) throws Exception {
        //which methods use
        if(isRSAMethod) {
            //check if key is just genereted
            if(!secretKey.equals("Ciao")) {
                encryptionRSA.initPublicFromStrings(secretKey);
            }
            checkMethodsRSA(true, indexMethod, message);
            if(printKeys) encryptionRSA.exportKey();
        }else {
            if(!secretKey.equals("Ciao") && !IV.equals("Ciao")) {
                encryptionASE.initFromStrings(secretKey, IV);
            }
            checkMethodsAES(true, indexMethod, message);
            if(printKeys) encryptionASE.exportKeys();
        }
        showMessageInDialog();
    }

    //decryption method
    public void decryption(boolean isRSAMethod, int indexMethod, String secretKey, String IV, String message, boolean printKeys) throws Exception{
        if(isRSAMethod) {
            if(!secretKey.equals("Ciao")) {
                encryptionRSA.initPrivateFromStrings(secretKey);
            }
            checkMethodsRSA(false, indexMethod, message);
            if(printKeys) encryptionRSA.exportKey();
        }else {
            if(!secretKey.equals("Ciao") && !IV.equals("Ciao")) {
                encryptionASE.initFromStrings(secretKey, IV);
            }
            checkMethodsAES(false, indexMethod, message);
            if(printKeys) encryptionASE.exportKeys();
        }
        if(indexMethod != 2) showMessageInDialog();
    }
    
    //check methods for RSA cryptography
    public void checkMethodsRSA(boolean isEncryption, int indexMethod, String message) throws Exception {
        String messageFile;
        switch (indexMethod) {
            case 0:
                // message to message
                if(isEncryption) this.messageInString = encryptionRSA.encryption(message);
                else this.messageInString = encryptionRSA.decryption(message);
                break;
            case 1:
                // message to file
                if(isEncryption) this.messageInString = encryptionRSA.encryption(message);
                else this.messageInString = encryptionRSA.decryption(message);
                createCryptographyFile(isEncryption, true);
                break;
            case 2:
                // file to file
                messageFile = convertFileToString(message);
                if(isEncryption) this.messageInString = encryptionRSA.encryption(messageFile);
                else this.messageInString = encryptionRSA.decryption(messageFile);
                createCryptographyFile(isEncryption, true);
                break;
            }
    }

    //check methods for AES cryptography
    public void checkMethodsAES(boolean isEncryption, int indexMethod, String message) throws Exception {
        String messageFile;
        switch (indexMethod) {
            case 0:
                // message to message
                if(isEncryption) this.messageInString = encryptionASE.encrypt(message, AES_KEY_SIZE);
                else this.messageInString = encryptionASE.decrypt(message, AES_KEY_SIZE);
                break;
            case 1:
                // message to file
                if(isEncryption) this.messageInString = encryptionASE.encrypt(message, AES_KEY_SIZE);
                else this.messageInString = encryptionASE.decrypt(message, AES_KEY_SIZE);
                createCryptographyFile(isEncryption, false);
                break;
            case 2:
                // file to file
                messageFile = convertFileToString(message);
                if(isEncryption) this.messageInString = encryptionASE.encrypt(messageFile, AES_KEY_SIZE);
                else this.messageInString = encryptionASE.decrypt(messageFile, AES_KEY_SIZE);
                createCryptographyFile(isEncryption, false);
                break;
            }
    }
    
    //update text area
    public void showMessageInDialog() {
        String message = this.messageInString;
        cryptographyDialog.updateTextArea(message);
    }

    //create encrypt / decrypt file
    public void createCryptographyFile(boolean isEncryptFile, boolean isRSA) throws IOException {
        File file;
        //create file
        if(fileToFileMethod != null) {
            if(!isEncryptFile) {
                String fileName = fileToFileMethod.getName().replaceAll(".encrypt", "");
                file = new File(fileName);
            }else file = new File(".\\output\\" + fileToFileMethod.getName() + ".encrypt");
        }else file = new File(".\\output\\"+ (isEncryptFile ? "Encrypt" : "Decrypt") + (isRSA ? " RSA" : " AES") + " Message.txt");
        file.createNewFile();
        //write in file
        try(FileOutputStream fileOutputStream = new FileOutputStream(file)) {
            fileOutputStream.write(messageInString.getBytes());
            fileOutputStream.flush();
            fileOutputStream.close();
            file.setReadOnly();
        }
    }

    //convert file in array of byte
    public String convertFileToString(String filePath) throws Exception{
        fileToFileMethod = new File(filePath);
        try(FileInputStream fileInputStream = new FileInputStream(fileToFileMethod)) {
            byte[] message = new byte[(int) fileToFileMethod.length()];
            fileInputStream.read(message);
            return new String(message);
        }
    }

    //convert file to 2 key
    public String[] convertFileToKey(String filePath) throws Exception{
        BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
        String[] content = new String[2];
        for(int i = 0; i < 2; i++) {
            content[i] = bufferedReader.readLine();
        }
        bufferedReader.close();
        this.key = content;
        return content;
    }

    //replace rsa argument
    public String[] replaceArgumentKey() {
        String[] replacement = {"Private Key:", "Public Key:"};
        this.key[0] = this.key[0].replaceAll(replacement[0], "");
        this.key[1] = this.key[1].replaceAll(replacement[1], "");
        return this.key;
    }

    //generate key
    public void generateKey() {
        try {
            //generate public and private key for RSA encryption method
            encryptionRSA.init(RSA_KEY_SIZE);
            //generate secret kry and IV for AES encryption method
            encryptionASE.init(AES_KEY_SIZE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //delete key
    public void deleteKey() {
        encryptionASE.deleteKey();
        encryptionRSA.deleteKey();
    }
}
