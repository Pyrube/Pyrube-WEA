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
 * Databinding-aware JSP tag for rendering an HTML <code>Form</code> whose
 * inner elements are bound to properties on a <em>form object</em>.
 * And it is used for the grid data in a popped dialog
 * 
 * @author Aranjuez
 * @version Dec 01, 2009
 * @since Pyrube-WEA 1.0
 */
public class LookupFormTag extends GridFormTag {
	
	/**
	 * serial version uid
	 */
	private static final long serialVersionUID = 1743138331780701868L;

	private Object returnProps;
	
	@Override
	public String getFormType() {
		return "lookup";
	}

	/**
	 * @return the returnProps
	 */
	public Object getReturnProps() {
		return returnProps;
	}

	/**
	 * @param returnProps the returnProps to set
	 */
	public void setReturnProps(Object returnProps) {
		this.returnProps = returnProps;
	}

	@Override
	protected void appendExtraOptions(JseaOptionsBuilder jsob) throws JspException {
		Object returnProps = this.getReturnProps();
		if (String.class.isInstance(returnProps)) {
			jsob.appendJseaOption(TagConstants.JSAF_OPTION_RETURN_PROPS, returnProps, JseaOptionsBuilder.JSEA_OPTION_TYPE_JS_OBJECT);
		} else {
			jsob.appendJseaOption(TagConstants.JSAF_OPTION_RETURN_PROPS, returnProps);
		}
	}
	
	@Override
	protected String resolveFormStylesheet() {
		return "lookup-form";
	}

}
