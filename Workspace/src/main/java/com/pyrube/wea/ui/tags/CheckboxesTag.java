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

/**
 * The <code>Checkboxes</code> tag renders multiple HTML 'li' tags with 
 * respective invisible HTML 'input' tag with type 'checkbox'.
 * 
 * @author Aranjuez
 * @version Dec 01, 2009
 * @since Pyrube-WEA 1.0
 */
public class CheckboxesTag extends JseaMultiCheckedFieldSupportTag {

	/**
	 * serial version uid
	 */
	private static final long serialVersionUID = 6517882611262851884L;

	/**
	 * @return the jseaAttrOptions
	 */
	@Override
	public String getJseaAttrOptions() {
		return TagConstants.JSEA_ATTR_CHECKBOXES_OPTIONS;
	}

	@Override
	public String getJseaAttrSingleFieldOptions() {
		return TagConstants.JSEA_ATTR_CHECKBOX_OPTIONS;
	}

	@Override
	protected void writeOptionalAttributes(TagWriter tagWriter) throws JspException {
		super.writeOptionalAttributes(tagWriter);
		
		tagWriter.writeAttribute(TagConstants.JSAF_ATTR_FIELD_TYPE, "checkboxes");
	}

	@Override
	protected String resolveFieldType() throws JspException { return "checkbox"; }

	@Override
	protected String getDefaultCssClass() { return "checkboxes"; }

}
