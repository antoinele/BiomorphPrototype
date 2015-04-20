package aston.group2.biomorph.Storage;

import java.util.List;
import aston.group2.biomorph.Model.*;
import aston.group2.biomorph.Model.Species;

//class used for storing biomorphs that have been selected

public class SelectedBiomorphs {
	private List<Biomorph> selectedList;
	
	public SelectedBiomorphs(){
		selectedList = new java.util.LinkedList();
		
	}
	
	public void addBiomorph(Biomorph biomorph){
		selectedList.add(biomorph);
		
	}
	
	public List returnSelectedBiomorphs(){
		return selectedList;
		
	}
}
