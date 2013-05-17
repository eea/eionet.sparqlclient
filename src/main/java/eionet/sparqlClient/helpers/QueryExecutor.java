package eionet.sparqlClient.helpers;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/*
 * This file contains two versions of some methods. One uses the OpenRDF
 * library, the other Jena. OpenRDF makes a POST request to the endpoint,
 * and that means it can't communicate with endpoints based on SPARQLer.
 * Jena checks the syntax of the query and therefore won't support endpoints
 * with SPARQL extensions.
 */
/*
// OpenRDF
import org.openrdf.query.BooleanQuery;
import org.openrdf.query.QueryLanguage;
import org.openrdf.query.TupleQuery;
import org.openrdf.query.TupleQueryResult;
import org.openrdf.repository.RepositoryConnection;
import org.openrdf.repository.sparql.SPARQLRepository;
// Jena
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.ResultSet;
*/
//SProtocol
import java.io.IOException;
import uk.me.mmt.sprotocol.SparqlQueryProtocolClient;
import uk.me.mmt.sprotocol.SprotocolException;
import uk.me.mmt.sprotocol.ResultType;
import uk.me.mmt.sprotocol.AnyResult;
import uk.me.mmt.sprotocol.SelectResultSet;


/**
 * Handle execution of SPARQL query and retrieval of result. Basically a wrapper around a SPARQL client library.
 *
 * @author <a href="mailto:jaanus.heinlaid@tieto.com">Jaanus Heinlaid</a>
 */
public class QueryExecutor {

    /** */
    private static Log logger = LogFactory.getLog(QueryExecutor.class);

    /** Variable for the results. */
    private QueryResult results;

    /**
     * Constructor.
     */
    public QueryExecutor() {
    }

    /**
     * Execute a query against an endpoint.
     *
     * @param endpoint
     * @param query
     */
    public void executeQuery(final String endpoint, final String query) {
        SparqlQueryProtocolClient sparql = new SparqlQueryProtocolClient(endpoint);
        sparql.setTimeout(60000); // 60 seconds
        try {
            AnyResult sparqlResult = sparql.genericQuery(query);
            ResultType sparqlResultType = sparqlResult.getResultType();
            if (ResultType.BOOLEAN == sparqlResultType) {
                logger.info("ASK result sent the answer to the ASK is: '" + sparqlResult.getBooleanResult() + "'");
            } else if (ResultType.RDF == sparqlResultType) {
                logger.info("RDF returned by the SPARQL query");
            } else if (ResultType.SPARQLRESULTS == sparqlResultType) {
                SelectResultSet sparqlResults = sparqlResult.getSparqlResult();
                results = new QueryResult(sparqlResults);
            }
        } catch (SprotocolException e) {
            logger.info(String.format("Threw one of its own SprotocolException: '%s'", e));
        } catch (IOException e) {
            logger.info(String.format("Threw an IOException: '%s'", e));
        }
    }

// Alternative based on OpenRDF
/*
    public void executeQuery(final String endpoint, final String query) {

        RepositoryConnection conn = null;
        try {
            SPARQLRepository repo = new SPARQLRepository(endpoint);
            repo.initialize();

            conn = repo.getConnection();

            TupleQuery q = conn.prepareTupleQuery(QueryLanguage.SPARQL, query);
            TupleQueryResult rs = q.evaluate();

            if (rs == null || !rs.hasNext()) {
                logger.info("The query gave no results");
            } else {
                results = new QueryResult(rs);
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
*/
// Alternative based on Jena
/*
    public void executeQuery(final String endpoint, final String query) {

        QueryExecution queryExecution = null;
        try {
            //Query q = QueryFactory.create(query)
            queryExecution = QueryExecutionFactory.sparqlService(endpoint, query);
            ResultSet rs = queryExecution.execSelect();

            if (rs == null || !rs.hasNext()) {
                results = null;
            } else {
                results = new QueryResult(rs);
            }
        } finally {
            if (queryExecution != null) {
                try {
                    queryExecution.close();
                } catch (Exception e) {
                    logger.info("Failed to close QueryExecution object: " + e.toString());
                }
            }
        }
    }
*/

// SProtocol
   /**
    * Execute an ASK query. Those can only return true or false.
    *
    * @param endpoint
    * @param query
    * @return boolean
    */
    public boolean executeASKQuery(final String endpoint, final String query) {
        SparqlQueryProtocolClient sparql = new SparqlQueryProtocolClient(endpoint);
        sparql.setTimeout(60000); // 60 seconds
        try {
            AnyResult sparqlResult = sparql.genericQuery(query);
            ResultType sparqlResultType = sparqlResult.getResultType();
            if (ResultType.BOOLEAN == sparqlResultType) {
                return sparqlResult.getBooleanResult();
            } else if (ResultType.RDF == sparqlResultType) {
                logger.info("RDF returned by the SPARQL query");
            } else if (ResultType.SPARQLRESULTS == sparqlResultType) {
                logger.info("SPARQL query returned table");
            }
        } catch (SprotocolException e) {
            logger.info(String.format("Threw one of its own SprotocolException: '%s'", e));
        } catch (IOException e) {
            logger.info(String.format("Threw an IOException: '%s'", e));
        }
        return false;
    }

// Alternative based on OpenRDF.
/*
   public boolean executeASKQuery(final String endpoint, final String query) {

       RepositoryConnection conn = null;
       boolean ret = false;
       try {
           SPARQLRepository repo = new SPARQLRepository(endpoint);
           repo.initialize();

           conn = repo.getConnection();

           BooleanQuery resultsTableBoolean = conn.prepareBooleanQuery(QueryLanguage.SPARQL, query);
           Boolean result = resultsTableBoolean.evaluate();

           ret = result.booleanValue();

       } catch (Exception e) {
           e.printStackTrace();
       } finally {
           if (conn != null) {
               try {
                   conn.close();
               } catch (Exception e) {
                   logger.info("Failed to close RepositoryConnection object: " + e.toString());
               }
           }
       }

       return ret;
   }
*/
// Alternative based on Jena
/*
    public boolean executeASKQuery(final String endpoint, final String query) {

        QueryExecution queryExecution = null;
        boolean ret = false;
        try {
            queryExecution = QueryExecutionFactory.sparqlService(endpoint, query);
            ret = queryExecution.execAsk();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (queryExecution != null) {
                try {
                    queryExecution.close();
                } catch (Exception e) {
                    logger.info("Failed to close QueryExecution object: " + e.toString());
                }
            }
        }
        return ret;
    }
*/
    /**
     *
     * @return QueryResult
     */
    public QueryResult getResults() {
        return results;
    }
}
