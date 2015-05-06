package aston.group2.biomorph.GUI;

import java.awt.*;

/**
 * @author Antoine
 */
public interface Processed {
    /**
     * This runs before rendering
     */
    void process(BiomorphRenderer.RenderState renderState, Graphics g);
}
