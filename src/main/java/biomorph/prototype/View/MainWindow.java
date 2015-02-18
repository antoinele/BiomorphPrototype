package biomorph.prototype.View;

import biomorph.prototype.Model.Biomorph;
import biomorph.prototype.Model.Genes.Gene;
import javafx.scene.transform.Affine;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by antoine on 29/10/14.
 */
public class MainWindow extends JFrame {

    public boolean CLIPMIRROR = false;

    public final int WINDOW_WIDTH  = 800;
    public final int WINDOW_HEIGHT = 600;

    class BiomorphSurface extends JPanel {

        private Biomorph biomorph;
        private Gene rootGene;
        private BufferedImage currentFrame = new BufferedImage(WINDOW_WIDTH, WINDOW_HEIGHT, BufferedImage.TYPE_INT_ARGB);

//        List<Gene>     biomorphprerenderables = new ArrayList<Gene>();
//        List<Renderer> biomorphrenderables = new ArrayList<Renderer>();

        private void doDrawing(Graphics2D g)
        {
            RenderingHints rh = new RenderingHints(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
            );
            //apple

            g.setRenderingHints(rh);
            g.setTransform(AffineTransform.getTranslateInstance(WINDOW_WIDTH / 2, WINDOW_HEIGHT / 2));
            g.setColor(Color.black);

            AffineTransform t = g.getTransform(); // Store transform before drawing messes with it

            if(CLIPMIRROR)
            {
                g.setClip(-WINDOW_WIDTH/2, -WINDOW_HEIGHT/2, WINDOW_WIDTH/2, WINDOW_HEIGHT);
//                g.clipRect(0, -WINDOW_HEIGHT/2, WINDOW_WIDTH/2, WINDOW_HEIGHT);
            }

            // Iterate subgenes directly to avoid processing/drawing the root gene, which is impossible
            for(Gene gene : rootGene.getSubGenes())
            {
                drawGene(g, gene);
            }

            g.setTransform(t); // Restore transform

            System.err.println("Flipping");

            AffineTransform mirrorTransform = AffineTransform.getScaleInstance(-1.0, 1.0);
//            mirrorTransform.translate(-WINDOW_WIDTH,0);

//            AffineTransformOp op = new AffineTransformOp(mirrorTransform, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);

            g.transform(mirrorTransform);


            if(CLIPMIRROR)
            {
                g.setClip(-WINDOW_WIDTH/2, -WINDOW_HEIGHT/2, WINDOW_WIDTH/2, WINDOW_HEIGHT);
//                g.clipRect(0, -WINDOW_HEIGHT/2, WINDOW_WIDTH/2, WINDOW_HEIGHT);
//                g.fillRect(-WINDOW_WIDTH/2, -WINDOW_HEIGHT/2, WINDOW_WIDTH, WINDOW_HEIGHT);
            }



//            g.transform(mirrorTransform);

            for(Gene gene : rootGene.getSubGenes())
            {
                drawGene(g, gene);
            }
        }

        private void drawGene(Graphics2D g, Gene gene)
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
            this.biomorph = biomorph;
            biomorph.setOrigin(new Coordinate(0, 0));

            rootGene = biomorph.getRootGene();

            repaint();

//            biomorphrenderables.clear();
//            biomorphprerenderables.clear();
        }

        public Biomorph getBiomorph()
        {
            return biomorph;
        }

        @Override
        public void paintComponent(Graphics g)
        {
            super.paintComponent(g);
//            System.out.println("Draw to image");
//            doDrawing((Graphics2D) currentFrame.createGraphics());
//            System.out.println("Draw to window");
            doDrawing((Graphics2D)g);
        }

