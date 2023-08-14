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

import org.springframework.security.authentication.AccountStatusException;

/**
 * Thrown if an authentication request is rejected because the account's credentials have
 * been initialized (first login or password reset by administrator).
 * 
 * @author Aranjuez
 * @version Dec 01, 2009
 * @since Pyrube-WEA 1.0
 */
public class CredentialsInitializedException extends AccountStatusException {

	/**
	 * serial version uid
	 */
	private static final long serialVersionUID = 2095130134278077046L;

	/**
	 * Constructs a <code>CredentialsInitializedException</code> with the specified message.
	 *
	 * @param msg the detail message
	 */
	public CredentialsInitializedException(String msg) {
		super(msg);
	}

	/**
	 * Constructs a <code>CredentialsInitializedException</code> with the specified message and
	 * root cause.
	 *
	 * @param msg the detail message
	 * @param t root cause
	 */
	public CredentialsInitializedException(String msg, Throwable t) {
		super(msg, t);
	}
}
