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
 * And it is used for the grid data
 * 
 * @author Aranjuez
 * @version Dec 01, 2009
 * @since Pyrube-WEA 1.0
 */
public class GridFormTag extends JseaFormSupportTag {

	/**
	 * serial version uid
	 */
	private static final long serialVersionUID = 361588218799084110L;

	private String rsProp;
	private String typeProp;
	
	private boolean filterable = true;
	
	@Override
	public String getFormType() {
		return "grid";
	}

	/**
	 * @return the rsProp
	 */
	public String getRsProp() {
		return rsProp;
	}

	/**
	 * @param rsProp the rsProp to set
	 */
	public void setRsProp(String rsProp) {
		this.rsProp = rsProp;
	}

	/**
	 * @return the typeProp
	 */
	public String getTypeProp() {
		return typeProp;
	}

	/**
	 * @param typeProp the typeProp to set
	 */
	public void setTypeProp(String typeProp) {
		this.typeProp = typeProp;
	}

	/**
	 * @return the filterable
	 */
	public boolean isFilterable() {
		return filterable;
	}

	/**
	 * @param filterable the filterable to set
	 */
	public void setFilterable(boolean filterable) {
		this.filterable = filterable;
	}

	@Override
	protected String resolveJseaFormOptions() throws JspException {
		JseaOptionsBuilder jsob = JseaOptionsBuilder.newBuilder();
		appendDefaultOptions(jsob);
		jsob.appendJseaOption(TagConstants.JSEA_OPTION_RS_PROP, getRsProp())
			.appendJseaOption(TagConstants.JSEA_OPTION_FILTERABLE, isFilterable());
		appendExtraOptions(jsob);
		return jsob.toString();
	}
	
	@Override
	protected void appendExtraOptions(JseaOptionsBuilder jsob) throws JspException {
		jsob.appendJseaOption(TagConstants.JSEA_OPTION_KEY_PROP, getKeyProp())
			.appendJseaOption(TagConstants.JSEA_OPTION_TYPE_PROP, getTypeProp())
			.appendJseaOption(TagConstants.JSEA_OPTION_STAT_PROP, getStatProp());
	}
	
	@Override
	protected String resolveFormStylesheet() {
		return "grid-form";
	}

}
