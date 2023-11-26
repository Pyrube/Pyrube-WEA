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

package com.pyrube.wea.ui.resolvers.core;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.ThemeResolver;

import com.pyrube.one.app.Apps;
import com.pyrube.one.app.logging.Logger;
import com.pyrube.one.app.user.User;
import com.pyrube.one.lang.Strings;
import com.pyrube.wea.security.core.WeaUserDetails;
import com.pyrube.wea.util.Weas;
/**
 * WEA theme resolver. Implementation of ThemeResolver uses a cookie (on/off defined 
 * in WeaConfig) first, and then a theme attribute in session, with a fallback to 
 * the default theme.
 * 
 * @author Aranjuez
 * @version Dec 01, 2023
 * @since Pyrube-WEA 1.1
 */
public class WeaThemeResolver implements ThemeResolver {

	/**
	 * logger
	 */
	private static Logger logger = Apps.a.logger.named(WeaThemeResolver.class.getName());

	@Override
	public String resolveThemeName(HttpServletRequest request) {
		String themeName = null;
		if (Weas.isThemeCookieEnabled()) {
			themeName = Weas.getThemeCookieValue(request);
		}
		if (themeName == null) {
			themeName = Weas.findTheme(request);
		}
		// Fall back to default theme.
		if (themeName == null) {
			themeName = Weas.getDefaultTheme();
		}
		return themeName;
	}

	@Override
	public void setThemeName(HttpServletRequest request, HttpServletResponse response, String themeName) {
		if (!Strings.isEmpty(themeName)) {
			if (Weas.isThemeCookieEnabled()) {
				Weas.setThemeCookie(request, response, themeName);
			}
			Weas.holdTheme(request, themeName);
			// hold it into security context
			Authentication authen = SecurityContextHolder.getContext().getAuthentication();
			if (authen != null) {
				Object principal = authen.getPrincipal();
				if (principal != null && principal instanceof WeaUserDetails) {
					User user = ((WeaUserDetails)principal).getUser();
					if (user != null) user.setTheme(themeName);
					if (logger.isDebugEnabled()) logger.debug("Hold theme (" + themeName + ") into security context");
				} else {
					logger.warn("Principal is null or not an instance of WeaUserDetails either.");
				}
			}
		} else {
			if (Weas.isThemeCookieEnabled()) {
				Weas.removeThemeCookie(request, response);
			}
			Weas.removeThemeCookie(request, response);
		}

	}

}
