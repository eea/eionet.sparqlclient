package eionet.sparqlClient.web.action;

import java.util.List;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.validation.ValidationMethod;

import org.apache.commons.lang.StringUtils;

import eionet.sparqlClient.helpers.QueryExecutor;
import eionet.sparqlClient.helpers.QueryResult;
import eionet.sparqlClient.helpers.SPARQLEndpoints;

/**
 * 
 * @author <a href="mailto:jaanus.heinlaid@tieto.com">Jaanus Heinlaid</a>
 *
 */
@UrlBinding("/sparqlClient.action")
public class SPARQLClientActionBean extends AbstractActionBean{
	
	/** */
	private static final String FORM_PAGE = "/pages/sparqlClient.jsp";
	
	/** */
	private String endpoint;
	private String query;
	
	/** */
	private QueryResult result;
	
	/**
	 * 
	 * @return
	 */
	@DefaultHandler
	public Resolution get(){
		return new ForwardResolution(FORM_PAGE);
	}
	
	/**
	 * 
	 * @return
	 */
	public Resolution execute(){
		
		if (!StringUtils.isBlank(endpoint) && !StringUtils.isBlank(query)){
		
			QueryExecutor queryExecutor = new QueryExecutor();
			queryExecutor.execute(endpoint, query);
			result = queryExecutor.getResults();
		}
		
		return new ForwardResolution(FORM_PAGE);
	}
	
	@ValidationMethod(on={"execute"})
	public void validateExecute(){
		
		// TODO Use a proper error feedback approach
		if (StringUtils.isBlank(endpoint) || StringUtils.isBlank(query)){
			throw new IllegalArgumentException("Both endpoint and query must not be empty");
		}
	}

	/**
	 * 
	 * @return
	 */
	public List<String> getEndpoints(){
		
		return SPARQLEndpoints.getInstance();
	}

	/**
	 * @return the endpoint
	 */
	public String getEndpoint() {
		return endpoint;
	}

	/**
	 * @param endpoint the endpoint to set
	 */
	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}

	/**
	 * @return the query
	 */
	public String getQuery() {
		return query;
	}

	/**
	 * @param query the query to set
	 */
	public void setQuery(String query) {
		this.query = query;
	}

	/**
	 * @return the result
	 */
	public QueryResult getResult() {
		return result;
	}
}
