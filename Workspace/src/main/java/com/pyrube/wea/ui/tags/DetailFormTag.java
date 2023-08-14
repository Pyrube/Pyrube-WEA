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

import org.springframework.util.ObjectUtils;

/**
 * Databinding-aware JSP tag for rendering an HTML <code>Form</code> whose
 * inner elements are bound to properties on a <em>form object</em>.
 * And it is used for the detailed data
 * 
 * @author Aranjuez
 * @version Dec 01, 2009
 * @since Pyrube-WEA 1.0
 */
public class DetailFormTag extends JseaFormSupportTag {
	/**
	 * serial version uid
	 */
	private static final long serialVersionUID = 2750211014717537854L;
	
	private String refProp;
	private String flagProp;
	private String preHandler;
	private String preHandlers;
	private String postHandler;
	private String postHandlers;
	private boolean validatable = false;
	private boolean modifiable = false;
	private boolean backable = true;
	private Object excludeds;
	
	/**
	 * Get the value of the '{@code action}' attribute.
	 */
	@Override
	public String getAction() {
		return this.getFuncname() + "/" + this.getOperation();
	}
	
	@Override
	public String getFormType() {
		return "detail";
	}

	/**
	 * @return the refProp
	 */
	public String getRefProp() {
		return refProp;
	}
	
	/**
	 * @param refProp the refProp to set
	 */
	public void setRefProp(String refProp) {
		this.refProp = refProp;
	}
	
	/**
	 * @return the flagProp
	 */
	public String getFlagProp() {
		return flagProp;
	}

	/**
	 * @param flagProp the flagProp to set
	 */
	public void setFlagProp(String flagProp) {
		this.flagProp = flagProp;
	}

	/**
	 * @return the preHandler
	 */
	public String getPreHandler() {
		return preHandler;
	}

	/**
	 * @param preHandler the preHandler to set
	 */
	public void setPreHandler(String preHandler) {
		this.preHandler = preHandler;
	}

	/**
	 * @return the preHandlers
	 */
	public String getPreHandlers() {
		return preHandlers;
	}

	/**
	 * @param preHandlers the preHandlers to set
	 */
	public void setPreHandlers(String preHandlers) {
		this.preHandlers = preHandlers;
	}

	/**
	 * @return the postHandler
	 */
	public String getPostHandler() {
		return postHandler;
	}

	/**
	 * @param postHandler the postHandler to set
	 */
	public void setPostHandler(String postHandler) {
		this.postHandler = postHandler;
	}

	/**
	 * @return the postHandlers
	 */
	public String getPostHandlers() {
		return postHandlers;
	}

	/**
	 * @param postHandlers the postHandlers to set
	 */
	public void setPostHandlers(String postHandlers) {
		this.postHandlers = postHandlers;
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
	
	/**
	 * @return the modifiable
	 */
	public boolean isModifiable() {
		return modifiable;
	}

	/**
	 * @param modifiable the modifiable to set
	 */
	public void setModifiable(boolean modifiable) {
		this.modifiable = modifiable;
	}

	/**
	 * @return the backable
	 */
	public boolean isBackable() {
		return backable;
	}

	/**
	 * @param backable the backable to set
	 */
	public void setBackable(boolean backable) {
		this.backable = backable;
	}

	/**
	 * @return the excluded properties
	 */
	public Object getExcludeds() {
		return excludeds;
	}

	/**
	 * @param excluded properties the excluded properties to set
	 */
	public void setExcludeds(Object excludeds) {
		this.excludeds = excludeds;
	}

	@Override
	protected String resolveJseaFormOptions() throws JspException {
		JseaOptionsBuilder jsob = JseaOptionsBuilder.newBuilder();
		appendDefaultOptions(jsob);
		jsob.appendJseaOption(TagConstants.JSEA_OPTION_OPERATION, getOperation())
			.appendJseaOption(TagConstants.JSEA_OPTION_KEY_PROP, getKeyProp())
			.appendJseaOption(TagConstants.JSEA_OPTION_REF_PROP, getRefProp())
			.appendJseaOption(TagConstants.JSEA_OPTION_STAT_PROP, getStatProp())
			.appendJseaOption(TagConstants.JSEA_OPTION_FLAG_PROP, getFlagProp())
			.appendJseaOption(TagConstants.JSEA_OPTION_PRE_HANDLER, getPreHandler(), JseaOptionsBuilder.JSEA_OPTION_TYPE_JS_FUNCTION)
			.appendJseaOption(TagConstants.JSEA_OPTION_PRE_HANDLERS, getPreHandlers(), JseaOptionsBuilder.JSEA_OPTION_TYPE_JS_OBJECT)
			.appendJseaOption(TagConstants.JSEA_OPTION_POST_HANDLER, getPostHandler(), JseaOptionsBuilder.JSEA_OPTION_TYPE_JS_FUNCTION)
			.appendJseaOption(TagConstants.JSEA_OPTION_POST_HANDLERS, getPostHandlers(), JseaOptionsBuilder.JSEA_OPTION_TYPE_JS_OBJECT)
			.appendJseaOption(TagConstants.JSEA_OPTION_VALIDATABLE, isValidatable())
			.appendJseaOption(TagConstants.JSEA_OPTION_MODIFIABLE, isModifiable());
		appendExtraOptions(jsob);
		return jsob.toString();
	}
	
	@Override
	protected void appendExtraOptions(JseaOptionsBuilder jsob) throws JspException {
		Object excludeds = this.getExcludeds();
		if (!ObjectUtils.isEmpty(excludeds)) {
			if (String.class.isInstance(excludeds)) {
				jsob.appendJseaOption("excludes", excludeds, JseaOptionsBuilder.JSEA_OPTION_TYPE_JS_OBJECT);
			} else {
				jsob.appendJseaOption("excludes", excludeds);
			}
		}
	}
	
	@Override
	protected String resolveFormStylesheet() {
		return "detail-form";
	}

}
