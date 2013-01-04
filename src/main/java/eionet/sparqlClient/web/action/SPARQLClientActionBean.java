package eionet.sparqlClient.web.action;

import java.util.List;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;

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
public class SPARQLClientActionBean extends AbstractActionBean {

    /** */
    private static final String FORM_PAGE = "/pages/sparqlClient.jsp";

    /** SPARQL endpoint URL. */
    private String endpoint;
    /** SPARQL query. */
    private String query;
    /** A URI to explore. */
    private String explore;
    /** Used to distinguish between explorer pages */
    private int tab;

    /** */
    private QueryResult result;

    /** Templates for the explore functionality. */
    private static final String EXPLORE_QUERY_TEMPL[] = {
    /* Properties of the subject */
       "SELECT *\nWHERE {\n<@exploreSubject@> ?property ?object\n} LIMIT 100\n",
    /* Links to the subject */
       "SELECT *\nWHERE {\n?subject ?linktype <@exploreSubject@>\n} LIMIT 100\n",
    /* Used as a predicate */
       "SELECT *\nWHERE {\n?subject <@exploreSubject@> ?object\n} LIMIT 100\n",
    /* Triples in graph */
       "SELECT DISTINCT ?subject WHERE {\n GRAPH <@exploreSubject@> {\n  ?subject ?predicate ?object\n }\n} LIMIT 100\n",
    /* Graphs */
       "SELECT *\nWHERE {\nGRAPH ?graph {\n<@exploreSubject@> ?property ?object\n}\n} LIMIT 100\n"
       
    };

        /** */
    private static final String EXPLORE_QUERY_TABS[] = {
     "Properties",
     "Links to subject",
     "Used as a predicate",
     "Entities in graph",
     "Graphs"
    };

    /**
     *
     * @return Resolution
     */
    @DefaultHandler
    public Resolution execute() {

        if (!StringUtils.isBlank(endpoint)) {

            if (!StringUtils.isBlank(explore)) {
                query = StringUtils.replace(EXPLORE_QUERY_TEMPL[tab], "@exploreSubject@", explore);
                QueryExecutor queryExecutor = new QueryExecutor();
                queryExecutor.executeQuery(endpoint, query);
                result = queryExecutor.getResults();
            } else if (!StringUtils.isBlank(query)) {
                QueryExecutor queryExecutor = new QueryExecutor();
                queryExecutor.executeQuery(endpoint, query);
                result = queryExecutor.getResults();
            }
        }

        return new ForwardResolution(FORM_PAGE);
    }

    /**
     *
     * @return List<String>
     */
    public List<String> getEndpoints() {

        return SPARQLEndpoints.getInstance();
    }

    /**
     *
     * @return tab names as an array.
     */
    public String[] getTabLabels() {

        return EXPLORE_QUERY_TABS;
    }

    /**
     *
     * @return number of tabs.
     */
    public int getNumTabs() {
        return EXPLORE_QUERY_TEMPL.length;
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

    /**
     * @param explore the URI to set
     */
    public void setExplore(String explore) {
        this.explore = explore;
    }

    /**
     * @return the explore
     */
    public String getExplore() {
        return explore;
    }

    /**
     * @param explore the URI to set
     */
    public void setTab(int tab) {
        //FIXME: Max value is the getNumTabs()-1
        this.tab = tab;
    }

    /**
     * @return the explore
     */
    public int getTab() {
        return tab;
    }
}
