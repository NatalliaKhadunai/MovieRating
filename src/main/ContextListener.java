package main;

import org.apache.log4j.PropertyConfigurator;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.File;

/**
 * Listener used for logger properties initialization.
 */

@WebListener()
public class ContextListener implements ServletContextListener {

    /**
     * Initializing logger properties.
     * @param event, that caused the call.
     */
    public void contextInitialized(ServletContextEvent event) {
        ServletContext context = event.getServletContext();
        String log4jConfigFile = context.getInitParameter("log4j-config-location");
        String fullPath = context.getRealPath("") + File.separator + log4jConfigFile;
        PropertyConfigurator.configure(fullPath);
    }

    public void contextDestroyed(ServletContextEvent sce) {

    }
}
