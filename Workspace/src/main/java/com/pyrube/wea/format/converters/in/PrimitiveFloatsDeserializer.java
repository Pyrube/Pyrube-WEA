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
 * Localized Primitive Float Array Deserializer provides functionality for converting localized
 * front-end property into <code>float[]</code> object.
 * 
 * @author Aranjuez
 * @version Dec 01, 2009
 * @since Pyrube-ONE 1.0
 */
public class PrimitiveFloatsDeserializer extends LocalizedPropertyDeserializer<float[]> {

	/**
	 * the deserializer for float[]
	 */
	public static final PrimitiveFloatsDeserializer INSTANCE = new PrimitiveFloatsDeserializer(float[].class);

	/**
	 * constructor
	 * @param clz Class
	 */
	protected PrimitiveFloatsDeserializer(Class<float[]> clz) { super(clz); }

	@Override
	public float[] deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		try {
			JsonToken token;
			String localeCode = Apps.the.user.locale().toString();
			String formatName = (this.converting == null) ? Apps.i18n.format.name.FLOAT : this.converting.format().getName();
			DecimalFormat format = (DecimalFormat) Apps.a.number.format.of(localeCode, formatName).value();
			if (!p.isExpectedStartArrayToken()) {
				float[] targets = null;
				String text;
				if (p.hasToken(JsonToken.VALUE_STRING) && !Strings.isEmpty(text = p.getText().trim())) {
					targets = new float[1];
					targets[0] = format.parse(text).floatValue();
				} else if (p.hasToken(JsonToken.VALUE_NUMBER_FLOAT)) {
					targets = new float[1];
					targets[0] = p.getFloatValue();
				}
				return(targets);
			}
			int total    = 0,
				capacity = 10,
				i        = 0;
			float[] array = new float[capacity];
			while ((token = p.nextToken()) != JsonToken.END_ARRAY) {
				if (token == JsonToken.VALUE_NULL) continue;
				if (i >= capacity) {
					float[] temp = new float[array.length + capacity];
					System.arraycopy(array, 0, temp, 0, array.length);
					array = temp;
					i = 0;
				}
				if (p.hasToken(JsonToken.VALUE_NUMBER_FLOAT)) {
					array[total++] = p.getFloatValue();
					i++;
					continue;
				}
				switch (p.getCurrentTokenId()) {
					case JsonTokenId.ID_STRING:
						String text = p.getText().trim();
						if (Strings.isEmpty(text)) array[total++] = 0.0F;
						else array[total++] = format.parse(text).floatValue();
						i++;
						break;
					case JsonTokenId.ID_NUMBER_INT:
						array[total++] = p.getFloatValue();
						i++;
						break;
					case JsonTokenId.ID_NULL:
						array[total++] = 0.0F;
						i++;
						break;
					case JsonTokenId.ID_START_ARRAY:
						break;
				}
			}
			float[] targets = new float[total];
			if (total > 0) System.arraycopy(array, 0, targets, 0, total);
			return(targets);
		} catch (Exception e) {
			throw Apps.an.exception.due(e);
		}
	}

}
