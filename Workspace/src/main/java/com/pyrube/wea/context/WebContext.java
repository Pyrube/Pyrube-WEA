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

package com.pyrube.wea.context;

import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.ThemeResolver;

import com.pyrube.one.app.context.AppContextManager;
import com.pyrube.one.app.i18n.I18nManager;
import com.pyrube.one.app.user.User;
import com.pyrube.wea.WeaConfig;
import com.pyrube.wea.security.core.WeaUserDetails;
import com.pyrube.wea.util.Weas;

/**
 * Web context
 * 
 * @author Aranjuez
 * @version Dec 01, 2009
 * @since Pyrube-WEA 1.0
 */
public class WebContext {
	
	/**
	 * request
	 */
	private HttpServletRequest request = null;
	
	/**
	 * response
	 */
	private HttpServletResponse response = null;
	
	/**
	 * locale
	 */
	private Locale locale;
	
	/**
	 * time zone
	 */
	private TimeZone timezone;

	/**
	 * theme
	 */
	private String theme;

	/**
	 * whether default HTML-escaping is enabled
	 */
	private Boolean defaultHtmlEscaping;
	
	/**
	 * constructor
	 * @param request
	 * @param response
	 */
	public WebContext(HttpServletRequest request, HttpServletResponse response) {
		this.request = request;
		this.response = response;
		
		// Resolve a Locale
		Locale locale = Weas.findLocale(request);
		if (locale == null) {
			locale = request.getLocale();
			if (locale == null) locale = I18nManager.getDefaultLocale();
			Weas.holdLocale(request, locale);
		}
		this.locale = locale;
		
		TimeZone timezone = null;
		Authentication authen = SecurityContextHolder.getContext().getAuthentication();
		if (authen != null) {
			Object principal = authen.getPrincipal();
			if (principal != null && principal instanceof WeaUserDetails) {
				User user = ((WeaUserDetails)principal).getUser();
				if (user != null) timezone = user.timezone();
			}
		}
		if (timezone == null) {
			Calendar calendar = Calendar.getInstance(locale);
			timezone = calendar.getTimeZone();
		}
		this.timezone = timezone;

		// Resolve a theme
		String themeName = null;
		ThemeResolver themeResolver = (ThemeResolver) AppContextManager.findBean("themeResolver");
		if (themeResolver != null) {
			themeName = themeResolver.resolveThemeName(request);
		}
		if (themeName == null) {
			themeName = Weas.getDefaultTheme();
		}
		this.theme = themeName;
		
		// Determine default HTML escaping configuration from the "defaultHtmlEscaping" in 
		// pyrube-config.xml
		this.defaultHtmlEscaping = WeaConfig.getWeaConfig().isDefaultHtmlEscaping();
	}

	/**
	 * get the HttpServlet request
	 * @return
	 */
	public HttpServletRequest getRequest() {
		return(request);
	}
	
	/**
	 * get HttpServlet response
	 * @return
	 */
	public HttpServletResponse getResponse() {
		return(response);
	}
	
	/**
	 * @return the locale
	 */
	public Locale getLocale() {
		return(locale);
	}

	/**
	 * @return the timezone
	 */
	public TimeZone getTimezone() {
		return(timezone);
	}

	/**
	 * @return the theme
	 */
	public String getTheme() {
		return theme;
	}

	/**
	 * Whether default HTML-escaping is enabled
	 * @return false if no default given
	 */
	public boolean isDefaultHtmlEscaping() {
		return (this.defaultHtmlEscaping != null && this.defaultHtmlEscaping.booleanValue());
	}
	
	/**
	 * 
	 * @param defaultHtmlEscaping
	 */
	public void setDefaultHtmlEscaping(Boolean defaultHtmlEscaping) {
		this.defaultHtmlEscaping = defaultHtmlEscaping;
	}
	/**
	 * clear all properties
	 */
	public void clear() {
		request = null;
		response = null;
		locale = null;
		timezone = null;
		theme = null;
	}
}
