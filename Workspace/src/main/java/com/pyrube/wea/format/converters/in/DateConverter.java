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

import java.text.DateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.TimeZone;

import org.apache.commons.beanutils.ConversionException;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.ConditionalGenericConverter;

import com.pyrube.one.app.Apps;
import com.pyrube.one.app.i18n.format.annotations.Converting;
import com.pyrube.one.lang.Strings;
import com.pyrube.wea.context.WebContextHolder;

/**
 * Convert a date string into a Date object.
 *
 * @author Aranjuez
 * @version Dec 01, 2009
 * @since Pyrube-WEA 1.0
 */
public class DateConverter implements ConditionalGenericConverter {

	@Override
	public Set<ConvertiblePair> getConvertibleTypes() {
		Set<ConvertiblePair> pairs = new HashSet<ConvertiblePair>();
		pairs.add(new ConvertiblePair(String.class, Date.class));
		pairs.add(new ConvertiblePair(String.class, Date[].class));
		return pairs;
	}

	@Override
	public boolean matches(TypeDescriptor sourceType, TypeDescriptor targetType) {
		Converting converting = targetType.getAnnotation(Converting.class);
		return (converting != null && 
				(Apps.i18n.format.name.DATE.equals(converting.format().getName()) 
				|| Apps.i18n.format.name.TIMESTAMP.equals(converting.format().getName())
				|| Apps.i18n.format.name.LONGTIMESTAMP.equals(converting.format().getName())
				|| Apps.i18n.format.name.LONGTIMESTAMPZ.equals(converting.format().getName())));
	}
	
	@Override
	public Object convert(Object value, TypeDescriptor sourceType, TypeDescriptor targetType) {
		String sDate = (String) value;
		if (Strings.isEmpty(sDate)) return null;

		try {
			Date d = null;
			if (Strings.isNumeric(sDate)) {
				d = new Date(Long.valueOf(sDate));
				return d;
			}
			TimeZone localTimezone = null;
			Converting converting = targetType.getAnnotation(Converting.class);
			if (converting.local()) {
				localTimezone = WebContextHolder.getWebContext().getTimezone();
			}
			Locale locale = WebContextHolder.getWebContext().getLocale();
			DateFormat format = Apps.a.date.format.of(locale.toString(), converting.format().getName(), localTimezone).value();
			d = format.parse(sDate);
			return (d);
		} catch (Exception e) {
			throw new ConversionException(e);
		}
	}
}
