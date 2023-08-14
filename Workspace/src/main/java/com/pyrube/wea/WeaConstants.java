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

package com.pyrube.wea;

/**
 * WEA Constants
 * 
 * @author Aranjuez
 * @version Dec 01, 2009
 * @since Pyrube-WEA 1.0
 */

public interface WeaConstants {
	
	/**
	 * request attribute for authentication filter
	 */
	public static final String REQUEST_ATTRNAME_AUTHEN_FILTER = "WEA.KEY_REQUESTATTRNAME_AUTHENFILTER";
	
	/**
	 * request attribute for authentication details property: usedFor
	 */
	public static final String REQUEST_ATTRNAME_AUTHEN_USED_FOR = "WEA.KEY_REQUESTATTRNAME_AUTHENUSEDFOR";

	/**
	 * usedFor of authentication
	 */
	public static final String AUTHENTICATION_USED_FOR_SIGNON = "signon";
	public static final String AUTHENTICATION_USED_FOR_PASSWORD = "password";
	public static final String AUTHENTICATION_USED_FOR_MOBILE = "mobile";
	public static final String AUTHENTICATION_USED_FOR_EMAIL = "email";
	
	/**
	 * 
	 */
	public static final String REQUEST_ATTRNAME_MESSAGES = "WEA.KEY_REQUESTATTRNAME_MESSAGES";
	
	/**
	 * 
	 */
	public static final String RESPONSE_HEADER_MESSAGE_LEVEL = "wea-message-level";
	
	/**
	 * The key for session attribute name of current User
	 */
	public static final String SESSION_ATTRNAME_USER = "WEA.KEY_SESSIONATTRNAME_USER";
	
	/**
	 * The key for session attribute name of current Locale
	 */
	public static final String SESSION_ATTRNAME_LOCLAE = "WEA.KEY_SESSIONATTRNAME_LOCALE";
	
	/**
	 * The key for session attribute name of captcha
	 */
	public static final String SESSION_ATTRNAME_CAPTCHA = "WEA.KEY_SESSIONATTRNAME_CAPTCHA";
}
