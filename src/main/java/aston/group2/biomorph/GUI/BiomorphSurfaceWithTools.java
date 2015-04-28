package aston.group2.biomorph.GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import aston.group2.biomorph.Model.Biomorph;

public class BiomorphSurfaceWithTools extends JPanel {
	public final BiomorphSurface biomorphSurface;

	private JCheckBox checkbox;
	private boolean selectable = false;
	private boolean selected = false;

	public BiomorphSurfaceWithTools()
	{
		this(false);

	}

	public BiomorphSurfaceWithTools(boolean selectable) {
		if(selectable){
		addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent arg0) {
				selected = !checkbox.isSelected();
				checkbox.setSelected(selected);
				impressBiomorphWindow();
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});};
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
			footer.add(heartButton, BorderLayout.WEST);
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

		BufferedImage saveButtonIcon;
		try {
			saveButtonIcon = ImageIO.read(new File("resources/icons/disk.png"));
			JButton saveButton = new JButton(new ImageIcon(saveButtonIcon));
			saveButton.setToolTipText("Save Biomorph");      
			footer.add(saveButton, BorderLayout.EAST);
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
							br.write(biomorphSurface.getBiomorph().toString());
							br.close();
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}
				}
			});
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if(selectable)
		{   //Add a checkbox
			checkbox = new JCheckBox();
			checkbox.addItemListener(new ItemListener(){

				@Override
				public void itemStateChanged(ItemEvent e) {
					// TODO Auto-generated method stub
					selected = checkbox.isSelected();
					impressBiomorphWindow();
				}
				
			});
		    header.add(checkbox, BorderLayout.EAST);
		    
		}
		
		impressBiomorphWindow();
	}
	public void impressBiomorphWindow(){
		if(selected){
			Border border = BorderFactory.createLineBorder(new Color(104, 175, 232), 5);
			setBorder(border);
		}else{
			setBorder(new EmptyBorder(5, 5, 5, 5) );
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