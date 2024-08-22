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

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.HashSet;
import java.util.Set;

import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.GenericConverter;

import com.pyrube.one.app.AppException;
import com.pyrube.one.app.Apps;
import com.pyrube.one.app.i18n.format.annotations.Converting;
import com.pyrube.one.app.logging.Logger;
import com.pyrube.one.lang.Strings;
import com.pyrube.wea.context.WebContextHolder;

/**
 * Big-decimal converter
 * 
 * @author Aranjuez
 * @version Dec 01, 2009
 * @since Pyrube-WEA 1.0
 */
public class BigDecimalConverter implements GenericConverter {

	/**
	 * logger
	 */
	private static Logger logger = Logger.getInstance(BigDecimalConverter.class.getName());

	@Override
	public Set<ConvertiblePair> getConvertibleTypes() {
		Set<ConvertiblePair> pairs = new HashSet<ConvertiblePair>();
		pairs.add(new ConvertiblePair(String.class, BigDecimal.class));
		pairs.add(new ConvertiblePair(String.class, BigDecimal[].class));
		return pairs;
	}

	@Override
	public BigDecimal convert(Object value, TypeDescriptor sourceType, TypeDescriptor targetType) {
		if (value == null) return null;
		String source = (String) value;
		if (Strings.isEmpty(source)) return null;
		try {
			Number target = null;
			String localeCode = WebContextHolder.getWebContext().getLocale().toString();
			Converting converting = targetType.getAnnotation(Converting.class);
			String formatName = (converting == null) ? Apps.i18n.format.name.MONEY : converting.format().getName();
			DecimalFormat format = (DecimalFormat) Apps.a.number.format.of(localeCode, formatName).value();
			format.setParseBigDecimal(true);
			target = format.parse(source);
			return (BigDecimal) target;
		} catch (Exception e) {
			logger.error("Failed to convert '" + value + "' to a big decimal.", e);
			throw new AppException("message.error.number-converting", value);
		}
	}

}
