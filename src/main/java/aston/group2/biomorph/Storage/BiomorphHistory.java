package aston.group2.biomorph.Storage;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import aston.group2.biomorph.Model.*;
/**
 * @author Antoine 
 */
public class BiomorphHistory implements Serializable {
	private List<Species> biomorphList;
	
	public BiomorphHistory() {
		biomorphList = new ArrayList<Species>();
	}
	
	public void add(Species species){
		biomorphList.add(species);	
	}
	
	public List<Species> get(){
		return biomorphList;	
	}
}
