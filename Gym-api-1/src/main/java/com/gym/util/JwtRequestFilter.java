package com.gym.util;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.gym.exception.CustomAuthenticationException;
import com.gym.service.Impl.CustomerUserDetailsService;

import java.io.IOException;
import java.util.Collection;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {
	private final JwtUtil jwtUtil;
	private final CustomerUserDetailsService userDetailsService;

	public JwtRequestFilter(JwtUtil jwtUtil,CustomerUserDetailsService userDetailsService) {
		this.jwtUtil = jwtUtil;
		this.userDetailsService = userDetailsService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {
        
		final String authorizationHeader = request.getHeader("Authorization");
		
		if (authorizationHeader != null) {

			String email = null;
			String jwt = null;
			String role = null;

			if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
				jwt = authorizationHeader.substring(7);
				try {
					email = jwtUtil.extractSubject(jwt);
				} catch (CustomAuthenticationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				role = jwtUtil.extractClaims(jwt).get("role").toString();
			}

			if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {

				UserDetails userDetails = null;

//				if (role.equals("trainer")) {
//					userDetails = userDetailsService.loadTrainerUser(email);
//				}
			    if(role.equals("member"))
				{
					userDetails = userDetailsService.loadCustomer(email);
				}
				
//				else if(role.equals("admin"))
//				{
//					userDetails = userDetailsService.loadAdmin(email);
//					System.out.println("Founded");
//				}
				
				
				Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();

				var authToken = new org.springframework.security.authentication.UsernamePasswordAuthenticationToken(
						userDetails, null, authorities);
				authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authToken);
			} 
		}

		chain.doFilter(request, response);
	}
}
