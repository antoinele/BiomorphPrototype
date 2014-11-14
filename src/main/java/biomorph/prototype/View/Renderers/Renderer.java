package biomorph.prototype.View.Renderers;

import biomorph.prototype.Model.Genes.Gene;
import biomorph.prototype.View.Coordinate;
import biomorph.prototype.View.Renderable;

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

    public abstract void draw(Graphics2D g);
    public abstract Coordinate getAttachPoint();

    public Coordinate getParentAttachPoint()
    {
        Gene parentGene = getGene().getParent();
        if(parentGene instanceof Renderable)
        {
            return ((Renderable) parentGene).getRenderer().getAttachPoint();
        }
        else
        {
            throw new RuntimeException("How...?");
        }
    }

    public T getGene()
    {
        return gene;
    }
}
