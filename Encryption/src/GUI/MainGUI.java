package GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import Constants.Const;

public class MainGUI extends JFrame {
    
    public MainGUI() {
        super("Cryptography App");

        //set preffered size for panel
        this.setSize(Const.APP_SIZE);
        
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
        addComponents();

        //set visible the panel
        this.setVisible(true);
    }

    private void addComponents() {
        //set propriety of title
        JLabel title = new JLabel("Cryptography");
        title.setForeground(Const.CL_GREEN);
        title.setFont(Const.TITLE_FONT);
        title.setBounds(0, 40, (int)Const.APP_SIZE.getWidth(), 50);
        title.setHorizontalAlignment(JLabel.CENTER);
        add(title);
        
        //set propriety of subtitle
        JLabel subTitle = new JLabel("Encryption / Decryption");
        subTitle.setForeground(Const.CL_DARK_GREEN);
        subTitle.setFont(Const.SUB_TITLE_FONT);
        subTitle.setBounds(0, 80, (int)Const.APP_SIZE.getWidth(), 50);
        subTitle.setHorizontalAlignment(JLabel.CENTER);
        add(subTitle);
        
        //encryption button
        JButton encryptionBtn = new JButton("Encryption");
        encryptionBtn.setForeground(Const.CL_GREEN);
        encryptionBtn.setBounds(Const.MAINGUI_ENCRYPT_BTN);
        encryptionBtn.setFont(Const.PRIMARY_BUTTON_FONT);
        encryptionBtn.setBackground(Const.BG_COLOR);
        encryptionBtn.setFocusable(false);
        encryptionBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //open encryption dialog
                System.out.println("encryption");
                new EncryptGUI(true);
                dispose();
            }
        });
        add(encryptionBtn);
        
        //decryption button
        JButton decryptionBtn = new JButton("Decryption");
        decryptionBtn.setForeground(Const.CL_GREEN);
        decryptionBtn.setBounds(Const.MAINGUI_DECRYPT_BTN);
        decryptionBtn.setFont(Const.PRIMARY_BUTTON_FONT);
        decryptionBtn.setBackground(Const.BG_COLOR);
        decryptionBtn.setFocusable(false);
        decryptionBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //open decryption dialog
                System.out.println("decryption");
                new EncryptGUI(false);
                dispose();
            }
        });
        add(decryptionBtn);
    }
}
