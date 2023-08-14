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

import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

/**
 * the <code>WeaAuthenticationDetailsSource</code> is a subclass of <code>WebAuthenticationDetailsSource</code> 
 * to create a <code>WeaAuthenticationDetails</code>
 * 
 * @author Aranjuez
 * @version Dec 01, 2009
 * @since Pyrube-WEA 1.0
 */
public class WeaAuthenticationDetailsSource extends WebAuthenticationDetailsSource {

	/** 
	 * 
	 * @param context the <code>HttpServletRequest</code> object.
	 * @return the <code>WeaAuthenticationDetails</code> containing information about the
	 * current request
	 */
	public WeaAuthenticationDetails buildDetails(HttpServletRequest context) {
		return new WeaAuthenticationDetails(context);
	}
}
