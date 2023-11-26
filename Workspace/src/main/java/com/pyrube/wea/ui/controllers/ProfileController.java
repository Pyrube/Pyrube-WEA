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

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ThemeResolver;

import com.pyrube.one.app.AppException;
import com.pyrube.one.app.Apps;
import com.pyrube.one.app.logging.Logger;
import com.pyrube.one.app.menu.MenuItem;
import com.pyrube.one.app.user.User;
import com.pyrube.one.lang.Strings;
import com.pyrube.wea.security.core.WeaUserDetails;
import com.pyrube.wea.util.Weas;

/**
 * User/guest menu/locale/profile controller.
 * User can change to one of the supported locales, 
 * and change locale in different places:
 *   on login page, user can choose a locale
 *   after user login, user can change the locale also
 * 
 * @author Aranjuez
 * @version Dec 01, 2009
 * @since Pyrube-WEA 1.0
 */
@Controller
@RequestMapping("user")
public class ProfileController extends WeaController {
	
	/**
	 * the logger
	 */
	private static Logger logger = Logger.getInstance(ProfileController.class.getName());

	@Autowired
	private ThemeResolver themeResolver;

	@RequestMapping("profile")
	public String showUserProfile(Model model) {
		model.addAttribute("profile", Apps.the.user());
		return "user.user_profile";
	}

	@ResponseBody
	@RequestMapping("menu")
	public List<MenuItem> myLevel1MenuItems() {
		return Weas.mySubmenuItems(MenuItem.MENU_ROOT, MenuItem.MENU_ROOT.getId());
	}

	@ResponseBody
	@RequestMapping("menu/children/{id}")
	public List<MenuItem> mySubmenuItems(@PathVariable String id) {
		return Weas.mySubmenuItems(MenuItem.MENU_ROOT, id);
	}

	@ResponseBody
	@RequestMapping("menu/parents")
	public List<MenuItem> findMenuPath(@RequestParam String page) {
		return null;
	}

	@ResponseBody
	@RequestMapping("nav")
	public List<MenuItem> myLevel1NavItems() {
		return Weas.mySubmenuItems(MenuItem.NAV_ROOT, MenuItem.NAV_ROOT.getId());
	}

	@ResponseBody
	@RequestMapping("locales")
	public List<MenuItem> findSupportedLocales() throws AppException {
		String[] localeCodes = Apps.some.locales.supported().to.codes();
		List<MenuItem> profileItems = new ArrayList<MenuItem>();
		for (String localeCode :  localeCodes) {
			MenuItem item = new MenuItem(localeCode, "locale." + localeCode);
			item.setIcon(localeCode);
			profileItems.add(item);
		}
		return profileItems;
	}

	/**
	 * changes to the specified locale
	 * @param locale
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="locale/{localeCode}")
	public boolean changeLocale(@PathVariable String localeCode, HttpServletRequest request, HttpServletResponse response) {
		if (Strings.isEmpty(localeCode)) {
			localeCode = Apps.the.sys_default.locale().value().toString();
		}
		Apps.a.locale locale = Apps.a.locale.of(localeCode);
		if (!locale.is.supported()) {
			if (logger.isDebugEnabled()) logger.debug("Application does not support locale '" + localeCode + "'");
			return false;
		}
		Weas.holdLocale(request, locale.value());

		Authentication authen = SecurityContextHolder.getContext().getAuthentication();
		if (authen != null) {
			Object principal = authen.getPrincipal();
			if (principal != null && principal instanceof WeaUserDetails) {
				User user = ((WeaUserDetails)principal).getUser();
				if (user != null) user.setLocale(locale.value());
				if (logger.isDebugEnabled()) logger.debug("Hold locale (" + localeCode + ") into security context");
			} else {
				logger.warn("Principal is null or not an instance of WeaUserDetails either.");
			}
		}
		return true;
	}

	@ResponseBody
	@RequestMapping("themes")
	public List<MenuItem> findThemes() throws AppException {
		String[] themeNames = Weas.getAppThemes();
		List<MenuItem> profileItems = new ArrayList<MenuItem>();
		for (String themeName :  themeNames) {
			MenuItem item = new MenuItem(themeName, "theme." + themeName);
			item.setIcon(themeName);
			profileItems.add(item);
		}
		return profileItems;
	}

	/**
	 * changes to the specified theme
	 * @param themeName String
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @return boolean
	 */
	@ResponseBody
	@RequestMapping(value="theme/{themeName}")
	public boolean changeTheme(@PathVariable String themeName, HttpServletRequest request, HttpServletResponse response) {
		if (Strings.isEmpty(themeName)) {
			themeName = Weas.getDefaultTheme();
		}
		boolean supported = true;
		if (!Weas.isThemeSupported(themeName)) {
			logger.warn("Application does not support theme '" + themeName + "'");
			themeName = Weas.getDefaultTheme();
			logger.warn("Application will use the default theme '" + themeName + "'");
			supported = false;
		}
		themeResolver.setThemeName(request, response, themeName);
		return supported;
	}

}