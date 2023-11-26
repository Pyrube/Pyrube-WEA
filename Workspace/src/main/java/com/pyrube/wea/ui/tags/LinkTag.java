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

import org.springframework.web.servlet.tags.form.TagWriter;

import com.pyrube.one.lang.Strings;

/**
 * An HTML 'a' element. This element is provided for access control with permission 
 * and user roles.
 * 
 * @author Aranjuez
 * @version Dec 01, 2009
 * @since Pyrube-WEA 1.0
 */
public class LinkTag extends JseaActionElementTag {

	/**
	 * serial version uid
	 */
	private static final long serialVersionUID = 5517134065228788512L;

	private static final String LINK_TAG = "a";
	private static final String LINK_TEXT_PREFIX = "link.text.";
	private static final String LINK_ALT_PREFIX = "link.alt.";

	/**
	 * @return the jseaAttrOptions
	 */
	@Override
	public String getJseaAttrOptions() {
		return TagConstants.JSEA_ATTR_LINK_OPTIONS;
	}

	@Override
	protected String resolveElementTag() throws JspException {
		return LINK_TAG;
	}

	@Override
	protected String resolveCssClass() throws JspException {
		String cssClass = super.resolveCssClass();
		return "lnk " + getName() + " " + (cssClass == null ? Strings.EMPTY : cssClass);
	}

	@Override
	protected void writeOptionalAttributes(TagWriter tagWriter) throws JspException {
		super.writeOptionalAttributes(tagWriter);
		tagWriter.writeOptionalAttributeValue(TagConstants.HTML_ATTR_HREF, "javascript:void(0);");
	}

	@Override
	protected String resolveDefaultText() throws JspException {
		return LINK_TEXT_PREFIX + getName();
	}

	@Override
	protected String resolveDefaultTitle() throws JspException {
		return LINK_ALT_PREFIX + getName();
	}

}