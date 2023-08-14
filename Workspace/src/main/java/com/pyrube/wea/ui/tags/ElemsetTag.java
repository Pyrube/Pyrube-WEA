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
import javax.servlet.jsp.PageContext;

import org.springframework.core.Conventions;
import org.springframework.web.servlet.tags.form.AbstractHtmlElementTag;
import org.springframework.web.servlet.tags.form.TagWriter;

import com.pyrube.one.lang.Strings;
import com.pyrube.wea.util.Weas;

/**
 * An HTML 'div' element. This element is provided for element set as a container. 
 * 
 * @author Aranjuez
 * @version Dec 01, 2009
 * @since Pyrube-WEA 1.0
 */
public class ElemsetTag extends AbstractHtmlElementTag {
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -7921943659050899941L;
	
	private static final String LI_TAG = "li";
	private static final String UL_TAG = "ul";
	private static final String DIV_TAG = "div";
	private TagWriter tagWriter;	
	
	private String type;
	private String header = String.valueOf(false);
	private String stylesheet;

	/**
	 * @return the tagWriter
	 */
	public TagWriter getTagWriter() {
		return tagWriter;
	}

	/**
	 * @param tagWriter the tagWriter to set
	 */
	public void setTagWriter(TagWriter tagWriter) {
		this.tagWriter = tagWriter;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the header
	 */
	public String getHeader() {
		return header;
	}

	/**
	 * @param header the header to set
	 */
	public void setHeader(String header) {
		this.header = header;
	}

	/**
	 * @return the stylesheet
	 */
	public String getStylesheet() {
		return stylesheet;
	}

	/**
	 * @param stylesheet the stylesheet to set
	 */
	public void setStylesheet(String stylesheet) {
		this.stylesheet = stylesheet;
	}
	
	@Override
	protected int writeTagContent(TagWriter tagWriter)
		throws JspException {
		this.setTagWriter(tagWriter);
		//write container, like <div class="form-area">
		this.tagWriter.startTag(DIV_TAG);
		writeOptionalAttribute(tagWriter, TagConstants.HTML_ATTR_CLASS, resolveContainerStylesheet());
		
		// write header, like <ul class="header"><li>Sample Info</li></ul>
		// if it is false, no header
		if (!String.valueOf(false).equalsIgnoreCase(header)) {
			this.tagWriter.startTag(UL_TAG);
			writeOptionalAttribute(tagWriter, TagConstants.HTML_ATTR_CLASS, resolveHeaderStylesheet());
			this.tagWriter.startTag(LI_TAG);
			this.tagWriter.appendValue(Weas.localizeMessage(this.resolveHeader()));
			this.tagWriter.endTag(); //end li
			this.tagWriter.endTag(); //end ul
		}
		
		//write fieldset, like <ul class="fields">
		this.tagWriter.startTag(UL_TAG);
		writeOptionalAttribute(tagWriter, TagConstants.HTML_ATTR_CLASS, this.resolveStylesheet());
		tagWriter.forceBlock();
		return EVAL_BODY_INCLUDE;
	}

	/**
	 * @return
	 * @throws JspException
	 * @see javax.servlet.jsp.tagext.TagSupport#doEndTag()
	 */
	@Override
	public int doEndTag()
		throws JspException {
		this.tagWriter.endTag();
		this.tagWriter.endTag();
		return EVAL_PAGE;
	}
	
	private String resolveHeader() {
		String header = this.getHeader();
		if (String.valueOf(true).equalsIgnoreCase(header)) {
			Object funcnameAttr =
				this.pageContext.getAttribute(
					Conventions.getQualifiedAttributeName(JseaFormSupportTag.class, "funcname"),
					PageContext.REQUEST_SCOPE);
			if (funcnameAttr != null) {
				String funcname = (String)funcnameAttr;
				header = funcname + ".header." + funcname + "-info";
			}
		}
		return header;
	}

	private String resolveContainerStylesheet() {
		return this.getType() + "-area";
	}

	private String resolveHeaderStylesheet() {
		return "header";
	}

	private String resolveStylesheet() {
		String stylesheet = this.getStylesheet();
		return Strings.isEmpty(stylesheet) ? "fields" : stylesheet;
	}
}
