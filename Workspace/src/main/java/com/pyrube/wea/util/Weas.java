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

package com.pyrube.wea.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.context.ApplicationContext;
import org.springframework.core.GenericTypeResolver;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ParseException;
import org.springframework.security.access.expression.ExpressionUtils;
import org.springframework.security.access.expression.SecurityExpressionHandler;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.FilterInvocation;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.util.HtmlUtils;

import com.pyrube.one.app.i18n.I18nManager;
import com.pyrube.one.app.logging.Logger;
import com.pyrube.one.app.menu.MenuItem;
import com.pyrube.one.app.user.User;
import com.pyrube.one.lang.Strings;
import com.pyrube.wea.WeaConstants;
import com.pyrube.wea.context.WebContext;
import com.pyrube.wea.context.WebContextHolder;

/**
 * <code>Pyrube-WEA</code> utilities
 * 
 * @author Aranjuez
 * @version Dec 01, 2009
 * @since Pyrube-WEA 1.0
 */
public class Weas {
	
	/**
	 * logger
	 */
	private static Logger logger = Logger.getInstance(Weas.class.getName());
	
	/**
	 * Turn special characters into HTML character references.
	 * Handles complete character set defined in HTML 4.01 recommendation.
	 * @param text the (unescaped) text String
	 * @return the escaped String
	 */
	public static String htmlEscape(String text) {
		return HtmlUtils.htmlEscape(text);
	}
	
	/**
	 * Turn special characters into HTML character references.
	 * Handles complete character set defined in HTML 4.01 recommendation.
	 * @param text the (unescaped) text String
	 * @param encoding the name of a supported <code>Charset</code>
	 * @return the escaped String
	 */
	public static String htmlEscape(String text, String encoding) {
		return HtmlUtils.htmlEscape(text, encoding);
	}

	/**
	 * Localize message for a given code and arguments with locale in current <code>WebContext</code>. 
	 * if no <code>WebContext</code> found, it will use locale in current session.
	 * @param request
	 * @param code the message code
	 * @param arguments the message arguments
	 * @return String
	 */
	public static String localizeMessage(HttpServletRequest request, String code, Object[] arguments) {
		Locale locale = null;
		WebContext webContext = WebContextHolder.getWebContext();
		if (webContext != null) locale = webContext.getLocale();
		if (locale == null) locale = findLocale(request);
		return I18nManager.getMessage(code, arguments, locale);
	}
	
	/**
	 * Localize message for a given code with locale in current <code>WebContext</code>. 
	 * if no <code>WebContext</code> found, it will use locale in current session.
	 * @param request
	 * @param code the message code
	 * @return String
	 */
	public static String localizeMessage(HttpServletRequest request, String code) {
		return localizeMessage(request, code, null);
	}
	
	/**
	 * Localize message for a given code and arguments with locale in current <code>WebContext</code>. 
	 * if no <code>WebContext</code> found, it will use application default locale.
	 * @param code the message code
	 * @param arguments the message arguments
	 * @return String
	 */
	public static String localizeMessage(String code, Object[] arguments) {
		Locale locale = null;
		WebContext webContext = WebContextHolder.getWebContext();
		if (webContext != null) locale = webContext.getLocale();
		return I18nManager.getMessage(code, arguments, locale);
	}
	/** 
	 * Localize message for a given code with locale in current <code>WebContext</code>. 
	 * if no <code>WebContext</code> found, it will use application default locale.
	 * @param code the message code
	 * @return String
	 */
	public static String localizeMessage(String code) {
		return localizeMessage(code, null);
	}
	
	/**
	 * Return result to validate captcha, then remove it from session
	 * Does not create a new session if none has existed before!
	 * @param request
	 * @param captcha
	 * @return boolean
	 */
	public static boolean validateCaptcha(HttpServletRequest request, String captcha) {
		HttpSession session = request.getSession(false);
		String captcha0 = (session != null ? (String) session.getAttribute(WeaConstants.SESSION_ATTRNAME_CAPTCHA) : null);
		if (captcha0 == null) return false;
		if (session != null) session.removeAttribute(WeaConstants.SESSION_ATTRNAME_CAPTCHA);
		return captcha0.equalsIgnoreCase(captcha);
	}

