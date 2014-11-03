package biomorph.prototype.View;

import biomorph.prototype.Model.Biomorph;
import biomorph.prototype.Model.Genes.Gene;

import javax.swing.*;
import java.awt.*;

/**
 * Created by antoine on 29/10/14.
 */
public class MainWindow extends JFrame {

    private final int WINDOW_WIDTH  = 800;
    private final int WINDOW_HEIGHT = 600;

    class BiomorphSurface extends JPanel {

        Gene rootGene;

//        List<Gene>     biomorphprerenderables = new ArrayList<Gene>();
//        List<Renderer> biomorphrenderables = new ArrayList<Renderer>();

        private void doDrawing(Graphics g)
        {
            Graphics2D g2 = (Graphics2D)g;

            RenderingHints rh = new RenderingHints(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
            );

            g2.setRenderingHints(rh);

            // Iterate subgenes directly to avoid processing/drawing the root gene, which is impossible
            for(Gene gene : rootGene.getSubGenes())
            {
                drawGene(g, gene);
            }
        }

        private void drawGene(Graphics g, Gene gene)
        {
            Gene[] subGenes = gene.getSubGenes();

            for(Gene sg : subGenes)
            {
                if(sg instanceof Processed)
                {
                    ((Processed) sg).process(g);
                }
            }

            if(gene instanceof Processed)
            {
                ((Processed) gene).process(g);
            }

            if(gene instanceof Renderable)
            {
                biomorph.prototype.View.Renderers.Renderer r = ((Renderable) gene).getRenderer();
                r.draw(g);
            }

            for(Gene sg : subGenes)
            {
                drawGene(g, sg);
            }
        }

        public void setBiomorph(Biomorph biomorph)
        {
            rootGene = biomorph.getRootGene();

//            biomorphrenderables.clear();
//            biomorphprerenderables.clear();
        }

        @Override
        public void paintComponent(Graphics g)
        {
            super.paintComponent(g);
            doDrawing(g);
        }
    }

    BiomorphSurface biomorphSurface;

    public MainWindow()
    {
        setTitle("Biomorph Example");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        biomorphSurface = new BiomorphSurface();

        add(biomorphSurface);

//        biomorphSurface.setBiomorph(new Biomorph("D21F00CSLBEEF00SMCAFEsL123456LFF12F0SLF24300s"));
        biomorphSurface.setBiomorph(new Biomorph("D59578ASL4785BEL985247SD589347"));

        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
    }

    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                MainWindow mw = new MainWindow();

                mw.setVisible(true);
            }
        });
    }
}
