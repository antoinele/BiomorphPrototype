package aston.group2.biomorph.GUI;

import javax.swing.JPanel;

import aston.group2.biomorph.Model.Biomorph;

public class BiomorphSurfaceWithTools extends JPanel {
	public BiomorphSurfaceWithTools(){
		BiomorphSurface bS = new BiomorphSurface();
		this.add(bS);
		bS.setBiomorph(new Biomorph("D21F00CSLBEEF00SMCAFEsL123456LFF12F0SLF24300s"));
		
	}
}
