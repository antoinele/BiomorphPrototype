package biomorph.prototype.View.Renderers;

import biomorph.prototype.Model.Genes.Gene;
import biomorph.prototype.View.Coordinate;

import java.awt.*;

/**
 * Created by antoine on 30/10/14.
 */
public abstract class Renderer<T extends Gene> {
    private T gene;

    public Renderer(T gene)
    {
        this.gene = gene;
    }

    public abstract void draw(Graphics g);
    public abstract Coordinate getAttachPoint();

    public T getGene()
    {
        return gene;
    }
}
