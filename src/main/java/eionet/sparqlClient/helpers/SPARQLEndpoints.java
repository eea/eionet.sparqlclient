package eionet.sparqlClient.helpers;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Gets a list of well-known SPARQL endpoints.
 *
 * @author <a href="mailto:jaanus.heinlaid@tieto.com">Jaanus Heinlaid</a>
 */
public class SPARQLEndpoints extends ArrayList<String> {

    public static final String PROPERTY_NAME = "endpoints";

    /** The name of the properties file containing the endpoints. */
    private static final String FILENAME = "endpoints.xml";

    /** */
    private static Log logger = LogFactory.getLog(SPARQLEndpoints.class);

    /** */
    private static SPARQLEndpoints instance;

    private static Object lock = new Object();

    /**
     *
     */
    private SPARQLEndpoints() {

        super();
        loadFromProperties();
        Collections.sort(this);
    }

    /**
     *
     */
    private void loadFromPropertiesFile() {

        InputStream inputStream = null;
        try {
            inputStream = SPARQLEndpoints.class.getClassLoader().getResourceAsStream(FILENAME);
            Properties properties = new Properties();
            properties.loadFromXML(inputStream);

            for (Object key : properties.keySet()) {
                this.add(key.toString());
            }
        } catch (InvalidPropertiesFormatException e) {
            logger.error("Failed to load endpoints from " + FILENAME, e);
        } catch (IOException e) {
            logger.error("Failed to load endpoints from " + FILENAME, e);
        } finally {
            IOUtils.closeQuietly(inputStream);
        }
    }

    private void loadFromProperties() {
        String endpoints = System.getProperty(PROPERTY_NAME);
        if (endpoints != null) {
            String[] endpointArr = endpoints.split("\\s*,\\s*");
            for (String key : endpointArr) {
                this.add(key);
            }
        } else {
            loadFromPropertiesFile();
        }
    }

    /**
     * Create singleton.
     *
     * @return SPARQLEndpoints
     */
    public static SPARQLEndpoints getInstance() {

        if (instance == null) {

            synchronized (lock) {

                // double-checked locking pattern
                // (http://www.ibm.com/developerworks/java/library/j-dcl.html)
                // FIXME: The IBM article says the pattern is broken. In Java 5 the volatile keyword can be used.
                if (instance == null) {
                    instance = new SPARQLEndpoints();
                }
            }
        }

        return instance;
    }

    /**
     * Delete the singleton.
     * For testing.
     */
    static void reset() {
        instance = null;
    }
}
