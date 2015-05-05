package aston.group2.biomorph.Utilities;

import java.awt.Image;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import aston.group2.biomorph.GUI.BiomorphSurface;
import aston.group2.biomorph.Model.Biomorph;

public class Exporter {
	public static void exportBiomorph(Biomorph biomorph){

		JFileChooser jf = new JFileChooser();
		jf.setFileFilter(new FileNameExtensionFilter("Biomorph file","biomorph"));
		int rv = jf.showSaveDialog(null);
		if(rv == JFileChooser.APPROVE_OPTION)
		{
			String path = jf.getSelectedFile().getPath();

			if (!path.endsWith(".biomorph")) {
				path += ".biomorph";
			}

			try {
				BufferedWriter br = Files.newBufferedWriter(Paths.get(path), Charset.forName("US-ASCII"));
				br.write(biomorph.toString());
				br.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} 

	}

	public static void exportPNG(Biomorph biomorph){

		BiomorphSurface biomorphSurface = new BiomorphSurface(biomorph);

		biomorphSurface.setSize(1000, 1000);

		Image img = biomorphSurface.getFrame();

		JFileChooser jf = new JFileChooser();
		jf.setFileFilter(new FileNameExtensionFilter("PNG file", "png"));
		int rv = jf.showSaveDialog(null);

		if (rv == JFileChooser.APPROVE_OPTION) {

			String path = jf.getSelectedFile().getPath();

			if (!path.endsWith(".png")) {
				path += ".png";
			}

			File of = new File(path);
			try {
				ImageIO.write((java.awt.image.RenderedImage) img, "png", of);
			} catch (IOException e2) {
				e2.printStackTrace();
			}
		}
	}
}
