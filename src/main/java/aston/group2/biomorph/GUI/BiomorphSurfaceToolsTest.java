package aston.group2.biomorph.GUI;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import aston.group2.biomorph.Model.Biomorph;

public class BiomorphSurfaceToolsTest extends JFrame {
	public BiomorphSurfaceToolsTest(){
		setMinimumSize(new Dimension(800,600));
		add(new BiomorphSurfaceWithTools());
	}
	
	public static void main(String[] args)
    {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
            	BiomorphSurfaceToolsTest mw = new BiomorphSurfaceToolsTest();

                mw.setVisible(true);
            }
        });
    }
}
