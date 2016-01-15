package eionet.sparqlClient.helpers;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class SPARQLEndpointsTest {

    @Test
    public void loadEndpointsFromFile() {
        SPARQLEndpoints endp = SPARQLEndpoints.getInstance();
        int numEndpoints = endp.size();
        SPARQLEndpoints.reset();
        assertEquals(16, numEndpoints);
    }

    @Test
    public void loadEndpoints() {
        System.setProperty(SPARQLEndpoints.PROPERTY_NAME, "http://cr.eionet.europa.eu/sparql, "
                + "http://semantic.eea.europa.eu/sparql,http://dbpedia.org/sparql");
        SPARQLEndpoints endp = SPARQLEndpoints.getInstance();
        int numEndpoints = endp.size();
        SPARQLEndpoints.reset();
        System.clearProperty(SPARQLEndpoints.PROPERTY_NAME);
        assertEquals(3, numEndpoints);
    }
}
