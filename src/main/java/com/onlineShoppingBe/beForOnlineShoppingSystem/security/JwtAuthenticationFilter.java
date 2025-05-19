package com.onlineShoppingBe.beForOnlineShoppingSystem.security;


import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtTokenProvider tokenProvider;
    private final CustomUserDetailsService customUserDetailsService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    if("OPTIONS".equalsIgnoreCase(request.getMethod())) {
        filterChain.doFilter(request, response);
        return;
    }
        final String authHeader = request.getHeader("Authorization");
        final String token;
        final String userEmail;

        if(authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        try {
            token = authHeader.substring(7);
            userEmail = tokenProvider.extractUsername(token);

            if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = customUserDetailsService.loadUserByUsername(userEmail);
                if (tokenProvider.isTokenValid(token, userDetails)) {
                    SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
                    UsernamePasswordAuthenticationToken tok = new UsernamePasswordAuthenticationToken(
                            userDetails, token, userDetails.getAuthorities());
                    tok.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    securityContext.setAuthentication(tok);
                    SecurityContextHolder.setContext(securityContext);
                }
            }

            filterChain.doFilter(request, response);
        } catch (StringIndexOutOfBoundsException e) {
            BadRequestException jWtAuthenticationResponse = new BadRequestException("Please provide a valid Bearer token");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            ObjectMapper mapper = new ObjectMapper();
            response.getWriter().write(mapper.writeValueAsString(jWtAuthenticationResponse));
        }
    }
}
