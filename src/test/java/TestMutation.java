import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;

import aston.group2.biomorph.Model.Biomorph;
import aston.group2.biomorph.Model.EvolutionHelper;
import aston.group2.biomorph.Model.Mutator;
import aston.group2.biomorph.Storage.Generation;


public class TestMutation {

	@Test
	public void test() {
		 String genome = "D01F00CSLBEEF00SMCAFEsL123456s";
		 Biomorph bm = Biomorph.deserialise(genome);
		 Biomorph[] bma = {bm};
		 Mutator mutator = new Mutator();
		 mutator.childrenRequired = 100;
		 
		 Generation g = mutator.mutateBiomorph(bma);
		 
		 int count=0;
		 for(Biomorph b : g.children)
		 {
			 if(b.toString() != genome)
				 count++;
		 }
		 
		 assertTrue(count > 0); // 1 is the minimum number of different biomorphs for mutator to be considered working
		 System.out.println(count);
	}

}
