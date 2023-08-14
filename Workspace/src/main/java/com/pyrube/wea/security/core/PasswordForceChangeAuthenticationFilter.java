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

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import com.pyrube.wea.WeaConstants;

/**
 * password force-change authentication filter based on <code>UserOnboardingAuthenticationFilter</code>
 * add filter by a RequestMatcher
 * 
 * @author Aranjuez
 * @version Dec 01, 2009
 * @since Pyrube-WEA 1.0
 */
public class PasswordForceChangeAuthenticationFilter extends UserOnboardingAuthenticationFilter {

	public static final String SPRING_SECURITY_FORM_PARAMETER0_KEY = "password0";

	public static final String SPRING_SECURITY_FORM_PARAMETER1_KEY = "password1";

	private String password0Parameter = SPRING_SECURITY_FORM_PARAMETER0_KEY;

	private String password1Parameter = SPRING_SECURITY_FORM_PARAMETER1_KEY;

	/**
	 * constructor
	 */
	public PasswordForceChangeAuthenticationFilter() {
		super();
	}

	@Override
	protected void setDetails(HttpServletRequest request,
			UsernamePasswordAuthenticationToken authRequest) {
		request.setAttribute(WeaConstants.REQUEST_ATTRNAME_AUTHEN_USED_FOR, WeaConstants.AUTHENTICATION_USED_FOR_PASSWORD);
		super.setDetails(request, authRequest);
		// keep the new password changed to in authentication details
		((WeaAuthenticationDetails) authRequest.getDetails()).setPassword(obtainPassword1(request));
	}

	@Override
	protected String obtainPassword(HttpServletRequest request) {
		return request.getParameter(password0Parameter);
	}
	
	protected String obtainPassword1(HttpServletRequest request) {
		return request.getParameter(password1Parameter);
	}

	/**
	 * @return the password0Parameter
	 */
	public String getPassword0Parameter() {
		return password0Parameter;
	}

	/**
	 * @param password0Parameter the password0Parameter to set
	 */
	public void setPassword0Parameter(String password0Parameter) {
		this.password0Parameter = password0Parameter;
	}

	/**
	 * @return the password1Parameter
	 */
	public String getPassword1Parameter() {
		return password1Parameter;
	}

	/**
	 * @param password1Parameter the password1Parameter to set
	 */
	public void setPassword1Parameter(String password1Parameter) {
		this.password1Parameter = password1Parameter;
	}

}
