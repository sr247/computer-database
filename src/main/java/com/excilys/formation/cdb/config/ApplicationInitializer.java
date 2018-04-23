package com.excilys.formation.cdb.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class ApplicationInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	protected Class<?>[] getRootConfigClasses() {
		 return new Class<?>[] { DataBaseConfig.class };
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class<?>[] { SpringMvcConfig.class };
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] {"/"};
	}
//	
//	@Override
//	protected DispatcherServlet createDispatcherServlet(WebApplicationContext servletAppContext) {
//		final DispatcherServlet servlet = (DispatcherServlet) super.createDispatcherServlet(servletAppContext);
//		servlet.setThrowExceptionIfNoHandlerFound(true);
//		return servlet;
//	}
//
//	@Override
//	protected void registerDispatcherServlet(ServletContext arg0) {
//		super.registerDispatcherServlet(arg0);
//	}
}
