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

import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsChecker;

import com.pyrube.one.app.Apps;
import com.pyrube.one.app.logging.Logger;

/**
 * <code>Pyrube-WEA</code> pre-authentication checker for more pre-authentication 
 * checks. When user logs on, it will check whether the account can be auto-unlocked if it
 * is locked.
 * a bean configured in security.xml as below:
 *   <beans:bean id="accountStatusChecker" class="com.pyrube.wea.security.core.AccountStatusChecker" />
 * 
 * @author Aranjuez
 * @version Dec 01, 2009
 * @since Pyrube-WEA 1.0
 */
public class AccountStatusChecker implements UserDetailsChecker {

	/**
	 * logger
	 */
	private static Logger logger = Apps.a.logger.named(AccountStatusChecker.class.getName());

	@Override
	public void check(UserDetails user) {
		
		if (!user.isAccountNonLocked()) {
			if (logger.isDebugEnabled()) logger.debug("User account is locked");
			if (!(user instanceof WeaUserDetails)) throw new LockedException("User account is locked");
			
			if (!Apps.the.sys_default.pass_policy().checks.autounlocking(((WeaUserDetails) user).getUser().getLastAttemptTime())) {
				throw new LockedException("User account is locked");
			}
			// or user will be auto-unlocked without LockedException thrown
			if (logger.isDebugEnabled()) logger.debug("User account can be auto-unlocked");

		}

		if (!user.isEnabled()) {
			if (logger.isDebugEnabled()) logger.debug("User account is disabled");
			throw new DisabledException("User account is disabled");
		}

		if (!user.isAccountNonExpired()) {
			if (logger.isDebugEnabled()) logger.debug("User account has expired");
			throw new AccountExpiredException("User account has expired");
		}

	}

}
