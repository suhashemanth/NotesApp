package com.suhas.NotesApp.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;
import java.util.logging.Logger;

@Slf4j
@Component
public class AuthFilter extends OncePerRequestFilter {
    private Logger logger=Logger.getLogger(AuthFilter.class.getName());
    private JwtUtils jwtUtils;
    private CustomUserDetailsService customUserDetailsService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = getTokenFromRequest(request);
        if(Objects.nonNull(token))
        {
            String userNameFromToken = jwtUtils.getUserNameFromToken(token);
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(userNameFromToken);
            if(StringUtils.hasText(userNameFromToken) && jwtUtils.isTokenValid(token,userDetails))
            {
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken
                        (userDetails.getUsername(),
                        null, userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        try
        {
            filterChain.doFilter(request,response);
        }catch (Exception e)
        {
            logger.info("Token is invalid");
        }
    }

    private String getTokenFromRequest(HttpServletRequest request)
    {
        String token=request.getHeader("Authorization");
        if(token!=null && token.startsWith("Bearer"))
        {
            return token.substring(7);
        }
        return null;
    }
}
