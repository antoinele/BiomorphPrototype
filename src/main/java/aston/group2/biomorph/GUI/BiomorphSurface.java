package aston.group2.biomorph.GUI;

import aston.group2.biomorph.Model.Biomorph;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
/**
 * 
 * @author Antoine
 * Creates placeholders for the Biomorphs to be stored in.
 */
public class BiomorphSurface extends JComponent {

	private BiomorphRenderer biomorphRenderer;

	private float scaleFactor = 1;

	public BiomorphSurface()
    {
        biomorphRenderer = new BiomorphRenderer();
    }
    public BiomorphSurface(Biomorph biomorph)
    {
        biomorphRenderer = new BiomorphRenderer(biomorph);
    }

	public void setBiomorph(Biomorph biomorph) {
		biomorphRenderer.setBiomorph(biomorph);

		repaint();
	}

	public Biomorph getBiomorph() {
		return biomorphRenderer.getBiomorph();
	}

	/**
	 * @param scaleFactor Controls how much a biomorph is scaled by.
	 */
	public void setScaleFactor(float scaleFactor){
		this.scaleFactor = scaleFactor;
		repaint();
	}
	
	@Override
	public void paintComponent(Graphics g) {
        super.paintComponent(g);

		biomorphRenderer.setScale(scaleFactor);
		biomorphRenderer.setSize(getWidth(), getHeight());

        biomorphRenderer.doDrawing((Graphics2D) g);
    }

	/**
	 * Creates the frame for storing the biomorph in.
	 */
	public BufferedImage getFrame() {
		int width = getWidth();
		int height = getHeight();
		
		BufferedImage currentFrame = new BufferedImage(width,
				height, BufferedImage.TYPE_INT_ARGB);
		biomorphRenderer.doDrawing(currentFrame.createGraphics());
		return currentFrame;
	}
}
