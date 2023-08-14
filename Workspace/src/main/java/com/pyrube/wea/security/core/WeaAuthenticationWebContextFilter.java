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

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.GenericFilterBean;

import com.pyrube.one.app.logging.Logger;
import com.pyrube.wea.context.WebContext;
import com.pyrube.wea.context.WebContextHolder;

/**
 * WEA Authentication web context filter. It binds the web context (request, response) on thread level
 * configured as Core security filter in security.xml as below:
 * <pre>
 *  <http>
 *    <custom-filter before="LOGOUT_FILTER" ref="weaAuthenticationWebContextFilter"/>
 *  </http>
 *  <beans:bean id="weaAuthenticationWebContextFilter" class="com.pyrube.wea.security.core.WeaAuthenticationWebContextFilter"/>
 * </pre>
 * 
 * @author Aranjuez
 * @version Dec 01, 2009
 * @since Pyrube-ONE 1.0
 */
public class WeaAuthenticationWebContextFilter extends GenericFilterBean {
	
	/**
	 * logger
	 */
	private static Logger logger = Logger.getInstance(WeaAuthenticationWebContextFilter.class.getName());
	
	/**
	 * constructor
	 */
	public WeaAuthenticationWebContextFilter() {
		super();
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		if (logger.isDebugEnabled()) {
			logger.debug("Bind web context onto thread level");
			HttpServletRequest req = (HttpServletRequest) request;
			logger.debug("Request info: requestUri=" + req.getRequestURI() + "; contextPath=" + req.getContextPath() + 
					"; servletPath=" + req.getServletPath() + "; pathInfo=" + req.getPathInfo() + "; queryString=" + req.getQueryString());
		}
		WebContextHolder.setWebContext(new WebContext((HttpServletRequest) request, (HttpServletResponse) response));
		try {
			chain.doFilter(request, response);
		} catch (ServletException e) {
			throw e;
		} catch (IOException e) {
			throw e;
		} catch (Throwable e) {
			throw new ServletException(e);
		} finally {
			WebContextHolder.removeWebContext();
			if (logger.isDebugEnabled()) logger.debug("Unbind web context from current thread");
		}
	}

	@Override
	protected void initFilterBean() throws ServletException {
	}
	
}