	/**
	 * Hold the given user as session attribute.
	 * @param request current HTTP request
	 * @param user the user
	 */
	public static void holdUser(HttpServletRequest request, User user) {
		request.getSession().setAttribute(WeaConstants.SESSION_ATTRNAME_USER, user);
	}
	/**
	 * Return the user from the session.
	 * Returns null if there is no session or if the session has no such attribute.
	 * Does not create a new session if none has existed before!
	 * @param request current HTTP request
	 * @return User. null if it is not found
	 */
	public static User findUser(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		return (session != null ? (User) session.getAttribute(WeaConstants.SESSION_ATTRNAME_USER) : null);
	}
	
	/**
	 * Removes user from the session, if a session existed at all.
	 * Does not create a new session if not necessary!
	 * @param request current HTTP request
	 */
	public static void removeUser(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.removeAttribute(WeaConstants.SESSION_ATTRNAME_USER);
		}
	}

	/**
	 * Hold the given locale as session attribute.
	 * @param request current HTTP request
	 * @param locale the locale
	 */
	public static void holdLocale(HttpServletRequest request, Locale locale) {
		request.getSession().setAttribute(WeaConstants.SESSION_ATTRNAME_LOCLAE, locale);
	}
	/**
	 * Return the locale from the session.
	 * Returns null if there is no session or if the session has no such attribute.
	 * Does not create a new session if none has existed before!
	 * @param request current HTTP request
	 * @return Locale. null if it is not found
	 */
	public static Locale findLocale(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		return (session != null ? (Locale) session.getAttribute(WeaConstants.SESSION_ATTRNAME_LOCLAE) : null);
	}
	
	/**
	 * Removes locale from the session, if a session existed at all.
	 * Does not create a new session if not necessary!
	 * @param request current HTTP request
	 */
	public static void removeLocale(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.removeAttribute(WeaConstants.SESSION_ATTRNAME_LOCLAE);
		}
	}
	
	/**
	 * Returns the sub-menu items user has access permission of a given id
	 * @param itemId
	 * @return
	 */
	public static List<MenuItem> mySubmenuItems(String itemId) {
		MenuItem item = MenuItem.ROOT.find(itemId);
		List<MenuItem> subs = null;
		List<MenuItem> myItems = null;
		if (item != null && (subs = item.getSubs()) != null) {
			myItems = new ArrayList<MenuItem>();
			for (MenuItem sub : subs) {
				String access = sub.getAccess();
				if (Strings.isEmpty(access)) myItems.add(sub.clone(false));
				else if (Weas.evaluateUserAccessExpression(access)) myItems.add(sub.clone(false));
			}
		}
		return myItems;
	}
	
	/**
	 * evaluate user access control expression
	 * @param access
	 * @return true user has access
	 */
	public static boolean evaluateUserAccessExpression(String access) {
		if (SecurityContextHolder.getContext().getAuthentication() == null) {
			return false;
		}
		SecurityExpressionHandler<FilterInvocation> handler = getExpressionHandler();
		Expression accessExpression;
		try {
			accessExpression = handler.getExpressionParser().parseExpression(access);
		}
		catch (ParseException e) {
			logger.error("access expression error", e);
			return(false);
		}

		return ExpressionUtils.evaluateAsBoolean(accessExpression,
				createExpressionEvaluationContext(handler));
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static SecurityExpressionHandler<FilterInvocation> getExpressionHandler() {
		HttpServletRequest request = WebContextHolder.getWebContext().getRequest();
		ApplicationContext appContext = WebApplicationContextUtils
				.getRequiredWebApplicationContext(request.getServletContext());
		Map<String, SecurityExpressionHandler> handlers = appContext
				.getBeansOfType(SecurityExpressionHandler.class);

		for (SecurityExpressionHandler h : handlers.values()) {
			if (FilterInvocation.class.equals(GenericTypeResolver.resolveTypeArgument(
					h.getClass(), SecurityExpressionHandler.class))) {
				return h;
			}
		}
		if (logger.isDebugEnabled()) logger.debug(
				"No visible WebSecurityExpressionHandler instance could be found in the application "
						+ "context. There must be at least one in order to support expressions in JSP 'authorize' tags.");
		return(null);
	}
	private static EvaluationContext createExpressionEvaluationContext(
			SecurityExpressionHandler<FilterInvocation> handler) {
		WebContext webCtx = WebContextHolder.getWebContext();
		ServletRequest request = webCtx.getRequest();
		ServletResponse response = webCtx.getResponse();
		FilterInvocation f = new FilterInvocation(request, response,
				new FilterChain() {
					public void doFilter(ServletRequest request, ServletResponse response)
							throws IOException, ServletException {
						throw new UnsupportedOperationException();
					}
				});

		return handler.createEvaluationContext(SecurityContextHolder.getContext()
				.getAuthentication(), f);
	}
}
