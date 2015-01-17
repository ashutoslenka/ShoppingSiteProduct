package main.java.shoppingsiteservices.filters;

import java.security.Principal;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.ext.Provider;

import com.sun.jersey.spi.container.ContainerRequest;
import com.sun.jersey.spi.container.ContainerRequestFilter;
import com.sun.jersey.spi.container.ContainerResponseFilter;
import com.sun.jersey.spi.container.ResourceFilter;

@Provider
public class AuthenticationFilter implements ResourceFilter, ContainerRequestFilter {

	@Override
	public ContainerRequest filter(final ContainerRequest requestContext) {

		requestContext.setSecurityContext(new SecurityContext() {
			@Override
			public Principal getUserPrincipal() {
				return new Principal() {
					@Override
					public String getName() {
						return "partha";
					}
				};
			}

			@Override
			public boolean isUserInRole(String string) {
				System.out.println("Here::Is user in role");
				return true;
			}

			@Override
			public boolean isSecure() {
				System.out.println("Here::" + requestContext);
				return false;
				// return requestContext.isSecure();
			}

			@Override
			public String getAuthenticationScheme() {
				System.out.println("Here::get auth scheme");
				return "BASIC";
				// return requestContext.getAuthenticationScheme();
			}
		});

		if (!isAuthenticated(requestContext)) {

			Response.status(Status.UNAUTHORIZED).header(HttpHeaders.WWW_AUTHENTICATE, "Basic realm=\"Example\"").entity("Login required.").build();
		}

		return requestContext;
	}

	private boolean isAuthenticated(final ContainerRequest requestContext) {
		System.out.println("Here::Is authenticated");
		return requestContext.getHeaderValue("authorization") != null; // simplified
	}

	@Override
	public ContainerRequestFilter getRequestFilter() {
		return this;
	}

	@Override
	public ContainerResponseFilter getResponseFilter() {
		return null;
	}
}