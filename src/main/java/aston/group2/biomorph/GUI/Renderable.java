package aston.group2.biomorph.GUI;

import aston.group2.biomorph.GUI.Renderers.Renderer;
import aston.group2.biomorph.Model.Genes.Gene;

/**
 * Created by antoine on 30/10/14.
 */
public interface Renderable<T extends Gene> {
    public Renderer<T> getRenderer();
}
