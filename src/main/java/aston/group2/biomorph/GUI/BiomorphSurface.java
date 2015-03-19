package aston.group2.biomorph.GUI;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import aston.group2.biomorph.Model.Biomorph;
import aston.group2.biomorph.Model.Genes.Gene;

class BiomorphSurface extends JPanel {

	public boolean CLIPMIRROR = false;

	private final int fixedWidth = 800;
	private final int fixedHeight = 600;
	private int newWidth = 0;
	private int newHeight = 0;
	

	private Biomorph biomorph;
	private Gene rootGene;
	
	// List<Gene> biomorphprerenderables = new ArrayList<Gene>();
	// List<Renderer> biomorphrenderables = new ArrayList<Renderer>();

	private void doDrawing(Graphics2D g) {
		RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		// apple PEAR

		g.setRenderingHints(rh);
		g.setTransform(AffineTransform.getTranslateInstance(newWidth / 2,
				newHeight / 2));
		//TODO: Search for Graphics2D Scaling 
		//Horizontal Scaling is newwidth/oldwidth 
		//Vertical Scaling is newheight/oldheight
		//Also fix main window Class
		g.scale((double)newWidth/fixedWidth, (double)newHeight/fixedHeight);
		//scaling added
		
		
		
		g.setColor(Color.black);

		AffineTransform t = g.getTransform(); // Store transform before drawing
												// messes with it

		if (CLIPMIRROR) {
			g.setClip(-newWidth / 2, -newHeight / 2, newWidth / 2,
					newHeight);
		}

		// Iterate subgenes directly to avoid processing/drawing the root gene,
		// which is impossible
		for (Gene gene : rootGene.getSubGenes()) {
			drawGene(g, gene);
		}

		g.setTransform(t); // Restore transform

		AffineTransform mirrorTransform = AffineTransform.getScaleInstance(
				-1.0, 1.0);

		g.transform(mirrorTransform);

		if (CLIPMIRROR) {
			g.setClip(-newWidth / 2, -newHeight / 2, newWidth / 2,
					newHeight);
		}

		for (Gene gene : rootGene.getSubGenes()) {
			drawGene(g, gene);
		}
	}

	private void drawGene(Graphics2D g, Gene gene) {
		Gene[] subGenes = gene.getSubGenes();

		for (Gene sg : subGenes) {
			if (sg instanceof Processed) {
				((Processed) sg).process(g);
			}
		}

		if (gene instanceof Processed) {
			((Processed) gene).process(g);
		}

		if (gene instanceof Renderable) {
			aston.group2.biomorph.GUI.Renderers.Renderer r = ((Renderable) gene)
					.getRenderer();
			r.draw(g);
		}

		for (Gene sg : subGenes) {
			drawGene(g, sg);
		}
	}

	public void setBiomorph(Biomorph biomorph) {
		this.biomorph = biomorph;
		biomorph.setOrigin(new Coordinate(0, 0));

		rootGene = biomorph.getRootGene();

		repaint();

		// biomorphrenderables.clear();
		// biomorphprerenderables.clear();
	}

	public Biomorph getBiomorph() {
		return biomorph;
	}

	@Override
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		//fetches Canvas Height and Width for Rescaling
		newWidth = getWidth();
		newHeight = getHeight();
		
		// System.out.println("Draw to image");
		// doDrawing((Graphics2D) currentFrame.createGraphics());
		// System.out.println("Draw to window");
		doDrawing((Graphics2D) g);
		//TODO: Set Window Width and Window Height
	}

	public BufferedImage getFrame() {
		newWidth = getWidth();
		newHeight = getHeight();
		
		BufferedImage currentFrame = new BufferedImage(newWidth,
				newHeight, BufferedImage.TYPE_INT_ARGB);
		doDrawing((Graphics2D) currentFrame.createGraphics());
		return currentFrame;
	}
}