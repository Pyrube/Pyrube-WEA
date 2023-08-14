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

import com.pyrube.one.lang.Strings;

/**
 * JSEA Captcha element.
 * 
 * @author Aranjuez
 * @version Dec 01, 2009
 * @since Pyrube-WEA 1.0
 */
public class CaptchaTag extends TextfieldTag {

	/**
	 * serial version uid
	 */
	private static final long serialVersionUID = 1251540928592819976L;

	private static final String CAPTCHA_TRIGGER_PREFIX = "CAPTCHA-TRIGGER-";

	private static final String CAPTCHA_IMAGE_PREFIX = "CAPTCHA-IMAGE-";
	
	private boolean preloading = true;
	
	private String placeholder = "global.placeholder.captcha";
	
	/**
	 * random trigger id
	 */
	private String triggerId;
	
	/**
	 * random image id
	 */
	private String imageId;

	/**
	 * @return the jseaAttrOptions
	 */
	@Override
	public String getJseaAttrOptions() {
		return TagConstants.JSEA_ATTR_CAPTCHA_OPTIONS;
	}
	
	/**
	 * @return the preloading
	 */
	public boolean isPreloading() {
		return preloading;
	}

	/**
	 * @param preloading the preloading to set
	 */
	public void setPreloading(boolean preloading) {
		this.preloading = preloading;
	}

	/**
	 * @return the placeholder
	 */
	public String getPlaceholder() {
		return placeholder;
	}

	/**
	 * @param placeholder the placeholder to set
	 */
	public void setPlaceholder(String placeholder) {
		this.placeholder = placeholder;
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
	 * @return the imageId
	 */
	public String getImageId() {
		return imageId;
	}

	/**
	 * @param imageId the imageId to set
	 */
	public void setImageId(String imageId) {
		this.imageId = imageId;
	}
	
	@Override
	protected String getDefaultCssClass() { return "captcha"; }

	@Override
	protected void appendExtraOptions(JseaOptionsBuilder jsob) throws JspException {
		this.setTriggerId(CAPTCHA_TRIGGER_PREFIX + UUID.randomUUID().toString());
		this.setImageId(CAPTCHA_IMAGE_PREFIX + UUID.randomUUID().toString());
		jsob.appendJseaOption("preloading", this.isPreloading())
			.appendJseaOption("triggerId", this.getTriggerId())
			.appendJseaOption("imageId", this.getImageId());
	}

	@Override
	protected void appendExtraValidRules(JseaOptionsBuilder jsob) throws JspException {
		super.appendExtraValidRules(jsob);
	}

	@Override
	protected void writeExtraTagContent(TagWriter tagWriter) throws JspException {
		tagWriter.startTag(TRIGGER_TAG);
		this.writeOptionalAttribute(tagWriter, TagConstants.HTML_ATTR_ID, this.getTriggerId());
		this.writeOptionalAttribute(tagWriter, TagConstants.HTML_ATTR_HREF, "javascript:void(0);");
		this.writeOptionalAttribute(tagWriter, TagConstants.HTML_ATTR_CLASS, "refresh");
		tagWriter.forceBlock();
		tagWriter.startTag(IMAGE_TAG);
		this.writeOptionalAttribute(tagWriter, TagConstants.HTML_ATTR_ID, this.getImageId());
		this.writeOptionalAttribute(tagWriter, TagConstants.HTML_ATTR_SRC, Strings.EMPTY);
		this.writeOptionalAttribute(tagWriter, TagConstants.HTML_ATTR_CLASS, "captcha");
		tagWriter.endTag(true);
		// end of trigger
		tagWriter.endTag();
	}
	
}
