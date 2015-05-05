package aston.group2.biomorph.GUI;

import aston.group2.biomorph.Model.Biomorph;
import aston.group2.biomorph.Storage.BiomorphHistoryLoader;
import aston.group2.biomorph.Utilities.Exporter;
import aston.group2.biomorph.Utilities.IconHelper;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;


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
        if (selectable) {
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

            });
        }
        ;
        this.selectable = selectable;

        biomorphSurface = new BiomorphSurface();

        this.setLayout(new BorderLayout());
        this.add(biomorphSurface, BorderLayout.CENTER);

        //add footer panel to the bottom of panel
        JPanel footer = new JPanel();
        footer.setLayout(new BorderLayout());
        add(footer, BorderLayout.SOUTH);

        JButton heartButton = IconHelper.makeButton("heart_add", "Add to Hall of Fame");
        footer.add(heartButton, BorderLayout.WEST);
        heartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean result = BiomorphHistoryLoader.hallOfFame.add(biomorphSurface.getBiomorph());
                if (!result) {
                    JOptionPane.showMessageDialog(BiomorphSurfaceWithTools.this, "Can't add to Hall of Fame because it is full", "Error adding to Hall of Fame", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        //add header to top of the panel
        JPanel header = new JPanel();
        header.setLayout(new BorderLayout());
        add(header, BorderLayout.NORTH);

        JButton magnifyGlassButton = IconHelper.makeButton("magnifier_zoom_in", "Zoom");
        header.add(magnifyGlassButton, BorderLayout.WEST);
        magnifyGlassButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                BiomorphPreview biomorphPreview = new BiomorphPreview(biomorphSurface.getBiomorph());
                biomorphPreview.setVisible(true);
            }
        });

	    JButton saveButton = IconHelper.makeButton("disk", "Save Biomorph");
        footer.add(saveButton, BorderLayout.EAST);
        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                final JDialog frame = new JDialog();
//                frame.setMinimumSize(new Dimension(250,150));
                frame.setResizable(false);
//                frame.setSize(220, 400);
//                frame.setLocation(400,300);

                frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.LINE_AXIS));

                JButton pngBiomorphs;
                JButton bioBiomorphs;

                pngBiomorphs = new JButton("Export As PNG");
                bioBiomorphs = new JButton("Export As Biomorph");

                frame.add(bioBiomorphs);
                frame.add(pngBiomorphs);
                bioBiomorphs.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent event) {
                        Exporter.exportBiomorph(BiomorphSurfaceWithTools.this.biomorphSurface.getBiomorph());
                        frame.dispose();
                    }
                });
                pngBiomorphs.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        Exporter.exportPNG(BiomorphSurfaceWithTools.this.biomorphSurface.getBiomorph());
                        frame.dispose();
                    }
                });

                frame.pack();
                frame.setVisible(true);
            }
        });

		if(selectable)
		{   //Add a checkbox
			checkbox = new JCheckBox();
			header.add(checkbox, BorderLayout.EAST);

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
