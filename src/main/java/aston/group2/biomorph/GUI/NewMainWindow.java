package aston.group2.biomorph.GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class NewMainWindow extends JFrame {

	
	private JLabel welcome;
	
	private JButton newBiomorphs;
	private JButton loadBiomorphs;
	
	private JPanel topPanel;
	private JPanel bottomPanel;
	
	// slider for number of biomorphs, increments in 2s
	private JSlider numOfBiomorphs;
	private JLabel numOfBiomorph;
	
	public NewMainWindow(){
		setMinimumSize(new Dimension(900, 800));
		
		
		welcome = new JLabel("Welcome");
		welcome.setFont (welcome.getFont ().deriveFont (40.0f));
		
		newBiomorphs = new JButton("New Biomorphs");
		loadBiomorphs = new JButton("Load Biomorphs");
		
		topPanel = new JPanel();
		topPanel.setLayout(new FlowLayout());
		bottomPanel = new JPanel();
		bottomPanel.setLayout(new FlowLayout());
		
		// Slider for number of biomorphs (increments in 2s)
		int initialValue = 2;
		numOfBiomorphs = new JSlider(JSlider.HORIZONTAL, 2, 10, initialValue);
		numOfBiomorphs.setValue(2);
		numOfBiomorphs.setMajorTickSpacing(2);
		numOfBiomorphs.setPaintTicks(true);
		// only values with ticks (marks) are selectable
		numOfBiomorphs.setSnapToTicks(true);
		
		// label for biomorph slider
		numOfBiomorph = new JLabel("Number of Biomorphs  " + initialValue);
		
		// number of biomorphs slider listener
		numOfBiomorphs.addChangeListener(new ChangeListener() {
		      public void stateChanged(ChangeEvent event) {
		       int numvalue = numOfBiomorphs.getValue();
		       numOfBiomorph.setText("Number of Biomorphs  " + numvalue);
		      }
		    });
		
		// passes value from slider into Gallery as a parameter
		loadBiomorphs.addActionListener(new ActionListener() {
			    public void actionPerformed(ActionEvent event) {
			    	Gallery gs = new Gallery(numOfBiomorphs.getValue());
			         gs.setVisible(true);
			     
			  }
		 });
		
		
		add(topPanel, BorderLayout.NORTH);
		add(bottomPanel, BorderLayout.SOUTH);
		
		
		topPanel.add(welcome);
		bottomPanel.add(newBiomorphs, BorderLayout.WEST);
		bottomPanel.add(loadBiomorphs, BorderLayout.EAST);
		bottomPanel.add(numOfBiomorph, BorderLayout.NORTH);
		bottomPanel.add(numOfBiomorphs);
		
		
		

	}
	
	 public static void main(String[] args)
	    {
	        SwingUtilities.invokeLater(new Runnable() {
	            @Override
	            public void run() {
	                JFrame nmw = new NewMainWindow();

	                nmw.setVisible(true);
	            }
	        });
	    }
	
	

}
