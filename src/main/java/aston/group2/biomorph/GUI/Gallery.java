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
	//bottom panel on the screen
	private JPanel panelOne;
	//top panel
	private JPanel panelTwo;
	//parent for halloffame and gallery
	private JPanel middlePanel;
	//panel on the left side
	private JPanel galleryPanel;
	//panel on the right side
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
		 setMinimumSize(new Dimension(400, 400));
		 panelOne = new JPanel();
		 panelOne.setLayout(new FlowLayout());
		 panelTwo = new JPanel();
		 panelTwo.setLayout(new FlowLayout());
	    
		 middlePanel = new JPanel();
		 middlePanel.setLayout(new BorderLayout());
		
		 galleryPanel = new JPanel();
		 galleryPanel.setLayout(new GridLayout(2,10,10,10));
		 hofPanel = new JPanel();
	
		 
		 back = new JButton("Back");
		 exit = new JButton("Exit");
		 save = new JButton("Save/Export");
		 compareBiomorphs = new JButton("Compare Biomorphs");
		
		//biomorph windows for gallery panel
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
		
		 
         
		 favBio1 = new JLabel("1");
		 Border border = BorderFactory.createLineBorder(Color.BLACK, 1);
		 favBio1.setPreferredSize(new Dimension(70, 70));
		 favBio1.setBorder(border);
		 favBio2 = new JLabel("2");
		 //FlowLayout layout = new FlowLayout();
		 
		 favBio2.setPreferredSize(new Dimension(70, 70));
		 favBio2.setBorder(border);
		 favBio3 = new JLabel("3");
		 favBio3.setPreferredSize(new Dimension(70, 70));
		 favBio3.setBorder(border);
		 favBio4 = new JLabel("4");
		 favBio4.setPreferredSize(new Dimension(70, 70));
		 favBio4.setBorder(border);
		 favBio5 = new JLabel("5");
		 favBio5.setPreferredSize(new Dimension(70, 70));
		 favBio5.setBorder(border);
		 favBio6 = new JLabel("6");
		 favBio6.setPreferredSize(new Dimension(70, 70));
		 favBio6.setBorder(border);
		 favBio7 = new JLabel("7");
		 favBio7.setPreferredSize(new Dimension(70, 70));
		 favBio7.setBorder(border);
		 favBio8 = new JLabel("8");
		 favBio8.setPreferredSize(new Dimension(70, 70));
		 favBio8.setBorder(border);
		 favBio9 = new JLabel("9");
		 favBio9.setPreferredSize(new Dimension(70, 70));
		 favBio9.setBorder(border);
		 
		 
		 GridBagLayout layout = new GridBagLayout();
		
		 hofPanel.setLayout(layout);
		 hofPanel.setBorder(border);
		 
		 addTiles(favBio1, 0, 0); 
		 addTiles(favBio2, 0, 1);
		 addTiles(favBio3, 0, 2); 
		 addTiles(favBio4, 0, 3); 
		 addTiles(favBio5, 0, 4); 
		 addTiles(favBio6, 0, 5);
		 addTiles(favBio7, 0, 6); 
		 addTiles(favBio8, 0, 7); 
		 addTiles(favBio9, 0, 8); 
		 

		 middlePanel.add(panelTwo, BorderLayout.NORTH);
		 middlePanel.add(galleryPanel, BorderLayout.WEST);
		 middlePanel.add(hofPanel);
		 middlePanel.add(panelOne, BorderLayout.SOUTH);
		 
		 add(middlePanel, BorderLayout.WEST);

		 exit.addActionListener(new ActionListener() {
			    public void actionPerformed(ActionEvent event) {
			        System.exit(0);
			  }
		 });
		
	}
	
	public void addTiles(JLabel l, int gridx, int gridy){
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = gridx;
		c.gridy = gridy;

		
		hofPanel.add(l, c);
	}
	
	
	
}


