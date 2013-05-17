package eionet.sparqlClient.util;

import static junit.framework.Assert.assertEquals;
import org.junit.Test;

public class UtilTest {

    @Test
    public void noEscapeNeeded() {
        String input = "This is a simple string";
        String expct = "This is a simple string";
        assertEquals(expct, Util.escapeHtml(input));
    }

    /*
     * The character entity is not known in XML, so don't use escapeHtml to escape XML.
     */
    @Test
    public void escapeOfNatl() {
        String input = "€";
        String expct = "&euro;";
        assertEquals(expct, Util.escapeHtml(input));
    }

    @Test
    public void escapeAmp() {
        String input = "Fruit & vegetables";
        String expct = "Fruit &amp; vegetables";
        assertEquals(expct, Util.escapeHtml(input));
    }

    @Test
    public void escapeApos() {
        String input = "<div class='Apostrophs'>";
        String expct = "&lt;div class='Apostrophs'&gt;";
        assertEquals(expct, Util.escapeHtml(input));
    }

    @Test
    public void escapeQuot() {
        String input = "<div class=\"Quotes\">";
        String expct = "&lt;div class=&quot;Quotes&quot;&gt;";
        assertEquals(expct, Util.escapeHtml(input));
    }


}
