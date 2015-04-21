package aston.group2.biomorph.GUI;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

import aston.group2.biomorph.Model.Biomorph;

public class BiomorphSurfaceWithTools extends JPanel {
	BiomorphSurface bS;
	
	public BiomorphSurfaceWithTools() {
		bS = new BiomorphSurface();

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
			header.add(magnifyGlassButton, BorderLayout.EAST);
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
        
        JButton saveButton = new JButton("Save");       
        header.add(saveButton, BorderLayout.WEST);
       
        saveButton.addActionListener(new ActionListener() {
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
                        br.write(BiomorphSurface.getBiomorph().toString());
                        br.close();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
        
        
	}
	
	public void setBiomorph(Biomorph biomorph)
	{
		bS.setBiomorph(biomorph);
	}
}
