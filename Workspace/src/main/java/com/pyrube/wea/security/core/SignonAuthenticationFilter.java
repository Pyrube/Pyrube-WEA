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
 * the <code>SignonAuthenticationFilter</code> class is a subclass of <code>MoreSecureAuthenticationFilter</code>
 * for sign-on related functions.
 * 
 * @author Aranjuez
 * @version Dec 01, 2009
 * @since Pyrube-WEA 1.0
 */
public class SignonAuthenticationFilter extends MoreSecureAuthenticationFilter {

	@Override
	protected void setDetails(HttpServletRequest request,
			UsernamePasswordAuthenticationToken authRequest) {
		request.setAttribute(WeaConstants.REQUEST_ATTRNAME_AUTHEN_USED_FOR, WeaConstants.AUTHENTICATION_USED_FOR_SIGNON);
		super.setDetails(request, authRequest);
	}
}
