package aston.group2.biomorph.GUI;

import aston.group2.biomorph.Model.Biomorph;
import aston.group2.biomorph.Model.Mutator;
import aston.group2.biomorph.Model.Species;
import aston.group2.biomorph.Storage.Generation;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

//import com.sun.glass.events.WindowEvent;

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

	// hall of fame biomorphs
	private JLabel favBio1;
	private JLabel favBio2;
	private JLabel favBio3;
	private JLabel favBio4;
	private JLabel favBio5;
	private JLabel favBio6;
	private JLabel favBio7;
	private JLabel favBio8;
	private JLabel favBio9;

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

		hofPanel = new JPanel();

		add(biomorphGrid, BorderLayout.CENTER);
		add(hofPanel, BorderLayout.EAST);
		add(topOfPage, BorderLayout.NORTH);

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
	}

	private void initialiseBiomorph() {
		Biomorph[] bma;

		if (generation == null) {
			mutator = new Mutator();
			mutator.childrenRequired = rows * cols;

			generation = new Generation(mutator);

			new Species(generation);

			bma = new Biomorph[] { new Biomorph(
					"D21F00CSLBEEF00SMCAFEsL123456LFF12F0SLF24300s") };
		} else {
			ArrayList<Biomorph> selected = new ArrayList<Biomorph>(rows * cols);

			Component[] components = biomorphGrid.getComponents();

			for (Component c : components) {
				if (c instanceof BiomorphSurfaceWithTools) {
					BiomorphSurfaceWithTools bS = (BiomorphSurfaceWithTools) c;
					if (bS.selected()) {
						selected.add(bS.biomorphSurface.getBiomorph());
					}
				}
			}

			bma = selected.toArray(new Biomorph[selected.size()]);
		}

		if (bma.length > 0) {
			System.out.println(String.format("Mutating %d biomorphs",
					bma.length));

			generation = mutator.mutateBiomorph(bma);
		}
	}

	private void refreshGrid() {
		Border border = BorderFactory.createLineBorder(Color.BLACK, 1);

		biomorphGrid.removeAll();

		for (int i = 0; i < Math.min(generation.children.length, rows * cols); i++) {
			BiomorphSurfaceWithTools bs = new BiomorphSurfaceWithTools(true);
			bs.setBiomorph(generation.children[i]);
			bs.setBorder(border);
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
		favouritePanelHF();

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

		JButton[] swaps = { swap1, swap2, swap3, swap4, swap5, swap6, swap7,
				swap8, swap9 };

		int y = 0;
		for (JButton swap : swaps) {
			addButtons(swap, 1, y);
			y++;
		}

		JButton[] clearbuttons = { clear1, clear2, clear3, clear4, clear5,
				clear6, clear7, clear8, clear9 };

		int cleary = 0;
		for (JButton clear : clearbuttons) {
			addButtons(clear, 2, cleary);
			cleary++;
		}

	}

	public void favouritePanelHF() {
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

		JLabel[] favourites = { favBio1, favBio2, favBio3, favBio4, favBio5,
				favBio6, favBio7, favBio8, favBio9 };

		for (JLabel pick : favourites) {
			pick.setPreferredSize(new Dimension(80, 70));
			pick.setBorder(border);
		}

		GridBagLayout layout = new GridBagLayout();

		hofPanel.setLayout(layout);
		hofPanel.setSize(200, -1);
		hofPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		int x = 0;
		for (int i = 0; i < favourites.length; i++) {
			addTiles(favourites[i], 0, x);
			x++;
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
