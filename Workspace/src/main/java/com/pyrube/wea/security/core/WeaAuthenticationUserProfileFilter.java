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

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import com.pyrube.one.app.logging.Logger;
import com.pyrube.one.app.user.User;
import com.pyrube.one.app.user.UserHolder;

/**
 * WEA Authentication last filter. It binds the user profile on thread level
 * configured as the last Core security filter in security.xml
 * <pre>
 *  <http>
 *    <custom-filter position="LAST" ref="weaAuthenticationUserProfileFilter"/>
 *  </http>
 *  <beans:bean id="weaAuthenticationUserProfileFilter" class="com.pyrube.wea.security.core.WeaAuthenticationUserProfileFilter"/>
 * </pre>
 * 
 * @author Aranjuez
 * @version Dec 01, 2009
 * @since Pyrube-ONE 1.0
 */
public class WeaAuthenticationUserProfileFilter extends GenericFilterBean implements
		ApplicationEventPublisherAware {
	
	/**
	 * logger
	 */
	private static Logger logger = Logger.getInstance(WeaAuthenticationUserProfileFilter.class.getName());
	
	/**
	 * constructor
	 */
	public WeaAuthenticationUserProfileFilter() {
		super();
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		Authentication authen = SecurityContextHolder.getContext().getAuthentication();
		if (authen != null) {
			Object principal = authen.getPrincipal();
			if (principal != null && principal instanceof WeaUserDetails) {
				User user = ((WeaUserDetails)principal).getUser();
				UserHolder.setUser(user);
				if (logger.isDebugEnabled()) 
					logger.debug("Bind user (" + UserHolder.getUser().getName() + ") onto thread level");
			} else {
				logger.warn("Principal is null or not an instance of WeaUserDetails either.");
			}
		}
		try {
			chain.doFilter(request, response);
		} catch (ServletException e) {
			throw e;
		} catch (IOException e) {
			throw e;
		} catch (Throwable e) {
			throw new ServletException(e);
		} finally {
			UserHolder.removeUser();
			if (logger.isDebugEnabled()) logger.debug("Unbind user from current thread");
		}
	}

	@Override
	protected void initFilterBean() throws ServletException {
	}

	@Override
	public void setApplicationEventPublisher(
			ApplicationEventPublisher applicationEventPublisher) {
	}
	
}
