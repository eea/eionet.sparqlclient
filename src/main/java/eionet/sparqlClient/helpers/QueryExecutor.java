package eionet.sparqlClient.helpers;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFormatter;

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
	public QueryExecutor(){
	}
	
	/**
	 * 
	 * @param endpoint
	 * @param query
	 */
	public void execute(String endpoint, String query){
		
		QueryExecution queryExecution = null;
		try{
			queryExecution = QueryExecutionFactory.sparqlService(endpoint, query);
			ResultSet rs = queryExecution.execSelect();

			if (rs==null || !rs.hasNext()){
				logger.info("The query gave no results");
			}
			else{
//				ResultSetFormatter.outputAsXML(System.out, rs);
				results = new QueryResult(rs);
			}
		}
		finally{
			if (queryExecution!=null){
				try{
					queryExecution.close();
				}
				catch (Exception e){
					logger.info("Failed to close QueryExecution object: " + e.toString());
				}
			}
		}
	}
	
	/**
	 * 
	 * @return
	 */
	public QueryResult getResults(){
		return results;
	}
}
