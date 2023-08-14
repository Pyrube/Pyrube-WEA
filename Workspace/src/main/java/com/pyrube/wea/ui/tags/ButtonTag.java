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

import com.pyrube.one.lang.Strings;

/**
 * An HTML 'button' element. This element is provided for access control with permission 
 * and user roles.
 * 
 * @author Aranjuez
 * @version Dec 01, 2009
 * @since Pyrube-WEA 1.0
 */
public class ButtonTag extends JseaActionElementTag {

	/**
	 * serial version uid
	 */
	private static final long serialVersionUID = 3775967879978319449L;
	
	private static final String BUTTON_TAG = "button";
	private static final String BUTTON_NAME_PREFIX = "btn";
	private static final String BUTTON_TEXT_PREFIX = "button.text.";
	private static final String BUTTON_ALT_PREFIX = "button.alt.";

	/**
	 * @return the jseaAttrOptions
	 */
	@Override
	public String getJseaAttrOptions() {
		return TagConstants.JSEA_ATTR_BUTTON_OPTIONS;
	}

	/**
	 * @return the name with prefix
	 */
	public String getName() throws JspException {
		return BUTTON_NAME_PREFIX + Strings.capitalize(super.getName());
	}

	@Override
	protected String resolveElementTag() throws JspException {
		return BUTTON_TAG;
	}

	@Override
	protected String resolveCssClass() throws JspException {
		String cssClass = super.resolveCssClass();
		return "btn " + super.getName() + " " + (cssClass == null ? Strings.EMPTY : cssClass);
	}

	@Override
	protected String resolveDefaultText() throws JspException {
		return BUTTON_TEXT_PREFIX + super.getName();
	}

	@Override
	protected String resolveDefaultTitle() throws JspException {
		return BUTTON_ALT_PREFIX + super.getName();
	}

}