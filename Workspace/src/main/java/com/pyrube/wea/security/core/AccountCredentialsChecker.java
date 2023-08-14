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

import org.springframework.security.authentication.CredentialsExpiredException;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsChecker;

import com.pyrube.one.app.Apps;
import com.pyrube.one.app.logging.Logger;

/**
 * <code>Pyrube-WEA</code> post authentication checker for more post-authentication 
 * checks. When user logs on, it will check whether the account credentials have expired, and 
 * been initialized (first login or password reset by administrator).
 * a bean configured in security.xml as below:
 *   <beans:bean id="accountCredentialsChecker" class="com.pyrube.wea.security.core.AccountCredentialsChecker" />
 * 
 * @author Aranjuez
 * @version Dec 01, 2009
 * @since Pyrube-WEA 1.0
 */
public class AccountCredentialsChecker implements UserDetailsChecker {

	/**
	 * logger
	 */
	private static Logger logger = Apps.a.logger.named(AccountCredentialsChecker.class.getName());

	@Override
	public void check(UserDetails user) {
		if (!user.isCredentialsNonExpired()) {
			if (logger.isDebugEnabled()) logger.debug("User account credentials have expired");
			throw new CredentialsExpiredException("User account credentials have expired.");
		}
		
		if ((user instanceof WeaUserDetails) && !((WeaUserDetails) user).isCredentialsNonInitialized()) {
			if (logger.isDebugEnabled()) logger.debug("User acccount credentials have been initialized");
			throw new CredentialsInitializedException("User account credentials have been initialized.");
		}

	}

}
