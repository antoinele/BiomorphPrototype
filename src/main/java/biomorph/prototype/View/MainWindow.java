package biomorph.prototype.View;

import biomorph.prototype.Model.Biomorph;
import biomorph.prototype.Model.Genes.Gene;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by antoine on 29/10/14.
 */
public class MainWindow extends JFrame {

    class BiomorphSurface extends JPanel {

        Gene rootGene;

        List<Gene>     biomorphprerenderables = new ArrayList<Gene>();
        List<Renderer> biomorphrenderables = new ArrayList<Renderer>();

        private void doDrawing(Graphics g)
        {

        }

        public void setBiomorph(Biomorph biomorph)
        {
            rootGene = biomorph.getRootGene();

            biomorphrenderables.clear();
            biomorphprerenderables.clear();

            for(Gene gene : rootGene.getSubGenes())
            {
                biomorphprerenderables.add(gene);

                if(gene instanceof Renderable)
                {
                    Renderer r = ((Renderable) gene).getRenderer();
                    biomorphrenderables.add(r);
                }
            }
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
