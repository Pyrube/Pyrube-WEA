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

/**
 * Web context holder
 * 
 * @author Aranjuez
 * @version Dec 01, 2009
 * @since Pyrube-WEA 1.0
 */
public class WebContextHolder {

	/**
	 * the Web Context holder on thread level
	 */
	private static final ThreadLocal<WebContext> ctxHolder = new ThreadLocal<WebContext>();
	
	/**
	 * constructor
	 */
	private WebContextHolder() { }
	
	/**
	 * set Web Context for current thread
	 * @param ctx
	 */
	public static void setWebContext(WebContext ctx) {
		ctxHolder.set(ctx);
	}

	/**
	 * remove Web Context for current thread
	 */
	public static void removeWebContext() {
		WebContext ctx = ctxHolder.get();
		if (ctx != null) ctx.clear();
		ctxHolder.remove();
	}
	
	/**
	 * get the Web Context for current thread
	 * @return
	 */
	public static WebContext getWebContext() {
		return(ctxHolder.get());
	}
}
