package aston.group2.biomorph.GUI;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayDeque;
import java.util.Stack;

import javax.swing.*;

import aston.group2.biomorph.Model.Biomorph;
import aston.group2.biomorph.Model.Genes.Gene;

public class BiomorphSurface extends JComponent {

    private BiomorphRenderer biomorphRenderer;

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

	@Override
	public void paintComponent(Graphics g) {
        super.paintComponent(g);
        //fetches Canvas Height and Width for Rescaling
        int width = getWidth(),
            height = getHeight();

        biomorphRenderer.setSize(width, height);

        biomorphRenderer.doDrawing((Graphics2D) g);
    }

	public BufferedImage getFrame() {
        int width = getWidth(), height = getHeight();

        biomorphRenderer.setSize(width, height);

        BufferedImage currentFrame = new BufferedImage(width,
				height, BufferedImage.TYPE_INT_ARGB);
		biomorphRenderer.doDrawing((Graphics2D) currentFrame.createGraphics());
		return currentFrame;
	}
}