package eionet.sparqlClient.helpers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
// OpenRDF
import org.openrdf.model.Literal;
import org.openrdf.model.Value;
import org.openrdf.query.BindingSet;
import org.openrdf.query.QueryEvaluationException;
import org.openrdf.query.TupleQueryResult;
// Jena
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.RDFNode;
*/
import uk.me.mmt.sprotocol.AnyResult;
import uk.me.mmt.sprotocol.SelectResultSet;
import uk.me.mmt.sprotocol.SelectResultRow;
import uk.me.mmt.sprotocol.SparqlResource;
import uk.me.mmt.sprotocol.Literal;
/**
 *
 * @author <a href="mailto:jaanus.heinlaid@tieto.com">Jaanus Heinlaid</a>
 *
 */
public class QueryResult {

    /** */
    private List<String> variables;
    private ArrayList<HashMap<String, ResultValue>> rows;
    private ArrayList<Map<String, Object>> cols;

    /**
     * Constructor.
     * @param queryResult
     * @throws QueryEvaluationException
     */
// SProtocol
    public QueryResult(final SelectResultSet rs) {
        if (rs != null) {
            this.variables = rs.getHead();
            addCols();
            for (SelectResultRow result : rs) {
                add(result);
            }
        }
    }
/*
// OpenRDF
    public QueryResult(final TupleQueryResult queryResult) throws QueryEvaluationException {

        if (queryResult != null && queryResult.hasNext()) {

            this.variables = queryResult.getBindingNames();
            addCols();
            while (queryResult.hasNext()) {
                add(queryResult.next());
            }
        }
    }
// Jena
    public QueryResult(final ResultSet rs) {

        if (rs != null && rs.hasNext()) {

            this.variables = rs.getResultVars();
            addCols();
            while (rs.hasNext()) {
                add(rs.next());
            }
        }
    }
*/

//SProtocol
    private void add(final SelectResultRow querySolution) {

        if (querySolution == null || variables == null || variables.isEmpty()) {
            return;
        }

        HashMap<String, ResultValue> map = new HashMap<String, ResultValue>();
        for (String variable : variables) {

            ResultValue resultValue = null;
            SparqlResource rdfNode = querySolution.get(variable);
            if (rdfNode != null) {
                if (rdfNode instanceof Literal) {
                    resultValue = new ResultValue(rdfNode.getValue(), true);
                } else {
                    resultValue = new ResultValue(rdfNode.getValue(), false);
                }
            }

            map.put(variable, resultValue);
        }

        if (rows == null) {
            rows = new ArrayList<HashMap<String, ResultValue>>();
        }
        rows.add(map);
    }
/*
// OpenRDF
    private void add(final BindingSet bindingSet) {

        if (bindingSet == null || variables == null || variables.isEmpty()) {
            return;
        }

        HashMap<String, ResultValue> map = new HashMap<String, ResultValue>();
        for (String variable : variables) {

            ResultValue resultValue = null;
            Value value = bindingSet.getValue(variable);

            if (value != null) {

                String valueString = value.stringValue();
                if (value instanceof Literal) {
                    resultValue = new ResultValue(valueString, true);
                } else {
                    resultValue = new ResultValue(valueString, false);
                }
            }

            map.put(variable, resultValue);
        }

        if (rows == null) {
            rows = new ArrayList<HashMap<String, ResultValue>>();
        }
        rows.add(map);
    }
// Jena
    private void add(final QuerySolution querySolution) {

        if (querySolution == null || variables == null || variables.isEmpty()) {
            return;
        }

        HashMap<String, ResultValue> map = new HashMap<String, ResultValue>();
        for (String variable : variables) {

            ResultValue resultValue = null;
            RDFNode rdfNode = querySolution.get(variable);
            if (rdfNode != null) {
                if (rdfNode.isLiteral()) {
                    resultValue = new ResultValue(rdfNode.asLiteral().getString(), true);
                } else if (rdfNode.isResource()) {
                    resultValue = new ResultValue(rdfNode.asResource().toString(), false);
                }
            }

            map.put(variable, resultValue);
        }

        if (rows == null) {
            rows = new ArrayList<HashMap<String, ResultValue>>();
        }
        rows.add(map);
    }
*/

    /**
     * Creates metadata information for the column names.
     */
    private void addCols() {

        if (variables == null || variables.isEmpty()) {
            return;
        }

        for (String variable : variables) {

            Map<String, Object> col = new HashMap<String, Object>();
            col.put("property", variable);
            col.put("title", variable);
            col.put("sortable", Boolean.TRUE);

            if (cols == null) {
                cols = new ArrayList<Map<String, Object>>();
            }
            cols.add(col);
        }
    }

    /**
     * @return the variables
     */
    public List<String> getVariables() {
        return variables;
    }

    /**
     * @return the rows
     */
    public ArrayList<HashMap<String, ResultValue>> getRows() {
        return rows;
    }

    /**
     * @param rows - the result rows.
     */
    public void setRows(ArrayList<HashMap<String, ResultValue>> rows) {
        this.rows = rows;
    }

    /**
     * @return the columns
     */
    public ArrayList<Map<String, Object>> getCols() {
        return cols;
    }

}
