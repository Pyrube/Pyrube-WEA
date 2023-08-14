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

/**
 * <code>Pyrube-WEA</code> core interface which provides the ability for user onboarding.
 * 
 * @author Aranjuez
 * @version Dec 01, 2009
 * @since Pyrube-WEA 1.0
 */
public interface UserOnboardingService {

	/**
	 * force-modifies the user's password when it's first login or password reset by administrator
	 *
	 * @param user the user to modify the password for
	 * @param password the new password to change to
	 * @throws AppException
	 */
	UserDetails forceChangePassword(UserDetails user, String password) throws AppException;

}
