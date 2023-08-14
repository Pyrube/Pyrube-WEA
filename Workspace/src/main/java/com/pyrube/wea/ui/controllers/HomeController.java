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

import com.pyrube.one.app.Apps;
import com.pyrube.one.app.logging.Logger;
import com.pyrube.one.app.security.SecurityManagerFactory;
import com.pyrube.one.app.user.User;
import com.pyrube.one.app.user.UserExt;
import com.pyrube.wea.ui.model.Home;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * User/guest home controller. Login user can edit its own details
 * 
 * @author Aranjuez
 * @version Dec 01, 2009
 * @since Pyrube-WEA 1.0
 */
@Controller
@RequestMapping({ "user" })
public class HomeController extends WeaController {

	/**
	 * logger
	 */
	private static Logger logger = Apps.a.logger.named(HomeController.class.getName());

	@RequestMapping({ "home" })
	public String showUserHome(Model model) {
		Home home = new Home();
		User profile = Apps.the.user();
		home.setProfile(profile);
		if (!profile.isGuest()) {
			User details = SecurityManagerFactory.getSecurityManager().findUser(profile.loginame());
			home.setDetails(details);
		}
		model.addAttribute("home", home);
		return "user.user_home";
	}

	@RequestMapping({ "details/edit" })
	public String initUserEdit(Model model) {
		User user = SecurityManagerFactory.getSecurityManager().findUser(Apps.the.user.loginame());
		Home details = new Home();
		details.setNick(user.getExt().getNick());
		details.setGender(user.getExt().getGender());
		details.setBirthdate(user.getExt().getBirthdate());
		details.setCountry(user.getExt().getCountry());
		details.setLocale(user.locale().toString());
		model.addAttribute("details", details);
		return "user.user_edit";
	}

	@ResponseBody
	@RequestMapping({ "details/edit/save" })
	public User saveUserDetails(@ModelAttribute Home model) {
		String loginame = Apps.the.user.loginame();
		if (logger.isDebugEnabled()) logger.debug("user (" + loginame + ") has details edited");
		UserExt extDetails = new UserExt();
		extDetails.setNick(model.getNick());
		extDetails.setGender(model.getGender());
		extDetails.setBirthdate(model.getBirthdate());
		extDetails.setCountry(model.getCountry());
		User details = new User();
		details.setExt(extDetails);
		details.setLogin(loginame);
		details.setLocale(Apps.a.locale.of(model.getLocale()).value());
		details = SecurityManagerFactory.getSecurityManager().updateUserDetails(details);
		return details;
	}
}
