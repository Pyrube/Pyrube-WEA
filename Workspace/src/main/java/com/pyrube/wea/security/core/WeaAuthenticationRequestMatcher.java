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

import org.springframework.security.web.util.matcher.RequestMatcher;

import com.pyrube.wea.WeaConstants;

/**
 * Base authentication request matcher
 * 
 * @author Aranjuez
 * @version Dec 01, 2009
 * @since Pyrube-WEA 1.0
 */
public abstract class WeaAuthenticationRequestMatcher implements RequestMatcher {
	
	private Class<?> authentication;
	
	private String usedFor;
	
	/**
	 * constructor
	 */
	public WeaAuthenticationRequestMatcher() { }

	/**
	 * call isMatched(), 
	 * if matches, then set the WeaAuthenticationFilterInfo to request attribute
	 */
	public final boolean matches(HttpServletRequest request) {
		boolean matched = isMatched(request, authentication);
		if (matched) 
			request.setAttribute(WeaConstants.REQUEST_ATTRNAME_AUTHEN_FILTER, null);
		return matched;
	}

	/**
	 * whether request is matched
	 * @param request
	 * @param authentication
	 * @return
	 */
	protected abstract boolean isMatched(HttpServletRequest request, Class<?> authentication);

	/**
	 * @return the authentication
	 */
	public Class<?> getAuthentication() {
		return authentication;
	}

	/**
	 * @param authentication the authentication to set
	 */
	public void setAuthentication(Class<?> authentication) {
		this.authentication = authentication;
	}

	/**
	 * @return the usedFor
	 */
	public String getUsedFor() {
		return usedFor;
	}

	/**
	 * @param usedFor the usedFor to set
	 */
	public void setUsedFor(String usedFor) {
		this.usedFor = usedFor;
	}
}
