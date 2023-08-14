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

import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.pyrube.one.app.AppException;

/**
 * Message controller
 * 
 * @author Aranjuez
 * @version Dec 01, 2009
 * @since Pyrube-WEA 1.0
 */
@Controller
@RequestMapping("message")
public class MessageController {
	
	@RequestMapping(value = "/app/messages", method = RequestMethod.GET)
	public String appMessages(ModelMap model, HttpServletRequest request) {
		return "message.app_messages";
	}
	
	@RequestMapping(value = "/access/denied", method = RequestMethod.GET)
	public String accessDenied(ModelMap model, HttpServletRequest request) {
		AccessDeniedException ex = (AccessDeniedException) request.getAttribute(WebAttributes.ACCESS_DENIED_403);
		StringWriter sw = new StringWriter();
		model.addAttribute("errorDetails", ex.getMessage());
		ex.printStackTrace(new PrintWriter(sw));
		model.addAttribute("errorTrace", sw.toString());
		return "message.access_denied";
	}
	
	@RequestMapping("http/{errorCode}")
	public String httpError(@PathVariable("errorCode") Integer errorCode) throws AppException {
		return "message.http_" + errorCode;
	}
}
