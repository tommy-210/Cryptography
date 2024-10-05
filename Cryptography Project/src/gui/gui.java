import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class gui extends JFrame implements ActionListener{
    Dimension FRAME_SIZE = new Dimension(400, 500);
    Dimension BUTTON_SIZE = new Dimension(150, 50);
    Font BUTTON_FONT = new Font("Dialog", Font.PLAIN, 24);
    Color CL_DARK_GREEN = new Color(0, 180, 0);
    JButton decryptBtn, encryptBtn;

    cryptographyDialog cryptographyDialog;

    public gui() throws Exception{
        super("Cryptography");
        this.getContentPane().setBackground(new Color(0x181818));
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLayout(null);
        this.setSize(FRAME_SIZE);
        this.setLocationRelativeTo(this);
        addComponents();
        this.setVisible(true);
    }

    public void addComponents() {
        //set title
        JLabel title = new JLabel("Cryptography");
        title.setFont(new Font("Dialog", Font.BOLD, 40));
        title.setBounds(0, 30, (int) FRAME_SIZE.getWidth(), 60);
        title.setHorizontalAlignment(JLabel.CENTER);
        title.setForeground(new Color(0, 255, 0));
        add(title);

        //setsubtitle or description
        JLabel subtitle = new JLabel("Encryption/Decryption");
        subtitle.setFont(new Font("Dialog", Font.PLAIN, 16));
        subtitle.setBounds(0, 90, (int) FRAME_SIZE.getWidth(), 30);
        subtitle.setHorizontalAlignment(JLabel.CENTER);
        subtitle.setForeground(CL_DARK_GREEN);
        add(subtitle);

        //encryption button
        encryptBtn = new JButton("Encryption");
        encryptBtn.setFont(BUTTON_FONT);
        encryptBtn.setBounds(((int) FRAME_SIZE.getWidth() - (int) BUTTON_SIZE.getWidth())/2,
                    200, (int) BUTTON_SIZE.getWidth(), (int) BUTTON_SIZE.getHeight());
        encryptBtn.setBackground(new Color(0x121212));
        encryptBtn.setForeground(CL_DARK_GREEN);
        encryptBtn.setFocusable(false);
        encryptBtn.addActionListener(this);
        add(encryptBtn);

        //encryption button
        decryptBtn = new JButton("Decryption");
        decryptBtn.setFont(BUTTON_FONT);
        decryptBtn.setBounds(((int) FRAME_SIZE.getWidth() - (int) BUTTON_SIZE.getWidth())/2,
                    300, (int) BUTTON_SIZE.getWidth(), (int) BUTTON_SIZE.getHeight());
        decryptBtn.setBackground(new Color(0x121212));
        decryptBtn.setForeground(CL_DARK_GREEN);
        decryptBtn.setFocusable(false);
        decryptBtn.addActionListener(this);
        add(decryptBtn);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == encryptBtn) {
            //open encryption dialog
            new cryptographyDialog(gui.this, true);
            this.setVisible(false);
        }else if(e.getSource() == decryptBtn) {
            //open decryption dialog
            new cryptographyDialog(gui.this, false);
            this.setVisible(false);
        }
    }
}
