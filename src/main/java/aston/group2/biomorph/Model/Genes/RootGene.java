package aston.group2.biomorph.Model.Genes;

import aston.group2.biomorph.GUI.Coordinate;
import aston.group2.biomorph.GUI.Renderable;
import aston.group2.biomorph.GUI.Renderers.Renderer;

import java.awt.*;

/**
 * Created by antoine on 17/04/15.
 */

public class RootGene extends Gene implements Renderable {

    private Coordinate origin;

    public RootGene(Coordinate origin)
    {
        super('X');
        this.origin = origin;
    }

    public RootGene()
    {
        super('X');
        this.origin = new Coordinate(0,0);
    }

    private class RootGeneRenderer extends Renderer<RootGene> {

        public RootGeneRenderer()
        {
            super(RootGene.this);
        }

        @Override
        public void draw(Graphics2D g) {
            throw new RuntimeException("This should never be called");
        }

        @Override
        public Coordinate getAttachPoint() {
            return origin;
        }
    }

    @Override
    protected int maxValues()
    {
        return 0;
    }

    @Override
    protected void parseValues() {

    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();

        for (Gene gene : getSubGenes()) {
            sb.append(gene.toString());
        }

        return sb.toString();
    }

    RootGeneRenderer r = null;

    @Override
    public Renderer getRenderer() {
        if(r == null) r = new RootGeneRenderer();
        return r;
    }
}