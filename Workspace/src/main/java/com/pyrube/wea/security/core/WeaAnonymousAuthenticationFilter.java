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

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

/**
 * WEA anonymous authentication filter based on AnonymousAuthenticationFilter
 * add filter by a RequestMatcher
 * <pre>
 *  <http>
 *    <custom-filter position="ANONYMOUS_FILTER" ref="WeaAnonymousAuthenticationFilter"/>
 *    <anonymous enabled="false" />
 *  </http>
 *  <beans:bean id="weaAnonymousAuthenticationFilter" class="com.pyrube.wea.security.core.WeaAnonymousAuthenticationFilter">
 *    <beans:constructor-arg index="0">
 *      <beans:bean class="com.pyrube.wea.security.core.WeaUserDetails" />
 *    </beans:constructor-arg>
 *  </beans:bean>
 * </pre>
 * 
 * @author Aranjuez
 * @version Dec 01, 2009
 * @since Pyrube-WEA 1.0
 */
public class WeaAnonymousAuthenticationFilter extends AnonymousAuthenticationFilter {

	/**
	 * request matcher. null means for all
	 */
	private RequestMatcher requestMatcher = null;
	
	/**
	 * constructor
	 * @param key
	 */
	public WeaAnonymousAuthenticationFilter(Object principal) {
		super("wea", principal, AuthorityUtils.createAuthorityList("ROLE_ANONYMOUS"));
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
	
	/**
	 * 
	 * @param requestMatcher
	 */
	public void setRequestMatcher(RequestMatcher requestMatcher) {
		this.requestMatcher = requestMatcher;
	}

}
