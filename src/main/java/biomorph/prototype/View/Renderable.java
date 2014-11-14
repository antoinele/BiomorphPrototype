package biomorph.prototype.View;

import biomorph.prototype.Model.Genes.Gene;
import biomorph.prototype.View.Renderers.Renderer;

/**
 * Created by antoine on 30/10/14.
 */
public interface Renderable<T extends Gene> {
    public Renderer<T> getRenderer();
}
