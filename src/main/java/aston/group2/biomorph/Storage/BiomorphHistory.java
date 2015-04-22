package aston.group2.biomorph.Storage;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import aston.group2.biomorph.Model.*;
/**
 * Created by antoine on 12/03/15.
 */
public class BiomorphHistory implements Serializable {
	private static List<Species> biomorphList;
	
	static {
		biomorphList = new ArrayList<Species>();
	}
	
	public static void add(Species species){
		biomorphList.add(species);	
	}
	
	public static List<Species> get(){
		return biomorphList;	
	}
}
