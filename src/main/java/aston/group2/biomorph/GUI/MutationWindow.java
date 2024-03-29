package aston.group2.biomorph.GUI;

import aston.group2.biomorph.Model.Biomorph;
import aston.group2.biomorph.Model.EvolutionHelper;
import aston.group2.biomorph.Model.Mutator;
import aston.group2.biomorph.Model.Species;
import aston.group2.biomorph.Storage.BiomorphHistoryLoader;
import aston.group2.biomorph.Storage.Generation;
import aston.group2.biomorph.Utilities.IconHelper;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * @author Antoine, Theo, Joshan, Joe and Alex
 * This is the core class of the program which is responsible for creating the main panels
 * for displaying biomorphs as well as the hall of fame.
 */
public class MutationWindow extends JFrame {
	JPanel biomorphGrid;
	Generation generation;
	Mutator newMutator;

	private JPanel hofPanel = null;

	private int rows = 2;
	private int cols = 3;

	public MutationWindow(int numberOfBiomorphs, boolean IFG) {
        this(numberOfBiomorphs, null, IFG);
	}

	public MutationWindow(int numberOfBiomorph){
		this(numberOfBiomorph, null);
	}

    public MutationWindow(int numberOfBiomorphs, Species species)
    {
        this(numberOfBiomorphs, species, false);
    }

