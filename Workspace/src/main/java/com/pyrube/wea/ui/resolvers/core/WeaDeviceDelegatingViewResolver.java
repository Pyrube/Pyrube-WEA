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

import org.springframework.mobile.device.view.LiteDeviceDelegatingViewResolver;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ViewResolver;

import com.pyrube.one.app.logging.Logger;

/**
 * WEA view resolver
 * <pre>
 * configure it in spring servlet context file:
 * 
 *   <beans:bean class="com.pyrube.wea.ui.resolvers.WeaDeviceDelegatingViewResolver">
 *      <beans:constructor-arg>
 *         <beans:bean id="tilesResolver" class="org.springframework.web.servlet.view.UrlBasedViewResolver">
 *            <beans:property name="viewClass" value="org.springframework.web.servlet.view.tiles3.TilesView"/>
 *         </beans:bean>
 *      </beans:constructor-arg>
 *      <beans:property name="order" value="1" />
 *      <beans:property name="enableFallback" value="true" />
 *      <beans:property name="normalPrefix" value="laptop." />
 *      <beans:property name="mobilePrefix" value="mobile." />
 *      <beans:property name="tabletPrefix" value="tablet." />
 *      <beans:property name="hrSuffix" value=".page" />
 *      <beans:property name="xhrSuffix" value=".box" />
 *   </beans:bean> 
 * 
 * </pre>
 * 
 * @author Aranjuez
 * @version Dec 01, 2009
 * @since Pyrube-WEA 1.0
 */
public class WeaDeviceDelegatingViewResolver extends LiteDeviceDelegatingViewResolver {

	private static Logger logger = Logger.getInstance(WeaDeviceDelegatingViewResolver.class.getName());
	
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
	 * view suffix for XmlHttpRequest
	 */
	private String xhrSuffix = "";
	
	/**
	 * view suffix for HttpRequest
	 */
	private String hrSuffix = "";
	
	/**
	 * constructor
	 * @param delegate
	 */
	public WeaDeviceDelegatingViewResolver(ViewResolver delegate) {
		super(delegate);
	}

	/**
	 * get view
	 */
	protected String getDeviceViewNameInternal(String viewName) {
		String view = super.getDeviceViewNameInternal(viewName);
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		String xhrHdr = request.getHeader(xhrHeaderName);
		if (xhrHeaderValue.equalsIgnoreCase(xhrHdr)) {
			view += xhrSuffix;
		} else {
			view += hrSuffix;
		}
		if (logger.isDebugEnabled()) logger.debug("view is " + view);
		return view;
	}

	public String getXhrHeaderName() {
		return xhrHeaderName;
	}

	public void setXhrHeaderName(String xhrHeaderName) {
		this.xhrHeaderName = (xhrHeaderName != null && xhrHeaderName.length() > 0) ? xhrHeaderName : DEF_XHRHEADERNAME;
	}

	public String getXhrHeaderValue() {
		return xhrHeaderValue;
	}

	public void setXhrHeaderValue(String xhrHeaderValue) {
		this.xhrHeaderValue = (xhrHeaderValue != null && xhrHeaderValue.length() > 0) ? xhrHeaderValue : DEF_XHRHEADERVALUE;
	}

	public String getXhrSuffix() {
		return xhrSuffix;
	}

	public void setXhrSuffix(String xhrSuffix) {
		this.xhrSuffix = (xhrSuffix != null ? xhrSuffix : "");
	}

	public String getHrSuffix() {
		return hrSuffix;
	}

	public void setHrSuffix(String hrSuffix) {
		this.hrSuffix = (hrSuffix!= null ? hrSuffix : "");
	}
}
