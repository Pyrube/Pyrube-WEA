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

import com.pyrube.one.app.AppException;
import com.pyrube.one.app.security.SecurityManager;
import com.pyrube.one.app.user.User;

/**
 * WEA account profile service implementation for managing account profile info, 
 * like credentials, mobile and email. It is used with WeaDaoAuthenticationProvider.
 * 
 * @author Aranjuez
 * @version Dec 01, 2009
 * @since Pyrube-WEA 1.0
 */
public class WeaAccountProfileService implements AccountProfileService {
	
	/**
	 * security manager
	 */
	private SecurityManager securityManager;

	@Override
	public UserDetails changePassword(UserDetails user, String password) throws AppException {
		User $user = securityManager.changePassword(((WeaUserDetails) user).getUser(), password);
		((WeaUserDetails) user).getUser().setCredentials($user.getCredentials());
		return user;
	}

	@Override
	public UserDetails changeMobile(UserDetails user, String mobile) throws AppException {
		User $user = securityManager.changeMobile(((WeaUserDetails) user).getUser(), mobile);
		((WeaUserDetails) user).getUser().setMobile($user.getMobile());
		return user;
	}

	@Override
	public UserDetails changeEmail(UserDetails user, String email) throws AppException {
		User $user = securityManager.changeEmail(((WeaUserDetails) user).getUser(), email);
		((WeaUserDetails) user).getUser().setEmail($user.getEmail());
		return user;
	}

	/**
	 * @return the securityManager
	 */
	public SecurityManager getSecurityManager() {
		return securityManager;
	}

	/**
	 * @param securityManager the securityManager to set
	 */
	public void setSecurityManager(SecurityManager securityManager) {
		this.securityManager = securityManager;
	}
}
