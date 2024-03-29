import aston.group2.biomorph.Model.Biomorph;
import aston.group2.biomorph.Model.IncompatibleSpeciesException;
import aston.group2.biomorph.Model.Mutator;
import aston.group2.biomorph.Storage.Generation;
import org.junit.Test;

import static org.junit.Assert.assertTrue;


public class TestMutation {

	@Test
	public void test() throws IncompatibleSpeciesException {
		 String genome = "D01F00CSLBEEF00L123456s";
		 Biomorph bm = Biomorph.deserialise(genome);
		 Biomorph[] bma = {bm};
		 Mutator mutator = new Mutator(System.currentTimeMillis());
		 mutator.setting("required_children").value = 6;
		 
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
