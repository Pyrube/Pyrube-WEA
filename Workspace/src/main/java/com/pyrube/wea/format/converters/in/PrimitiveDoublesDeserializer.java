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
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.JsonTokenId;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.pyrube.one.app.Apps;
import com.pyrube.one.lang.Strings;

/**
 * Localized Primitive Double Array Deserializer provides functionality for converting localized
 * front-end property into <code>double[]</code> object.
 * 
 * @author Aranjuez
 * @version Dec 01, 2009
 * @since Pyrube-ONE 1.0
 */
public class PrimitiveDoublesDeserializer extends LocalizedPropertyDeserializer<double[]> {

	/**
	 * the deserializer for double[]
	 */
	public static final PrimitiveDoublesDeserializer INSTANCE = new PrimitiveDoublesDeserializer(double[].class);

	/**
	 * constructor
	 * @param clz Class
	 */
	protected PrimitiveDoublesDeserializer(Class<double[]> clz) { super(clz); }

	@Override
	public double[] deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		try {
			JsonToken token;
			String localeCode = Apps.the.user.locale().toString();
			String formatName = (this.converting == null) ? Apps.i18n.format.name.FLOAT : this.converting.format().getName();
			DecimalFormat format = (DecimalFormat) Apps.a.number.format.of(localeCode, formatName).value();
			if (!p.isExpectedStartArrayToken()) {
				double[] targets = null;
				String text;
				if (p.hasToken(JsonToken.VALUE_STRING) && !Strings.isEmpty(text = p.getText().trim())) {
					targets = new double[1];
					targets[0] = format.parse(text).doubleValue();
				} else if (p.hasToken(JsonToken.VALUE_NUMBER_FLOAT)) {
					targets = new double[1];
					targets[0] = p.getDoubleValue();
				}
				return(targets);
			}
			int total    = 0,
				capacity = 10,
				i        = 0;
			double[] array = new double[capacity];
			while ((token = p.nextToken()) != JsonToken.END_ARRAY) {
				if (token == JsonToken.VALUE_NULL) continue;
				if (i >= capacity) {
					double[] temp = new double[array.length + capacity];
					System.arraycopy(array, 0, temp, 0, array.length);
					array = temp;
					i = 0;
				}
				if (p.hasToken(JsonToken.VALUE_NUMBER_FLOAT)) {
					array[total++] = p.getDoubleValue();
					i++;
					continue;
				}
				switch (p.getCurrentTokenId()) {
					case JsonTokenId.ID_STRING:
						String text = p.getText().trim();
						if (Strings.isEmpty(text)) array[total++] = 0.0;
						else array[total++] = format.parse(text).doubleValue();
						i++;
						break;
					case JsonTokenId.ID_NUMBER_INT:
						array[total++] = p.getDoubleValue();
						i++;
						break;
					case JsonTokenId.ID_NULL:
						array[total++] = 0.0;
						i++;
						break;
					case JsonTokenId.ID_START_ARRAY:
						break;
				}
			}
			double[] targets = new double[total];
			if (total > 0) System.arraycopy(array, 0, targets, 0, total);
			return(targets);
		} catch (Exception e) {
			throw Apps.an.exception.due(e);
		}
	}

}
