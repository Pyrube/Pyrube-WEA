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
import javax.servlet.jsp.tagext.BodyContent;

import org.springframework.web.servlet.tags.form.TagWriter;

import com.pyrube.one.lang.Strings;
import com.pyrube.wea.util.Weas;
/**
 * Super class for actiontaking-aware JSP tag for rendering an HTML <code>Element</code> whose
 * inner elements are bound to properties on a <em>element object</em>.
 * 
 * @author Aranjuez
 * @version Dec 01, 2009
 * @since Pyrube-WEA 1.0
 */
public abstract class JseaActionElementTag extends AccessControllingTag {
	
	/**
	 * serial version uid
	 */
	private static final long serialVersionUID = 8292234395881763174L;

	private static final String EM_TAG = "em";
	private static final String SPAN_TAG = "span";

	private BodyContent bodyContent;

	private String url;
	private String mode;
	private String success;
	private String confirm;
	private String reason;
	private String yesno;
	private String method;
	private String callback;
	private boolean dors;
	private boolean toggleable;

	/**
	 * @return the title
	 */
	@Override
	public String getTitle() {
		String title = super.getTitle();
		if (title == null) {
			try {
				title = resolveDefaultTitle();
			} catch (JspException e) {
				title = Strings.EMPTY;
			}
		}
		return Weas.localizeMessage(title);
	}
	
	/**
	 * @return the bodyContent
	 */
	public BodyContent getBodyContent() {
		return bodyContent;
	}

	/**
	 * @param bodyContent the bodyContent to set
	 */
	public void setBodyContent(BodyContent bodyContent) {
		this.bodyContent = bodyContent;
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
	 * @return the success
	 */
	public String getSuccess() {
		return success;
	}

	/**
	 * @param success the success to set
	 */
	public void setSuccess(String success) {
		this.success = success;
	}

	/**
	 * @return the confirm
	 */
	public String getConfirm() {
		return confirm;
	}

	/**
	 * @param confirm the confirm to set
	 */
	public void setConfirm(String confirm) {
		this.confirm = confirm;
	}

	/**
	 * @return the reason
	 */
	public String getReason() {
		return reason;
	}

	/**
	 * @param reason the reason to set
	 */
	public void setReason(String reason) {
		this.reason = reason;
	}
	
	/**
	 * @return the yesno
	 */
	public String getYesno() {
		return yesno;
	}

	/**
	 * @param yesno the yesno to set
	 */
	public void setYesno(String yesno) {
		this.yesno = yesno;
	}
	
	/**
	 * @return the method
	 */
	public String getMethod() {
		return method;
	}

	/**
	 * @param method the method to set
	 */
	public void setMethod(String method) {
		this.method = method;
	}

	/**
	 * @return the callback
	 */
	public String getCallback() {
		return callback;
	}
	/**
	 * @param callback
	 *        the callback to set
	 */
	public void setCallback(String callback) {
		this.callback = callback;
	}

	/**
	 * @return the dors
	 */
	public boolean isDors() {
		return dors;
	}

	/**
	 * @param dors the dors to set
	 */
	public void setDors(boolean dors) {
		this.dors = dors;
	}

	/**
	 * @return the toggleable
	 */
	public boolean isToggleable() {
		return toggleable;
	}

	/**
	 * @param toggleable the toggleable to set
	 */
	public void setToggleable(boolean toggleable) {
		this.toggleable = toggleable;
	}

	@Override
	public void doInitBody() throws JspException { }

	@Override
	protected int writeTagContent(TagWriter tagWriter) throws JspException {
		this.setTagWriter(tagWriter);
		if (hasAccess(this.getAccess())) {
			tagWriter.startTag(resolveElementTag());
			writeDefaultAttributes(tagWriter);
			writeOptionalAttribute(tagWriter, TagConstants.HTML_ATTR_TYPE, resolveElementTag());
			writeJseaOptionsAttribute(tagWriter);
			tagWriter.forceBlock();
			
			//write span tag
			tagWriter.startTag(SPAN_TAG);
			writeOptionalAttribute(tagWriter, TagConstants.HTML_ATTR_CLASS, getName());
			tagWriter.endTag(true);
			
			//write em tag
			tagWriter.startTag(EM_TAG);
		}
		return EVAL_BODY_BUFFERED;
	}

	@Override
	public int doEndTag() throws JspException {
		if (hasAccess(this.getAccess())) {
			TagWriter tagWriter = this.getTagWriter();
			String elementText = null;
			if (this.bodyContent != null) {
				elementText = this.bodyContent.getString();
			} else {
				elementText = resolveDefaultText();
			}
			tagWriter.appendValue(Weas.localizeMessage(elementText));
			tagWriter.endTag();		
			tagWriter.endTag();
		}
		return EVAL_PAGE;
	}

	@Override
	public void doFinally() {
		super.doFinally();
		this.setTitle(null);
	}

	/**
	 * return a concrete element tag for subclasses
	 * @return
	 * @throws JspException
	 */
	protected abstract String resolveElementTag() throws JspException;

	@Override
	protected void appendJseaOptions(JseaOptionsBuilder jsob) throws JspException {
		jsob.appendJseaOption(TagConstants.JSEA_OPTION_URL, getUrl())
			.appendJseaOption(TagConstants.JSEA_OPTION_MODE, getMode())
			.appendJseaOption(TagConstants.JSEA_OPTION_SUCCESS, getSuccess())
			.appendJseaOption(TagConstants.JSEA_OPTION_CONFIRM, getConfirm())
			.appendJseaOption(TagConstants.JSEA_OPTION_REASON, getReason())
			.appendJseaOption(TagConstants.JSEA_OPTION_YESNO, getYesno())
			.appendJseaOption(TagConstants.JSEA_OPTION_METHOD, getMethod(), JseaOptionsBuilder.JSEA_OPTION_TYPE_FUNCTION)
			.appendJseaOption(TagConstants.JSEA_OPTION_CALLBACK, getCallback(), JseaOptionsBuilder.JSEA_OPTION_TYPE_FUNCTION)
			.appendJseaOption(TagConstants.JSEA_OPTION_DORS, isDors())
			.appendJseaOption(TagConstants.JSEA_OPTION_TOGGLEABLE, isToggleable());
		super.appendJseaOptions(jsob);
	}

	@Override
	protected String resolveCssClass() throws JspException {
		String cssClass = super.resolveCssClass();
		StringBuffer buf = new StringBuffer();
		if (!Strings.isEmpty(cssClass)) { buf.append(cssClass + " "); }
		if (this.isToggleable()) { buf.append("toggleable "); }
		return buf.toString().trim();
	}

	/**
	 * resolve default text of this element
	 * @return
	 * @throws JspException
	 */
	protected abstract String resolveDefaultText() throws JspException;

	/**
	 * resolve default title of this element
	 * @return
	 * @throws JspException
	 */
	protected abstract String resolveDefaultTitle() throws JspException;

}
