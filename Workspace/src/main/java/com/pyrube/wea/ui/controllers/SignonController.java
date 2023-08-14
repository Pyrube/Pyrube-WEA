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

import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pyrube.one.app.AppMessage;
import com.pyrube.one.app.Apps;
import com.pyrube.one.app.logging.Logger;
import com.pyrube.one.app.security.AppPolicy;
import com.pyrube.one.app.user.User;
import com.pyrube.wea.WeaConstants;
import com.pyrube.wea.security.core.BadCaptchaException;
import com.pyrube.wea.security.core.TooManyAttemptsException;
import com.pyrube.wea.ui.model.Authen;

/**
 * Sign-on controller
 * 
 * @author Aranjuez
 * @version Dec 01, 2009
 * @since Pyrube-WEA 1.0
 */
@Controller
@RequestMapping("authen/signon")
public class SignonController extends WeaController {
	
	/**
	 * logger
	 */
	private static Logger logger = Logger.getInstance(SignonController.class.getName());
	
	@RequestMapping(value = "")
	public String login(Model model) {
		if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
			if (!User.GUEST.getName().equals(SecurityContextHolder.getContext().getAuthentication().getName()))
				return("redirect:/acct/home");
		}
		model.addAttribute("authen", new Authen());
		return "signon";
	}
	
	@RequestMapping(value = "failed")
	public String failedLogin(Model model, HttpServletRequest request) {
		AuthenticationException ae = (AuthenticationException) request.getSession().getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
		AppMessage[] messages = null;
		AppPolicy passPolicy = Apps.the.sys_default.pass_policy();
		if (ae instanceof BadCaptchaException) {
			messages = Apps.some.messages(Apps.a.message.with.error("message.error.authen.captcha-invalid"));
		} else if (ae instanceof UsernameNotFoundException) {
			messages = Apps.some.messages(Apps.a.message.with.error("message.error.authen.user-not-found"));
		} else if (ae instanceof BadCredentialsException) {
			if (passPolicy.has.attempt_limit()) {
				messages = Apps.some.messages(Apps.a.message.with.error("message.error.authen.password-wrong"),
											Apps.a.message.with.warn("message.warn.authen.account-locking").params(passPolicy.max_attempts()));
			} else {
				messages = Apps.some.messages(Apps.a.message.with.error("message.error.authen.password-wrong"));
			}
		} else if (ae instanceof TooManyAttemptsException) {
			if (passPolicy.has.locking_period()) {
				messages = Apps.some.messages(Apps.a.message.with.error("message.error.authen.too-many-attempts"), 
											Apps.a.message.with.info("message.info.authen.auto-unlocking").params(passPolicy.locking_period()));
			} else {
				messages = Apps.some.messages(Apps.a.message.with.error("message.error.authen.too-many-attempts"));
			}
		} else if (ae instanceof LockedException) {
			if (passPolicy.has.locking_period()) {
				messages = Apps.some.messages(Apps.a.message.with.error("message.error.authen.user-locked"), 
											Apps.a.message.with.info("message.info.authen.auto-unlocking").params(passPolicy.locking_period()));
			} else {
				messages = Apps.some.messages(Apps.a.message.with.error("message.error.authen.user-locked"));
			}
		} else if (ae instanceof AccountExpiredException) {
			messages = Apps.some.messages(Apps.a.message.with.error("message.error.authen.user-expired"));
		} else if (ae instanceof DisabledException) {
			messages = Apps.some.messages(Apps.a.message.with.error("message.error.authen.user-disabled"));
		} else {
			messages = Apps.some.messages(Apps.a.message.with.error(ae.getMessage()));
		}
		logger.warn("Failed to sign-on due to " + ae.getMessage());
		model.addAttribute(WeaConstants.REQUEST_ATTRNAME_MESSAGES, messages);
		model.addAttribute("authen", new Authen());
		return "signon";
	}
	
}
