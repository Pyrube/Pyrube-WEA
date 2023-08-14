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

import java.text.DecimalFormat;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.GenericConverter;

import com.pyrube.one.app.AppException;
import com.pyrube.one.app.i18n.format.FormatManager;
import com.pyrube.one.app.logging.Logger;
import com.pyrube.wea.context.WebContextHolder;

/**
 * Float converter
 * 
 * @author Aranjuez
 * @version Dec 01, 2009
 * @since Pyrube-WEA 1.0
 */
public class FloatConverter implements GenericConverter {
	
	/**
	 * logger
	 */
	private static Logger logger = Logger.getInstance(FloatConverter.class.getName());

	@Override
	public Set<ConvertiblePair> getConvertibleTypes() {
		Set<ConvertiblePair> pairs = new HashSet<ConvertiblePair>();
		pairs.add(new ConvertiblePair(String.class, float.class));
		pairs.add(new ConvertiblePair(String.class, Float.class));
		pairs.add(new ConvertiblePair(String.class, float[].class));
		pairs.add(new ConvertiblePair(String.class, Float[].class));
		return pairs;
	}
	
	@Override
	public Object convert(Object value, TypeDescriptor sourceType, TypeDescriptor targetType) {
		if (value == null || sourceType == null || targetType == null) {
			return null;
		}
		Locale locale = WebContextHolder.getWebContext().getLocale();
		DecimalFormat format = (DecimalFormat) FormatManager.numberFormatOf(locale.toString(), FormatManager.NFN_FLOAT);
		if (targetType.getType() == float.class || targetType.getType() == Float.class) {
			if (targetType.getType() == float.class) {
				if (((String)value).length() == 0) {
					return (float)0;
				}
			} else if (targetType.getType() == Float.class) {
				if (((String)value).length() == 0) {
					return null;
				}
			}
			try {
				Number number = format.parse((String) value);
				return (number.floatValue());
			} catch (Exception e) {
				logger.error("Failed to convert '" + value + "' to a float.", e);
				throw new AppException("message.error.number-converting", value);
			}
		} else if (targetType.getType() == float[].class || targetType.getType() == Float[].class) {
			if (((String)value).length() == 0) {
				return null;
			}
			if (targetType.getType() == float[].class) {
				try {
					float[] floats = new float[1];
					Number number = format.parse((String) value);
					floats[0] = number.floatValue();
					return (floats);
				} catch (Exception e) {
					logger.error("Failed to convert '" + value + "' to a float.", e);
					throw new AppException("message.error.number-converting", value);
				}
			} else if (targetType.getType() == Float[].class) {
				try {
					Float[] floats = new Float[1];
					Number number = format.parse((String) value);
					floats[0] = number.floatValue();
					return (floats);
				} catch (Exception e) {
					logger.error("Failed to convert '" + value + "' to a float.", e);
					throw new AppException("message.error.number-converting", value);
				}
			}
		}
		return null;
	}

}
