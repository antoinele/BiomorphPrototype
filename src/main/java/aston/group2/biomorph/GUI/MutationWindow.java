package aston.group2.biomorph.GUI;

import aston.group2.biomorph.Model.Biomorph;
import aston.group2.biomorph.Model.EvolutionHelper;
import aston.group2.biomorph.Model.Mutator;
import aston.group2.biomorph.Storage.BiomorphHistoryLoader;
import aston.group2.biomorph.Storage.Generation;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by antoine on 17/04/15.
 */
public class MutationWindow extends JFrame {
	JPanel biomorphGrid;
	Generation generation;
	Mutator mutator;
	private JPanel hofPanel;
	private JPanel topOfPage;
	private JFrame frame;
	private JPanel mainPanel;

	private JSlider numberOfBios;
	private JSlider slider1;
	private JSlider slider2;
	private JLabel numOfBios;
	private JLabel slider1l;
	private JLabel slider2l;

	private int rows = 2;
	private int cols = 3;

    private JButton makeButton(String icon, String tooltip)
    {
        try {
            BufferedImage iconImg = ImageIO.read(new File("resources/icons/" + icon + ".png"));
            JButton button = new JButton(new ImageIcon(iconImg));
            button.setToolTipText(tooltip);
            return button;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }


	public MutationWindow(int numberOfBiomorphs) {
		this.cols = numberOfBiomorphs / rows;

		setLayout(new BorderLayout());
		setTitle("Mutation Tester");
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		setMinimumSize(new Dimension(900, 800));

		biomorphGrid = new JPanel();
		biomorphGrid.setLayout(new GridLayout(rows, cols));
		biomorphGrid.setBorder(new EmptyBorder(10, 10, 10, 10));

		topOfPage = new JPanel();
		topOfPage.setLayout(new BorderLayout());

		initialiseBiomorph();
		createHallOfFamePanel();
		refreshGrid();
		
		JPanel header = new JPanel();
		header.setLayout(new BorderLayout());
		add(header, BorderLayout.NORTH);
		
		JPanel footer = new JPanel();
		Border border = BorderFactory.createLineBorder(Color.BLACK, 1); 
		footer.setLayout(new BorderLayout());
		footer.setBorder(border);
		add(footer, BorderLayout.SOUTH);

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS));
		footer.add(buttonPanel, BorderLayout.EAST);
		
