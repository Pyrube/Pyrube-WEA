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

package com.pyrube.wea.format.converters.in;

import java.io.IOException;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.pyrube.one.app.Apps;
import com.pyrube.one.lang.Strings;

/**
 * Localized Date Deserializer provides functionality for converting localized
 * front-end property into <code>Date</code> object.
 * 
 * @author Aranjuez
 * @version Dec 01, 2009
 * @since Pyrube-ONE 1.0
 */
public class DateDeserializer extends LocalizedPropertyDeserializer<Date> {

	/**
	 * the deserializer for Date
	 */
	public static final DateDeserializer INSTANCE = new DateDeserializer(Date.class);

	/**
	 * constructor
	 * @param clz Class
	 */
	protected DateDeserializer(Class<Date> clz) { super(clz); }

	@Override
	public Date deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		String source = p.getText();
		if (Strings.isEmpty(source)) return null;
		try {
			Date target = null;
			Locale locale = Apps.the.user.locale();
			if (Strings.isNumeric(source)) {
				target = new Date(Long.valueOf(source));
				return(target);
			}
			if (this.converting == null) {
				DateFormat defaultFormat = Apps.a.date.format.of(locale.toString(), Apps.i18n.format.name.DATE).value();
				target = defaultFormat.parse(source);
				return(target);
			}
			TimeZone localTimezone = null;
			if (this.converting.local()) {
				localTimezone = Apps.the.user.timezone();
			}
			DateFormat format = Apps.a.date.format.of(locale.toString(), converting.format().getName(), localTimezone).value();
			target = format.parse(source);
			return(target);
		} catch (Exception e) {
			throw Apps.an.exception.due(e);
		}
	}

}
