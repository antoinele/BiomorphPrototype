package aston.group2.biomorph.GUI;

import aston.group2.biomorph.Model.Mutator;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by antoine on 01/05/15.
 */
public class MutationSettings extends JDialog {
    private JPanel sliderPanel;
    private Mutator mutator;

    private Map<String, JComponent> components = new HashMap<>();

    private void insertLabelledComponent(String name, String text, JComponent component, int row)
    {
        component.setPreferredSize(new Dimension(300, 20));

        JLabel label = new JLabel(text);

        components.put(name, component);

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(3, 0, 3, 0);
        c.gridy = row;

        c.gridx = 0;
        c.anchor = GridBagConstraints.WEST;
        sliderPanel.add(label, c);

        c.gridx = 1;
        c.anchor = GridBagConstraints.EAST;
        sliderPanel.add(component, c);
    }

    private void createSlider(final String name, String text, double min, double max, double value, int precision, int row) {
        int imin, imax, ivalue;

        JPanel sliderPanel = new JPanel();
        sliderPanel.setLayout(new BoxLayout(sliderPanel, BoxLayout.LINE_AXIS));

        final int magnitude = (int)Math.pow(10, precision);
        imin = (int)(min * magnitude);
        imax = (int)(max * magnitude);
        ivalue = (int)(value * magnitude);

        final JSlider slider = new JSlider(JSlider.HORIZONTAL, imin, imax, ivalue);
        slider.setMajorTickSpacing((imax - imin) / 4); // 5 steps
        slider.setPaintTicks(true);
        slider.setToolTipText(text);
//        slider.setSnapToTicks(true);

        final JLabel valueLabel = new JLabel();
        {
            Dimension d = new Dimension(100, 20);
            valueLabel.setMaximumSize(d);
            valueLabel.setMinimumSize(d);
            valueLabel.setPreferredSize(d);

            Mutator.Setting setting = mutator.settings.get(name);
            if(setting.type == Mutator.Setting.Type.INT)
                valueLabel.setText(String.valueOf((int)setting.value));
            else
                valueLabel.setText(String.valueOf(setting.value));
        }

        slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                Mutator.Setting setting = mutator.settings.get(name);
                setting.value = (double)slider.getValue() / magnitude;
                if(setting.type == Mutator.Setting.Type.INT)
                    valueLabel.setText(String.valueOf(Math.round((double)setting.value)));
                else
                    valueLabel.setText(String.valueOf(setting.value));
            }
        });

        sliderPanel.add(valueLabel);
        sliderPanel.add(slider);

        insertLabelledComponent(name, text, sliderPanel, row);
    }

    private void createNumberInput(final String name, String text, int value, int row)
    {
        final JSpinner spinner = new JSpinner();
        spinner.setValue(value);

        spinner.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                Mutator.Setting setting = mutator.settings.get(name);
                setting.value = spinner.getValue();
            }
        });

        insertLabelledComponent(name, text, spinner, row);
    }

    private void createTextBox(String name, String text, String value, int row)
    {
        JTextField textField = new JTextField(value);

        insertLabelledComponent(name, text, textField, row);
    }

    public void finish()
    {
        for (Map.Entry<String, JComponent> kv : components.entrySet())
        {
            Mutator.Setting setting = mutator.settings.get(kv.getKey());
            switch(setting.type)
            {
                case INT:
                    if(setting.min == -1 && setting.max == -1) {
                        setting.value = ((JSpinner)kv.getValue()).getValue();
                    }
                    else {
                        setting.value = ((JSlider)kv.getValue().getComponent(1)).getValue();
                    }
                    break;

                case DOUBLE:
                    setting.value = ((JSlider)kv.getValue().getComponent(1)).getValue() / Math.pow(10, setting.precision);
                    break;

                case STRING:
                    setting.value = ((JTextField)kv.getValue()).getText();
            }
        }
    }

    public MutationSettings(JFrame parent, Mutator mutator) {
        super(parent, "Settings");
        this.mutator = mutator;

        sliderPanel = new JPanel();

        setResizable(false);

        sliderPanel.setLayout(new GridBagLayout());

        add(sliderPanel, BorderLayout.CENTER);

//        createSlider("Number of Biomorphs", 2, 10, 6, 0);

        int i=0;
        for (Map.Entry<String, Mutator.Setting> kv: mutator.settings.entrySet())
        {
            Mutator.Setting setting = kv.getValue();
            switch(setting.type)
            {
                case INT:
                    if(setting.min == -1 && setting.max == -1) {
                        createNumberInput(kv.getKey(), setting.name, (int)setting.value, i++);
                    }
                    else {
                        createSlider(kv.getKey(), setting.name, (int) setting.min, (int) setting.max, (int) setting.value, 0, i++);
                    }
                    break;

                case DOUBLE:
                    createSlider(kv.getKey(), setting.name, setting.min, setting.max, (double)setting.value, setting.precision, i++);
                    break;

                case STRING:
                    createTextBox(kv.getKey(), setting.name, (String)setting.value, i++);
                    break;
                default:
                    System.err.println(String.format("Unhandled type: %s. Field: %s", setting.type.name(), kv.getKey()));
            }
        }

        JButton ok = new JButton("OK");
        ok.setBounds(150, 250, 80, 20);
        add(ok, BorderLayout.SOUTH);

        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MutationSettings.this.finish();
                MutationSettings.this.dispose();
            }
        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                finish();
            }
        });

        pack();
    }
}
