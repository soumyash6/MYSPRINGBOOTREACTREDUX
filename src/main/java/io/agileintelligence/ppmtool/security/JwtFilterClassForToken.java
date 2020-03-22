package io.agileintelligence.ppmtool.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import io.agileintelligence.ppmtool.domain.User;
import io.agileintelligence.ppmtool.services.CustomerUserDetailService;

public class JwtFilterClassForToken extends OncePerRequestFilter {
	@Autowired
	private JwtTokenProvider tokenProvider;

	@Autowired
	private CustomerUserDetailService custuserservice;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		try {
			String request_Token = getToken(request);
			boolean validateToken = tokenProvider.validateToken(request_Token);
			System.out.print("validateToken: ");
			System.out.println(validateToken);
			if (StringUtils.hasText(request_Token) && validateToken) {
				String token_id = tokenProvider.getUserIDFrom_token(request_Token);
				System.out.println("token_id: ");
				System.out.println(token_id);
				User user = custuserservice.loadUserById(Long.parseLong(token_id));

				UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user,
						null, user.getAuthorities());
				authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authenticationToken);
			}

		} catch (Exception e) {
			logger.error("error seting context", e);
		}
		filterChain.doFilter(request, response);
	}

	private String getToken(HttpServletRequest request) {
		String token_WithBerear = request.getHeader(SecurityConstant.HEADER_STRING);
		if (StringUtils.hasText(token_WithBerear) && token_WithBerear.startsWith(SecurityConstant.TOKEN_PREFIX)) {
			String splitToken = token_WithBerear.substring(7, token_WithBerear.length());
			return splitToken;
		}
		return null;
	}

}
