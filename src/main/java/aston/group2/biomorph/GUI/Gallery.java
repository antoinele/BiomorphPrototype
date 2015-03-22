package aston.group2.biomorph.GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import aston.group2.biomorph.Model.Biomorph;
import aston.group2.biomorph.Model.Genes.Gene;

import javax.imageio.ImageIO;
import javax.swing.*;
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

	private JPanel panelOne;
	private JPanel panelTwo;
	private JPanel middlePanel;
	private JButton biomorph1;
	private JButton biomorph2;
	private JButton biomorph3;
	private JButton biomorph4;
	private JButton biomorph5;
	private JButton biomorph6;
	
	
	private JButton back;
	private JButton exit;
	private JButton compareBiomorphs;
	private JButton save;
	
	
	private final int fixedWidth = 800;
	private final int fixedHeight = 600;
	private int newWidth = 0;
	private int newHeight = 0;
	
	
	
	
	public Gallery(){
		
		setMinimumSize(new Dimension(400,400));
		panelOne = new JPanel();
		panelOne.setLayout(new FlowLayout());
		panelTwo = new JPanel();
		panelTwo.setLayout(new FlowLayout());
		middlePanel = new JPanel();
		middlePanel.setLayout(new FlowLayout());
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
		
		
		biomorph1.setPreferredSize(new Dimension(100, 100));
	    biomorph2.setPreferredSize(new Dimension(100, 100));
	    biomorph3.setPreferredSize(new Dimension(100, 100));
	    biomorph4.setPreferredSize(new Dimension(100, 100));
	    biomorph5.setPreferredSize(new Dimension(100, 100));
	    biomorph6.setPreferredSize(new Dimension(100, 100));
	    
	    
	    
	    middlePanel.add(biomorph1);
	    middlePanel.add(biomorph2);
	    middlePanel.add(biomorph3);
	    middlePanel.add(biomorph4);
	    middlePanel.add(biomorph5);
	    middlePanel.add(biomorph6);
	    
		
	    
	    
		panelOne.add(compareBiomorphs);
		panelOne.add(save);
		panelTwo.add(back);
		panelTwo.add(exit);
		

		 add(panelTwo, BorderLayout.NORTH);
		 add(middlePanel, BorderLayout.CENTER);
		 add(panelOne, BorderLayout.SOUTH);
		
		
		
	}
}


