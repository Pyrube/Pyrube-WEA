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

import javax.servlet.jsp.JspException;

/**
 * JSEA Text-field element.
 * 
 * @author Aranjuez
 * @version Dec 01, 2009
 * @since Pyrube-WEA 1.0
 */
public class TextfieldTag extends FieldTag {
	/**
	 * serial version uid
	 */
	private static final long serialVersionUID = 8866742433234457910L;

	/**
	 * @return the jseaAttrOptions
	 */
	@Override
	public String getJseaAttrOptions() {
		return TagConstants.JSEA_ATTR_TEXTFIELD_OPTIONS;
	}
	
	@Override
	protected String resolveFieldType() throws JspException { return "text"; }

	@Override
	protected void appendExtraOptions(JseaOptionsBuilder jsob) throws JspException { }

}
