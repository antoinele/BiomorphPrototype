package aston.group2.biomorph.GUI;

import aston.group2.biomorph.Model.Biomorph;
import aston.group2.biomorph.Model.Genes.Gene;

import java.awt.*;
import java.awt.geom.AffineTransform;

/**
 * @author Antoine
 */
public class BiomorphRenderer {

    private Biomorph biomorph;
    private Gene rootGene;

    public final boolean CLIPMIRROR = true;

    private final int fixedWidth = 800;
    private final int fixedHeight = 600;
    private int width = 800;
    private int height = 600;

    private double scale = 1d;

    public BiomorphRenderer() {}
    public BiomorphRenderer(Biomorph biomorph)
    {
        this();
        setBiomorph(biomorph);
    }
    public BiomorphRenderer(Biomorph biomorph, int width, int height)
    {
        this(biomorph);
        setSize(width, height);
    }

    public void setScale(double scale)
    {
        this.scale = scale;
    }

    public void setSize(int width, int height)
    {
        this.width = width;
        this.height = height;
    }

    public void doDrawing(Graphics2D g) {
        RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        g.setRenderingHints(rh);
        g.translate(width / 2, height / 2);

        Shape oldClip;

        if (CLIPMIRROR) {
            oldClip = g.getClip();
            g.clip(new Rectangle(-width/2,-height/2,width/2,height));
        }

        AffineTransform t = g.getTransform(); // Store transform before drawing messes with it

        g.scale((double) width/fixedWidth, (double) height/fixedHeight);
        g.scale(scale, scale);

        // Iterate subgenes directly to avoid processing/drawing the root gene,
        // which is impossible
        for (Gene gene : rootGene.getSubGenes()) {
            drawGene(g, gene);
        }

        g.setTransform(t); // Restore transform

        if (CLIPMIRROR) {
            g.setClip(oldClip);
            g.clip(new Rectangle(0, -height / 2, width / 2, height));
        }

        g.scale((double) width / fixedWidth, (double) height / fixedHeight);
        g.scale(scale, scale);

        AffineTransform mirrorTransform = AffineTransform.getScaleInstance(-1.0, 1.0);

        g.transform(mirrorTransform);

        // Draw genes again, but mirrored
        for (Gene gene : rootGene.getSubGenes()) {
            drawGene(g, gene);
        }
    }

    private void drawGene(Graphics2D g, Gene gene)
    {
        drawGene(g, gene, true);
    }
    private void drawGene(Graphics2D g, Gene gene, boolean draw) {
        Gene[] subGenes = gene.getSubGenes();

        for (Gene sg : subGenes) {
            if (sg instanceof Processed) {
                ((Processed) sg).process(g);
            }
        }

        if (gene instanceof Processed) {
            ((Processed) gene).process(g);
        }



        if (draw && gene instanceof Renderable) {
            aston.group2.biomorph.GUI.Renderers.Renderer r = ((Renderable) gene).getRenderer();
            r.draw(g);
        }

        for (Gene sg : subGenes) {
            drawGene(g, sg, draw && gene instanceof Renderable);
        }
    }

    public void setBiomorph(Biomorph biomorph) {
        this.biomorph = biomorph;

        rootGene = biomorph.getRootGene();

    }

    public Biomorph getBiomorph() {
        return biomorph;
    }
}
