package main.java.shoppingsiteservices.filters;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import com.sun.jersey.api.container.filter.RolesAllowedResourceFilterFactory;
import com.sun.jersey.api.model.AbstractMethod;
import com.sun.jersey.spi.container.ResourceFilter;

public class ResourceFilterFactoryRoles extends RolesAllowedResourceFilterFactory {

	private AuthenticationFilter authenticationFilter;

	@PostConstruct
	private void init() {
		System.out.println("ResourceFilterFactory initialized");
		authenticationFilter = new AuthenticationFilter();
	}

	@Override
	public List<ResourceFilter> create(AbstractMethod am) {
		// System.out.println("Creating resource filters list");
		List<ResourceFilter> filters = super.create(am);
		if (filters == null) {
			filters = new ArrayList<ResourceFilter>();
		}

		List<ResourceFilter> securityFilters = new ArrayList<ResourceFilter>(filters);
		securityFilters.clear();
		securityFilters.add(0, authenticationFilter);

		return securityFilters;
	}
}
