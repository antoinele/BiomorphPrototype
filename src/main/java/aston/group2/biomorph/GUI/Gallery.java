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

import com.sun.xml.internal.ws.api.config.management.policy.ManagementAssertion.Setting;

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
	
	private JButton swap1;
	private JButton clear1;
	private JButton swap2;
	private JButton clear2;
	private JButton swap3;
	private JButton clear3;
	private JButton swap4;
	private JButton clear4;
	private JButton swap5;
	private JButton clear5;
	private JButton swap6;
	private JButton clear6;
	private JButton swap7;
	private JButton clear7;
	private JButton swap8;
	private JButton clear8;
	private JButton swap9;
	private JButton clear9;
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	private final int fixedWidth = 800;
	private final int fixedHeight = 600;
	private int newWidth = 0;
	private int newHeight = 0;
	// dimensions of the individual biomorph box (for dynamic resizing)
	// both equal 400
	private int boxWidth = fixedWidth/2;
	private int boxHeight = (fixedHeight/3)*2;
	
	
	
	
	
	public Gallery(){
	     frame = new JFrame();
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
		 
		 swap1 = new JButton("Swap");
		 clear1 = new JButton("Clear");
		 swap2 = new JButton("Swap");
		 clear2 = new JButton("Clear");
		 swap3 = new JButton("Swap");
		 clear3 = new JButton("Clear");
		 swap4 = new JButton("Swap");
		 clear4 = new JButton("Clear");
		 swap5 = new JButton("Swap");
		 clear5 = new JButton("Clear");
		 swap6 = new JButton("Swap");
		 clear6 = new JButton("Clear");
		 swap7 = new JButton("Swap");
		 clear7 = new JButton("Clear");
		 swap8 = new JButton("Swap");
		 clear8 = new JButton("Clear");
		 swap9 = new JButton("Swap");
		 clear9 = new JButton("Clear");
	
		 
		 createBiomorphTiles();
	

	     frame.add(middlePanel);
	    
	    
	    
		 panelOne.add(compareBiomorphs);
		 panelOne.add(save);
		 panelTwo.add(back, BorderLayout.WEST);
		 panelTwo.add(exit);
		

		
		 
         
		 favBio1 = new JLabel("1");
		 Border border = BorderFactory.createLineBorder(Color.BLACK, 1);
		 favBio1.setPreferredSize(new Dimension(80, 70));
		 favBio1.setBorder(border);
		 favBio2 = new JLabel("2");
		 //FlowLayout layout = new FlowLayout();
		 
		 favBio2.setPreferredSize(new Dimension(80, 70));
		 favBio2.setBorder(border);
		 favBio3 = new JLabel("3");
		 favBio3.setPreferredSize(new Dimension(80, 70));
		 favBio3.setBorder(border);
		 favBio4 = new JLabel("4");
		 favBio4.setPreferredSize(new Dimension(80, 70));
		 favBio4.setBorder(border);
		 favBio5 = new JLabel("5");
		 favBio5.setPreferredSize(new Dimension(80, 70));
		 favBio5.setBorder(border);
		 favBio6 = new JLabel("6");
		 favBio6.setPreferredSize(new Dimension(80, 70));
		 favBio6.setBorder(border);
		 favBio7 = new JLabel("7");
		 favBio7.setPreferredSize(new Dimension(80, 70));
		 favBio7.setBorder(border);
		 favBio8 = new JLabel("8");
		 favBio8.setPreferredSize(new Dimension(80, 70));
		 favBio8.setBorder(border);
		 favBio9 = new JLabel("9");
		 favBio9.setPreferredSize(new Dimension(80, 70));
		 favBio9.setBorder(border);
		 
		 
		 GridBagLayout layout = new GridBagLayout();
		
		 hofPanel.setLayout(layout);
		 hofPanel.setBorder(border);
		 
		 addTiles(favBio1, 0, 0); 
		 addButtons(swap1,1,0);
		 addButtons(clear1, 2, 0);
		 addTiles(favBio2, 0, 1);
		 addButtons(swap2,1,1);
		 addButtons(clear2, 2, 1);
		 addTiles(favBio3, 0, 2); 
		 addButtons(swap3,1,2);
		 addButtons(clear3, 2, 2);
		 addTiles(favBio4, 0, 3); 
		 addButtons(swap4,1,3);
		 addButtons(clear4, 2, 3);
		 addTiles(favBio5, 0, 4); 
		 addButtons(swap5,1,4);
		 addButtons(clear5, 2, 4);
		 addTiles(favBio6, 0, 5);
		 addButtons(swap6,1,5);
		 addButtons(clear6, 2, 5);
		 addTiles(favBio7, 0, 6); 
		 addButtons(swap7,1,6);
		 addButtons(clear7, 2, 6);
		 addTiles(favBio8, 0, 7);
		 addButtons(swap8,1,7);
		 addButtons(clear8, 2, 7);
		 addTiles(favBio9, 0, 8); 
		 addButtons(swap9,1,8);
		 addButtons(clear9, 2, 8);
		 
	
		 
		 

		 middlePanel.add(panelTwo, BorderLayout.NORTH);
		 middlePanel.add(galleryPanel, BorderLayout.WEST);
		 middlePanel.add(hofPanel);
		 middlePanel.add(panelOne, BorderLayout.SOUTH);
		 
		 add(middlePanel, BorderLayout.WEST);
		 
		 back.addActionListener(new ActionListener() {
			    public void actionPerformed(ActionEvent event) {
			    	MainWindow mw = new MainWindow();
					mw.setVisible(false);
					dispose();
			  }
		 });

		 exit.addActionListener(new ActionListener() {
			    public void actionPerformed(ActionEvent event) {
			        System.exit(0);
			  }
		 });
		
	}
	
	//for hall of fame
	public void addTiles(JLabel l, int gridx, int gridy){
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = gridx;
		c.gridy = gridy;
		hofPanel.add(l, c);
	}
	
	//for hall of fame
	public void addButtons(JButton button, int gridx, int gridy){
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = gridx;
		c.gridy = gridy;
		
		hofPanel.add(button, c);
	}
	
	public void createBiomorphTiles(){
		//biomorph windows for gallery panel
		 biomorph1 = new JButton();
		 biomorph2 = new JButton();
		 biomorph3 = new JButton();
		 biomorph4 = new JButton();
		 biomorph5 = new JButton();
		 biomorph6 = new JButton();
		 
		 JButton[] biomorphs = {biomorph1, biomorph2, biomorph3, biomorph4, biomorph5, biomorph6};
		
		 // sets size of biomorph relative to canvas size
		 for(int i = 0; i<biomorphs.length; i++){
	     biomorphs[i].setPreferredSize(new Dimension(boxWidth/4, boxHeight/4));
	     galleryPanel.add(biomorphs[i]);
		 }
	  
	}
	
	
}


