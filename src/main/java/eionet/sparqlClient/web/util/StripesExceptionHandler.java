package eionet.sparqlClient.web.util;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sourceforge.stripes.config.Configuration;
import net.sourceforge.stripes.exception.ExceptionHandler;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author <a href="mailto:jaanus.heinlaid@tieto.com">Jaanus Heinlaid</a>
 *
 */
public class StripesExceptionHandler implements ExceptionHandler {

    /** */
    private static Log logger = LogFactory.getLog(StripesExceptionHandler.class);

    /*
     * (non-Javadoc)
     * @see net.sourceforge.stripes.config.ConfigurableComponent#init(net.sourceforge.stripes.config.Configuration)
     */
    public void init(Configuration configuration) throws Exception {
    }

    /*
     * (non-Javadoc)
     * @see net.sourceforge.stripes.exception.ExceptionHandler#handle(java.lang.Throwable, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    public void handle(Throwable t,
                       HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {

        Throwable newThrowable = (t instanceof ServletException) ? getRootCause((ServletException)t) : t;
        if (newThrowable == null)
            newThrowable = t;

        logger.error(newThrowable.getMessage(), newThrowable);
        request.setAttribute("exception", newThrowable);
        request.getRequestDispatcher("/pages/error.jsp").forward(request, response);
    }

    /**
     *
     * @param servletException
     * @return
     */
    private Throwable getRootCause(ServletException servletException) {

        Throwable rootCause = servletException.getRootCause();
        if (rootCause instanceof ServletException)
            return getRootCause((ServletException)rootCause);
        else
            return rootCause;
    }
}
