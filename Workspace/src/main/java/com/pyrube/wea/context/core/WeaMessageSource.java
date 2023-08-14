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

package com.pyrube.wea.context.core;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.NoSuchMessageException;

import com.pyrube.one.app.i18n.I18nManager;
import com.pyrube.one.app.logging.Logger;
import com.pyrube.one.lang.Strings;

/**
 * WEA Message source to retrieve resource messages. 
 * <code>ResourceBundleManager</code> is used to manage the resource messages.
 * 
 * @author Aranjuez
 * @version Dec 01, 2009
 * @since Pyrube-WEA 1.0
 */
public class WeaMessageSource implements MessageSource {
	
	/**
	 * logger
	 */
	private static Logger logger = Logger.getInstance(WeaMessageSource.class.getName());
	
	/**
	 * constructor
	 */
	public WeaMessageSource() {
		if (logger.isDebugEnabled()) logger.debug("New instance for WEA message source.");
	}

	/**
	 * return localized message. if code not found, then returns defaultMessage or the code if defaultMessage not provided.
	 * defaultMessage is not used in this implementation.
	 */
	@Override
	public String getMessage(String code, Object[] args, String defaultMessage, Locale locale) {
		return I18nManager.getMessage(code, args, defaultMessage, locale);
	}

	/**
	 * get message. if code not found, then returns the code
	 */
	@Override
	public String getMessage(String code, Object[] args, Locale locale) throws NoSuchMessageException {
		return I18nManager.getMessage(code, args, locale);
	}

	/**
	 * get message. if codes not found, then returns defaultMessage or the first code if defaultMessage not available
	 */
	@Override
	public String getMessage(MessageSourceResolvable resolvable, Locale locale) throws NoSuchMessageException {
		if (resolvable == null) return Strings.EMPTY;
		return I18nManager.getMessage(resolvable.getCodes()[0], resolvable.getArguments(), resolvable.getDefaultMessage(), locale);
	}

}
