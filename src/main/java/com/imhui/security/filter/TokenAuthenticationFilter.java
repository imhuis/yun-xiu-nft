package com.imhui.security.filter;

import com.google.common.base.Strings;
import com.imhui.security.common.constant.SecurityConstants;
import com.imhui.security.security.TokenAuthentication;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TokenAuthenticationFilter extends BasicAuthenticationFilter {

    private static final Logger logger = LoggerFactory.getLogger(TokenAuthenticationFilter.class);

//    public static final String TOKEN_HEADER = "Authorization";

    public TokenAuthenticationFilter(AuthenticationManager authenticationManager, AuthenticationEntryPoint authenticationEntryPoint) {
        super(authenticationManager, authenticationEntryPoint);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String requestTokenInServlet = request.getParameter(SecurityConstants.TOKEN_HEADER);
        if (Strings.isNullOrEmpty(requestTokenInServlet)){
            chain.doFilter(request, response);
            return;
        }
        // start Authentication

        String requestToken = requestTokenInServlet.toLowerCase().trim();
        logger.info("[TokenAuthenticationFilter] request token: {}", requestToken);
        try {
            TokenAuthentication tokenAuthentication = new TokenAuthentication(requestToken);
            Authentication authResult = getAuthenticationManager().authenticate(tokenAuthentication);
            SecurityContextHolder.getContext().setAuthentication(authResult);

        }catch (AuthenticationException e){
            SecurityContextHolder.clearContext();
            this.onUnsuccessfulAuthentication(request, response, e);
            if (this.isIgnoreFailure()) {
                chain.doFilter(request, response);
            } else {
                this.getAuthenticationEntryPoint().commence(request, response, e);
            }
            return;
        }

        chain.doFilter(request, response);
    }
}
