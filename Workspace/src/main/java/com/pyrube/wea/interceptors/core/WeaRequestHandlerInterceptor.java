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

package com.pyrube.wea.interceptors.core;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.pyrube.one.app.logging.Logger;

/**
 * WEA Http request handler interceptor
 * 	manage the page transaction 
 * 
 * @author Aranjuez
 * @version Dec 01, 2009
 * @since Pyrube-WEA 1.0
 */
public class WeaRequestHandlerInterceptor extends HandlerInterceptorAdapter {
	
	/**
	 * logger
	 */
	private static Logger logger = Logger.getInstance(WeaRequestHandlerInterceptor.class.getName());
	
	/**
	 * whether it is enabled
	 */
	private boolean enabled = true;
	
	/**
	 * constructor
	 */
	public WeaRequestHandlerInterceptor() {
	}
	
	/**
	 * handle request before the Controller method is called
	 */
	public boolean preHandle(HttpServletRequest request, 
			HttpServletResponse response, Object handler)
			throws Exception {
		if (logger.isDebugEnabled()) logger.debug("Performs pre-handling...");
		// pre-handle Locale
		if (handler instanceof HandlerMethod) {
		}
		return true;
	}

	/**
	 * @return the enabled
	 */
	public boolean isEnabled() {
		return enabled;
	}

	/**
	 * @param enabled the enabled to set
	 */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
}
