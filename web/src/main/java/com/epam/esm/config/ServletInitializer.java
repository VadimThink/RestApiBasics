package com.epam.esm.config;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

public class ServletInitializer implements WebApplicationInitializer {

    /*@Override
    protected Class<?>[] getRootConfigClasses() {
        return null;
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[] {ServletConfig.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[] {"/"};
    }
    */
    @Override
    public void onStartup(ServletContext servletContext) {
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.register(ServletConfig.class);
        context.setServletContext(servletContext);

        DispatcherServlet servlet = new DispatcherServlet(context);
        ServletRegistration.Dynamic dispatcher =
                servletContext.addServlet("dispatcher", servlet);
        dispatcher.setLoadOnStartup(1);
        dispatcher.setInitParameter("throwExceptionIfNoHandlerFound", "true");
        dispatcher.addMapping("/*");
    }

    /*@Override
    protected DispatcherServlet createDispatcherServlet(WebApplicationContext servletAppContext) {
        DispatcherServlet dispatcherServlet = (DispatcherServlet) super.createDispatcherServlet(servletAppContext);
        dispatcherServlet.setThrowExceptionIfNoHandlerFound(true);
        return dispatcherServlet;
    }*/


}
