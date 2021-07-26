package com.imhui.security.filter;

import com.google.common.base.Strings;
import com.imhui.security.common.constant.SecurityConstants;
import com.imhui.security.core.security.bo.TokenAuthentication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

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
//        String requestTokenInServlet = request.getParameter(SecurityConstants.TOKEN_PARAMETER);
//        if (Strings.isNullOrEmpty(requestTokenInServlet)){
//
//        }
        // start Authentication
        try {
            TokenAuthentication authRequest = this.convert(request);
            if (authRequest == null){
                chain.doFilter(request, response);
                return;
            }
            logger.info("[TokenAuthenticationFilter] request token: {}", authRequest.getCredentials());
            Authentication authResult = this.getAuthenticationManager().authenticate(authRequest);
            SecurityContextHolder.getContext().setAuthentication(authResult);
            this.onSuccessfulAuthentication(request, response, authResult);
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

    private TokenAuthentication convert(HttpServletRequest request){
        String headerTokenInServlet = request.getHeader(SecurityConstants.TOKEN_HEADER);
        String requestToken = headerTokenInServlet;
        if (Strings.isNullOrEmpty(requestToken)){
            return null;
        }else {
            // 读取参数
            String requestTokenInServlet = request.getParameter(SecurityConstants.TOKEN_PARAMETER);
            requestToken = requestTokenInServlet;
            if (Strings.isNullOrEmpty(requestToken)){
                return null;
            }
        }

        TokenAuthentication tokenAuthentication = new TokenAuthentication(requestToken.toLowerCase().trim());
        return tokenAuthentication;

    }
}
