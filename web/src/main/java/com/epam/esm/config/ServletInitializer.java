package com.epam.esm.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

@ComponentScan("com.epam.esm")
public class ServletInitializer implements WebApplicationInitializer {

    private static final String versionName = "version1";
    private static final String profile = "prod";

    @Override
    public void onStartup(ServletContext servletContext) {
        servletContext.setInitParameter(
                "spring.profiles.active", profile);
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.register(ServletConfig.class);
        context.setServletContext(servletContext);
        DispatcherServlet servlet = new DispatcherServlet(context);
        ServletRegistration.Dynamic dispatcher =
                servletContext.addServlet("dispatcher", servlet);
        dispatcher.setLoadOnStartup(1);
        dispatcher.setInitParameter("throwExceptionIfNoHandlerFound", "true");
        dispatcher.addMapping("/" + versionName + "/*");
    }


}
