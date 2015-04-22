package aston.group2.biomorph.GUI;

import java.awt.*;

/**
 * Created by antoine on 31/10/14.
 */
public interface Processed {
    /**
     * This runs before rendering
     */
    void process(BiomorphRenderer.RenderState renderState, Graphics g);
}
