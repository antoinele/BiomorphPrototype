package aston.group2.biomorph.GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import aston.group2.biomorph.Model.Biomorph;
import aston.group2.biomorph.Model.Species;
import aston.group2.biomorph.Storage.BiomorphHistory;
import aston.group2.biomorph.Storage.BiomorphHistoryLoader;

public class NewMainWindow extends JFrame {

	private JLabel welcome;
	
	private JButton newBiomorphs;
	private JButton loadBiomorphs;

	// slider for number of biomorphs, increments in 2s
	private JSlider numOfBiomorphs;
	private JLabel numOfBiomorph;
	
	public NewMainWindow(){
		initHistory();
		
		setMinimumSize(new Dimension(900, 500));

        setLayout(null);

		welcome = new JLabel("Welcome");
		welcome.setFont (welcome.getFont ().deriveFont (40.0f));
		welcome.setBounds(370, 80, 200, 100);
	
		
		newBiomorphs = new JButton("New Biomorph");
		loadBiomorphs = new JButton("Load Biomorph");
		
		newBiomorphs.setBounds(300, 400, 120, 40);
		loadBiomorphs.setBounds(450, 400, 120, 40);
		
		// Slider for number of biomorphs (increments in 2s)
		int initialValue = 2;
		numOfBiomorphs = new JSlider(JSlider.HORIZONTAL, 2, 10, initialValue);
		numOfBiomorphs.setValue(2);
		numOfBiomorphs.setMajorTickSpacing(2);
		numOfBiomorphs.setPaintTicks(true);
		// only values with ticks (marks) are selectable
		numOfBiomorphs.setSnapToTicks(true);
		numOfBiomorphs.setBounds(300, 300, 300, 50);
		// label for biomorph slider
		numOfBiomorph = new JLabel("Number of Biomorphs  " + initialValue);
		numOfBiomorph.setFont (welcome.getFont ().deriveFont (20.0f));
		numOfBiomorph.setBounds(320, 250, 300, 50);
		
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
			    	MutationWindow mw = new MutationWindow(numOfBiomorphs.getValue());
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

            }
        });

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
