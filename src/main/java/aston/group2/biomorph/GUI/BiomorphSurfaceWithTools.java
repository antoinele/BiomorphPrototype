package aston.group2.biomorph.GUI;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import aston.group2.biomorph.Model.Biomorph;

public class BiomorphSurfaceWithTools extends JPanel {
	public BiomorphSurfaceWithTools() {
		BiomorphSurface bS = new BiomorphSurface();
		bS.setBiomorph(new Biomorph("D21F00CSLBEEF00SMCAFEsL123456LFF12F0SLF24300s"));
		
		
		this.setLayout(new BorderLayout());
		this.add(bS, BorderLayout.CENTER);
		
		//add footer panel to the bottom of panel
		JPanel footer = new JPanel();
        footer.setLayout(new BorderLayout());
        add(footer, BorderLayout.SOUTH);
        
        BufferedImage heartIcon;
		try {
			heartIcon = ImageIO.read(new File("resources/icons/heart_add.png"));
			JButton heartButton = new JButton(new ImageIcon(heartIcon));
			heartButton.setToolTipText("Add to Hall of Fame");
			footer.add(heartButton, BorderLayout.EAST);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//add header to top of the panel
		JPanel header = new JPanel();
		header.setLayout(new BorderLayout());
        add(header, BorderLayout.NORTH);
        
        BufferedImage magnifyGlassIcon;
        try {
			magnifyGlassIcon = ImageIO.read(new File("resources/icons/magnifier_zoom_in.png"));
			JButton magnifyGlassButton = new JButton(new ImageIcon(magnifyGlassIcon));
	        magnifyGlassButton.setToolTipText("Zoom");
			header.add(magnifyGlassButton, BorderLayout.WEST);
			magnifyGlassButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					BiomorphPreview biomorphPreview= new BiomorphPreview(bS.getBiomorph());
					biomorphPreview.setVisible(true);
					
				}
				 
				
			});
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       
        
	}
}
