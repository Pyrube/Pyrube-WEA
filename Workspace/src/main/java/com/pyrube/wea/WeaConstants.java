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
	 * custom response headers
	 */
	public static final String RESPONSE_HEADER_MESSAGE_CONTAINER = "Message-Container";
	public static final String RESPONSE_HEADER_MESSAGE_LEVEL = "Message-Level";
	public static final String RESPONSE_HEADER_TARGET_URL = "Target-Url";
	
	/**
	 * The key for session attribute name of upload progress
	 */
	public static final String SESSION_ATTRNAME_UPLOAD_PROGRESS = "WEA.KEY_SESSIONATTRNAME_UPLOADPROGRESS";
	
	/**
	 * The key for session attribute name of current User
	 */
	public static final String SESSION_ATTRNAME_USER = "WEA.KEY_SESSIONATTRNAME_USER";
	
	/**
	 * The key for session attribute name of current Locale
	 */
	public static final String SESSION_ATTRNAME_LOCLAE = "WEA.KEY_SESSIONATTRNAME_LOCALE";
	
	/**
	 * The key for session attribute name of current Theme
	 */
	public static final String SESSION_ATTRNAME_THEME = "WEA.KEY_SESSIONATTRNAME_THEME";
	
	/**
	 * The key for session attribute name of captcha
	 */
	public static final String SESSION_ATTRNAME_CAPTCHA = "WEA.KEY_SESSIONATTRNAME_CAPTCHA";
	
	/**
	 * The key for session attribute name of onboarding for user enrollment
	 */
	public static final String SESSION_ATTRNAME_ONBOARDING = "WEA.KEY_SESSIONATTRNAME_ONBOARDING";
	
	/**
	 * The key for user attribute name of home url
	 */
	public static final String USER_ATTRNAME_HOME_URL = "WEA.KEY_USERATTRNAME_HOMEURL";
}
