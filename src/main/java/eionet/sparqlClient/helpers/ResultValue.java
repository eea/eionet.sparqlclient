package eionet.sparqlClient.helpers;

/**
 * Class to hold a result value. A value comes back from SPARQL in XML, and
 * can have metadata: The datatype, language and whether it is a literal or resource.
 * Currently we're only interested in whether it is a literal or not.
 * TODO: Space for datatype and language.
 */
public class ResultValue {

    /** The value of the result. */
    private String value;
    /** Whether the value is a literal or resource. */
    private boolean isLiteral;

    /**
     *
     * @param value
     * @param isLiteral
     */
    public ResultValue(String value, boolean isLiteral) {

        this.value = value;
        this.isLiteral = isLiteral;
    }

    /**
     * @return the value
     */
    public String getValue() {
        return value;
    }
    /**
     * @param value the value to set
     */
    public void setValue(final String value) {
        this.value = value;
    }
    /**
     * @return the isLiteral
     */
    public boolean isLiteral() {
        return isLiteral;
    }
    /**
     * @param isLiteral the isLiteral to set
     */
    public void setLiteral(final boolean isLiteral) {
        this.isLiteral = isLiteral;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return value;
    }
}
