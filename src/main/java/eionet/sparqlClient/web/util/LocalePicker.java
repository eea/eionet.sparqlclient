package eionet.sparqlClient.web.util;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import net.sourceforge.stripes.localization.DefaultLocalePicker;

/**
 * 
 * @author <a href="mailto:jaanus.heinlaid@tieto.com">Jaanus Heinlaid</a>
 *
 */
public class LocalePicker extends DefaultLocalePicker {
    
    /*
     * (non-Javadoc)
     * @see net.sourceforge.stripes.localization.DefaultLocalePicker#pickCharacterEncoding(javax.servlet.http.HttpServletRequest, java.util.Locale)
     */
    public String pickCharacterEncoding(HttpServletRequest request, Locale locale) {
        
        String encoding = super.pickCharacterEncoding(request, locale);
        return encoding == null ? "UTF-8" : null;
    }

}
