package aston.group2.biomorph.GUI;

import aston.group2.biomorph.Model.Biomorph;
import aston.group2.biomorph.Model.EvolutionHelper;
import aston.group2.biomorph.Model.Mutator;
import aston.group2.biomorph.Model.Species;
import aston.group2.biomorph.Storage.BiomorphHistoryLoader;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.List;
/**
 *  @author Joshan, Alex, Joe and Theo
 * Responsible for creating the evolutionary art grid and whether or not the first generation
 * will be take into account intelligent first generation.
 */
public class NewMainWindow extends JFrame {
	private JCheckBox intelligentGen;
	private boolean isIntelligentFirst;
	
	public NewMainWindow(){
		initHistory();
		
		setMinimumSize(new Dimension(900, 500));
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(null);

		JLabel welcome = new JLabel("Welcome");
		welcome.setFont (welcome.getFont ().deriveFont (40.0f));
		welcome.setBounds(370, 80, 200, 100);
	
		
		JButton newBiomorphs = new JButton("New Biomorph");
		JButton loadBiomorphs = new JButton("Load Biomorph");
		
		newBiomorphs.setBounds(300, 400, 120, 40);
		loadBiomorphs.setBounds(450, 400, 120, 40);
		
		// Slider for number of biomorphs (increments in 2s)
		int initialValue = 6;
		final JSlider numOfBiomorphs = new JSlider(JSlider.HORIZONTAL, 2, 10, initialValue);
		numOfBiomorphs.setValue(6);
		numOfBiomorphs.setMajorTickSpacing(2);
		numOfBiomorphs.setPaintTicks(true);
		// only values with ticks (marks) are selectable
		numOfBiomorphs.setSnapToTicks(true);
		numOfBiomorphs.setBounds(300, 300, 300, 50);
		// label for biomorph slider
		final JLabel numOfBiomorph = new JLabel("Number of Biomorphs " + initialValue);
		numOfBiomorph.setFont(welcome.getFont().deriveFont(20.0f));
		numOfBiomorph.setBounds(320, 250, 300, 50);
		
		intelligentGen = new JCheckBox("Intelligent First Generation");
		intelligentGen.setFont(intelligentGen.getFont().deriveFont(15.0f));
		intelligentGen.setBounds(630, 370, 300, 100);

		isIntelligentFirst = intelligentGen.isSelected();
		
		intelligentGen.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent arg0) {
				// TODO Auto-generated method stub
				isIntelligentFirst = intelligentGen.isSelected();
			}


		});
		
		// number of biomorphs slider listener
		numOfBiomorphs.addChangeListener(new ChangeListener() {
		      public void stateChanged(ChangeEvent event) {
		       int numvalue = numOfBiomorphs.getValue();
		       numOfBiomorph.setText("Number of Biomorphs  " + numvalue);
		      }
		    });
		
		// passes value from slider into MutationWindow as a parameter
		newBiomorphs.addActionListener(new ActionListener() {
			    public void actionPerformed(ActionEvent event) {
			    	MutationWindow mw = new MutationWindow(numOfBiomorphs.getValue(), isIntelligentFirst);
			         mw.setVisible(true);


			         NewMainWindow.this.dispose();
			  }
		 });
		
		loadBiomorphs.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser jf = new JFileChooser();
                jf.setFileFilter(new FileNameExtensionFilter("Biomorph file","biomorph"));

                int rv = jf.showOpenDialog(getParent());

                if(rv == JFileChooser.APPROVE_OPTION)
                {
                    File file = jf.getSelectedFile();

                    try {
						BufferedReader br = Files.newBufferedReader(file.toPath(), Charset.forName("US-ASCII"));

						StringBuilder sb = new StringBuilder();

						String line;
						while ((line = br.readLine()) != null) {
//                            if(!line.startsWith("#"))
//                            {
							sb.append(line);
//                            }
						}

						//  genomeField.setText(sb.toString());
						Biomorph bm = Biomorph.deserialise(sb.toString());

						Mutator mutator = new Mutator(System.currentTimeMillis() / 1000);
						mutator.setting("required_children").value = numOfBiomorphs.getValue();

						Biomorph[] bma = {bm};

						Species loadedSpecies = EvolutionHelper.generateSpecies(mutator, bma);
						MutationWindow mw = new MutationWindow(numOfBiomorphs.getValue(), loadedSpecies);
						mw.setVisible(true);
						NewMainWindow.this.dispose();

					} catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }

            }
        });

		add(intelligentGen);
		add(welcome);
		add(newBiomorphs);
		add(loadBiomorphs);
		add(numOfBiomorph);
		add(numOfBiomorphs);
	}

    private void initHistory() {
        if(!BiomorphHistoryLoader.load())
        {
            int result = JOptionPane.showConfirmDialog(this,
                    String.format("Biomorph History file not found or inaccessible. Create a new one?%n" +
                                  "Note: This will delete any existing biomorph history files"),
                    "Biomorph History not found",
                    JOptionPane.OK_CANCEL_OPTION);

            if(result == JOptionPane.OK_OPTION)
            {
                BiomorphHistoryLoader.create();
            }
            else {
                System.exit(0);
            }
        }

        {
            List<Species> species = BiomorphHistoryLoader.biomorphHistory.get();
            for(int i=0; i<species.size(); i++)
            {
                System.out.println(String.format("Species %d",i));
                species.get(i).printTree();
            }
        }
    }

	public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                NewMainWindow nmw = new NewMainWindow();

                nmw.setVisible(true);

                nmw.setResizable(false);

            }
        });
    }



}
