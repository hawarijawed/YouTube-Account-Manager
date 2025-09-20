package com.YoutubeAccount.Manager.filter;

import com.YoutubeAccount.Manager.utility.JwtUtils;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
        throws ServletException, IOException{
        String authHeader = request.getHeader("Authorization");
//        log.info("*** Request content: {}", request);
//        log.info("** AuhtHeadr content: {}", authHeader);

        if(authHeader != null && authHeader.startsWith("Bearer")){
            String token = authHeader.substring(7);

            if(jwtUtils.isTokenValid(token)){
                Claims claims = jwtUtils.extractAllClaims(token);
                String userId = claims.getSubject();
                String username = (String) claims.get("username");

                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userId, null, null);

                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        filterChain.doFilter(request, response);
    }
}
