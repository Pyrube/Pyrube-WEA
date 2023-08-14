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

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.pyrube.one.app.AppException;
import com.pyrube.one.app.logging.Logger;

/**
 * WEA Application context holder.
 * 
 * In bean definition xml file, configured as below:
 *   <beans:bean id="weaAppContextHolder" class="com.pyrube.wea.security.core.WeaAppContextHolder" factory-method="getInstance"/>
 * 
 * @author Aranjuez
 * @version Dec 01, 2009
 * @since Pyrube-WEA 1.0
 */
public class WeaAppContextHolder implements ApplicationContextAware {
	
	/**
	 * logger
	 */
	private static Logger logger = Logger.getInstance(WeaAppContextHolder.class.getName());
	
	/**
	 * Core Application Context
	 */
	private static ApplicationContext appContext = null;
	
	/**
	 * instance
	 */
	private static WeaAppContextHolder instance = new WeaAppContextHolder();
	
	/**
	 * constructor
	 */
	private WeaAppContextHolder() {
	}
	
	/**
	 * return an instance
	 * @return WeaAppContextHolder
	 */
	public static WeaAppContextHolder getInstance() {
		return instance;
	}
	
	/**
	 * set spring applicationContext when this bean is initialized in spring
	 */
	public void setApplicationContext(final ApplicationContext applicationContext) throws BeansException {
		appContext = applicationContext;
	}
	
	/**
	 * get a bean from Core Application Context
	 * @param beanId
	 * @return Object
	 * @throws AppException
	 */
	public Object getBean(String beanId) throws AppException {
		if (appContext == null) {
			logger.error("Core Application Context not found. Please configure " + WeaAppContextHolder.class.getName() + " in bean config.");
			throw new AppException("Core Application Context not found.");
		}
		return appContext.getBean(beanId);
	}
}
	
