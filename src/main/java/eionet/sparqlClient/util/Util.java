package eionet.sparqlClient.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * 
 * @author <a href="mailto:jaanus.heinlaid@tieto.com">Jaanus Heinlaid</a>
 *
 */
public class Util {

	/**
	 * 
	 * @param t
	 * @return
	 */
	public static String getStackTrace(Throwable t){
		
		StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw, true);
        t.printStackTrace(pw);
        return sw.getBuffer().toString();
	}

	/**
	 * 
	 * @param t
	 * @return
	 */
	public static String getStackTraceForHTML(Throwable t){
		
		return processStackTraceForHTML(getStackTrace(t));
	}
	
	/**
	 * 
	 * @param stackTrace
	 * @return
	 */
	public static String processStackTraceForHTML(String stackTrace){
		
		if (stackTrace==null || stackTrace.trim().length()==0)
			return stackTrace;
		
		StringBuffer buf = new StringBuffer();
		String[] stackFrames = getStackFrames(stackTrace);
		for (int i=0; stackFrames!=null && i<stackFrames.length; i++){
			buf.append(stackFrames[i].replaceFirst("\t", "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;")).append("<br/>");
		}
		
		return buf.length()>0 ? buf.toString() : stackTrace;
	}
	
	/**
	 * 
	 * @param stackTrace
	 * @return
	 */
	public static String[] getStackFrames(String stackTrace){
        StringTokenizer frames = new StringTokenizer(stackTrace, System.getProperty("line.separator"));
        List list = new LinkedList();
        for(; frames.hasMoreTokens(); list.add(frames.nextToken()));
        return (String[])list.toArray(new String[list.size()]);
    }

}
