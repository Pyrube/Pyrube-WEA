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
import org.springframework.security.core.userdetails.UserDetailsChecker;

/**
 * <code>Pyrube-WEA</code> nothing-to-do post authentication checker for profile/password modification 
 * to override the default post authentication checker provided by Spring Security.
 * When user does these modifications, it needs not to check whether the account credentials have expired, 
 * and been initialized (first login or password reset by administrator).
 * a bean configured in security.xml as below:
 *   <beans:bean id="nothingChecker" class="com.pyrube.wea.security.core.NothingChecker" />
 * 
 * @author Aranjuez
 * @version Dec 01, 2009
 * @since Pyrube-WEA 1.0
 */
public class NothingChecker implements UserDetailsChecker {

	@Override
	public void check(UserDetails user) {

	}

}
