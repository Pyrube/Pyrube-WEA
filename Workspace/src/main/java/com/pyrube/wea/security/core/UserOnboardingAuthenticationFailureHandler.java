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

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

/**
 * WEA user onboarding authentication failure handler: Success and Failure
 * a bean configured in security.xml as below:
 *   <beans:bean id="userOnboardingAuthenticationFailureHandler" class="com.pyrube.wea.security.core.UserOnboardingAuthenticationFailureHandler">
 *     <beans:property name="defaultFailureUrl" value="/authen/xxxx/failed"/>
 *   </beans:bean>
 * 
 * @author Aranjuez
 * @version Dec 01, 2009
 * @since Pyrube-WEA 1.0
 */
public class UserOnboardingAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {
	
	private String defaultFailureUrl;
	
	/**
	 * constructor
	 */
	public UserOnboardingAuthenticationFailureHandler() { }

	@Override
	public void onAuthenticationFailure(HttpServletRequest request,
			HttpServletResponse response, AuthenticationException exception) 
			throws IOException, ServletException {
		String username = request.getParameter("username");
		super.setDefaultFailureUrl(this.defaultFailureUrl + "?username=" + username);
		super.onAuthenticationFailure(request, response, exception);
	}

	/**
	 * @return the defaultFailureUrl
	 */
	public String getDefaultFailureUrl() {
		return defaultFailureUrl;
	}

	/**
	 * @param defaultFailureUrl the defaultFailureUrl to set
	 */
	public void setDefaultFailureUrl(String defaultFailureUrl) {
		this.defaultFailureUrl = defaultFailureUrl;
	}

}
