package eionet.sparqlClient.web.action;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;

/**
 *
 * @author <a href="mailto:jaanus.heinlaid@tieto.com">Jaanus Heinlaid</a>
 *
 */
@UrlBinding("/index.jsp")
public class FrontPageActionBean extends AbstractActionBean{

    /**
     *
     * @return
     */
    @DefaultHandler
    public Resolution defaultHandler() {

        return new ForwardResolution(SPARQLClientActionBean.class);
    }
}
