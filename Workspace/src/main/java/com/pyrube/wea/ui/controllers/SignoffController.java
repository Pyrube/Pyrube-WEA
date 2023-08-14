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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.pyrube.one.app.logging.Logger;
import com.pyrube.one.lang.Strings;
import com.pyrube.wea.ui.model.Authen;

/**
 * Sign-off controller
 * 
 * @author Aranjuez
 * @version Dec 01, 2009
 * @since Pyrube-WEA 1.0
 */
@Controller
@RequestMapping("authen/signoff")
public class SignoffController extends WeaController {
	
	/**
	 * logger
	 */
	private static Logger logger = Logger.getInstance(SignoffController.class.getName());
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String signoff() {
		return "signoff";
	}
	
	/**
	 * this will be forwarded after successful log-out
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "success", method = RequestMethod.GET)
	public String signoffSuccess(HttpServletRequest request, HttpServletResponse response, Model model) {
		String reason = request.getParameter("reason");
		if (Strings.isEmpty(reason)) reason = "signon";
		if (logger.isDebugEnabled()) logger.debug("Logging off with reason " + reason + " ...");
		
		try {
			// notify listeners
			notifyListeners(request, response);
			
			// remove cookies
			removeCookies(request, response, reason);

		} catch (Exception e) {
			logger.warn("Error occurs while logging out", e);
		}
		
		if (logger.isDebugEnabled()) logger.debug("Logged out");
		model.addAttribute("authen", new Authen());
		return reason;
	}
	
	/**
	 * notify sign-off listeners
	 * @param request
	 * @param response
	 */	
	private void notifyListeners(HttpServletRequest request, HttpServletResponse response) {
	}
	
	/**
	 * remove cookies
	 * @param request
	 * @param response
	 */	
	private void removeCookies(HttpServletRequest request, HttpServletResponse response, String reason) {
	}

}
