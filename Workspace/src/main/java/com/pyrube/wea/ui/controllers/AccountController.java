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

package com.pyrube.wea.ui.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pyrube.one.app.AppMessage;
import com.pyrube.one.app.logging.Logger;
import com.pyrube.one.app.user.User;
import com.pyrube.one.app.user.UserHolder;
import com.pyrube.wea.WeaConstants;
import com.pyrube.wea.security.core.BadCaptchaException;
import com.pyrube.wea.ui.model.Authen;

/**
 * User account controller
 * 
 * @author Aranjuez
 * @version Dec 01, 2009
 * @since Pyrube-WEA 1.0
 */
@Controller
@RequestMapping("acct")
public class AccountController extends WeaController {
	
	/**
	 * logger
	 */
	private static Logger logger = Logger.getInstance(AccountController.class.getName());
	
	@RequestMapping("home")
	public String goHome(Model model) {
		User user = UserHolder.getUser();
		model.addAttribute("user", user);
		return "acct.home";
	}

	@RequestMapping(value = "changePassword")
	public String initChangePassword(Model model) {
		model.addAttribute("authen", new Authen());
		return "acct.change_password";
	}
	
	@RequestMapping(value = "changePassword/success")
	public String successChangePassword(Model model, HttpServletRequest request) {
		AppMessage appMessage = AppMessage.success("message.success.password-modification");
		model.addAttribute(WeaConstants.REQUEST_ATTRNAME_MESSAGES, AppMessage.arrayOf(appMessage));
		model.addAttribute("authen", new Authen());
		return "acct.change_password";
	}
	
	@RequestMapping(value = "changePassword/failed")
	public String failedChangePassword(Model model, HttpServletRequest request) {
		AuthenticationException ae = (AuthenticationException) request.getSession().getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
		String code = ae.getMessage();
		logger.warn("Failed to change password due to :" + code, ae);
		if (ae instanceof BadCaptchaException) {
			code = "message.error.authen.captcha-invalid";
		} else if (ae instanceof BadCredentialsException) {
			code = "message.error.authen.password-wrong";
		} else {
			code = "message.error.authen.password-unchangeable";
		}
		model.addAttribute(WeaConstants.REQUEST_ATTRNAME_MESSAGES, AppMessage.arrayOf(AppMessage.error(code)));
		model.addAttribute("authen", new Authen());
		return "acct.change_password";
	}

	@RequestMapping(value = "changeMobile")
	public String initChangeMobile(Model model) {
		model.addAttribute("authen", new Authen());
		return "acct.change_mobile";
	}
	
	@RequestMapping(value = "changeMobile/success")
	public String successChangeMobile(Model model, HttpServletRequest request) {
		AppMessage appMessage = AppMessage.success("message.success.mobile-modification");
		model.addAttribute(WeaConstants.REQUEST_ATTRNAME_MESSAGES, AppMessage.arrayOf(appMessage));
		model.addAttribute("authen", new Authen());
		return "acct.change_mobile";
	}
	
	@RequestMapping(value = "changeMobile/failed")
	public String failedChangeMobile(Model model, HttpServletRequest request) {
		AuthenticationException ae = (AuthenticationException) request.getSession().getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
		String code = ae.getMessage();
		logger.warn("Failed to change mobile due to :" + code, ae);
		if (ae instanceof BadCaptchaException) {
			code = "message.error.authen.captcha-invalid";
		} else if (ae instanceof BadCredentialsException) {
			code = "message.error.authen.password-wrong";
		} else {
			code = "message.error.authen.mobile-unchangeable";
		}
		model.addAttribute(WeaConstants.REQUEST_ATTRNAME_MESSAGES, AppMessage.arrayOf(AppMessage.error(code)));
		model.addAttribute("authen", new Authen());
		return "acct.change_mobile";
	}

	@RequestMapping(value = "changeEmail")
	public String initChangeEmail(Model model) {
		model.addAttribute("authen", new Authen());
		return "acct.change_email";
	}
	
	@RequestMapping(value = "changeEmail/success")
	public String successChangeEmail(Model model, HttpServletRequest request) {
		AppMessage appMessage = AppMessage.success("message.success.email-modification");
		model.addAttribute(WeaConstants.REQUEST_ATTRNAME_MESSAGES, AppMessage.arrayOf(appMessage));
		model.addAttribute("authen", new Authen());
		return "acct.change_email";
	}
	
	@RequestMapping(value = "changeEmail/failed")
	public String failedChangeEmail(Model model, HttpServletRequest request) {
		AuthenticationException ae = (AuthenticationException) request.getSession().getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
		String code = ae.getMessage();
		logger.warn("Failed to change e-mail due to :" + code, ae);
		if (ae instanceof BadCaptchaException) {
			code = "message.error.authen.captcha-invalid";
		} else if (ae instanceof BadCredentialsException) {
			code = "message.error.authen.password-wrong";
		} else {
			code = "message.error.authen.email-unchangeable";
		}
		model.addAttribute(WeaConstants.REQUEST_ATTRNAME_MESSAGES, AppMessage.arrayOf(AppMessage.error(code)));
		model.addAttribute("authen", new Authen());
		return "acct.change_email";
	}

}
