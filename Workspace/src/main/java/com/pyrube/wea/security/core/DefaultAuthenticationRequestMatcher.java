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

import com.pyrube.one.lang.Strings;
import com.pyrube.wea.WeaConstants;

/**
 * Default authentication request matcher
 * 
 * @author Aranjuez
 * @version Dec 01, 2009
 * @since Pyrube-WEA 1.0
 */
public class DefaultAuthenticationRequestMatcher extends WeaAuthenticationRequestMatcher {
	
	/**
	 * value from configuration in security.xml
	 */
	private boolean matched = true;
	
	public DefaultAuthenticationRequestMatcher() {}

	@Override
	public boolean isMatched(HttpServletRequest request, Class<?> authentication) {
		String usedFor = (String) request.getAttribute(WeaConstants.REQUEST_ATTRNAME_AUTHEN_USED_FOR);
		if (!Strings.isEmpty(usedFor)) {
			if (!usedFor.equals(this.getUsedFor())) return false;
		}
		return this.matched;
	}
	
	/**
	 * 
	 * @param matched
	 */
	public void setMatched(boolean matched) {
		this.matched = matched;
	}
}
