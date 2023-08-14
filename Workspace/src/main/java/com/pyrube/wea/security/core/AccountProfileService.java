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
 * <code>Pyrube-WEA</code> core interface which manages account profile data.
 * 
 * @author Aranjuez
 * @version Dec 01, 2009
 * @since Pyrube-WEA 1.0
 */
public interface AccountProfileService {

	/**
	 * modify the current user's password. 
	 *
	 * @param user the user to modify the password for
	 * @param password the new password to change to
	 * @throws AppException
	 */
	UserDetails changePassword(UserDetails user, String password) throws AppException;

	/**
	 * modify the current user's mobile. 
	 *
	 * @param user the user to modify the mobile for
	 * @param mobile the new mobile to change to
	 * @throws AppException
	 */
	UserDetails changeMobile(UserDetails user, String mobile) throws AppException;

	/**
	 * modify the current user's email. 
	 *
	 * @param user the user to modify the email for
	 * @param email the new email to change to
	 * @throws AppException
	 */
	UserDetails changeEmail(UserDetails user, String email) throws AppException;
}
