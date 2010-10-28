package eionet.sparqlClient.web.action;

import net.sourceforge.stripes.action.ActionBean;
import net.sourceforge.stripes.action.ActionBeanContext;

/**
 * 
 * @author <a href="mailto:jaanus.heinlaid@tieto.com">Jaanus Heinlaid</a>
 *
 */
public class AbstractActionBean implements ActionBean{
	
	/** */
	private eionet.sparqlClient.web.context.ActionBeanContext context;

	/*
	 * (non-Javadoc)
	 * @see net.sourceforge.stripes.action.ActionBean#getContext()
	 */
	public ActionBeanContext getContext() {
		return context;
	}

	/*
	 * (non-Javadoc)
	 * @see net.sourceforge.stripes.action.ActionBean#setContext(net.sourceforge.stripes.action.ActionBeanContext)
	 */
	public void setContext(ActionBeanContext context){
		
		if (!(context instanceof eionet.sparqlClient.web.context.ActionBeanContext)){
			throw new IllegalArgumentException("Was expecting " +
					eionet.sparqlClient.web.context.ActionBeanContext.class.getName());
		}
		
		this.context = (eionet.sparqlClient.web.context.ActionBeanContext)context;
	}

}
