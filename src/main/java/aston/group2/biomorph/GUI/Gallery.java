package aston.group2.biomorph.GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;




import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import aston.group2.biomorph.Model.Biomorph;
import aston.group2.biomorph.Model.Genes.Gene;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Gallery extends JFrame{

	private JFrame frame;
	private JPanel panelOne;
	private JPanel panelTwo;
	private JPanel middlePanel;
	private JPanel galleryPanel;
	private JPanel hofPanel;
	
	private JButton biomorph1;
	private JButton biomorph2;
	private JButton biomorph3;
	private JButton biomorph4;
	private JButton biomorph5;
	private JButton biomorph6;
	
	//hall of fame biomorphs
	private JLabel favBio1;
	private JLabel favBio2;
	private JLabel favBio3;
	private JLabel favBio4;
	private JLabel favBio5;
	private JLabel favBio6;
	private JLabel favBio7;
	private JLabel favBio8;
	private JLabel favBio9;
	
	
	private JButton back;
	private JButton exit;
	private JButton compareBiomorphs;
	private JButton save;
	
	
	private final int fixedWidth = 800;
	private final int fixedHeight = 600;
	private int newWidth = 0;
	private int newHeight = 0;
	// dimensions of the individual biomorph box (for dynamic resizing)
	// both equal 400
	private int boxWidth = fixedWidth/2;
	private int boxHeight = (fixedHeight/3)*2;
	
	
	
	
	
	public Gallery(){
		JFrame frame = new JFrame();
		frame.pack();
		frame.setResizable(true);
		
		//setMinimumSize(new Dimension(boxHeight,boxWidth));
		setMinimumSize(new Dimension(1000, 400));
		panelOne = new JPanel();
		panelOne.setLayout(new FlowLayout());
		panelTwo = new JPanel();
		panelTwo.setLayout(new FlowLayout());
	    
		middlePanel = new JPanel();
		middlePanel.setLayout(new BorderLayout());
		
		galleryPanel = new JPanel();
		galleryPanel.setLayout(new GridLayout(2,10,10,10));
		hofPanel = new JPanel();
		hofPanel.setPreferredSize(new Dimension(1,1));
		//hofPanel.setLayout(new GridLayout(5, 0, 0, 0));
		
		
		back = new JButton("Back");
		exit = new JButton("Exit");
		save = new JButton("Save/Export");
		compareBiomorphs = new JButton("Compare Biomorphs");
		biomorph1 = new JButton();
		biomorph2 = new JButton();
		biomorph3 = new JButton();
		biomorph4 = new JButton();
		biomorph5 = new JButton();
		biomorph6 = new JButton();
		
		// sets size of biomorph relative to canvas size
		biomorph1.setPreferredSize(new Dimension(boxWidth/4, boxHeight/4));
		
	    biomorph2.setPreferredSize(new Dimension(boxWidth/4, boxHeight/4));
	    biomorph3.setPreferredSize(new Dimension(boxWidth/4, boxHeight/4));
	    biomorph4.setPreferredSize(new Dimension(boxWidth/4, boxHeight/4));
	    biomorph5.setPreferredSize(new Dimension(boxWidth/4, boxHeight/4));
	    biomorph6.setPreferredSize(new Dimension(boxWidth/4, boxHeight/4));
	    
	    frame.add(middlePanel);
	    
	    
	    
	    galleryPanel.add(biomorph1);
	    galleryPanel.add(biomorph2);
	    galleryPanel.add(biomorph3);
	    galleryPanel.add(biomorph4);
	    galleryPanel.add(biomorph5);
	    galleryPanel.add(biomorph6);
	    
	   
	    
	    
	    
	    
	    
		panelOne.add(compareBiomorphs);
		panelOne.add(save);
		panelTwo.add(back, BorderLayout.WEST);
		panelTwo.add(exit);
		
         
		 add(panelTwo, BorderLayout.NORTH);
		 add(middlePanel, BorderLayout.CENTER);
		 hofPanel.setPreferredSize(new Dimension(100, 1000));
		 middlePanel.add(galleryPanel, BorderLayout.WEST);
		 middlePanel.add(hofPanel);
		 add(panelOne, BorderLayout.SOUTH);
		 
		 favBio1 = new JLabel("ddfdcfdc");
		 Border border = BorderFactory.createLineBorder(Color.BLACK, 1);
		// favBio1.setPreferredSize(new Dimension(10, 10));
		 favBio1.setBorder(border);
		 favBio2 = new JLabel("2");
		 GridLayout layout = new GridLayout(2,0);
		 
		 //favBio2.setPreferredSize(new Dimension(100, 100));
		 
		 favBio2.setBorder(border);
		 hofPanel.setLayout(layout);
		 hofPanel.setBorder(border);
		 hofPanel.add(favBio1);
		 hofPanel.add(favBio2);
		 
		
		
		 exit.addActionListener(new ActionListener() {
			    public void actionPerformed(ActionEvent event) {
			        System.exit(0);
			  }
		 });
		
	}
	
	
	
}


