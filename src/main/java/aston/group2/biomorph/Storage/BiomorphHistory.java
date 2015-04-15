package aston.group2.biomorph.Storage;
import java.util.ArrayList;
import java.util.List;

import aston.group2.biomorph.Model.*;
/**
 * Created by antoine on 12/03/15.
 */
public class BiomorphHistory {
	private List<Species> biomorphList;
	
	public BiomorphHistory(){
		List<Species> biomorphList = new ArrayList<Species>();
		
	}
	
	public void addToBiomorphHistory(Species species){
		biomorphList.add(species);	
	}
	
	public List<Species> returnBiomorphHistory(){
		return biomorphList;	
	}
}
