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

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.event.AbstractAuthenticationEvent;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import com.pyrube.one.app.logging.Logger;
import com.pyrube.one.app.security.SecurityManagerFactory;
import com.pyrube.one.app.user.SecurityStatus;
import com.pyrube.one.app.user.User;
import com.pyrube.one.app.user.UserHolder;
import com.pyrube.one.lang.Strings;
import com.pyrube.wea.WeaConstants;
import com.pyrube.wea.context.WebContext;
import com.pyrube.wea.context.WebContextHolder;
import com.pyrube.wea.util.Weas;

/**
 * WEA Authentication event listener: Success and Failure
 * a bean configured in security.xml as below:
 *   <beans:bean id="weaAuthenticationEventListener" class="com.pyrube.wea.security.core.WeaAuthenticationEventListener"/>
 * 
 * @author Aranjuez
 * @version Dec 01, 2009
 * @since Pyrube-WEA 1.0
 */
public class WeaAuthenticationEventListener implements ApplicationListener<AbstractAuthenticationEvent> {
	
	/**
	 * logger
	 */
	private static Logger logger = Logger.getInstance(WeaAuthenticationEventListener.class.getName());
	
	/**
	 * constructor
	 */
	public WeaAuthenticationEventListener() { }

	/**
	 * events
	 *  AuthenticationSuccessEvent
	 */
	public void onApplicationEvent(AbstractAuthenticationEvent event) {
		if (logger.isDebugEnabled()) {
			String principalName = (event != null && event.getAuthentication() != null) ? 
				event.getAuthentication().getName() : null;
			logger.debug("Authentication event(" + principalName + "): " + event);
		}
		if (event instanceof AuthenticationSuccessEvent) {
			afterAuthenticated(event);
		} else if (event instanceof AbstractAuthenticationFailureEvent) {
			failedAuthentication(event);
		}
	}
	
	/**
	 * after user has been authenticated
	 * 
	 * @param event
	 */
	private void afterAuthenticated(AbstractAuthenticationEvent event) {
		User user = userFrom(event);
		if (user == null) return;
		// re-hold user onto thread. it will be removed by filter WeaAuthenticationUserProfileFilter
		UserHolder.setUser(user);
		
		if (!usedForSignon(event)) return;
		
		// perform any additional tasks for a concrete project
		if (logger.isDebugEnabled()) logger.debug("After signon-authenticated, and then...");
		try {
			SecurityManagerFactory.getSecurityManager().afterSignon(user, null);
		} catch (Exception e) {
			logger.warn("Failed post-authentication for SecurityManager.", e);
		}
		
		WebContext ctx = WebContextHolder.getWebContext();
		HttpServletRequest request = ctx.getRequest();
		HttpServletResponse response = ctx.getResponse();
		
		// hold the user locale onto session.
		Locale locale = user.locale();
		if (locale != null) Weas.holdLocale(request, locale);

		// re-hold web context onto thread. it will be removed by filter WeaAuthenticationWebContextFilter
		WebContextHolder.setWebContext(new WebContext(request, response));
		
		// notify listeners
		notifyListeners(request, response);
		
		// set cookies
		setCookies(request, response, user);
		
		// register session listeners
		//SessionListenerManager.registerListeners(request.getSession());
	}
	
	/**
	 * failed authentication
	 * 
	 * @param event
	 */
	private void failedAuthentication(AbstractAuthenticationEvent event) {
		AbstractAuthenticationFailureEvent failedEvent = (AbstractAuthenticationFailureEvent) event;
		AuthenticationException failedException = failedEvent.getException();
		String errMsg = (failedException != null) ? failedException.getMessage() : Strings.EMPTY;
		if (logger.isDebugEnabled()) {
			logger.debug("Authentication failed due to " + errMsg + ".");
		}
		
		if (!usedForSignon(event)) return;
		User user = userFrom(event);
		String userKey = null;
		if (user == null) {
			Authentication authen = event.getAuthentication();
			Object principal = (authen != null) ? authen.getPrincipal() : null;
			if (principal != null && principal instanceof String) {
				userKey = (String) principal;
			}
		} else {
			userKey = user.loginame();
		}
		// perform any additional tasks for a concrete project
		if (logger.isDebugEnabled()) logger.debug("After signon-failedAuthentication, and then...");
		try {
			SecurityStatus failedStatus = null;
			if (failedException instanceof BadCredentialsException) {
				failedStatus = SecurityStatus.INVALID_CREDENTIAL;
			} else if (failedException instanceof TooManyAttemptsException) {
				failedStatus = SecurityStatus.TOO_MANY_TRIES;
			} else {
				failedStatus = SecurityStatus.AUTHEN_FAILED;
			}
			SecurityManagerFactory.getSecurityManager().failedSignon(userKey, failedStatus);
		} catch (Exception e) {
			logger.warn("Failed post-authentication for SecurityManager.", e);
		}
	}
	
	private boolean usedForSignon(AbstractAuthenticationEvent event) {
		Authentication authen = event.getAuthentication();
		WeaAuthenticationDetails details = (WeaAuthenticationDetails) authen.getDetails();
		return(WeaConstants.AUTHENTICATION_USED_FOR_SIGNON.equals(details.getUsedFor()));
	}
	
	/**
	 * 
	 * @param event
	 * @return
	 */
	private User userFrom(AbstractAuthenticationEvent event) {
		Authentication authen = event.getAuthentication();
		Object principal = (authen != null) ? authen.getPrincipal() : null;
		if (principal != null && principal instanceof WeaUserDetails) {
			return ((WeaUserDetails) principal).getUser();
		}
		logger.warn("Invalid authentication. Principal is null or not an instance of WeaUserDetails.");
		return null;
	}
	
	/**
	 * notify signon listeners
	 * @param request
	 * @param response
	 */	
	private void notifyListeners(HttpServletRequest request, HttpServletResponse response) {
//		Class[] lsnClasses = WeaConfig.getWeaConfig().getSignonInfo().getSignonListenerClasses();
//		if (lsnClasses != null) {
//			for (int i = 0; i < lsnClasses.length; ++i) {
//				try {
//					SignonListener lsn = (SignonListener) clss[i].newInstance();
//					lsn.notify(request, response);
//				} catch (Exception e) {
//					logger.error("error", e);
//				}
//			}
//		}
	}
	
	/**
	 * set cookies
	 * @param request
	 * @param response
	 */	
	private void setCookies(HttpServletRequest request, HttpServletResponse response, User user) {
		try {
		} catch (Exception e) {
			logger.error("error", e);
		}
	}
}
