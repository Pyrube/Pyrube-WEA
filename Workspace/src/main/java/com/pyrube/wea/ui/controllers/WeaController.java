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

package com.pyrube.wea.ui.controllers;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * Pyrube-WEA base controller
 * 
 * @author Aranjuez
 * @version Dec 01, 2009
 * @since Pyrube-WEA 1.0
 */
public abstract class WeaController {

	private HttpServletRequest request;
	private HttpServletResponse response;
	
	@Autowired
	private MessageSource messageSource;
	
	//@Autowired
	//private AuthenticationManager authenticationManager;
	
	@Autowired
	private ApplicationContext appContext;
	
	/**
	 * 
	 * @return HttpServletRequest
	 */
	protected HttpServletRequest getRequest() {
		if (request == null) {
			request = ((ServletRequestAttributes) RequestContextHolder
					.currentRequestAttributes()).getRequest();
		}
		return this.request;
	}
	
	/**
	 * 
	 * @return HttpServletResponse
	 */
	protected HttpServletResponse getResponse() {
		if (response == null) {
			response = ((ServletRequestAttributes) RequestContextHolder
					.currentRequestAttributes()).getResponse();
		}
		return this.response;
	}
	
	/**
	 * @return the messageSource
	 */
	public MessageSource getMessageSource() {
		return messageSource;
	}

	/**
	 * @param messageSource the messageSource to set
	 */
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	/**
	 * @return the authenticationManager
	 */
	//public AuthenticationManager getAuthenticationManager() {
	//	return authenticationManager;
	//}

	/**
	 * @param authenticationManager the authenticationManager to set
	 */
	//public void setAuthenticationManager(AuthenticationManager authenticationManager) {
	//	this.authenticationManager = authenticationManager;
	//}

	/**
	 * @return the appContext
	 */
	public ApplicationContext getAppContext() {
		return appContext;
	}

	/**
	 * @param appContext the appContext to set
	 */
	public void setAppContext(ApplicationContext appContext) {
		this.appContext = appContext;
	}

	/**
	 * return the current user details
	 * @return UserDetails
	 */
	public UserDetails getUserDetails() {
		Authentication authen 
			= SecurityContextHolder.getContext().getAuthentication();
		Object obj = authen.getPrincipal();
		if (obj instanceof String) {
			return null;
		} else {
			return (UserDetails) obj;
		}
	}
	
	/**
	 * return a localized message for the given code
	 * @param code
	 * @param args
	 * @param locale
	 * @return String
	 */
	public String getMessage(String code, Object[] args, Locale locale) {
		return this.messageSource.getMessage(code, args, locale);
	}
	
}