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

package com.pyrube.wea.ui.model;

import com.pyrube.one.app.persistence.Data;

/**
 * the <code>Authen</code> data for the authentication as below:
 * 1. sign-on
 * 2. profile modification
 * 
 * @author Aranjuez
 * @version Dec 01, 2009
 * @since Pyrube-WEA 1.0
 */
public class Authen extends Data<String> {
	/**
	 * serial version uid
	 */
	private static final long serialVersionUID = 12799823210818211L;
	
	/**
	 * instance fields
	 */
	private String username;
	private String password;
	private String password0; // old password
	private String password1; // new password
	private String password2; // confirm password
	private String mobile;
	private String email;
	private String captcha;
	
	/**
	 * Constructor
	 */
	public Authen() {
		
	}
	
	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}
	
	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
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
	 * @return the password0
	 */
	public String getPassword0() {
		return password0;
	}
	
	/**
	 * @param password0 the password0 to set
	 */
	public void setPassword0(String password0) {
		this.password0 = password0;
	}
	
	/**
	 * @return the password1
	 */
	public String getPassword1() {
		return password1;
	}
	
	/**
	 * @param password1 the password1 to set
	 */
	public void setPassword1(String password1) {
		this.password1 = password1;
	}
	
	/**
	 * @return the password2
	 */
	public String getPassword2() {
		return password2;
	}
	
	/**
	 * @param password2 the password2 to set
	 */
	public void setPassword2(String password2) {
		this.password2 = password2;
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

	/**
	 * @return the captcha
	 */
	public String getCaptcha() {
		return captcha;
	}

	/**
	 * @param captcha the captcha to set
	 */
	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}
	
}
