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
	
	private String funcname;
	private String uploadFile = "uploadFile";
	private boolean uploadable = true;
	private String progressbarId = "progressbar";
	private String uploadedFile;
	private String uploadType = "TPL";
	private String more = "";
	private Object mimes; 
	private String placeholder = "global.placeholder.choose-file";
	private String onSuccess;
	
	/**
	 * random trigger id
	 */
	private String triggerId;

	/**
	 * @return the jseaAttrOptions
	 */
	@Override
	public String getJseaAttrOptions() {
		return TagConstants.JSEA_ATTR_UPLOAD_OPTIONS;
	}
	
	@Override
	public String getName() {
		return this.getUploadFile() + "Path";
	}

	@Override
	protected boolean isReadonly() {
		return true;
	}

	/**
	 * @return the funcname
	 */
	public String getFuncname() {
		return funcname;
	}

	/**
	 * @param funcname the funcname to set
	 */
	public void setFuncname(String funcname) {
		this.funcname = funcname;
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
	 * @return the uploadable
	 */
	public boolean isUploadable() {
		return uploadable;
	}

	/**
	 * @param uploadable the uploadable to set
	 */
	public void setUploadable(boolean uploadable) {
		this.uploadable = uploadable;
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
	 * @return the uploadedFile
	 */
	public String getUploadedFile() {
		return uploadedFile;
	}

	/**
	 * @param uploadedFile the uploadedFile to set
	 */
	public void setUploadedFile(String uploadedFile) {
		this.uploadedFile = uploadedFile;
	}

	/**
	 * @return the uploadType
	 */
	public String getUploadType() {
		return uploadType;
	}

	/**
	 * @param uploadType the uploadType to set
	 */
	public void setUploadType(String uploadType) {
		this.uploadType = uploadType;
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
	
	@Override
	protected String getDefaultCssClass() { return "upload"; }
	
	@Override
	protected void appendExtraOptions(JseaOptionsBuilder jsob) throws JspException {
		this.setTriggerId(UPLOAD_TRIGGER_PREFIX + UUID.randomUUID().toString());
		jsob.appendJseaOption("funcname", this.getFuncname())
			.appendJseaOption("uploadFile", this.getUploadFile())
			.appendJseaOption("uploadable", this.isUploadable())
			.appendJseaOption("uploadedFile", this.getUploadedFile())
			.appendJseaOption("uploadType", this.getUploadType())
 			.appendJseaOption("more", "{" + this.getMore() + "}", JseaOptionsBuilder.JSEA_OPTION_TYPE_JS_OBJECT)
			.appendJseaOption("triggerId", this.getTriggerId())
			.appendJseaOption("mimes", this.getMimes(), JseaOptionsBuilder.JSEA_OPTION_TYPE_JS_OBJECT)
			.appendJseaOption("onSuccess", this.getOnSuccess(), JseaOptionsBuilder.JSEA_OPTION_TYPE_JS_FUNCTION);
	}

	@Override
	protected void appendExtraValidRules(JseaOptionsBuilder jsob) throws JspException {
		super.appendExtraValidRules(jsob);
		jsob.appendJseaOption("mimes", this.getMimes(), JseaOptionsBuilder.JSEA_OPTION_TYPE_JS_OBJECT);
	}

	@Override
	protected void writeExtraTagContent(TagWriter tagWriter) throws JspException {
		tagWriter.startTag(TRIGGER_TAG);
		this.writeOptionalAttribute(tagWriter, TagConstants.HTML_ATTR_ID, this.getTriggerId());
		this.writeOptionalAttribute(tagWriter, TagConstants.HTML_ATTR_HREF, "javascript:void(0);");
		this.writeOptionalAttribute(tagWriter, TagConstants.HTML_ATTR_CLASS, "upload");
		tagWriter.endTag(true);
	}
	
}
