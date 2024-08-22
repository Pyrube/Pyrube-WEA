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

package com.pyrube.wea.format.converters;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.text.Format;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pyrube.one.app.i18n.format.FormatManager;
import com.pyrube.one.app.i18n.format.annotations.Converting;
import com.pyrube.one.app.inquiry.SearchCriteria;
import com.pyrube.one.app.logging.Logger;
import com.pyrube.one.app.persistence.Data;
import com.pyrube.wea.context.WebContextHolder;

/**
 * Implementation of {@link org.springframework.http.converter.HttpMessageConverter HttpMessageConverter} that
 * can read and write JSON using <a href="http://wiki.fasterxml.com/JacksonHome">Jackson 2.x's</a> {@link ObjectMapper}.
 *
 * <p>This converter can be used to localize a date/timestamp or number/decimal with <code>Converting</code>.
 * 
 * @author Aranjuez
 * @version Dec 01, 2009
 * @since Pyrube-WEA 1.0
 */
public class LocalizationJackson2HttpMessageConverter extends MappingJackson2HttpMessageConverter {
	/**
	 * logger
	 */
	private static Logger logger = Logger.getInstance(LocalizationJackson2HttpMessageConverter.class.getName());
	@Override
	protected void writeInternal(Object object, Type type, HttpOutputMessage outputMessage)
		throws IOException, HttpMessageNotWritableException {
		if (object instanceof SearchCriteria) {
			// grid data
			SearchCriteria<?> searchCriteria = (SearchCriteria<?>) object;
			localizeTimestamp(searchCriteria.getResults());
		} else if (object instanceof Data) {
			// detailed data
			localizeTimestamp((Data<?>) object);
		}
		super.writeInternal(object, type, outputMessage);
	}

	/**
	 * localize the timestamp-converting property in given models if they are local
	 * @param models
	 */
	private void localizeTimestamp(List<?> models) {
		if (models == null) return;
		for (Object model : models) {
			if (model instanceof Data) { localizeTimestamp((Data<?>) model); }
		}
	}

	/**
	 * localize the timestamp-converting property in given models if they are local
	 * @param models
	 */
	private void localizeTimestamp(Object[] models) {
		if (models == null) return;
		for (Object model : models) {
			if (model instanceof Data) { localizeTimestamp((Data<?>) model); }
		}
	}

	/**
	 * localize the timestamp-converting property in a given model if it is local
	 * @param model
	 */
	private void localizeTimestamp(Data<?> model) {
		if (model == null) return;
		Field[] fields = model.getClass().getDeclaredFields();
		for (Field field : fields) {
			try {
				if (Modifier.isStatic(field.getModifiers())) continue;
				Class<?> clz = field.getType();
				if (List.class.isAssignableFrom(clz)) {
					localizeTimestamp((List<?>) PropertyUtils.getProperty(model, field.getName()));
				} else if (clz.isArray()) {
					Class<?> elemClz = clz.getComponentType();
					if (Data.class.isAssignableFrom(elemClz)) {
						localizeTimestamp((Object[]) PropertyUtils.getProperty(model, field.getName()));
					}
				} else if (Data.class.isAssignableFrom(clz)) {
					localizeTimestamp((Data<?>) PropertyUtils.getProperty(model, field.getName()));
				}
				Converting converting = field.getAnnotation(Converting.class);
				if (converting != null && converting.local()) {
					Date date = (Date) PropertyUtils.getProperty(model, field.getName());
					if (date != null) {
						TimeZone localTimezone = WebContextHolder.getWebContext().getTimezone();
						// solution 1: timezone offset
						Calendar calendar = Calendar.getInstance(localTimezone);
						calendar.setTime(date);
						int timezoneOffset = (calendar.get(Calendar.ZONE_OFFSET) + calendar.get(Calendar.DST_OFFSET)) / (60 * 1000);
						model.getTimezoneOffsets().put(field.getName(), timezoneOffset);
						// solution 2: date formated at back-end
						Locale locale = WebContextHolder.getWebContext().getLocale();
						Format format = FormatManager.dateFormatOf(locale.toString(), converting.format().getName(), localTimezone);
						model.getLocalDates().put(field.getName(), format.format(date));
					}
				}
			} catch (IllegalAccessException | InvocationTargetException
					| NoSuchMethodException e) {
				logger.error("Failed to handle local date: " + field.getName(), e);
			}
		}
	}

}
