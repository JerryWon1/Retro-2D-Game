package main;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * A variety of convenient methods.
 */
public class UtilityTool {

    /**
     * Returns a scaled image.
     * @param original
     * @param width
     * @param height
     * @return
     */
    public BufferedImage scaleImage(BufferedImage original, int width, int height) {
        BufferedImage scaled = new BufferedImage(width, height,2);
        Graphics g2 = scaled.getGraphics();
        g2.drawImage(original, 0, 0, width, height, null);
        g2.dispose();
        return scaled;
    }

}
