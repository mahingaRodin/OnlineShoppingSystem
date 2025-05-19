package com.onlineShoppingBe.beForOnlineShoppingSystem.config;

import com.onlineShoppingBe.beForOnlineShoppingSystem.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;


@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter {
    private final UserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;


    @Override
    protected void doFilterInternal(HttpServletResponse resp, HttpServletRequest req, FilterChain chain)
        throws ServletException, IOException
    {
        final String authHeader = req.getHeader("Authorization");
        //check if the authorization header is valid
        if(StringUtils.isEmpty(authHeader) || !authHeader.startsWith("Bearer ")) {
            chain.doFilter(req, resp);
            return;
        }

        //extract jwt token
        final String jwt = authHeader.substring(7);

        //extract username from the token
        String userEmail = "";
        try {
            userEmail = jwtUtil.extractUserName(jwt);
        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid jwt token");
        }

        //validate the token and set the authentication
        if(org.apache.commons.lang3.StringUtils.isNotEmpty(userEmail) && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);

            if (jwtUtil.isTokenValid(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));

                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        chain.doFilter(req, resp);
    }
}
