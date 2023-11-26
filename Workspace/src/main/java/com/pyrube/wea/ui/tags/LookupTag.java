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

import java.util.UUID;

import javax.servlet.jsp.JspException;

import org.springframework.web.servlet.tags.form.TagWriter;

/**
 * JSEA Lookup-field element.
 * 
 * @author Aranjuez
 * @version Oct 01, 2023
 * @since Pyrube-WEA 1.1
 */
public class LookupTag extends TextfieldTag {
	/**
	 * serial version uid
	 */
	private static final long serialVersionUID = 403773773759416830L;

	private static final String LOOKUP_TRIGGER_PREFIX = "LOOKUP-TRIGGER-";

	/**
	 * JSEA Optional Option, to open Lookup
	 */
	private String url;
	private String urlParams;
	private String args;
	private Object cascades;

	/**
	 * random trigger id for link: Lookup
	 */
	private String triggerId;

	/**
	 * JSEA Event, callback after chosen
	 */
	private String onChoose;

	/**
	 * @return the jseaAttrOptions
	 */
	@Override
	public String getJseaAttrOptions() { return TagConstants.JSEA_ATTR_LOOKUP_OPTIONS; }

	@Override
	public boolean isEmptiable() { return true; }

	@Override
	protected boolean isReadonly() { return true; }

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return the urlParams
	 */
	public String getUrlParams() {
		return urlParams;
	}

	/**
	 * @param urlParams the urlParams to set
	 */
	public void setUrlParams(String urlParams) {
		this.urlParams = urlParams;
	}

	/**
	 * @return the args
	 */
	public String getArgs() {
		return args;
	}

	/**
	 * @param args the args to set
	 */
	public void setArgs(String args) {
		this.args = args;
	}

	/**
	 * @return the cascades
	 */
	public Object getCascades() {
		return cascades;
	}

	/**
	 * @param cascades the cascades to set
	 */
	public void setCascades(Object cascades) {
		this.cascades = cascades;
	}

	/**
	 * @return the triggerId
	 */
	public String getTriggerId() {
		return triggerId;
	}

	/**
	 * @param triggerId the triggerId to set
	 */
	public void setTriggerId(String triggerId) {
		this.triggerId = triggerId;
	}

	/**
	 * @return the onChoose
	 */
	public String getOnChoose() {
		return onChoose;
	}

	/**
	 * @param onChoose the onChoose to set
	 */
	public void setOnChoose(String onChoose) {
		this.onChoose = onChoose;
	}
	@Override
	protected String getDefaultCssClass() throws JspException { return"chooser"; }

	@Override
	protected void appendExtraOptions(JseaOptionsBuilder jsob) throws JspException {
		this.setTriggerId(LOOKUP_TRIGGER_PREFIX + UUID.randomUUID().toString());
		jsob.appendJseaOption(TagConstants.JSEA_OPTION_URL, this.getUrl())
			.appendJseaOption(TagConstants.JSEA_OPTION_PARAMETERS, this.getUrlParams())
			.appendJseaOption(TagConstants.JSEA_OPTION_ARGUMENTS, this.getArgs())
			.appendJseaOption("cascades", this.getCascades(), JseaOptionsBuilder.JSEA_OPTION_TYPE_OBJECT)
			.appendJseaOption(TagConstants.JSEA_OPTION_EMPTIABLE, this.isEmptiable())
			.appendJseaOption(TagConstants.JSEA_OPTION_TRIGGER_ID, this.getTriggerId())
			.appendJseaOption(TagConstants.JSEA_EVENT_ONCHOOSE, this.getOnChoose(), JseaOptionsBuilder.JSEA_OPTION_TYPE_FUNCTION);
	}

	@Override
	protected void writeExtraTagContent(TagWriter tagWriter) throws JspException {
		tagWriter.startTag(TRIGGER_TAG);
		this.writeOptionalAttribute(tagWriter, TagConstants.HTML_ATTR_ID, this.getTriggerId());
		this.writeOptionalAttribute(tagWriter, TagConstants.HTML_ATTR_HREF, "javascript:void(0);");
		this.writeOptionalAttribute(tagWriter, TagConstants.HTML_ATTR_CLASS, "lookup");
		tagWriter.appendValue("&nbsp;");
		tagWriter.endTag(true);
	}

}
