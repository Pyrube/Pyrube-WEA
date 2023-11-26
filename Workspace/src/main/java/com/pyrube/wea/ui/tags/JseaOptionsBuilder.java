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

package com.pyrube.wea.ui.tags;

import java.util.List;

import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pyrube.one.app.persistence.Data;
import com.pyrube.one.lang.Strings;

/**
 * The builder utility is used to build JSEA options for WEA tags, converting
 * an Object to String in JSON format
 * 
 * @author Aranjuez
 * @version Dec 01, 2009
 * @since Pyrube-WEA 1.0
 */
public class JseaOptionsBuilder {
	
	public static final String JSEA_OPTION_TYPE_FUNCTION = "JAVASCRIPT_FUNC";
	public static final String JSEA_OPTION_TYPE_OBJECT = "JAVASCRIPT_OBJ";
	private static final String ARRAY_BRACKET_OPEN = "[", ARRAY_BRACKET_CLOSE = "]";
	private static final String JSON_BRACE_OPEN    = "{", JSON_BRACE_CLOSE    = "}";
	private static final String COLON = ":", COMMA = ",", SINGLE_QUOTE = "'";
	
	/**
	 * whether rendering with braces '{}'
	 */
	private boolean renderingWithBraces = false;
	
	/**
	 * count for options
	 */
	private int count;
	private StringBuilder optionsBuilder; 
	
	/**
	 * constructor
	 */
	private JseaOptionsBuilder() {
		count = 0;
		optionsBuilder = new StringBuilder();
	}
	
	/**
	 * new an instance of <code>JseaOptionsBuilder</code>
	 * @return
	 */
	public static JseaOptionsBuilder newBuilder() {
		return new JseaOptionsBuilder();
	}
	
	/**
	 * Append one JSEA option entry for later builder.
	 * @param name
	 * @param value
	 * @param type
	 * @return
	 */
	public JseaOptionsBuilder appendJseaOption(String name, Object value, String type) {
		if (value != null) {
			if (count++ > 0) {
				optionsBuilder.append(COMMA);
			}
			optionsBuilder.append(name).append(COLON);
			String jsonString = null;
			if (StringUtils.isEmpty(type)) {
				jsonString = this.convertToJsonString(value);
			} else if (JSEA_OPTION_TYPE_FUNCTION.equals(type)) {
				jsonString = this.convertToJavascriptString(value);
			} else if (JSEA_OPTION_TYPE_OBJECT.equals(type)) {
				jsonString = this.convertToJavascriptString(value);
			}
			optionsBuilder.append(jsonString);
		}
		return this;
	}	
	
	/**
	 * Append one JSEA option entry for later builder.
	 * 
	 * @param name
	 * @param value
	 * @return JseaOptionsBuilder
	 */
	public JseaOptionsBuilder appendJseaOption(String name, Object value) {
		return appendJseaOption(name, value, null);
	}
	
	/**
	 * Append extra JSEA options string
	 * 
	 * @param options
	 * @return JseaOptionsBuilder
	 */
	public JseaOptionsBuilder appendJseaOptions(String options) {
		if (!Strings.isEmpty(options)) {
			if (count > 0) {
				optionsBuilder.append(COMMA);
			}
			count += options.split(COMMA).length;
			optionsBuilder.append(options);
		}
		return this;
	}

	/**
	 * @param renderingWithBraces the renderingWithBraces to set
	 */
	public JseaOptionsBuilder setRenderingWithBraces(boolean renderingWithBraces) {
		this.renderingWithBraces = renderingWithBraces;
		return this;
	}

	/**
	 * returns the number of JSEA options in this builder
	 * @return the number of JSEA options in this builder
	 */
	public int size() {
		return(count);
	}
	
	/**
	 * Reset builder.
	 */
	public void clear() {
		this.optionsBuilder = new StringBuilder();
		this.count = 0;
	}
	
	/**
	 * return the converted string as build result.
	 * @return
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		StringBuilder result = new StringBuilder();
		if (this.renderingWithBraces) {
			result.append(JSON_BRACE_OPEN)
				.append(this.optionsBuilder.toString())
				.append(JSON_BRACE_CLOSE);
		} else {
			result.append(this.optionsBuilder.toString());
		}
		return result.toString();
	}
	
	/**
	 * 
	 * @param value
	 * @return
	 */
	private String convertToJsonString(Object value) {
		StringBuffer buf = new StringBuffer();
		if (String.class.isInstance(value)) {
			String strVal = (String) value;
			if (strVal.startsWith(ARRAY_BRACKET_OPEN) && strVal.endsWith(ARRAY_BRACKET_CLOSE)) {
				buf.append(strVal);
			} else if (strVal.startsWith(JSON_BRACE_OPEN) && strVal.endsWith(JSON_BRACE_CLOSE)) {
				buf.append(strVal);
			} else {
				buf.append(SINGLE_QUOTE + strVal + SINGLE_QUOTE);
			}
		} else if (Boolean.class.isInstance(value)) {
			buf.append(value.toString());
		} else if (int.class.isInstance(value)) {
			buf.append(value.toString());
		} else if (value.getClass().isArray()) {
			Object[] values = ObjectUtils.toObjectArray(value);
			buf.append(ARRAY_BRACKET_OPEN);
			for (int i = 0; i < values.length; i++) {
				if (i > 0) {
					buf.append(COMMA);
				}
				buf.append(convertToJsonString(values[i]));
			}
			buf.append(ARRAY_BRACKET_CLOSE);
		} else if (value instanceof List) {
			buf.append(ARRAY_BRACKET_OPEN);
			List<?> list = (List<?>) value;
			for (int i = 0; i < list.size(); i++) {
				if (i > 0) {
					buf.append(COMMA);
				}
				buf.append(convertToJsonString(list.get(i)));
			}
			buf.append(ARRAY_BRACKET_CLOSE);
		} else {
			try {
				if (value != null && value instanceof Data) {
					localizeTimestamp(value);
				}
				// if optionValueObj is a value model, 
				// change to string in json format {'sampleCode':'001','sampleName':'NAME OF 001'}
				ObjectMapper mapper = new ObjectMapper();
				mapper.configure(Feature.ALLOW_SINGLE_QUOTES, true);
				buf.append(mapper.writeValueAsString(value));
			} catch (JsonProcessingException e) {
			}
		}
		return buf.toString();
	}
	
	private String convertToJavascriptString(Object value) {
		return (String) value;
	}	
	
	/**
	 * localize the timestamp field with Local annotation in the given bean
	 * @param bean
	 */
	private void localizeTimestamp(Object bean) {
		// TO-DO
	}	
}
