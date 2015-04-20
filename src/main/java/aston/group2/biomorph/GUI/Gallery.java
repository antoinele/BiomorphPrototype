package aston.group2.biomorph.GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;

import aston.group2.biomorph.Model.Biomorph;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
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
	
	
	 public BiomorphSurface biomorphSurface;
	
	
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
		
		 hofPanel = new JPanel();
 
		 back = new JButton("Back");
		 exit = new JButton("Exit");
		 save = new JButton("Save/Export");
		 compareBiomorphs = new JButton("Compare Biomorphs");

		 panelOne.add(compareBiomorphs);
		 panelOne.add(save);
		 panelTwo.add(back, BorderLayout.WEST);
		 panelTwo.add(exit);
		 
		 createBiomorphTiles();
		

	     frame.add(middlePanel);

	     favouritePanelHF();
	     createHallOfFamePanel();
		 
		 Border border = BorderFactory.createLineBorder(Color.BLACK, 1);

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
		 
		 
		 
		 save.addActionListener(new ActionListener() {
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
		 galleryPanel = new JPanel();
		 galleryPanel.setLayout(new GridLayout(2,3,10,10));
		 
		//biomorph windows for gallery panel
		 
		 //JPanel[] biomorphs = new JPanel[6];
		 for(int i =0; i<6; i++){
			 BiomorphSurfaceWithTools bS = new BiomorphSurfaceWithTools();
			 Biomorph bm = new Biomorph("D21F00CSLBEEF00SMCAFEsL123456LFF12F0SLF2430"+i+"s");
			 
			 bS.setBiomorph(bm);
			 //biomorphs[i] = bS;
			 bS.setPreferredSize(new Dimension(boxWidth/4, boxHeight/4));
			 galleryPanel.add(bS);
		 }
		
		 // sets size of biomorph relative to canvas size
		 //for(int i = 0; i<biomorphs.length; i++){
	     //biomorphs[i].setPreferredSize(new Dimension(boxWidth/4, boxHeight/4));
	     //galleryPanel.add(biomorphs[i]);
		 //}
	  
	}
	
	public void createHallOfFamePanel(){
		
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
		 
		 JButton[] swaps = {swap1, swap2, swap3, swap4, swap5, swap6, swap7, swap8, swap9};
		
		 int y = 0;
		 for(JButton swap: swaps){
		 addButtons(swap, 1, y);
		 y++;
		 }
		 
		 JButton[] clearbuttons = {clear1, clear2, clear3, clear4, clear5, clear6, clear7, clear8, clear9};
		 
		 int cleary = 0;
		 for(JButton clear: clearbuttons){
		 addButtons(clear, 2, cleary);
		 cleary++;
		 }
		 
		
	}
	
	public void favouritePanelHF(){
		 Border border = BorderFactory.createLineBorder(Color.BLACK, 1);
		 favBio1 = new JLabel("1");
		 favBio2 = new JLabel("2");
		 favBio3 = new JLabel("3");
		 favBio4 = new JLabel("4");
		 favBio5 = new JLabel("5");
		 favBio6 = new JLabel("6");
		 favBio7 = new JLabel("7");
		 favBio8 = new JLabel("8");
		 favBio9 = new JLabel("9");
		 
		 JLabel[] favourites = {favBio1, favBio2, favBio3, favBio4, favBio5, favBio6, favBio7, favBio8, favBio9};
		 
		 for(JLabel pick: favourites){
			 pick.setPreferredSize(new Dimension(80, 70));
			 pick.setBorder(border); 
		 }
		 
		 GridBagLayout layout = new GridBagLayout();
			
		 hofPanel.setLayout(layout);
		 hofPanel.setBorder(border);
		 
		 int x = 0;
		 for(int i = 0; i<favourites.length; i++){
			addTiles(favourites[i], 0, x);
			x++;
		 }
		 
		
		
		 
	
	}
	
}


