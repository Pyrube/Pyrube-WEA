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
 * Databinding-aware JSP tag for rendering an HTML <code>Form</code> whose
 * inner elements are bound to properties on a <em>form object</em>.
 * And it is used for the simple page, such as sign-on, forget password, etc
 * 
 * @author Aranjuez
 * @version Dec 01, 2009
 * @since Pyrube-WEA 1.0
 */
public class SimpleFormTag extends JseaFormSupportTag {
	/**
	 * serial version uid
	 */
	private static final long serialVersionUID = 236329165261626763L;
	
	private boolean validatable = false;
	
	@Override
	public String getFormType() {
		return "simple";
	}

	/**
	 * @return the validatable
	 */
	public boolean isValidatable() {
		return this.validatable;
	}

	/**
	 * @param validatable the validate to set
	 */
	public void setValidatable(boolean validatable) {
		this.validatable = validatable;
	}
	
	@Override
	protected int writeTagContent(TagWriter tagWriter) throws JspException {
		
		super.writeTagContent(tagWriter);
		
		return EVAL_BODY_INCLUDE;
	}

	@Override
	protected String resolveJseaFormOptions() throws JspException {
		JseaOptionsBuilder jsob = JseaOptionsBuilder.newBuilder();
		appendDefaultOptions(jsob);
		jsob.appendJseaOption(TagConstants.JSEA_OPTION_OPERATION, getOperation())
			.appendJseaOption(TagConstants.JSEA_OPTION_VALIDATABLE, isValidatable());
		appendExtraOptions(jsob);
		appendJseaEventOptions(jsob);
		return jsob.toString();
	}
	
	@Override
	protected void appendExtraOptions(JseaOptionsBuilder jsob) throws JspException {
	}
	
	@Override
	protected String resolveFormStylesheet() {
		return "simple-form";
	}

}
