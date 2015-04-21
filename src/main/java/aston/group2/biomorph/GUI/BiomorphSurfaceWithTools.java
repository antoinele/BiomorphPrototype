package aston.group2.biomorph.GUI;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

import aston.group2.biomorph.Model.Biomorph;

public class BiomorphSurfaceWithTools extends JPanel {
	public final BiomorphSurface biomorphSurface;

    private JCheckBox checkbox;

    private boolean selectable = false;

    public BiomorphSurfaceWithTools()
    {
        this(false);
    }

	public BiomorphSurfaceWithTools(boolean selectable) {
        this.selectable = selectable;

		biomorphSurface = new BiomorphSurface();

		this.setLayout(new BorderLayout());
		this.add(biomorphSurface, BorderLayout.CENTER);
		
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
					BiomorphPreview biomorphPreview= new BiomorphPreview(biomorphSurface.getBiomorph());
					biomorphPreview.setVisible(true);
					
				}
				 
				
			});
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        if(selectable)
        {   //Add a checkbox
            checkbox = new JCheckBox();
            header.add(checkbox, BorderLayout.EAST);
        }
	}
	
	public void setBiomorph(Biomorph biomorph)
	{
		biomorphSurface.setBiomorph(biomorph);
	}

    public boolean selected()
    {
        return selectable && checkbox.isSelected();
    }
}
