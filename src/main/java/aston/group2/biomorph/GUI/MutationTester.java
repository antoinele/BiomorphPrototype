package aston.group2.biomorph.GUI;

import aston.group2.biomorph.Model.Biomorph;
import aston.group2.biomorph.Model.Mutator;
import aston.group2.biomorph.Model.Species;
import aston.group2.biomorph.Storage.Generation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by antoine on 17/04/15.
 */
public class MutationTester extends JFrame {
    JPanel biomorphGrid;

    Generation generation;
    Mutator mutator;

    final int rows = 2, cols = 3;

    public MutationTester()
    {
        setLayout(new BorderLayout());
        setTitle("Mutation Tester");
        setMinimumSize(new Dimension(800, 600));

        biomorphGrid = new JPanel();
        biomorphGrid.setLayout(new GridLayout(rows,cols));

        add(biomorphGrid, BorderLayout.CENTER);

        initialiseBiomorph();

        refreshGrid();

        {
            JLabel infoLabel = new JLabel("This program takes the top left biomorph and mutates it into 6 children");
            add(infoLabel, BorderLayout.NORTH);
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

    private void initialiseBiomorph()
    {
        Biomorph bm;
        if(generation == null) {
            mutator = new Mutator();
            mutator.childrenRequired = rows * cols;

            generation = new Generation(mutator);

            new Species(generation);

            bm = new Biomorph("D21F00CSLBEEF00SMCAFEsL123456LFF12F0SLF24300s");
        }
        else
        {
            bm = generation.children[0];
        }

        Biomorph[] bma = {bm};

        generation = mutator.mutateBiomorph(bma);
    }

    private void refreshGrid()
    {
        biomorphGrid.removeAll();

        for (int i=0; i<Math.min(generation.children.length, rows*cols); i++)
        {
            BiomorphSurface bs = new BiomorphSurface();
            bs.setBiomorph(generation.children[i]);

            biomorphGrid.add(bs);
        }

        biomorphGrid.revalidate();
    }

    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame mw = new MutationTester();

                mw.setVisible(true);
            }
        });
    }
}
