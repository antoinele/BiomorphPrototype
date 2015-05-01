package aston.group2.biomorph.GUI;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by antoine on 01/05/15.
 */
public class MutationSettings extends JFrame {
    private JPanel mainPanel;

    private JSlider numberOfBios;
    private JSlider slider1;
    private JSlider slider2;
    private JLabel numOfBios;
    private JLabel slider1l;
    private JLabel slider2l;

    private static JSlider createSlider(String label, int min, int max, int value)

    public MutationSettings() {
        mainPanel = new JPanel();

        setMinimumSize(new Dimension(380, 340));
        setResizable(false);

        mainPanel.setLayout(null);

        add(mainPanel, BorderLayout.CENTER);

        numOfBios = new JLabel("Number of Biomorphs " + 2);
        numberOfBios = new JSlider(JSlider.HORIZONTAL, 2, 10, 2);
        numberOfBios.setValue(2);
        numberOfBios.setMajorTickSpacing(2);
        numberOfBios.setPaintTicks(true);
        numberOfBios.setSnapToTicks(true);

        // mainPanel.setBounds(x, y, width, height);
        numberOfBios.setBounds(80, 70, 200, 20);
        numOfBios.setBounds(105, 50, 500, 20);
        mainPanel.add(numberOfBios);
        mainPanel.add(numOfBios);

        slider1l = new JLabel("Slider 1");
        slider1 = new JSlider(JSlider.HORIZONTAL, 2, 10, 2);
        slider1.setValue(1);
        slider1.setMajorTickSpacing(2);
        slider1.setPaintTicks(true);

        // mainPanel.setBounds(x, y, width, height);
        slider1l.setBounds(150, 100, 500, 20);
        slider1.setBounds(80, 120, 200, 20);
        mainPanel.add(slider1l);
        mainPanel.add(slider1);

        slider2l = new JLabel("Slider 2");
        slider2 = new JSlider(JSlider.HORIZONTAL, 2, 10, 2);
        slider2.setValue(1);
        slider2.setMajorTickSpacing(2);
        slider2.setPaintTicks(true);

        // mainPanel.setBounds(x, y, width, height);
        slider2l.setBounds(150, 150, 500, 20);
        slider2.setBounds(80, 170, 200, 20);
        mainPanel.add(slider2l);
        mainPanel.add(slider2);

        numberOfBios.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent event) {
                int numvalue = numberOfBios.getValue();
                numOfBios
                        .setText("Number of Biomorphs " + numvalue);
            }
        });

        JButton ok = new JButton("OK");
        ok.setBounds(150, 250, 80, 20);
        mainPanel.add(ok);

        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MutationSettings.this.dispose();
            }
        });
    }
}
