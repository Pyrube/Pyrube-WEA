/*******************************************************************************
 * Copyright 2019, 2023 Aranjuez Poon.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/

package com.pyrube.wea.security.core;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import com.pyrube.one.lang.Strings;
import com.pyrube.wea.WeaConfig;
import com.pyrube.wea.util.Weas;

/**
 * a <code>Pyrube-WEA</code> authentication filter provides more secure authentication with captcha 
 * checking based on <code>UsernamePasswordAuthenticationFilter</code>.
 * filter added by a RequestMatcher
 * 
 * @author Aranjuez
 * @version Dec 01, 2009
 * @since Pyrube-WEA 1.0
 */
public class MoreSecureAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	public static final String SPRING_SECURITY_FORM_CAPTCHA_KEY = "captcha";

	private String captchaParameter = SPRING_SECURITY_FORM_CAPTCHA_KEY;

	/**
	 * request matcher. null means for all
	 */
	private RequestMatcher requestMatcher = null;
	
	/**
	 * constructor
	 */
	public MoreSecureAuthenticationFilter() {
		super();
	}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		if (requestMatcher != null
				&& (!requestMatcher.matches((HttpServletRequest) request))) {
			chain.doFilter(request, response);
			return;
		}
		super.doFilter(request, response, chain);
	}
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request,
		HttpServletResponse response) throws AuthenticationException {
		
		if (WeaConfig.getWeaConfig().getCaptcha().isEnabled()) {

			String captcha = obtainCaptcha(request);
			
			if (captcha == null) {
				captcha = Strings.EMPTY;
			}
			
			captcha = captcha.trim();
			
			if (!Weas.validateCaptcha(request, captcha)) {
				throw new BadCaptchaException("Captcha is invalid.");
			}
		}
		
		return super.attemptAuthentication(request, response);
	}

	/**
	 * Enables subclasses to override the composition of the captcha, such as by
	 * including additional values and a separator.
	 *
	 * @param request so that request attributes can be retrieved
	 *
	 * @return the captcha that will be presented in the <code>Authentication</code>
	 * request token to the <code>AuthenticationManager</code>
	 */
	protected String obtainCaptcha(HttpServletRequest request) {
		return request.getParameter(captchaParameter);
	}
	
	/**
	 * 
	 * @param requestMatcher
	 */
	public void setRequestMatcher(RequestMatcher requestMatcher) {
		this.requestMatcher = requestMatcher;
	}

}
