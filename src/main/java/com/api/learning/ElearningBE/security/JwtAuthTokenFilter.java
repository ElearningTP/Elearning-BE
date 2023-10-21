package com.api.learning.ElearningBE.security;

import com.api.learning.ElearningBE.security.impl.UserDetailsImpl;
import com.api.learning.ElearningBE.security.impl.UserDetailsServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Component
@Slf4j
public class JwtAuthTokenFilter extends OncePerRequestFilter {

    @Autowired
    private UserDetailsServiceImpl userDetailService;
    @Autowired
    private JwtUtils jwtUtils;

    public String extractToken(HttpServletRequest httpServletRequest){
        final String requestTokenHeader = httpServletRequest.getHeader("Authorization");
        if (StringUtils.hasText(requestTokenHeader) && requestTokenHeader.startsWith("Bearer ")){
            return requestTokenHeader.substring(7);
        }
        return null;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwt = extractToken(httpServletRequest);
            String email = jwtUtils.getEmailFromToken(jwt);
            if (email != null && SecurityContextHolder.getContext().getAuthentication() == null){
                UserDetailsImpl userDetails = userDetailService.loadUserByUsername(email);
                if (userDetails != null && jwtUtils.validateToken(userDetails,jwt)){
                    UsernamePasswordAuthenticationToken authenticationToken = new
                            UsernamePasswordAuthenticationToken(userDetails,null, userDetails.getAuthorities());
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
        }catch (Exception e){
            log.error("Occurred an error authentication: "+ e.getMessage());
        }

        filterChain.doFilter(httpServletRequest,httpServletResponse);
    }
}