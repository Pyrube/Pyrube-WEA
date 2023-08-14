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

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;

import com.pyrube.one.app.user.User;

/**
 * account profile modification authentication filter based on <code>MoreSecureAuthenticationFilter</code>
 * add filter by a RequestMatcher
 * 
 * @author Aranjuez
 * @version Dec 01, 2009
 * @since Pyrube-WEA 1.0
 */
public class AccountProfileModificationAuthenticationFilter extends MoreSecureAuthenticationFilter {

	/**
	 * constructor
	 */
	public AccountProfileModificationAuthenticationFilter() {
		super();
	}

	/**
	 * obtains the current user's name from security context holder in session, 
	 * instead of value posted in request
	 * @param request so that request attributes can be retrieved
	 * @return the username that will be presented in the <code>Authentication</code>
	 * request token to the <code>AuthenticationManager</code>
	 */
	@Override
	protected String obtainUsername(HttpServletRequest request) {
		Authentication authen = SecurityContextHolder.getContext().getAuthentication();
		if (authen != null) {
			Object principal = authen.getPrincipal();
			if (principal != null && principal instanceof WeaUserDetails) {
				User user = ((WeaUserDetails)principal).getUser();
				return user.loginame();
			}
		}
		return null;
	}

	/**
	 * comments out as below, just notice the FailureHandler.
	 * <ol>
	 * <li>Clears the {@link SecurityContextHolder}</li>
	 * <li>Informs the configured <tt>RememberMeServices</tt> of the failed login</li>
	 * </ol>
	 * @see {@link org.springframe.security.web.authentication.AbstractAuthenticationProcessingFilter#unsuccessfulAuthentication(HttpServletRequest, HttpServletResponse, AuthenticationException)}
	 */
	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request,
			HttpServletResponse response, AuthenticationException failed)
			throws IOException, ServletException {
		getFailureHandler().onAuthenticationFailure(request, response, failed);
	}

}
