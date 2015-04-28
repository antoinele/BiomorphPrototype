package aston.group2.biomorph.GUI;

import aston.group2.biomorph.Model.Biomorph;
import aston.group2.biomorph.Model.EvolutionHelper;
import aston.group2.biomorph.Model.Mutator;
import aston.group2.biomorph.Model.Species;
import aston.group2.biomorph.Storage.Generation;
import aston.group2.biomorph.Storage.HallOfFame;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import com.sun.glass.events.WindowEvent;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowListener;
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

    private final int hofEntries = 10;

	private int rows = 2;
	private int cols = 3;
	

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

		{
			addWindowListener(new WindowListener(){

				@Override
				public void windowClosing(java.awt.event.WindowEvent e) {
					int confirm = JOptionPane.showConfirmDialog(e.getWindow(),
							"Are you sure you want to exit?", "Confirm",
							JOptionPane.OK_CANCEL_OPTION);

					if (confirm == JOptionPane.OK_OPTION) {
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
		}

		{
			JButton mutateButton = new JButton("Mutate");
			mutateButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					initialiseBiomorph();

					refreshGrid();
				}
			});

			add(mutateButton, BorderLayout.SOUTH);
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

            ArrayList<Biomorph> selected = new ArrayList<Biomorph>(rows * cols);

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

	// for hall of fame
	public void addTiles(JLabel l, int gridx, int gridy) {
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = gridx;
		c.gridy = gridy;
		hofPanel.add(l, c);
	}

	// for hall of fame
	public void addButtons(JButton button, int gridx, int gridy) {
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = gridx;
		c.gridy = gridy;

		hofPanel.add(button, c);
	}

	public void createHallOfFamePanel() {
        if(hofPanel == null) {
            hofPanel = new JPanel();
            hofPanel.setLayout(new GridLayout(0, 2));
            hofPanel.setSize(200,0);
            hofPanel.setBorder(new EmptyBorder(10,10,10,10));
        }
        else {
            hofPanel.removeAll();
        }

        for(int i=0; i<hofEntries; i++)
        {
            JComponent biomorph = new JLabel(String.format("BM:%d", i));

            {
                biomorph.setPreferredSize(new Dimension(80, 60));
                biomorph.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
            }

            JPanel buttonPanel = new JPanel();
            buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.PAGE_AXIS));

            JButton swap = new JButton("Swap");
            JButton clear = new JButton("Clear");

            buttonPanel.add(swap);
            buttonPanel.add(clear);

            hofPanel.add(biomorph);
            hofPanel.add(buttonPanel);
        }
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				JFrame mw = new MutationWindow(4);

				mw.setVisible(true);
			}
		});
	}
}
