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

import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

/**
 * Authentication failure event for too many attempts
 * @author Aranjuez
 * @version Dec 01, 2009
 * @since Pyrube-WEA 1.0
 */
public class AuthenticationFailureTooManyAttemptsEvent extends AbstractAuthenticationFailureEvent {
	/**
	 * serial version uid
	 */
	private static final long serialVersionUID = 4424726476614088279L;

	/**
	 * constructor
	 * @param authentication
	 * @param exception
	 */
	public AuthenticationFailureTooManyAttemptsEvent(Authentication authentication,
			AuthenticationException exception) {
		super(authentication, exception);
	}
}
