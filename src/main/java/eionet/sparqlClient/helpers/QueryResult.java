package eionet.sparqlClient.helpers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.RDFNode;

/**
 *
 * @author <a href="mailto:jaanus.heinlaid@tieto.com">Jaanus Heinlaid</a>
 *
 */
public class QueryResult{

    /** */
    private List<String> variables;
    private ArrayList<HashMap<String,ResultValue>> rows;

    /**
     *
     * @param rs
     */
    public QueryResult(ResultSet rs) {

        if (rs != null && rs.hasNext()) {

            this.variables = rs.getResultVars();
            while (rs.hasNext()) {
                add(rs.next());
            }
        }
    }

    /**
     *
     * @param querySolution
     */
    private void add(QuerySolution querySolution) {

        if (querySolution == null || variables == null || variables.isEmpty()) {
            return;
        }

        HashMap<String,ResultValue> map = new HashMap<String, ResultValue>();
        for (String variable : variables) {

            ResultValue resultValue = null;
            RDFNode rdfNode = querySolution.get(variable);
            if (rdfNode != null) {
                if (rdfNode.isLiteral()) {
                    resultValue = new ResultValue(rdfNode.asLiteral().getString(), true);
                }
                else if (rdfNode.isResource()) {
                    resultValue = new ResultValue(rdfNode.asResource().toString(), false);
                }
            }

            map.put(variable, resultValue);
        }

        if (rows == null) {
            rows = new ArrayList<HashMap<String,ResultValue>>();
        }
        rows.add(map);
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
}
