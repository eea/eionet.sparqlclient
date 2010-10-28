package eionet.sparqlClient.web.util;

import javax.servlet.http.HttpServletRequest;

/**
 * 
 * @author <a href="mailto:jaanus.heinlaid@tieto.com">Jaanus Heinlaid</a>
 *
 */
public class BaseUrl {

	static boolean valueSet = false;
	static String baseUrl = "";
	
	/**
	 * 
	 * @param request
	 * @return
	 */
	public static String getBaseUrl(HttpServletRequest request){
		if (!valueSet){
			valueSet = true;
			StringBuffer requestUrl = request.getRequestURL();
			String servletPath = request.getServletPath();
			baseUrl = requestUrl.substring(0, requestUrl.length() - servletPath.length())+"/";
		}
		return baseUrl;
	}
}
