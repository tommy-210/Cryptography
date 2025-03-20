package Constants;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;

import javax.swing.BorderFactory;
import javax.swing.border.Border;

public class Const {
    public static final String FILE_CHOOSER_DIRECTORY_PATH = "src\\example\\";

    //set font family
    private static String nameFont = "Dialog";

    public static final int LEFT_ALLIGNAMENT = 30;
    public static final String[] RADIO_BTN_NAME = {"AES", "RSA", "IMAGE"};
    public static final Border BORDER = BorderFactory.createLineBorder(Const.CL_WHITE, 2);

    //dimension
    public static final Dimension APP_SIZE = new Dimension(400, 520);
    public static final Dimension DIALOG_SIZE = new Dimension(600, 600);
    public static final Dimension BUTTON_SIZE = new Dimension(200, 70);
    public static final Dimension TEXT_AREA_SIZE = new Dimension(250, 200);
    public static final Dimension FIELD_SIZE = new Dimension(250, 50);

    //position
    public static final Rectangle MAINGUI_ENCRYPT_BTN = new Rectangle(
                                (int)(APP_SIZE.getWidth()/2 - BUTTON_SIZE.getWidth()/2), 180,
                                (int)BUTTON_SIZE.getWidth(), (int)BUTTON_SIZE.getHeight());
    public static final Rectangle MAINGUI_DECRYPT_BTN = new Rectangle(
                                (int)(APP_SIZE.getWidth()/2 - BUTTON_SIZE.getWidth()/2), 320,
                                (int)BUTTON_SIZE.getWidth(), (int)BUTTON_SIZE.getHeight());
    
    //color
    public static final Color BG_COLOR = new Color(0x121212);
    public static final Color CL_GREEN = new Color(0, 255, 0);
    public static final Color CL_DARK_GREEN = new Color(0, 180, 0);
    public static final Color CL_GRAY = new Color(120, 120, 120);
    public static final Color CL_WHITE = new Color(240, 240, 240);
    public static final Color CL_SELECT = new Color(200, 200, 250);
    
    //font
    public static final Font TITLE_FONT = new Font(nameFont, Font.BOLD, 42);
    public static final Font SUB_TITLE_FONT = new Font(nameFont, Font.PLAIN, 18);
    public static final Font PRIMARY_BUTTON_FONT = new Font(nameFont, Font.BOLD, 30);
    public static final Font BUTTON_FONT = new Font(nameFont, Font.PLAIN, 16);
    public static final Font RADIO_BTN_FONT = new Font(nameFont, Font.BOLD, 15);
    public static final Font NORMAL_FONT = new Font(nameFont, Font.PLAIN, 15);

    //encryption methods parameter
    public static final int AES_T_LEN = 128;
    public static final int AES_KEY_LEN = 128;
    public static final String AES_METHODS = "AES";
    public static final String AES_METHODS1 = "AES/GCM/NoPadding";
    public static final int RSA_KEY_LEN = 512;
    public static final String RSA_METHODS1 = "RSA/ECB/PKCS1Padding";
    public static final String RSA_METHODS = "RSA";
}
