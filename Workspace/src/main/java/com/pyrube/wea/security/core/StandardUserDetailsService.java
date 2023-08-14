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

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.pyrube.one.app.AppException;
import com.pyrube.one.app.logging.Logger;
import com.pyrube.one.app.user.SecurityStatus;
import com.pyrube.one.app.user.User;

/**
 * <code>Pyrube-WEA</code> standard user details service for authentication with authorization, 
 * like sign-on, loading user info (including password), and user rights (if user is 
 * enabled/authenticated-success). It is used with <code>DAOAuthenticationProvider</code>. 
 * 
 * @author Aranjuez
 * @version Dec 01, 2009
 * @since Pyrube-WEA 1.0
 */
public class StandardUserDetailsService extends WeaUserDetailsService {
	
	/**
	 * logger
	 */
	private static Logger logger = Logger.getInstance(StandardUserDetailsService.class.getName());

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		try {
			if (logger.isDebugEnabled()) logger.debug("Starting to find standard user details.");
			User user = getSecurityManager().findUser(username);
			if (user != null && user.is(SecurityStatus.ENABLED)) {
				user.setRights(getSecurityManager().findUserRights(username));
			}
			
			WeaUserDetails userDetails = new WeaUserDetails(user);
			if (logger.isDebugEnabled()) logger.debug("Ending to find user: " + userDetails.toString());
			return userDetails;
		} catch (AppException e) {
			logger.error("Error to find user.", e);
			throw new UsernameNotFoundException(e.getCode(), e);
		}
	}

}
