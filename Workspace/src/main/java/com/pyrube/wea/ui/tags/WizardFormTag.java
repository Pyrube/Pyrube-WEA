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
 * Databinding-aware JSP tag for rendering an HTML '{@code form}' whose
 * inner elements are bound to properties on a <em>form object</em>.
 * And it is used for a wizard to edit the detailed data step by step.
 * 
 * @author Aranjuez
 * @version OCT 01, 2023
 * @since Pyrube-WEA 1.1
 */
public class WizardFormTag extends DetailFormTag {
	/**
	 * serial version uid
	 */
	private static final long serialVersionUID = 7248524992269790641L;

	/**
	 * the index of current step
	 */
	private int current = 0;
	
	/**
	 * @return the current
	 */
	public int getCurrent() {
		return current;
	}
	
	/**
	 * @param current the current to set
	 */
	public void setCurrent(int current) {
		this.current = current;
	}

	@Override
	public String getFormType() {
		return "wizard";
	}

	@Override
	public boolean isValidatable() {
		return false;
	}

	@Override
	protected void appendExtraOptions(JseaOptionsBuilder jsob) throws JspException {
		jsob.appendJseaOption(TagConstants.JSEA_OPTION_CURRENT, getCurrent());
	}
	
	@Override
	protected String resolveFormStylesheet() {
		return "wizard-form";
	}

}
