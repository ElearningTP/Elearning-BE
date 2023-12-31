package com.api.learning.ElearningBE.security;

import com.api.learning.ElearningBE.security.impl.UserDetailsImpl;
import com.api.learning.ElearningBE.security.impl.UserDetailsServiceImpl;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
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

    private static final String contentType = "application/json";
    @Autowired
    private UserDetailsServiceImpl userDetailService;
    @Autowired
    private JwtUtils jwtUtils;

    private void handleValidationException(String message,HttpServletResponse httpServletResponse) throws IOException {
        final String response = "{\"result\": false, \"message\": \"" + message + "\"}";
        httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        httpServletResponse.setContentType(contentType);
        httpServletResponse.getWriter().write(response);
        httpServletResponse.getWriter().flush();
        httpServletResponse.getWriter().close();
    }
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
        }catch (ExpiredJwtException e){
            log.error("Occurred an error authentication: "+e.getMessage());
            handleValidationException("Token has expired",httpServletResponse);
            return;
        }catch (MalformedJwtException | SignatureException e){
            log.error("Occurred an error authentication: "+ e.getMessage());
            handleValidationException("Invalid token",httpServletResponse);
            return;
        }catch (Exception e){
            log.error("Occurred an error authentication: "+ e.getMessage());
        }
        filterChain.doFilter(httpServletRequest,httpServletResponse);
    }
}