	public MutationWindow(int numberOfBiomorphs, Species species, boolean IFG) {
		if(species != null)
		{
			generation = species.getLastestGeneration();
			newMutator = generation.mutator;
		}

		this.cols = numberOfBiomorphs / rows;

		setLayout(new BorderLayout());
		setTitle("Mutation Tester");
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		setMinimumSize(new Dimension(900, 800));

		biomorphGrid = new JPanel();
		biomorphGrid.setLayout(new GridLayout(rows, cols));
		biomorphGrid.setBorder(new EmptyBorder(10, 10, 10, 10));

		JPanel topOfPage = new JPanel();
		topOfPage.setLayout(new BorderLayout());

		if(species == null) {
			initialiseBiomorph(numberOfBiomorphs, IFG);
		}
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

			buttonPanel.add(refresh = IconHelper.makeButton("table_refresh", "Refresh"));
			buttonPanel.add(undo = IconHelper.makeButton("arrow_left", "Undo"));
			buttonPanel.add(settings = IconHelper.makeButton("table_gear", "Settings"));

			settings.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
                    if(newMutator == null)
                    {
                        newMutator = generation.mutator;
                    }

                    JDialog settingsF = new MutationSettings(MutationWindow.this, newMutator);
                    settingsF.setVisible(true);
				}
			});

            undo.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    undoMutation();
                }
            });

            refresh.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    regenerateBiomorphs();
                }
            });
		}

		addWindowListener(new WindowListener() {

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

			@Override
			public void windowActivated(java.awt.event.WindowEvent e) {
			}

			@Override
			public void windowClosed(java.awt.event.WindowEvent e) {
			}

			@Override
			public void windowDeactivated(java.awt.event.WindowEvent e) {
			}

			@Override
			public void windowDeiconified(java.awt.event.WindowEvent e) {
			}

			@Override
			public void windowIconified(java.awt.event.WindowEvent e) {
			}

			@Override
			public void windowOpened(java.awt.event.WindowEvent e) {
			}

		});

		{
			JButton mutateButton = new JButton("Mutate");
			mutateButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {

					boolean selected = false;

					Component[] components = biomorphGrid.getComponents();

					for (Component c : components) {
						if (c instanceof BiomorphSurfaceWithTools) {
							BiomorphSurfaceWithTools bS = (BiomorphSurfaceWithTools) c;

							if (bS.selected()) {
								selected = true;
								break; // we only swap the first one anyway
							}
						}
					}

					if (selected) {
						mutateBiomorphs();
						refreshGrid();
					}
				}
			});

			footer.add(mutateButton, BorderLayout.CENTER);
		}

		add(biomorphGrid, BorderLayout.CENTER);
		add(hofPanel, BorderLayout.EAST);
		add(topOfPage, BorderLayout.NORTH);
	}

	private void swapClick(int slot) {
		BiomorphSurfaceWithTools selected = null;

		Component[] components = biomorphGrid.getComponents();

		for (Component c : components) {
			if (c instanceof BiomorphSurfaceWithTools) {
				BiomorphSurfaceWithTools bS = (BiomorphSurfaceWithTools) c;

				if (bS.selected()) {
					selected = bS;
					break; // we only swap the first one anyway
				}
			}
		}

		if (selected != null) {
			Biomorph biomorph = BiomorphHistoryLoader.hallOfFame.get(slot);

			if (biomorph != null) {
				Biomorph oldSelectedBM = selected.biomorphSurface.getBiomorph();

                biomorph.generation = generation;

                for(int i=0; i<generation.children.length; i++) {
                    if(generation.children[i] == oldSelectedBM)
                    {
                        generation.children[i] = biomorph;
                        break;
                    }
                }

				selected.setBiomorph(biomorph);

				BiomorphHistoryLoader.hallOfFame.set(oldSelectedBM, slot);
			} else {
				BiomorphHistoryLoader.hallOfFame.set(selected.biomorphSurface.getBiomorph(), slot);
			}
		}
	}

    private void initialiseBiomorph() {
        initialiseBiomorph(0, false);
    }

	private void initialiseBiomorph(int childrenRequired, boolean IFG) {
        if(childrenRequired <= 0)
        {
            childrenRequired = rows * cols;
        }

        Mutator mutator = new Mutator(Calendar.getInstance().getTimeInMillis());

        mutator.setting("required_children").value = childrenRequired;

        generation = EvolutionHelper.generateSpecies(mutator, IFG)
                .getLastestGeneration();

    }

    private void mutateBiomorphs() {
        ArrayList<Biomorph> selected = new ArrayList<>(rows * cols);

        Component[] components = biomorphGrid.getComponents();

        for (Component c : components) {
            if (c instanceof BiomorphSurfaceWithTools) {
                BiomorphSurfaceWithTools bS = (BiomorphSurfaceWithTools) c;

                if (bS.selected()) {
                    selected.add(bS.biomorphSurface.getBiomorph());
                }
            }
        }

        Biomorph[] bma = selected.toArray(new Biomorph[selected.size()]);

        if (bma.length > 0) {
            Mutator mutator;
            if(newMutator != null)
            {
                mutator = newMutator;
            }
            else
            {
                mutator = generation.mutator;
            }

            generation = EvolutionHelper.mutate(bma, mutator);

            newMutator = null;
        }
    }

    private void regenerateBiomorphs() {
        Biomorph[] bma = generation.parents;
        if(bma == null || bma.length == 0)
        {
            return;
        }

        Mutator mutator;

        if(newMutator != null)
        {
			if(generation.mutator.setting("seed").value.equals(newMutator.setting("seed").value))
			{
                newMutator.setting("seed").value = String.valueOf(System.currentTimeMillis());
			}
            mutator = newMutator;
        }
        else
        {
            mutator = generation.mutator;
            mutator.setting("seed").value = String.valueOf(System.currentTimeMillis());
        }

        generation = EvolutionHelper.mutate(bma, mutator);

        newMutator = null;

        refreshGrid();
    }

    private void undoMutation()
    {
        if(generation.prevGeneration != null) {
            generation = generation.prevGeneration;

            newMutator = null;

            refreshGrid();
        }
    }

	private void refreshGrid() {
		biomorphGrid.removeAll();

		for (int i = 0; i < generation.children.length; i++) {
			BiomorphSurfaceWithTools bs = new BiomorphSurfaceWithTools(true);
			bs.setBiomorph(generation.children[i]);
			biomorphGrid.add(bs);
		}

		biomorphGrid.revalidate();
        biomorphGrid.repaint();
	}

	private JComponent createHallOfFameSlot(int i) {
		JComponent biomorph = (BiomorphHistoryLoader.hallOfFame.get(i) != null ? new BiomorphSurface(
				BiomorphHistoryLoader.hallOfFame.get(i)) : new JLabel(
				"None"));

		return biomorph;
	}

	private void createHallOfFamePanel() {
		if (hofPanel == null) {
			hofPanel = new JPanel();
			hofPanel.setLayout(new GridBagLayout());
			hofPanel.setPreferredSize(new Dimension(150, 0));
			hofPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 10));

            BiomorphHistoryLoader.hallOfFame.addUpdateListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    createHallOfFamePanel();
                    hofPanel.revalidate();
                    hofPanel.repaint();
                }
            });
		} else {
			hofPanel.removeAll();
		}

		for (int i = 0; i < BiomorphHistoryLoader.hallOfFame.maxSize(); i++) {
			final JPanel biomorphHolder = new JPanel();
			biomorphHolder.setLayout(new BorderLayout());
			biomorphHolder.add(createHallOfFameSlot(i));

			// biomorph.setPreferredSize(new Dimension(80, 60));
			biomorphHolder.setBorder(BorderFactory.createLineBorder(
					Color.BLACK, 1));

			JButton swap = IconHelper.makeButton("arrow_switch", "Swap");
			JButton clear = IconHelper.makeButton("cancel", "Clear");

			swap.addActionListener(new ActionListener() {
				private int slot;

				@Override
				public void actionPerformed(ActionEvent e) {
					swapClick(slot);
					biomorphHolder.removeAll();
					biomorphHolder.add(createHallOfFameSlot(slot));
					biomorphHolder.revalidate();
				}

				public ActionListener init(int slot) {
					this.slot = slot;
					return this;
				}
			}.init(i));
			
			clear.addActionListener(new ActionListener() {
				private int slot;

				@Override
				public void actionPerformed(ActionEvent e) {
					BiomorphHistoryLoader.hallOfFame.remove(slot);
					biomorphHolder.removeAll();
					biomorphHolder.add(createHallOfFameSlot(slot));
					biomorphHolder.revalidate();
				}

				public ActionListener init(int slot) {
					this.slot = slot;
					return this;
				}
			}.init(i));
			
			{
				Dimension d = new Dimension(22, 22);
				swap.setPreferredSize(d);
				clear.setPreferredSize(d);
			}

			GridBagConstraints c = new GridBagConstraints();

			Insets is = new Insets(5, 10, 0, 0);

			c.insets = is;

			{
				c.fill = GridBagConstraints.BOTH;

				c.gridx = 0;
				c.gridy = 2 * i;
				c.gridheight = 2;
				c.weightx = 1;

				hofPanel.add(biomorphHolder, c);
			}

			{
				c.fill = GridBagConstraints.NONE;

				c.gridx = 1;
				c.gridy = 2 * i;
				c.gridheight = 1;
				c.weightx = 0;
				c.weighty = 0.5;

                c.anchor = GridBagConstraints.CENTER;

				hofPanel.add(swap, c);

				c.gridy = 2 * i + 1;
				hofPanel.add(clear, c);
			}
		}
	}
}
