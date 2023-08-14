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

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import com.pyrube.one.app.logging.Logger;

/**
 * WEA mapping exception resolver
 * <pre>
 * configure it in spring servlet context file:
 * 
 * <beans:bean id="exceptionResolver" class="com.pyrube.wea.ui.resolvers.core.WeaMappingExceptionResolver" p:order="1">  
 *   <beans:property name="defaultErrorView">
 *     <beans:value>common/messages/app_messages</beans:value>
 *   </beans:property>
 *   <beans:property name="defaultStatusCode">
 *     <beans:value>500</beans:value>
 *   </beans:property>
 *   <beans:property name="warnLogCategory">
 *     <beans:value>org.springframework.web.servlet.handler.SimpleMappingExceptionResolver</beans:value>
 *   </beans:property>
 *   <beans:property name="exceptionMappings">
 *     <beans:props>
 *       <beans:prop key="org.springframework.web.multipart.MaxUploadSizeExceededException">common/messages/upload_error</beans:prop>
 *       <beans:prop key="java.lang.RuntimeException">common/messages/app_messages</beans:prop>
 *       <beans:prop key="java.lang.Exception">common/messages/app_messages</beans:prop>
 *       <beans:prop key="org.springframework.security.access.AccessDeniedException">accessDenied</beans:prop>
 *     </beans:props>
 *   </beans:property>
 * </beans:bean>
 * </pre>
 * 
 * @author Aranjuez
 * @version Dec 01, 2009
 * @since Pyrube-WEA 1.0
 */
public class WeaMappingExceptionResolver extends SimpleMappingExceptionResolver {
	
	/**
	 * logger
	 */
	private static Logger logger = Logger.getInstance(WeaMappingExceptionResolver.class.getName());
	
	private static final String DEF_XHRHEADERNAME = "X-Requested-With";
	private static final String DEF_XHRHEADERVALUE = "XMLHttpRequest";
	
	/**
	 * XmlHttpRequest header name
	 */
	private String xhrHeaderName = DEF_XHRHEADERNAME;
	
	/**
	 * XmlHttpRequest header value
	 */
	private String xhrHeaderValue = DEF_XHRHEADERVALUE;
	
	/**
	 * constructor
	 */
	public WeaMappingExceptionResolver() {
		super();
	}
	
	@Override
	protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response,
			Object handler, Exception ex) {

		// Expose ModelAndView for chosen error view.
		String viewName = null;
		String xhrHeader = request.getHeader(xhrHeaderName);
		if (xhrHeaderValue.equalsIgnoreCase(xhrHeader)) {
			viewName = determineViewName(ex, request);
		} else {
			viewName = determineViewName(ex, handler, request);
		}
		logger.error("Due to exception, forward to view '" + viewName + "'.", ex);
		if (viewName != null) {
			// Apply HTTP status code for error views, if specified.
			// Only apply it if we're processing a top-level request.
			Integer statusCode = determineStatusCode(request, viewName);
			if (statusCode != null) {
				applyStatusCodeIfPossible(request, response, statusCode);
			}
			return getModelAndView(viewName, ex, request);
		} else {
			return null;
		}
	}
	
	/**
	 * Determine the view name for the given exception in non-ajax method
	 * @param ex the exception that got thrown during handler execution
	 * @param handler the executed handler, or {@code null} if none chosen at the time
	 * of the exception (for example, if multipart resolution failed)
	 * @param request current HTTP request (useful for obtaining metadata)
	 * @return the resolved view name
	 */
	protected String determineViewName(Exception ex, Object handler, HttpServletRequest request) {
		String viewName = null;
		if (handler instanceof HandlerMethod) {
			viewName = "home";
		}
		return viewName;
	}

	/**
	 * @return the xhrHeaderName
	 */
	public String getXhrHeaderName() {
		return xhrHeaderName;
	}

	/**
	 * @param xhrHeaderName the xhrHeaderName to set
	 */
	public void setXhrHeaderName(String xhrHeaderName) {
		this.xhrHeaderName = (xhrHeaderName != null && xhrHeaderName.length() > 0) ? xhrHeaderName : DEF_XHRHEADERNAME;
	}

	/**
	 * @return the xhrHeaderValue
	 */
	public String getXhrHeaderValue() {
		return xhrHeaderValue;
	}

	/**
	 * @param xhrHeaderValue the xhrHeaderValue to set
	 */
	public void setXhrHeaderValue(String xhrHeaderValue) {
		this.xhrHeaderValue = (xhrHeaderValue != null && xhrHeaderValue.length() > 0) ? xhrHeaderValue : DEF_XHRHEADERVALUE;
	}
}
