package aston.group2.biomorph.GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import aston.group2.biomorph.Model.Biomorph;

public class BiomorphSurfaceToolsTest extends JFrame {
	public BiomorphSurfaceToolsTest(){
		setMinimumSize(new Dimension(800,600));
		setLayout(new BorderLayout());
		
		JPanel bs = new BiomorphSurfaceWithTools();
		
		add(bs, BorderLayout.CENTER);
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
