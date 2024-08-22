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

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.pyrube.wea.format.converters.in.BigDecimalDeserializer;
import com.pyrube.wea.format.converters.in.DateDeserializer;
import com.pyrube.wea.format.converters.in.DoubleDeserializer;
import com.pyrube.wea.format.converters.in.FloatDeserializer;
import com.pyrube.wea.format.converters.in.PrimitiveDoublesDeserializer;
import com.pyrube.wea.format.converters.in.PrimitiveFloatsDeserializer;

/**
 * WEA Localized Object Mapper provides functionality for reading localized
 * JSON including <code>Date</code>, <code>BigDecimal</code>, <code>Double</code>
 * or <code>Float</code> property.
 * 
 * @author Aranjuez
 * @version Dec 01, 2009
 * @since Pyrube-ONE 1.0
 */
public class LocalizedObjectMapper extends ObjectMapper {

	/**
	 * serial version uid
	 */
	private static final long serialVersionUID = 7536086116660671072L;

	/**
	 * constructor
	 */
	public LocalizedObjectMapper() {
		super();
		// ignore unknown properties
		this.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		// register custom deserializers
		SimpleModule module = new SimpleModule();
		module.addDeserializer(Date.class, DateDeserializer.INSTANCE);
		module.addDeserializer(BigDecimal.class, BigDecimalDeserializer.INSTANCE);
		module.addDeserializer(Double.TYPE, DoubleDeserializer.PRIMITIVE);
		module.addDeserializer(Double.class, DoubleDeserializer.REFERENCE);
		module.addDeserializer(double[].class, PrimitiveDoublesDeserializer.INSTANCE);
		module.addDeserializer(Float.TYPE, FloatDeserializer.PRIMITIVE);
		module.addDeserializer(Float.class, FloatDeserializer.REFERENCE);
		module.addDeserializer(float[].class, PrimitiveFloatsDeserializer.INSTANCE);
		this.registerModule(module);
	}
}
