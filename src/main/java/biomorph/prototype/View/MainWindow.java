package biomorph.prototype.View;

import biomorph.prototype.Model.Biomorph;
import biomorph.prototype.Model.Genes.Gene;

import javax.swing.*;
import java.awt.*;

/**
 * Created by antoine on 29/10/14.
 */
public class MainWindow extends JFrame {

    class BiomorphSurface extends JPanel {

        Gene rootGene;

//        List<Gene>     biomorphprerenderables = new ArrayList<Gene>();
//        List<Renderer> biomorphrenderables = new ArrayList<Renderer>();

        private void doDrawing(Graphics g)
        {
            for(Gene gene : rootGene.getSubGenes())
            {
                drawGene(g, gene);
            }
        }

        private void drawGene(Graphics g, Gene gene)
        {
            for(Gene sg : rootGene.getSubGenes())
            {
                drawGene(g, sg);
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

    public MainWindow()
    {
        setTitle("Biomorph Example");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        add(new BiomorphSurface());

        setSize(800,600);
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
