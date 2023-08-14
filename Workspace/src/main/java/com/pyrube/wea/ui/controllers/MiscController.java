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

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.pyrube.one.app.logging.Logger;

/**
 * Miscellanies controller
 * 
 * @author Aranjuez
 * @version Dec 01, 2009
 * @since Pyrube-WEA 1.0
 */
@Controller
@RequestMapping("")
public class MiscController {
	
	/**
	 * logger
	 */
	private static Logger logger = Logger.getInstance(MiscController.class.getName());
	
	@RequestMapping("blank")
	public String main(@RequestParam(name = "target", required = false) String target) {
		String view = "main";
		// get the main url. if it exists, then redirect to the url, otherwise go to the default.
		String mainUrl = null;
		if (target != null) {
			mainUrl = target;
		} 
		if (mainUrl != null) view = "redirect:" + mainUrl;
		if (logger.isDebugEnabled()) logger.debug("The main page will be " + view);
		return view;
	}

}
