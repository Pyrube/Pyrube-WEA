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

import org.springframework.security.authentication.AnonymousAuthenticationProvider;
import org.springframework.security.web.util.matcher.RequestMatcher;

import com.pyrube.wea.context.WebContextHolder;

/**
 * WEA anonymous authentication provider. It's based on external AnonymousAuthenticationProvider, 
 * matcher added by a requestMatcher
 * 
 * @author Aranjuez
 * @version Dec 01, 2009
 * @since Pyrube-WEA 1.0
 */
public class WeaAnonymousAuthenticationProvider extends AnonymousAuthenticationProvider {

	/**
	 * filter matcher. null means for all
	 */
	private RequestMatcher requestMatcher = null;
	
	/**
	 * constructor
	 */
	public WeaAnonymousAuthenticationProvider(String key) {
		super(key);
	}

	/**
	 * check 
	 */
	public boolean supports(Class<?> authentication) {
		if (requestMatcher != null && 
				(!requestMatcher.matches(WebContextHolder.getWebContext().getRequest()))) {
			return false;
		}
		return super.supports(authentication);
	}
	
	/**
	 * 
	 * @param requestMatcher
	 */
	public void setRequestMatcher(RequestMatcher requestMatcher) {
		this.requestMatcher = requestMatcher;
	}

}
