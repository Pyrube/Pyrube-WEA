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

package com.pyrube.wea;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.pyrube.one.app.AppLifecycleManager;

/**
 * <pre>
 * Configuration loader when the application starts
 * web.xml:
 * 	<listener>
 * 		<listener-class>com.pyrube.wea.WeaConfigLoader</listener-class>
 * 	</listener>
 * 
 * </pre>
 * 
 * @author Aranjuez
 * @version Dec 01, 2009
 * @since Pyrube-WEA 1.0
 */
public class WeaConfigLoader implements ServletContextListener {
	
	/**
	 * constructor
	 */
	public WeaConfigLoader() {
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		AppLifecycleManager.startup();
	}

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		AppLifecycleManager.shutdown();
	}

}
