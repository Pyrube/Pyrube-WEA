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
import java.text.DecimalFormat;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.pyrube.one.app.Apps;
import com.pyrube.one.lang.Strings;

/**
 * Localized Double  Deserializer provides functionality for converting localized
 * front-end property into <code>Double</code> object.
 * 
 * @author Aranjuez
 * @version Dec 01, 2009
 * @since Pyrube-ONE 1.0
 */
public class DoubleDeserializer extends LocalizedPropertyDeserializer<Double> {

	/**
	 * the deserializer for double
	 */
	public static final DoubleDeserializer PRIMITIVE = new DoubleDeserializer(Double.TYPE);
	/**
	 * the deserializer for Double
	 */
	public static final DoubleDeserializer REFERENCE = new DoubleDeserializer(Double.class);

	/**
	 * constructor
	 * @param clz Class
	 */
	protected DoubleDeserializer(Class<Double> clz) { super(clz); }

	@Override
	public Double deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		String source = p.getText();
		if (source == null) return null;
		if (Strings.isEmpty(source) && this.propClass == Double.TYPE) return 0.0D;
		if (Strings.isEmpty(source) && this.propClass == Double.class) return null;
		try {
			Number target = null;
			String localeCode = Apps.the.user.locale().toString();
			String formatName = (this.converting == null) ? Apps.i18n.format.name.FLOAT : this.converting.format().getName();
			DecimalFormat format = (DecimalFormat) Apps.a.number.format.of(localeCode, formatName).value();
			target = format.parse(source);
			return target.doubleValue();
		} catch (Exception e) {
			throw Apps.an.exception.due(e);
		}
	}

}
