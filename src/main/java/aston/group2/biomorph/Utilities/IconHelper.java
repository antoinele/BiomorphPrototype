package aston.group2.biomorph.Utilities;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * @author Antoine
 */
public class IconHelper {
	//Used for converting JButtons into image icons
    public static JButton makeButton(String icon, String tooltip) {
        try {
            BufferedImage iconImg = ImageIO.read(new File("resources/icons/"
                    + icon + ".png"));
            JButton button = new JButton(new ImageIcon(iconImg));
            button.setToolTipText(tooltip);
            return button;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }
}
