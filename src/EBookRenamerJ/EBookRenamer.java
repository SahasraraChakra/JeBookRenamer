/*
************************************************
    Author	:   Jeevan Kumar Vishwakarman
    Company	:   J Soft
    Created on	:   25-Jul-2025  12:17:52 pm
************************************************
 */
package EBookRenamerJ;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.RenderingHints;
import java.io.IOException;
import java.io.InputStream;
import javax.swing.UIDefaults;
import javax.swing.UIManager;

/**
 *
 * @author Jeevan Kumar Vishwakarman
 */
public class EBookRenamer {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
	    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            UIManager.put("ButtonUI", "ClassicXUI.ClassicXButtonUI"); // Use the fully qualified name if in a package
            UIManager.put(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            UIManager.put(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);

            RegisterFonts();

            frmRenamer form = new frmRenamer();
            form.setVisible(true);
            form.setLocationRelativeTo(null);

        } catch (/*UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException | IllegalAccess*/Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    private static void RegisterFonts() {
        try {
            // Load font from the "fonts" subfolder in the resources directory
            InputStream customFontFile = EBookRenamer.class.getResourceAsStream("/resources/fonts/RedHatDisplay-Regular.ttf");
            InputStream customBoldFontFile = EBookRenamer.class.getResourceAsStream("/resources/fonts/RedHatDisplay-Bold.ttf");

            
            // Create font
            Font customFont = Font.createFont(Font.TRUETYPE_FONT, customFontFile).deriveFont(Font.PLAIN,12f);
            Font customBoldFont = Font.createFont(Font.TRUETYPE_FONT, customBoldFontFile).deriveFont(Font.BOLD,12f);
//	    System.out.println(customFont.getName());
//	    System.out.println(customBoldFont.getName());

            // Register the font with GraphicsEnvironment
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont);
            ge.registerFont(customBoldFont);

            UIDefaults uidefaults = UIManager.getDefaults();
            
            uidefaults.put("regFont",customFont);
            uidefaults.put("boldFont",customBoldFont);

            uidefaults.put("Label.font", customFont);
            uidefaults.put("Button.font", customFont);
            uidefaults.put("TextField.font", customFont);
            uidefaults.put("Table.font", customFont);
            uidefaults.put("CheckBox.font", customFont);
            uidefaults.put("RadioButton.font", customFont);
            uidefaults.put("List.font", customFont);
            uidefaults.put("ComboBox.font", customFont);

        } catch (FontFormatException | IOException e) {
            System.out.println("Error Loading in Fonts : \n" + e.getMessage());
        }
    }

}
