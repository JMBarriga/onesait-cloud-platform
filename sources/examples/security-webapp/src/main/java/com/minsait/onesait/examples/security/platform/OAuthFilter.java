package com.minsait.onesait.examples.security.platform;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class OAuthFilter implements Filter {

    private static final String AUTHORIZATION = "Authorization";
	private OAuthAuthenticator data = new OAuthAuthenticator("child1");

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		//this.clientId = arg0.getInitParameter("applicationId");
		//this.data = new clientId="child1";
		
	}

    @Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {

        Optional<String> token = extractAccessTokenFromHeader(servletRequest);
        OAuthAuthorization authenticatedUser = data.authenticate(token.orElse(""));
        
        HttpServletRequest wrappedRequest = new AuthenticatedUserRequestWrapper((HttpServletRequest)servletRequest, authenticatedUser);;

        if (filterChain != null)
        {
            filterChain.doFilter(wrappedRequest, servletResponse);
        }

	}

    protected Optional<String> extractAccessTokenFromHeader(ServletRequest request)
    {
        HttpServletRequest httpRequest = (HttpServletRequest)request;
        String authorizationHeader = httpRequest.getHeader(AUTHORIZATION);
        String result = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer"))
        {
            String[] tokenSplit = authorizationHeader.split("[Bb][Ee][Aa][Rr][Ee][Rr]\\s+");

            if(tokenSplit.length == 2)
            {
                result = tokenSplit[1];
            }
        }

        return Optional.ofNullable(result);
    }
    
	@Override
	public void destroy() {
		
	}
	
}