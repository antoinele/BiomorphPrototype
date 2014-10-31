package biomorph.prototype.View;

import biomorph.prototype.Model.Genes.Gene;

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

    public abstract void draw(Graphics g, int originX, int originY);

    public T getGene()
    {
        return gene;
    }
}
