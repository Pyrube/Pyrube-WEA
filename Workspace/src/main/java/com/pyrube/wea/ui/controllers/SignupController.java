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

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pyrube.one.app.Apps;
import com.pyrube.one.app.logging.Logger;
import com.pyrube.one.app.security.SecurityManagerFactory;
import com.pyrube.one.app.user.Authen;
import com.pyrube.one.app.user.Home;
import com.pyrube.one.app.user.Onboarding;
import com.pyrube.wea.WeaConstants;

/**
 * Sign-up controller
 * 
 * @author Aranjuez
 * @version Oct 01, 2023
 * @since Pyrube-WEA 1.1
 */
@Controller
@RequestMapping("authen/signup")
public class SignupController extends WeaController {
	
	/**
	 * logger
	 */
	private static Logger logger = Apps.a.logger.named(SignupController.class.getName());

	@RequestMapping(value = "")
	public String enroll(Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		Onboarding onboarding = (Onboarding) session.getAttribute(WeaConstants.SESSION_ATTRNAME_ONBOARDING);
		if (onboarding == null) onboarding = Apps.a.data(Onboarding.class).id(UUID.randomUUID().toString());
		session.setAttribute(WeaConstants.SESSION_ATTRNAME_ONBOARDING, onboarding);
		model.addAttribute("onboarding", onboarding);
		return "authen.signup";
	}

	@RequestMapping(value = "general")
	public String initGeneralEnroll(Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		Onboarding onboarding = (Onboarding) session.getAttribute(WeaConstants.SESSION_ATTRNAME_ONBOARDING);
		Authen authen = onboarding.getAuthen();
		if (authen == null) authen = Apps.a.data(Authen.class);
		model.addAttribute("authen", authen);
		return "authen.signup_general";
	}

	@ResponseBody
	@RequestMapping(value = "unique/username/{username}")
	public boolean ifUsernameUnique(@PathVariable String username, Model model) {
		return(SecurityManagerFactory.getSecurityManager().findUser(username) == null);
	}

	@ResponseBody
	@RequestMapping(value = "general/save")
	public Onboarding enrollSaveGeneral(@ModelAttribute Authen model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		Onboarding onboarding = (Onboarding) session.getAttribute(WeaConstants.SESSION_ATTRNAME_ONBOARDING);
		onboarding.setAuthen(model);
		return onboarding;
	}

	@RequestMapping(value = "details")
	public String initDetailsEnroll(Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		Onboarding onboarding = (Onboarding) session.getAttribute(WeaConstants.SESSION_ATTRNAME_ONBOARDING);
		Home details = onboarding.getDetails();
		if (details == null) details = Apps.a.data(Home.class);
		model.addAttribute("details", details);
		return "authen.signup_details";
	}

	@ResponseBody
	@RequestMapping(value = "details/save")
	public Onboarding enrollSaveDetails(@ModelAttribute Home model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		Onboarding onboarding = (Onboarding) session.getAttribute(WeaConstants.SESSION_ATTRNAME_ONBOARDING);
		onboarding.setDetails(model);
		return onboarding;
	}

	@RequestMapping(value = "summary")
	public String initSummary(Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		Onboarding onboarding = (Onboarding) session.getAttribute(WeaConstants.SESSION_ATTRNAME_ONBOARDING);
		model.addAttribute("onboarding", onboarding);
		return "authen.signup_summary";
	}

	@ResponseBody
	@RequestMapping(value = "submit")
	public Onboarding enrollSubmit(Model model, HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		Onboarding onboarding = (Onboarding) session.getAttribute(WeaConstants.SESSION_ATTRNAME_ONBOARDING);
		if (logger.isDebugEnabled()) logger.debug("user (" + onboarding.getAuthen().getUsername() + ") will be onboard");
		//SecurityManagerFactory.getSecurityManager().enroll(onboarding);
		session.removeAttribute(WeaConstants.SESSION_ATTRNAME_ONBOARDING);
		response.setHeader(WeaConstants.RESPONSE_HEADER_TARGET_URL, "authen/signon");
		return onboarding;
	}
	
}