		{
			JButton refresh, undo, settings;
			
			buttonPanel.add(refresh = makeButton("table_refresh", "Refresh"));
			buttonPanel.add(undo = makeButton("arrow_left", "Undo"));
			buttonPanel.add(settings = makeButton("table_gear", "Settings"));
			
			settings.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					frame = new JFrame("Settings");
					mainPanel = new JPanel();
					
					
					
					frame.pack();
					frame.setVisible(true);
					frame.setMinimumSize(new Dimension(380, 340));
					frame.setResizable(false);
					
					mainPanel.setLayout(null);
					
					
					frame.add(mainPanel, BorderLayout.CENTER);
					
					
					numOfBios = new JLabel("Number of Biomorphs " + 2);
					numberOfBios = new JSlider(JSlider.HORIZONTAL, 2, 10, 2);
					numberOfBios.setValue(2);
					numberOfBios.setMajorTickSpacing(2);
					numberOfBios.setPaintTicks(true);
					numberOfBios.setSnapToTicks(true);
					
					//mainPanel.setBounds(x, y, width, height);
					numberOfBios.setBounds(80, 70, 200, 20);
					numOfBios.setBounds(105, 50, 500, 20);
					mainPanel.add(numberOfBios);
					mainPanel.add(numOfBios);
					
					
					slider1l = new JLabel("Slider 1");
					slider1 = new JSlider(JSlider.HORIZONTAL, 2, 10, 2);
					slider1.setValue(1);
					slider1.setMajorTickSpacing(2);
					slider1.setPaintTicks(true);
					
					//mainPanel.setBounds(x, y, width, height);
					slider1l.setBounds(150, 100, 500, 20);
					slider1.setBounds(80, 120, 200, 20);
					mainPanel.add(slider1l);
					mainPanel.add(slider1);
					
					slider2l = new JLabel("Slider 2");
					slider2 = new JSlider(JSlider.HORIZONTAL, 2, 10, 2);
					slider2.setValue(1);
					slider2.setMajorTickSpacing(2);
					slider2.setPaintTicks(true);
					
					//mainPanel.setBounds(x, y, width, height);
					slider2l.setBounds(150, 150, 500, 20);
					slider2.setBounds(80, 170, 200, 20);
					mainPanel.add(slider2l);
					mainPanel.add(slider2);
					
					
					numberOfBios.addChangeListener(new ChangeListener() {
					      public void stateChanged(ChangeEvent event) {
					       int numvalue = numberOfBios.getValue();
					       numOfBios.setText("Number of Biomorphs " + numvalue);
					      }
					    });
					
					
					JButton ok = new JButton("OK");
					ok.setBounds(150, 250, 80, 20);
					mainPanel.add(ok);
					
					 ok.addActionListener(new ActionListener() {
			             @Override
			             public void actionPerformed(ActionEvent e) {
			             frame.dispose();
			             }
			         });
					
					
					
				}
			});
		}
		
        addWindowListener(new WindowListener(){

            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                int confirm = JOptionPane.showConfirmDialog(e.getWindow(),
                        "Are you sure you want to exit?", "Confirm",
                        JOptionPane.OK_CANCEL_OPTION);

                if (confirm == JOptionPane.OK_OPTION) {
                    BiomorphHistoryLoader.save();
                    e.getWindow().dispose();
                }
            }

            @Override public void windowActivated(java.awt.event.WindowEvent e) {}
            @Override public void windowClosed(java.awt.event.WindowEvent e) {}
            @Override public void windowDeactivated(java.awt.event.WindowEvent e) {}
            @Override public void windowDeiconified(java.awt.event.WindowEvent e) {}
            @Override public void windowIconified(java.awt.event.WindowEvent e) {}
            @Override public void windowOpened(java.awt.event.WindowEvent e) {}

        });

		{
			JButton mutateButton = new JButton("Mutate");
			mutateButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					initialiseBiomorph();

					refreshGrid();
				}
			});

			footer.add(mutateButton, BorderLayout.CENTER);
		}

        add(biomorphGrid, BorderLayout.CENTER);
        add(hofPanel, BorderLayout.EAST);
        add(topOfPage, BorderLayout.NORTH);
	}

    private void initialiseBiomorph()
    {
        if(generation == null)
        {
            mutator = new Mutator();
            mutator.childrenRequired = rows * cols;

            generation = EvolutionHelper.generateSpecies(mutator).getLastestGeneration();
        }
        else
        {
            Biomorph[] bma;

            ArrayList<Biomorph> selected = new ArrayList<>(rows * cols);

            Component[] components = biomorphGrid.getComponents();

            for(Component c : components)
            {
                if(c instanceof BiomorphSurfaceWithTools)
                {
                    BiomorphSurfaceWithTools bS = (BiomorphSurfaceWithTools)c;

                    if(bS.selected())
                    {
                        selected.add(bS.biomorphSurface.getBiomorph());
                    }
                }
            }

            bma = selected.toArray(new Biomorph[selected.size()]);

            if(bma.length > 0) {
                System.out.println(String.format("Mutating %d biomorphs", bma.length));

//                generation = mutator.mutateBiomorph(bma);
                generation = EvolutionHelper.mutate(bma, mutator);

//                generation.species.printTree();
            }
        }
    }

	private void refreshGrid() {
		biomorphGrid.removeAll();

		for (int i = 0; i < Math.min(generation.children.length, rows * cols); i++) {
			BiomorphSurfaceWithTools bs = new BiomorphSurfaceWithTools(true);
			bs.setBiomorph(generation.children[i]);
			biomorphGrid.add(bs);
		}

		biomorphGrid.revalidate();
	}

	public void createHallOfFamePanel() {
        if(hofPanel == null) {
            hofPanel = new JPanel();
            hofPanel.setLayout(new GridBagLayout());
            hofPanel.setSize(200,0);
            hofPanel.setBorder(new EmptyBorder(10,10,10,10));
        }
        else {
            hofPanel.removeAll();
        }

        for(int i=0; i<BiomorphHistoryLoader.hallOfFame.hallOfFame.length; i++)
        {
            JComponent biomorph = (BiomorphHistoryLoader.hallOfFame.hallOfFame[i] != null ?
                                        new BiomorphSurface(BiomorphHistoryLoader.hallOfFame.hallOfFame[i]) :
                                        new JLabel("None") );

            biomorph.setPreferredSize(new Dimension(80, 60));
            biomorph.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

            JButton swap = makeButton("arrow_switch", "Swap");
            JButton clear = makeButton("cancel", "Clear");

            GridBagConstraints c = new GridBagConstraints();

            c.fill = GridBagConstraints.HORIZONTAL | GridBagConstraints.VERTICAL;

            {
                c.gridx = 0;
                c.gridy = 2*i;
                c.gridheight = 2;
                hofPanel.add(biomorph, c);
            }

            {
                c.gridx = 1;
                c.gridy = 2*i;
                c.gridheight = 1;
                c.weighty = 0.5;

                hofPanel.add(swap, c);

                c.gridy = 2*i + 1;
                hofPanel.add(clear, c);
            }
        }
	}	
}
