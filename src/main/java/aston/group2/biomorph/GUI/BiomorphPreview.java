package aston.group2.biomorph.GUI;

import aston.group2.biomorph.Model.Biomorph;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

/**
 * @author Antoine and Theo
 * This class handles the biomorph preview window
 */
public class BiomorphPreview extends JFrame {

	JPanel bottomPanel = new JPanel();
	JSlider zoomSlider = new JSlider();
	JLabel zoomSliderLabel = new JLabel();
	BiomorphSurface bs = new BiomorphSurface();
	
	/*
	 * Maximum length a biomorph can be scaled by
	 */
	private final int zoomSliderSteps = 2000;
	
	public BiomorphPreview(Biomorph biomorph) {

	/*
	 * Controls the look and display of the zoom window
	 */
		setMinimumSize(new Dimension(800, 600));
		setLayout(new BorderLayout());

		bs.setBiomorph(biomorph);

		{
			int initialValue = zoomSliderSteps / 2;
			zoomSlider = new JSlider(JSlider.HORIZONTAL, 0, zoomSliderSteps, initialValue);
			// zoomSlider.setMajorTickSpacing(2);
			// zoomSlider.setPaintTicks(true);
			// only values with ticks (marks) are selectable
			// zoomSlider.setSnapToTicks(true);
			zoomSlider.setBounds(300, 300, 300, 50);
			// label for zoom slider
			zoomSliderLabel = new JLabel();
			zoomSliderLabel
					.setFont(zoomSliderLabel.getFont().deriveFont(20.0f));
			zoomSliderLabel.setBounds(320, 250, 300, 50);

			int numvalue = 200*zoomSlider.getValue() / zoomSliderSteps;
			zoomSliderLabel.setText("Zoom " + numvalue + "%");
			
			// number of biomorphs slider listener
			zoomSlider.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent event) {
					int numvalue = 200*zoomSlider.getValue() / zoomSliderSteps;
					zoomSliderLabel.setText("Zoom " + numvalue + "%");
				}
			});

			// passes value from slider into BiomorphSurface
			zoomSlider.addChangeListener(new ZoomMouseListener());
			bottomPanel.add(zoomSlider, BorderLayout.CENTER);
			bottomPanel.add(zoomSliderLabel, BorderLayout.SOUTH);
			add(bs, BorderLayout.CENTER);
			add(bottomPanel, BorderLayout.SOUTH);
		}

	}

	class ZoomMouseListener implements ChangeListener {

		/*
		 * Takes in the result obtained from the slider and updates the size of the biomorph accordingly
		 */
		public void stateChanged(ChangeEvent e) {
			bs.setScaleFactor(((float)zoomSlider.getValue())*2/zoomSliderSteps);
		}
	
	}
}
