package net.leejjon.javawebservice.config;

import javax.servlet.Filter;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class SpringMvcInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class[]{AppConfig.class};
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class[0];
	}

	@Override
	protected String[] getServletMappings() {
		return new String[]{"/"};
	}

	@Override
	protected Filter[] getServletFilters() {

		Filter[] filters;
		CharacterEncodingFilter encFilter;
		HiddenHttpMethodFilter httpMethodFilter = new HiddenHttpMethodFilter();

		encFilter = new CharacterEncodingFilter();

		encFilter.setEncoding("UTF-8");
		encFilter.setForceEncoding(true);

		// encFilter must be the first filter otherwise it doesn't work
		filters = new Filter[]{encFilter, httpMethodFilter};
		return filters;
	}
}
