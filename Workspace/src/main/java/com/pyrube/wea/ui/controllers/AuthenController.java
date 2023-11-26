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

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.pyrube.one.app.AppMessage;
import com.pyrube.one.app.Apps;
import com.pyrube.one.app.logging.Logger;
import com.pyrube.one.app.user.Authen;
import com.pyrube.wea.WeaConfig;
import com.pyrube.wea.WeaConfig.Captcha;
import com.pyrube.wea.WeaConstants;
import com.pyrube.wea.security.core.BadCaptchaException;
import com.pyrube.wea.security.core.CredentialsInitializedException;

/**
 * Authentication controller
 * 
 * @author Aranjuez
 * @version Dec 01, 2009
 * @since Pyrube-WEA 1.0
 */
@Controller
@RequestMapping("authen")
public class AuthenController extends WeaController {
	
	/**
	 * logger
	 */
	private static Logger logger = Apps.a.logger.named(AuthenController.class.getName());
	
	@RequestMapping(value = "")
	public String authen() {
		return "authen.signon";
	}
	
	@RequestMapping(value = "forgetPassword")
	public String initForgetPassword(Model model) {
		model.addAttribute("authen", Apps.a.data(Authen.class));
		return "authen.forget_password";
	}
	
	@RequestMapping(value = "findPassword")
	public String findPassword(@ModelAttribute Authen model) {
		return "authen.find_password";
	}

	@RequestMapping(value = {"forceChangePassword"})
	public String intiForceChangePassword(@RequestParam String username, Model model, HttpServletRequest request) {
		if (logger.isDebugEnabled()) logger.debug("user (" + username + ") has password-expired/initialized.");
		AuthenticationException ae = (AuthenticationException) request.getSession().getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
		AppMessage message = Apps.a.message.with.error(ae.getMessage());
		if (ae instanceof CredentialsExpiredException) {
			message = Apps.a.message.with.error("message.error.authen.password-expired");
		} else if (ae instanceof CredentialsInitializedException) {
			message = Apps.a.message.with.info("message.info.authen.password-initialized");
		}
		model.addAttribute(WeaConstants.REQUEST_ATTRNAME_MESSAGES, Apps.some.messages(message));
		Authen authen 
			= Apps.a.data(Authen.class)
					.username(username);
		model.addAttribute("authen", authen);
		return "authen.force_change_password";
	}
	
	@RequestMapping(value = "forceChangePassword/success")
	public String successForceChangePassword(Model model, HttpServletRequest request) {
		AppMessage message = Apps.a.message.with.success("message.success.password-modification");
		model.addAttribute(WeaConstants.REQUEST_ATTRNAME_MESSAGES, Apps.some.messages(message));
		model.addAttribute("authen", new Authen());
		return "signon";
	}
	
	@RequestMapping(value = "forceChangePassword/failed")
	public String failedForceChangePassword(@RequestParam String username, Model model, HttpServletRequest request) {
		AuthenticationException ae = (AuthenticationException) request.getSession().getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
		String code = ae.getMessage();
		logger.warn("Failed to force change password due to :" + code, ae);
		if (ae instanceof BadCaptchaException) {
			code = "message.error.authen.captcha-invalid";
		} else if (ae instanceof BadCredentialsException) {
			code = "message.error.authen.password-wrong";
		} else {
			code = "message.error.authen.password-unchangeable";
		}
		model.addAttribute(WeaConstants.REQUEST_ATTRNAME_MESSAGES, Apps.some.messages(Apps.a.message.with.error(code)));
		Authen authen 
			= Apps.a.data(Authen.class)
					.username(username);
		model.addAttribute("authen", authen);
		return "authen.force_change_password";
	}
	
	@RequestMapping("/captcha")  
	public void generateCaptcha(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Captcha captcha = WeaConfig.getWeaConfig().getCaptcha();
		BufferedImage bufImage = new BufferedImage(captcha.getImageWidth(), captcha.getImageHeight(), BufferedImage.TYPE_INT_RGB);
		Graphics gd = bufImage.getGraphics();
		Random random = new Random();
		gd.setColor(Color.WHITE);
		gd.fillRect(0, 0, captcha.getImageWidth(), captcha.getImageHeight());

		Font font = new Font("Fixedsys", Font.BOLD, captcha.getFontSize());
		gd.setFont(font);

		gd.setColor(Color.GRAY);
		gd.drawRect(0, 0, captcha.getImageWidth() - 1, captcha.getImageHeight() - 1);

		gd.setColor(Color.BLACK);
		for (int i = 0; i < 50; i++) {
			int x = random.nextInt(captcha.getImageWidth());
			int y = random.nextInt(captcha.getImageHeight());
			int xl = random.nextInt(12);
			int yl = random.nextInt(12);
			gd.drawLine(x, y, x + xl, y + yl);
		}

		StringBuffer buf = new StringBuffer();
		int red = 0, green = 0, blue = 0;

		for (int i = 0; i < captcha.getCodeLength(); i++) {
			String $char = String.valueOf(captcha.getAvailableChars()[random.nextInt(captcha.getAvailableChars().length)]);
			red = random.nextInt(255);
			green = random.nextInt(255);
			blue = random.nextInt(255);
			
			gd.setColor(new Color(red, green, blue));
			gd.drawString($char, (i + 1) * captcha.getCharWidth(), captcha.getCharPosY());

			buf.append($char);
		}
		// hold captcha code in session
		HttpSession session = request.getSession();
		session.setAttribute(WeaConstants.SESSION_ATTRNAME_CAPTCHA, buf.toString());

		// no cache for captcha image
		// Set standard HTTP/1.1 no-cache headers.
		response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
		// Set IE extended HTTP/1.1 no-cache headers (use addHeader).
		response.addHeader("Cache-Control", "post-check=0, pre-check=0");
		// Set standard HTTP/1.0 no-cache header.
		response.setHeader("Pragma", "no-cache");
		// Set to expire far in the past.
		response.setHeader("Expires", "0");

		response.setContentType(captcha.getMimeType());

		// write out
		ServletOutputStream sos = response.getOutputStream();
		ImageIO.write(bufImage, "png", sos);
		sos.close();
	} 
	
}
