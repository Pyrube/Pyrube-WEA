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

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.web.authentication.WebAuthenticationDetails;

import com.pyrube.wea.WeaConstants;

/**
 * the <code>WeaAuthenticationDetails</code> is a subclass of <code>WebAuthenticationDetails</code> 
 * to hold more details for authentication
 * 
 * @author Aranjuez
 * @version Dec 01, 2009
 * @since Pyrube-WEA 1.0
 */
public class WeaAuthenticationDetails extends WebAuthenticationDetails {

	/**
	 * serial version uid
	 */
	private static final long serialVersionUID = 990206372692014378L;
	
	private String usedFor;
	
	private String password;
	
	private String mobile;
	
	private String email;
	
	/**
	 * default constructor
	 * @param request
	 */
	public WeaAuthenticationDetails(HttpServletRequest request) {
		super(request);
		this.usedFor = (String) request.getAttribute(WeaConstants.REQUEST_ATTRNAME_AUTHEN_USED_FOR);
	}
	/**
	 * @return the usedFor
	 */
	public String getUsedFor() {
		return usedFor;
	}
	/**
	 * @param usedFor the usedFor to set
	 */
	public void setUsedFor(String usedFor) {
		this.usedFor = usedFor;
	}
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * @return the mobile
	 */
	public String getMobile() {
		return mobile;
	}
	/**
	 * @param mobile the mobile to set
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

}
