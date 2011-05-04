package eionet.sparqlClient.helpers;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openrdf.query.QueryLanguage;
import org.openrdf.query.TupleQuery;
import org.openrdf.query.TupleQueryResult;
import org.openrdf.repository.RepositoryConnection;
import org.openrdf.repository.sparql.SPARQLRepository;


/**
 *
 * @author <a href="mailto:jaanus.heinlaid@tieto.com">Jaanus Heinlaid</a>
 *
 */
public class QueryExecutor {

    /** */
    private static Log logger = LogFactory.getLog(QueryExecutor.class);

    /** */
    private QueryResult results;

    /**
     *
     */
    public QueryExecutor() {
    }

    /**
     *
     * @param endpoint
     * @param query
     */
    public void executeQuery(String endpoint, String query) {

        RepositoryConnection conn = null;
        try {
            SPARQLRepository repo = new SPARQLRepository(endpoint);
            repo.initialize();

            conn = repo.getConnection();
            
            TupleQuery q = conn.prepareTupleQuery(QueryLanguage.SPARQL, query);
            TupleQueryResult bindings = q.evaluate();

            if (bindings == null || !bindings.hasNext()) {
                logger.info("The query gave no results");
            } else {
                results = new QueryResult(bindings);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (Exception e) {
                    logger.info("Failed to close QueryExecution object: " + e.toString());
                }
            }
        }
    }

    /** */
    private static final String EXPLORE_QUERY_TEMPL = "SELECT DISTINCT ?subj ?pred ?obj WHERE {\n"
            + " {?subj ?pred ?obj . FILTER (?subj = <@exploreSubject@>) . }\n"
            + " UNION {?subj ?pred ?obj . FILTER (?obj = <@exploreSubject@> ) . }\n} LIMIT 50";
    /**
     *
     * @param endpoint
     * @param exploreSubject
     * @return String
     */
    public String executeExploreQuery(String endpoint, String exploreSubject) {

        String exploreQuery = StringUtils.replace(EXPLORE_QUERY_TEMPL, "@exploreSubject@", exploreSubject);
        executeQuery(endpoint, exploreQuery);
        return exploreQuery;
    }

    /**
     *
     * @return QueryResult
     */
    public QueryResult getResults() {
        return results;
    }
}
