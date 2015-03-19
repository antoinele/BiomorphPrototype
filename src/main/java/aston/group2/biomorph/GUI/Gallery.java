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
	private JButton back;
	private JButton exit;
	private JButton compareBiomorphs;
	private JButton save;
	
	
	
	public Gallery(){
		setMinimumSize(new Dimension(400,400));
		panelOne = new JPanel();
		panelOne.setLayout(new FlowLayout());
		panelTwo = new JPanel();
		panelTwo.setLayout(new FlowLayout());
		back = new JButton("Back");
		exit = new JButton("Exit");
		save = new JButton("Save/Export");
		compareBiomorphs = new JButton("Compare Biomorphs");
		
		
		panelOne.add(compareBiomorphs);
		panelOne.add(save);
		panelTwo.add(back);
		panelTwo.add(exit);
		
		 add(panelOne, BorderLayout.SOUTH);
		 add(panelTwo, BorderLayout.NORTH);
		
		
	}
}


