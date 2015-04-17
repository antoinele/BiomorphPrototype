package aston.group2.biomorph.GUI;
import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import aston.group2.biomorph.Model.Biomorph;

public class BiomorphPreview extends JFrame{
	public BiomorphPreview(Biomorph biomorph){
		setMinimumSize(new Dimension(800,600));
		setLayout(new BorderLayout());
		
		BiomorphSurface bs = new BiomorphSurface();
		bs.setBiomorph(biomorph);
		
		add(bs, BorderLayout.CENTER);
	}
}