        public BufferedImage getFrame()
        {
            doDrawing((Graphics2D) currentFrame.createGraphics());
            return currentFrame.getSubimage(0,0,WINDOW_WIDTH,WINDOW_HEIGHT);
        }
    }

    public BiomorphSurface biomorphSurface;

    public MainWindow()
    {
        final JTextField genomeField;

        setTitle("Biomorph Example");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        setMinimumSize(new Dimension(WINDOW_WIDTH,WINDOW_HEIGHT));
//        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);

        biomorphSurface = new BiomorphSurface();
        biomorphSurface.setSize(WINDOW_WIDTH,WINDOW_HEIGHT);
        add(biomorphSurface, BorderLayout.CENTER);

        {   //Create header
            JPanel header = new JPanel();
            header.setLayout(new FlowLayout());

            add(header, BorderLayout.NORTH);

            genomeField = new JTextField("D21F00CSLBEEF00SMCAFEsL123456LFF12F0SLF24300s");
            header.add(genomeField);

            JButton setBiomorph = new JButton("Set biomorph");
            header.add(setBiomorph);

            setBiomorph.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    biomorphSurface.setBiomorph(new Biomorph(genomeField.getText()));
                }
            });
        }

        {   //Create footer thing
            JPanel footer = new JPanel();
            footer.setLayout(new FlowLayout());

            add(footer, BorderLayout.SOUTH);

            {   // Load/Save buttons
                JButton loadButton = new JButton("Load");
                JButton saveButton = new JButton("Save");
                footer.add(loadButton);
                footer.add(saveButton);

                loadButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        JFileChooser jf = new JFileChooser();
                        jf.setFileFilter(new FileNameExtensionFilter("Biomorph file","biomorph"));

                        int rv = jf.showOpenDialog(getParent());

                        if(rv == JFileChooser.APPROVE_OPTION)
                        {
                            File file = jf.getSelectedFile();

                            try {
                                BufferedReader br = Files.newBufferedReader(file.toPath(), Charset.forName("US-ASCII"));

                                StringBuilder sb = new StringBuilder();

                                String line;
                                while((line = br.readLine()) != null)
                                {
//                                    if(!line.startsWith("#"))
//                                    {
                                        sb.append(line);
//                                    }
                                }

                                genomeField.setText(sb.toString());
                                Biomorph bm = Biomorph.deserialise(sb.toString());
                                biomorphSurface.setBiomorph(bm);

                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                        }
                    }
                });

                saveButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        JFileChooser jf = new JFileChooser();
                        jf.setFileFilter(new FileNameExtensionFilter("Biomorph file","biomorph"));

                        int rv = jf.showSaveDialog(getParent());

                        if(rv == JFileChooser.APPROVE_OPTION)
                        {
                            String path = jf.getSelectedFile().getPath();

                            if (!path.endsWith(".biomorph")) {
                                path += ".biomorph";
                            }

                            try {
                                BufferedWriter br = Files.newBufferedWriter(Paths.get(path), Charset.forName("US-ASCII"));
                                br.write(biomorphSurface.getBiomorph().toString());
                                br.close();
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                        }
                    }
                });
            }

            {   // Export button
                JButton exportButton = new JButton("Export to PNG");
                footer.add(exportButton);

                exportButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        Image img = biomorphSurface.getFrame();

                        JFileChooser jf = new JFileChooser();
                        jf.setFileFilter(new FileNameExtensionFilter("PNG file", "png"));
                        int rv = jf.showSaveDialog(getParent());

                        if (rv == JFileChooser.APPROVE_OPTION) {

                            String path = jf.getSelectedFile().getPath();

                            if (!path.endsWith(".png")) {
                                path += ".png";
                            }

                            File of = new File(path);
                            try {
                                ImageIO.write((java.awt.image.RenderedImage) img, "png", of);
                            } catch (IOException e2) {
                                e2.printStackTrace();
                            }
                        }
                    }
                });
            }
        }


//        add(biomorphSurface);

        biomorphSurface.setBiomorph(new Biomorph("D21F00CSLBEEF00SMCAFEsL123456LFF12F0SLF24300s"));
//        biomorphSurface.setBiomorph(new Biomorph("D59578ASL4785BEL985247SD589347"));
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
