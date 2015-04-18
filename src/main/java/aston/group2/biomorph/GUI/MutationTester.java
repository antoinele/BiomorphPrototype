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

        biomorphGrid = new JPanel();
        biomorphGrid.setLayout(new GridLayout(rows,cols));

        add(biomorphGrid, BorderLayout.CENTER);

        initialiseBiomorph();

        refreshGrid();

        {
            JButton mutateButton = new JButton("Mutate");
            mutateButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                }
            });
        }
    }

    private void initialiseBiomorph()
    {
        mutator = new Mutator();

        generation = new Generation(mutator);

        new Species(generation);

        Biomorph bm = new Biomorph("D21F00CSLBEEF00SMCAFEsL123456LFF12F0SLF24300s");

        Biomorph[] bma = {bm};

        generation = mutator.mutateBiomorph(bma);
    }

    private void refreshGrid()
    {
        biomorphGrid.removeAll();

        for (int i=0; i<generation.children.length; i++)
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
