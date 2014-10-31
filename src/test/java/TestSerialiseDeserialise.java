import biomorph.prototype.Model.Biomorph;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by antoine on 31/10/14.
 */
public class TestSerialiseDeserialise {

    @Test
    public void testSerialiseDeserialise()
    {
        String genome = "D01F00CSLBEEF00SMCAFEsL123456s";

        Biomorph bm = Biomorph.deserialise(genome);
        assertEquals(bm.toString(), genome);
    }

}
