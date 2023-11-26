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
 * JSEA Upload-field element.
 * 
 * @author Aranjuez
 * @version Dec 01, 2009
 * @since Pyrube-WEA 1.0
 */
public class UploadTag extends TextfieldTag {
	
	/**
	 * serial version uid
	 */
	private static final long serialVersionUID = 7965057068008823599L;

	private static final String UPLOAD_TRIGGER_PREFIX = "UPLOAD-TRIGGER-";
	private static final String UPLOAD_TRIGGER2_PREFIX = "UPLOAD-TRIGGER2-";
	
	private static final String UPLOAD_PROGRESSBAR_PREFIX = "UPLOAD-PROGRESSBAR-";

	/**
	 * JSEA Standard Option, uploading mode as below
	 * posting : form submit
	 * instant : ajax upload instantly once a file was chosen
	 * manual  : ajax upload manually by clicking 'Upload' icon
	 */
	private String mode = "posting";

	/**
	 * JSEA Optional Option, to ajax upload
	 */
	private String url;
	private String uploadFile = "uploadFile";
	private Object mimes;

	/**
	 * JSEA Optional Option, to ajax upload
	 */
	private String more;

	/**
	 * random trigger id for link: Browse
	 */
	private String triggerId;

	/**
	 * random trigger-2 id for link: Upload
	 */
	private String trigger2Id;

	/**
	 * random progress-bar id
	 */
	private String progressbarId;

	/**
	 * JSEA Event, callback after ajax upload success
	 */
	private String onSuccess;

	/**
	 * @return the jseaAttrOptions
	 */
	@Override
	public String getJseaAttrOptions() {
		return TagConstants.JSEA_ATTR_UPLOAD_OPTIONS;
	}

	@Override
	protected boolean isReadonly() {
		return true;
	}

	@Override
	public String getPlaceholder() {
		String placeholder = super.getPlaceholder();
		return Strings.isEmpty(placeholder) ? "global.placeholder.choose-file" : placeholder;
	}

	/**
	 * @return the mode
	 */
	public String getMode() {
		return mode;
	}

	/**
	 * @param mode the mode to set
	 */
	public void setMode(String mode) {
		this.mode = mode;
	}

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
	 * @return the uploadFile
	 */
	public String getUploadFile() {
		return uploadFile;
	}

	/**
	 * @param uploadFile the uploadFile to set
	 */
	public void setUploadFile(String uploadFile) {
		this.uploadFile = uploadFile;
	}

	/**
	 * @return the more
	 */
	public String getMore() {
		return more;
	}

	/**
	 * @param more the more to set
	 */
	public void setMore(String more) {
		this.more = more;
	}

	/**
	 * @return the mimes
	 */
	public Object getMimes() {
		return mimes;
	}

	/**
	 * @param mimes the mimes to set
	 */
	public void setMimes(Object mimes) {
		this.mimes = mimes;
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
	 * @return the trigger2Id
	 */
	public String getTrigger2Id() {
		return trigger2Id;
	}

	/**
	 * @param trigger2Id the trigger2Id to set
	 */
	public void setTrigger2Id(String trigger2Id) {
		this.trigger2Id = trigger2Id;
	}

	/**
	 * @return the progressbarId
	 */
	public String getProgressbarId() {
		return progressbarId;
	}

	/**
	 * @param progressbarId the progressbarId to set
	 */
	public void setProgressbarId(String progressbarId) {
		this.progressbarId = progressbarId;
	}

	/**
	 * @return the onSuccess
	 */
	public String getOnSuccess() {
		return onSuccess;
	}

	/**
	 * @param onSuccess the onSuccess to set
	 */
	public void setOnSuccess(String onSuccess) {
		this.onSuccess = onSuccess;
	}
	
	@Override
	protected String getDefaultCssClass() { return "upload-value"; }
	
	@Override
	protected void appendExtraOptions(JseaOptionsBuilder jsob) throws JspException {
		this.setTriggerId(UPLOAD_TRIGGER_PREFIX + UUID.randomUUID().toString());
		this.setTrigger2Id(UPLOAD_TRIGGER2_PREFIX + UUID.randomUUID().toString());
		this.setProgressbarId(UPLOAD_PROGRESSBAR_PREFIX + UUID.randomUUID().toString());
		jsob.appendJseaOption(TagConstants.JSEA_OPTION_MODE, this.getMode())
			.appendJseaOption(TagConstants.JSEA_OPTION_URL, this.getUrl())
			.appendJseaOption("uploadFile", this.getUploadFile())
			.appendJseaOption("mimes", this.getMimes(), JseaOptionsBuilder.JSEA_OPTION_TYPE_OBJECT)
			.appendJseaOption(TagConstants.JSEA_OPTION_MORE, this.getMore(), JseaOptionsBuilder.JSEA_OPTION_TYPE_OBJECT)
			.appendJseaOption(TagConstants.JSEA_EVENT_ONSUCCESS, this.getOnSuccess(), JseaOptionsBuilder.JSEA_OPTION_TYPE_FUNCTION)
			.appendJseaOption("triggerId", this.getTriggerId());
		if ("manual".equals(this.getMode())) {
			jsob.appendJseaOption("hasTrigger2", true);
			jsob.appendJseaOption("trigger2Id", this.getTrigger2Id());
		}
		jsob.appendJseaOption("progressbarId", this.getProgressbarId());
	}

	@Override
	protected void appendExtraValidRules(JseaOptionsBuilder jsob) throws JspException {
		super.appendExtraValidRules(jsob);
		jsob.appendJseaOption("mimes", this.getMimes(), JseaOptionsBuilder.JSEA_OPTION_TYPE_OBJECT);
	}

	@Override
	protected void writeExtraTagContent(TagWriter tagWriter) throws JspException {
		tagWriter.startTag(TRIGGER_TAG);
		this.writeOptionalAttribute(tagWriter, TagConstants.HTML_ATTR_ID, this.getTriggerId());
		this.writeOptionalAttribute(tagWriter, TagConstants.HTML_ATTR_HREF, "javascript:void(0);");
		this.writeOptionalAttribute(tagWriter, TagConstants.HTML_ATTR_CLASS, "browse");
		tagWriter.appendValue("&nbsp;");
		tagWriter.endTag(true);
		if ("manual".equals(this.getMode())) {
			tagWriter.startTag(TRIGGER_TAG);
			this.writeOptionalAttribute(tagWriter, TagConstants.HTML_ATTR_ID, this.getTrigger2Id());
			this.writeOptionalAttribute(tagWriter, TagConstants.HTML_ATTR_HREF, "javascript:void(0);");
			this.writeOptionalAttribute(tagWriter, TagConstants.HTML_ATTR_CLASS, "upload");
			tagWriter.appendValue("&nbsp;");
			tagWriter.endTag(true);
		}
	}

}
