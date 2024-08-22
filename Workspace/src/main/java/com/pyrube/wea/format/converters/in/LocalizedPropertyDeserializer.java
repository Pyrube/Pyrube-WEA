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

import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import com.pyrube.one.app.i18n.format.annotations.Converting;

/**
 * WEA Localized Property Deserializer provides functionality for converting localized
 * front-end property into <code>Date</code>, <code>Float</code>, <code>Double</code> or
 * <code>BigDecimal</code> object.
 * 
 * @author Aranjuez
 * @version Dec 01, 2009
 * @since Pyrube-ONE 1.0
 */
public abstract class LocalizedPropertyDeserializer<T> extends JsonDeserializer<T> implements ContextualDeserializer {

	/**
	 * an annotation of <code>Converting</code>
	 */
	protected Converting converting;

	/**
	 * the raw class of this property
	 */
	protected final Class<?> propClass;

	/**
	 * constructor
	 * @param propClass Class
	 */
	public LocalizedPropertyDeserializer(Class<T> propClass) {
		this.propClass = propClass;
	}

	@Override
	public JsonDeserializer<T> createContextual(DeserializationContext deserializationContext,
			BeanProperty beanProperty) throws JsonMappingException {
		this.converting = beanProperty.getAnnotation(Converting.class);
		return this;
	}

}
