import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;

public class cryptographyDialog extends JFrame implements ActionListener{

    boolean IS_ENCRYPTION_DIALOG, isKeyGenereted = false;
    String[] methodsList = {"Msg to msg", "Msg to file", "File to file"};
    Color BG_COLOR = new Color(0x181818);
    Color LABEL_CL = new Color(220, 220, 220);
    Color DISABLE_CL = new Color(160, 180, 160);
    Color BORDER_CL = new Color(240, 240, 255);
    Font LABEL_FONT = new Font("Dialog", Font.PLAIN, 14);
    JButton exitButton, fileChooserBtn, confirmBtn, generateKeyBtn, deleteKeyBtn, textOpenFileBtn, textFilePathBtn;
    Dimension APP_SIZE = new Dimension(600, 500);
    JRadioButton AESRadioBtn, RSARadioBtn;
    JTextField IVTextField, keyTextField;
    static JTextArea messagTextArea;
    JCheckBox printKeyCheckBox;
    @SuppressWarnings("rawtypes")
    JComboBox comboBox;

    //import file
    gui gui;
    encryptManager encryptManager = new encryptManager();

    cryptographyDialog(gui gui, boolean IS_ENCRYPTION_DIALOG) {
        super(IS_ENCRYPTION_DIALOG ? "Encryption" : "Decryption");
        this.getContentPane().setBackground(BG_COLOR);
        this.IS_ENCRYPTION_DIALOG = IS_ENCRYPTION_DIALOG;
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLayout(null);
        this.setSize(APP_SIZE);
        this.setLocationRelativeTo(gui);
        addComponentGui();
        this.setVisible(true);
        this.gui = gui;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == exitButton) {
            //close this panel
            gui.setVisible(true);
            this.dispose();
        }else if(e.getSource() == RSARadioBtn) {
            //disable iv input
            IVTextField.setEnabled(false);
            IVTextField.setBorder(BorderFactory.createLineBorder(DISABLE_CL, 2));
        }else if(e.getSource() == AESRadioBtn) {
            //enable IV input
            if(!isKeyGenereted && !keyTextField.isEnabled()) {
                IVTextField.setEnabled(true);
                IVTextField.setBorder(BorderFactory.createLineBorder(BORDER_CL, 2));
            }
        }else if(e.getSource() == fileChooserBtn) {
            //serch a file
            openKeyFile();
        }else if(e.getSource() == generateKeyBtn) {
            //generate key automaticaly
            generateKey();
        }else if(e.getSource() == deleteKeyBtn) {
            //delete key
            deleteKey();
        }else if(e.getSource() == textFilePathBtn) {
            //get absolute file path
            getAbsoluteFilePath();
        }else if(e.getSource() == textOpenFileBtn) {
            //file to text
            fileToTextMessage();
        }else if(e.getSource() == confirmBtn) {
            checkIfFormIsCorrect();
        }
    }

    //generate key automaticaly
    public void generateKey() {
        encryptManager.generateKey();
        isKeyGenereted = true;
        generateKeyBtn.setEnabled(false);
        generateKeyBtn.setVisible(false);
        deleteKeyBtn.setEnabled(true);
        deleteKeyBtn.setVisible(true);
        disebleKeyForm();
    }

    //delete key
    public void deleteKey() {
        encryptManager.deleteKey();
        isKeyGenereted = false;
        deleteKeyBtn.setEnabled(false);
        deleteKeyBtn.setVisible(false);
        generateKeyBtn.setEnabled(true);
        generateKeyBtn.setVisible(true);
        enableKeyForm();
    }

    //print in form the keys
    public void openKeyFile() {
        String privateKey = "Private Key:", publicKey = "Public Key:";
        //get file path of key
        String filePath = serchFile();
        if(filePath != null) {
            try {
                //get key from file
                String[] key = encryptManager.convertFileToKey(filePath);
                if(key[0] != null) {
                    disebleKeyForm();
                    //check if RSA keys
                    if(key[0].contains(privateKey) || key[1].contains(publicKey)) {
                        //remove rsa key argument
                        key = encryptManager.replaceArgumentKey();
                        if(IS_ENCRYPTION_DIALOG) {
                            //print public key
                            keyTextField.setText(key[1]);
                        }else {
                            //print private key
                            keyTextField.setText(key[0]);
                        }
                    }else {
                        //print key on gui
                        keyTextField.setText(key[0]);
                        IVTextField.setText(key[1]);
                    }
                }else JOptionPane.showMessageDialog(cryptographyDialog.this, "Error", "ERROR!", JOptionPane.ERROR_MESSAGE);
            } catch (Exception e) {e.printStackTrace();}
        }
    }

    //conversion file to text
    public void fileToTextMessage() {
        //get file path
        String filePath = serchFile();
        if(filePath != null) {
            try {
                //convert file to string
                String message = encryptManager.convertFileToString(filePath);
                //update gui
                updateTextArea(message);
            } catch (Exception e) {e.printStackTrace();}
        }
    }

    //selection any file
    public String serchFile() {
        //chose file
        JFileChooser fileChooser = new JFileChooser(".\\");
        fileChooser.setFileFilter(new FileNameExtensionFilter("TXT", "txt"));
        int response = fileChooser.showOpenDialog(cryptographyDialog.this);
        File selectFile = fileChooser.getSelectedFile();
        //check if file selected is correct
        if(response == JFileChooser.APPROVE_OPTION && selectFile != null) {
            return selectFile.getPath();
        }else return null;
    }

    //print file path in text area
    public void getAbsoluteFilePath() {
        //get file path
        String absoluteFilePath = serchFile();
        if(absoluteFilePath != null) {
            //update with file path the text area
            updateTextArea(absoluteFilePath);
        }
    }

    //disebla form
    public void disebleKeyForm() {
        keyTextField.setEnabled(false);
        keyTextField.setBorder(BorderFactory.createLineBorder(DISABLE_CL, 2));
        IVTextField.setEnabled(false);
        IVTextField.setBorder(BorderFactory.createLineBorder(DISABLE_CL, 2));
    }

    //enable form
    public void enableKeyForm() {
        keyTextField.setEnabled(true);
        keyTextField.setBorder(BorderFactory.createLineBorder(BORDER_CL, 2));
        IVTextField.setEnabled(true);
        IVTextField.setBorder(BorderFactory.createLineBorder(BORDER_CL, 2));
    }

    //check if form is correct for encryption or decryption
    public void checkIfFormIsCorrect() {
        //check method
        if(RSARadioBtn.isSelected() || AESRadioBtn.isSelected()) {
            //get method
            boolean isRSAMethod = RSARadioBtn.isSelected();
            //check secret key
            if(isKeyGenereted || !keyTextField.getText().equals(IS_ENCRYPTION_DIALOG ? "Public Key" : "Private Key")) {
                //check IV
                if(isRSAMethod || isKeyGenereted || !IVTextField.getText().equals("IV (only if you chosen AES)")) {
                    //check message
                    if(!messagTextArea.getText().equals("Type your message for " + (IS_ENCRYPTION_DIALOG ? "Encryption" : "Decryption"))) {
                        //get secret key
                        String key = "Ciao";
                        if(!keyTextField.getText().equals(IS_ENCRYPTION_DIALOG ? "Public Key" : "Private Key")) {
                            key = keyTextField.getText();
                        }
                        //check IV
                        String IV = "Ciao";
                        if(AESRadioBtn.isSelected() && !IVTextField.getText().equals("IV (only if you chosen AES)")) {
                            IV = IVTextField.getText();
                        }
                        //get methods
                        int method = comboBox.getSelectedIndex();
                        //check message
                        String message = messagTextArea.getText();
                        //check if print keys
                        boolean isPrintKeys = printKeyCheckBox.isSelected();
                        try {
                            encryptManager.cryptography(IS_ENCRYPTION_DIALOG, isRSAMethod, method, key, IV, message, isPrintKeys);
                        } catch (Exception e) {e.printStackTrace();}
                    }else JOptionPane.showMessageDialog(cryptographyDialog.this, "You're not print a message", "ERROR!", JOptionPane.ERROR_MESSAGE);
                }else JOptionPane.showMessageDialog(cryptographyDialog.this, "You're not print a IV", "ERRORE!", JOptionPane.ERROR_MESSAGE);
            }else JOptionPane.showMessageDialog(cryptographyDialog.this, "You're not print a key", "ERROR!", JOptionPane.ERROR_MESSAGE);
        }else JOptionPane.showMessageDialog(cryptographyDialog.this, "You're not select a method", "ERROR!", JOptionPane.ERROR_MESSAGE);
    }

    //update text area
    public static void updateTextArea(String message) {
        messagTextArea.setText(message);
        messagTextArea.updateUI();
    }

    //add all components
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void addComponentGui() {
        int leftAlliniamet = 30;
        
        //set title
        JLabel title = new JLabel(IS_ENCRYPTION_DIALOG ? "Encryption" : "Decryption");
        title.setBounds(leftAlliniamet, 10, (int) APP_SIZE.getWidth(), 50);
        title.setFont(new Font("Dialog", Font.BOLD, 32));
        title.setForeground(new Color(0, 255, 0));
        add(title);
        
        //set radio button for method of cryptography
        ButtonGroup methodGroup = new ButtonGroup();
        AESRadioBtn = new JRadioButton("AES");
        AESRadioBtn.setBounds(leftAlliniamet, 70, 80, 30);
        AESRadioBtn.setFocusable(false);
        AESRadioBtn.addActionListener(this);
        AESRadioBtn.setForeground(LABEL_CL);
        AESRadioBtn.setBackground(BG_COLOR);
        AESRadioBtn.setFont(LABEL_FONT);
        methodGroup.add(AESRadioBtn);
        add(AESRadioBtn);
        
        //set radio button for method of cryptography
        RSARadioBtn = new JRadioButton("RSA");
        RSARadioBtn.setBounds(leftAlliniamet, 100, 80, 30);
        RSARadioBtn.setFocusable(false);
        RSARadioBtn.addActionListener(this);
        RSARadioBtn.setForeground(LABEL_CL);
        RSARadioBtn.setBackground(BG_COLOR);
        RSARadioBtn.setFont(LABEL_FONT);
        methodGroup.add(RSARadioBtn);
        add(RSARadioBtn);

        //set text selection methods
        JLabel comboBoxLabel = new JLabel("Select methods:");
        comboBoxLabel.setBounds(280, 90, 120, 35);
        comboBoxLabel.setFont(LABEL_FONT);
        comboBoxLabel.setForeground(LABEL_CL);
        add(comboBoxLabel);

        //set combo box
        comboBox = new JComboBox(methodsList);
        comboBox.setBounds(390, 90, 150, 35);
        comboBox.setFont(LABEL_FONT);
        comboBox.setBackground(BG_COLOR);
        comboBox.setFocusable(false);
        comboBox.setForeground(LABEL_CL);
        add(comboBox);

        //set private key
        keyTextField = new JTextField(IS_ENCRYPTION_DIALOG ? "Public Key" : "Private Key");
        keyTextField.setBorder(BorderFactory.createLineBorder(BORDER_CL, 2));
        keyTextField.setBounds(leftAlliniamet, 150, 400, 35);
        keyTextField.setAutoscrolls(true);
        keyTextField.setForeground(LABEL_CL);
        keyTextField.setBackground(BG_COLOR);
        keyTextField.setFont(LABEL_FONT);
        add(keyTextField);
        
        //set file chooser for keys
        fileChooserBtn = new JButton("Select File");
        fileChooserBtn.setBounds(440, 150, 100, 35);
        fileChooserBtn.setFocusable(false);
        fileChooserBtn.addActionListener(this);
        fileChooserBtn.setBackground(BG_COLOR);
        fileChooserBtn.setForeground(LABEL_CL);
        fileChooserBtn.setFont(LABEL_FONT);
        add(fileChooserBtn);
        
        //set IV
        IVTextField = new JTextField("IV (only if you chosen AES)");
        IVTextField.setBorder(BorderFactory.createLineBorder(BORDER_CL, 2));
        IVTextField.setBounds(leftAlliniamet, 195, 300, 35);
        IVTextField.setAutoscrolls(true);
        IVTextField.setForeground(LABEL_CL);
        IVTextField.setBackground(BG_COLOR);
        IVTextField.setFont(LABEL_FONT);
        add(IVTextField);

        //generate key automaticaly
        generateKeyBtn = new JButton("Generate KEY");
        generateKeyBtn.setBounds(400, 195, 140, 35);
        generateKeyBtn.setFocusable(false);
        generateKeyBtn.addActionListener(this);
        generateKeyBtn.setBackground(BG_COLOR);
        generateKeyBtn.setForeground(LABEL_CL);
        generateKeyBtn.setFont(LABEL_FONT);
        add(generateKeyBtn);
        
        //delete of generate key
        deleteKeyBtn = new JButton("Delete KEY");
        deleteKeyBtn.setBounds(400, 195, 140, 35);
        deleteKeyBtn.setFocusable(false);
        deleteKeyBtn.addActionListener(this);
        deleteKeyBtn.setBackground(BG_COLOR);
        deleteKeyBtn.setForeground(LABEL_CL);
        deleteKeyBtn.setVisible(false);
        deleteKeyBtn.setEnabled(false);
        deleteKeyBtn.setFont(LABEL_FONT);
        add(deleteKeyBtn);

        //set text area for message
        messagTextArea = new JTextArea("Type your message for " + (IS_ENCRYPTION_DIALOG ? "Encryption" : "Decryption"));
        messagTextArea.setBorder(BorderFactory.createLineBorder(BORDER_CL, 2));
        messagTextArea.setBounds(leftAlliniamet, 260, 400, 100);
        messagTextArea.setAutoscrolls(true);
        messagTextArea.setWrapStyleWord(false);
        messagTextArea.setBackground(BG_COLOR);
        messagTextArea.setForeground(LABEL_CL);
        messagTextArea.setLineWrap(true);
        messagTextArea.setFont(LABEL_FONT);
        add(messagTextArea);

        //set btn for open message file
        textOpenFileBtn = new JButton("Open file");
        textOpenFileBtn.setBounds(440, 260, 100, 35);
        textOpenFileBtn.setFocusable(false);
        textOpenFileBtn.addActionListener(this);
        textOpenFileBtn.setBackground(BG_COLOR);
        textOpenFileBtn.setForeground(LABEL_CL);
        textOpenFileBtn.setFont(LABEL_FONT);
        add(textOpenFileBtn);

        //set absolute file path
        textFilePathBtn = new JButton("File path");
        textFilePathBtn.setBounds(440, 325, 100, 35);
        textFilePathBtn.setFocusable(false);
        textFilePathBtn.addActionListener(this);
        textFilePathBtn.setBackground(BG_COLOR);
        textFilePathBtn.setForeground(LABEL_CL);
        textFilePathBtn.setFont(LABEL_FONT);
        add(textFilePathBtn);

        //set print  key on file
        printKeyCheckBox = new JCheckBox("Print Keys on File");
        printKeyCheckBox.setBounds(leftAlliniamet, 360, 200, 50);
        printKeyCheckBox.setFocusable(false);
        printKeyCheckBox.setBackground(BG_COLOR);
        printKeyCheckBox.setForeground(LABEL_CL);
        printKeyCheckBox.setFont(LABEL_FONT);
        add(printKeyCheckBox);

        //confirm
        confirmBtn = new JButton(IS_ENCRYPTION_DIALOG ? "Encryption" : "Decryption");
        confirmBtn.setBounds(350, (int) APP_SIZE.getHeight() - 100, 120, 40);
        confirmBtn.setFont(new Font("sans serif", Font.PLAIN, 18));
        confirmBtn.setForeground(new Color(0, 200, 0));
        confirmBtn.setBackground(new Color(0x121212));
        confirmBtn.setFocusable(false);
        confirmBtn.addActionListener(this);
        add(confirmBtn);
        
        //set exit button
        exitButton = new JButton("EXIT");
        exitButton.setBounds((int) APP_SIZE.getWidth() - 110, (int) APP_SIZE.getHeight() - 100, 80, 40);
        exitButton.setFont(new Font("sans serif", Font.PLAIN, 18));
        exitButton.setForeground(new Color(0, 200, 0));
        exitButton.setBackground(new Color(0x121212));
        exitButton.setFocusable(false);
        exitButton.addActionListener(this);
        add(exitButton);
    }
}
