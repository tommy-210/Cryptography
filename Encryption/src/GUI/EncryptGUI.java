package GUI;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JScrollPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import Constants.Const;
import Methods.Controller;

public class EncryptGUI extends JFrame {
    private boolean IS_ENCRYPTION;
    private JTextField firstKeyField, secondKeyField;
    private JTextArea textArea;
    private JButton generateKey, deleteKey;
    private JRadioButton[] radioBtns;
    private Controller controller;
    
    public EncryptGUI(boolean IS_ENCRYPTION) {
        super((IS_ENCRYPTION ? "Encryption" : "Decryption") + " App");
        this.IS_ENCRYPTION = IS_ENCRYPTION;
        //inizialization controller
        controller = new Controller(this);
        //set preffered size for panel
        this.setSize(Const.DIALOG_SIZE);
        //set dialog not resizable
        this.setResizable(false);
        //set close operation on exit
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        //set background color to black
        this.getContentPane().setBackground(Const.BG_COLOR);
        //disable layout for displacement components anywhere
        this.setLayout(null);
        //set location to spawn frame
        this.setLocationRelativeTo(null);
        //add components in main gui
        addComponentsGui();
        //set visible the panel
        this.setVisible(true);
    }

    private void addComponentsGui() {
        //set propriety of title
        JLabel title = new JLabel(IS_ENCRYPTION ? "Encryption" : "Decryption");
        title.setForeground(Const.CL_GREEN);
        title.setFont(Const.TITLE_FONT);
        title.setBounds(Const.LEFT_ALLIGNAMENT, 20, (int)Const.DIALOG_SIZE.getWidth(), 50);
        title.setHorizontalAlignment(JLabel.LEFT);
        add(title);

        //add radio button
        addRadioButton();

        //add text input
        addTextInput();

        //add button for choose key file, generate key and trascribe message in text area
        addFileChooser();

        //set checkbox for export keys
        JCheckBox exportKey = new JCheckBox("Export Key");
        exportKey.setFont(Const.NORMAL_FONT);
        exportKey.setForeground(Const.CL_WHITE);
        exportKey.setBackground(Const.BG_COLOR);
        exportKey.setBounds(Const.LEFT_ALLIGNAMENT, 435, 120, 30);
        exportKey.setHorizontalAlignment(SwingConstants.LEFT);
        exportKey.setFocusable(false);
        add(exportKey);

        //button for confirm action
        JButton confirm = new JButton(IS_ENCRYPTION ? "ENCRYPT" : "DECRYPT");
        confirm.setFont(Const.PRIMARY_BUTTON_FONT);
        confirm.setForeground(Const.CL_GREEN);
        confirm.setBackground(Const.BG_COLOR);
        confirm.setFocusable(false);
        confirm.setBounds((int)Const.DIALOG_SIZE.getWidth()-230, (int)Const.DIALOG_SIZE.getHeight()-110, 180, 50);
        confirm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("CONFIRM: " + (IS_ENCRYPTION ? "Encrypt" : "Decrypt"));
                controller.confirm(IS_ENCRYPTION, getRadioBtnSelect(), firstKeyField.getText(), secondKeyField.getText(), textArea.getText(), exportKey.isSelected());
            }
        });
        add(confirm);

        //button for exit and return at main gui
        JButton exit = new JButton("EXIT");
        exit.setForeground(Color.RED);
        exit.setBackground(Const.BG_COLOR);
        exit.setFont(Const.PRIMARY_BUTTON_FONT);
        exit.setFocusable(false);
        exit.setBounds(Const.LEFT_ALLIGNAMENT, (int)Const.DIALOG_SIZE.getHeight()-110, 100, 50);
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("exit from dialog");
                new MainGUI();
                dispose();
            }
        });
        add(exit);
    }

    private void addFileChooser() {
        //create button for generate key
        generateKey = new JButton("Generate Key");
        generateKey.setFont(Const.BUTTON_FONT);
        generateKey.setForeground(Const.CL_WHITE);
        generateKey.setBackground(Const.BG_COLOR);
        generateKey.setFocusable(false);
        generateKey.setBorder(Const.BORDER);
        generateKey.setBounds(430, 220, 120, 30);
        generateKey.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //generate key and switch it with delete button
                System.out.println("generate key");
                controller.generateKey(getRadioBtnSelect());
                genDelKeySwitch(false, true);
            }
        });
        add(generateKey);
        
        //create button for delete key
        deleteKey = new JButton("Delete Key");
        deleteKey.setFont(Const.BUTTON_FONT);
        deleteKey.setForeground(Const.CL_WHITE);
        deleteKey.setBackground(Const.BG_COLOR);
        deleteKey.setFocusable(false);
        deleteKey.setBorder(Const.BORDER);
        deleteKey.setVisible(false);
        deleteKey.setEnabled(false);
        deleteKey.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //delete key and switch it with generate button
                System.out.println("delete key");
                firstKeyField.setText(null);
                secondKeyField.setText(null);
                genDelKeySwitch(true, false);
            }
        });
        deleteKey.setBounds(420, 220, 130, 30);
        add(deleteKey);

        //file chooser for insert key
        JButton keyFileChooser = new JButton("Select Key");
        keyFileChooser.setFont(Const.BUTTON_FONT);
        keyFileChooser.setForeground(Const.CL_WHITE);
        keyFileChooser.setBackground(Const.BG_COLOR);
        keyFileChooser.setBounds(450, 170, 100, 30);
        keyFileChooser.setFocusable(false);
        keyFileChooser.setBorder(Const.BORDER);
        keyFileChooser.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("file chooser");
                controller.openKeyFile();
            }
        });
        add(keyFileChooser);

        //button for trascribe message in text area
        JButton messageFileChooser = new JButton("Get text");
        messageFileChooser.setFont(Const.BUTTON_FONT);
        messageFileChooser.setForeground(Const.CL_WHITE);
        messageFileChooser.setBackground(Const.BG_COLOR);
        messageFileChooser.setFocusable(false);
        messageFileChooser.setBorder(Const.BORDER);
        messageFileChooser.setBounds(470, 435, 80, 30);
        messageFileChooser.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("get message");
                controller.openTextFile();
            }
        });
        add(messageFileChooser);
    }

    private void addTextInput() {
        //create text field for insert key
        firstKeyField = new JTextField();
        firstKeyField.setFont(Const.NORMAL_FONT);
        firstKeyField.setBounds(Const.LEFT_ALLIGNAMENT, 170, 400, 30);
        firstKeyField.setBorder(Const.BORDER);
        firstKeyField.setForeground(Const.CL_WHITE);
        firstKeyField.setBackground(Const.BG_COLOR);
        add(firstKeyField);

        secondKeyField = new JTextField();
        secondKeyField.setFont(Const.NORMAL_FONT);
        secondKeyField.setBounds(Const.LEFT_ALLIGNAMENT, 220, 380, 30);
        secondKeyField.setBorder(Const.BORDER);
        secondKeyField.setForeground(Const.CL_WHITE);
        secondKeyField.setBackground(Const.BG_COLOR);
        add(secondKeyField);

        //add text area for write message
        textArea = new JTextArea("Write your message...");
        textArea.setLineWrap(true);
        textArea.setBorder(Const.BORDER);
        textArea.setFont(Const.NORMAL_FONT);
        textArea.setWrapStyleWord(true);
        textArea.setForeground(Const.CL_WHITE);
        textArea.setBackground(Const.BG_COLOR);
        textArea.setCaretColor(Const.CL_SELECT);
        textArea.setSelectedTextColor(Const.BG_COLOR);
        //scroll pane
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBounds(Const.LEFT_ALLIGNAMENT, 270, 520, 150);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        add(scrollPane);
    }

    private void addRadioButton() {
        //create radio button for choose methods
        // 1) => AES
        // 2) => RSA
        // 3) => Image Encryption
        int y = 80;
        ButtonGroup radioGroup = new ButtonGroup();
        radioBtns = new JRadioButton[3];
        for(int i = 0; i < radioBtns.length; i++) {
            radioBtns[i] = new JRadioButton(Const.RADIO_BTN_NAME[i]);
            radioBtns[i].setVerticalAlignment(JRadioButton.CENTER);
            radioBtns[i].setFont(Const.RADIO_BTN_FONT);
            radioBtns[i].setForeground(Const.CL_WHITE);
            radioBtns[i].setBackground(Const.BG_COLOR);
            radioBtns[i].setFocusable(false);
            radioBtns[i].setBounds(Const.LEFT_ALLIGNAMENT, y, 80, 20);
            radioGroup.add(radioBtns[i]);
            add(radioBtns[i]);
            y += 25;
        }
        radioBtns[0].addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //AES
                setInput(true, true);
                impostInputText("Secret Key", "IV", "Write your message...");
            }
        });
        radioBtns[1].addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //RSA
                setInput(true, true);
                impostInputText("Private Key", "Public Key", "Write your message...");
            }
        });
        radioBtns[2].addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //Image
                setInput(true, false);
                impostInputText("Secret Key", "", IS_ENCRYPTION ? "Write here the file path of image to encrypt" : "Write here a string of encrypt image");
            }
        });
    }

    private void genDelKeySwitch(boolean gen, boolean del) {
        generateKey.setVisible(gen);
        generateKey.setEnabled(gen);
        deleteKey.setVisible(del);
        deleteKey.setEnabled(del);
    }
    
    private void impostInputText(String first, String second, String msg) {
        firstKeyField.setText(first);
        secondKeyField.setText(second);
        textArea.setText(msg);
    }

    public void setInput(boolean first, boolean second) {
        firstKeyField.setFocusable(first);
        firstKeyField.setEditable(first);
        firstKeyField.setBorder(BorderFactory.createLineBorder(first ? Const.CL_WHITE : Const.CL_GRAY, 2));
        secondKeyField.setFocusable(second);
        secondKeyField.setEditable(second);
        secondKeyField.setBorder(BorderFactory.createLineBorder(second ? Const.CL_WHITE : Const.CL_GRAY, 2));
    }

    private int getRadioBtnSelect() {
        for(int i = 0; i < radioBtns.length; i++) {
            if(radioBtns[i].isSelected()) {
                return i;
            }
        }
        return -1;
    }
    
    public JTextArea getTextArea() {
        return textArea;
    }

    public JTextField getFirstKeyField() {
        return firstKeyField;
    }

    public JTextField getSecondKeyField() {
        return secondKeyField;
    }
}
