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
import javax.servlet.jsp.tagext.BodyTag;

import org.springframework.web.servlet.tags.form.TagWriter;

import com.pyrube.one.lang.Strings;

/**
 * JSEA Component tag for rendering a HTML 'li' element to be used as a JSEA step header and a 
 * HTML 'div' element to be used as a JSEA step body.
 * 
 * @author Aranjuez
 * @version Oct 01, 2023
 * @since Pyrube-WEA 1.1
 */
public class StepTag extends JseaElementSupportTag implements BodyTag {
	/**
	 * serial version uid
	 */
	private static final long serialVersionUID = 2030443559544333796L;
	
	private String header;
	private String url;
	private BodyContent bodyContent;

	/**
	 * @return the jseaAttrOptions
	 */
	@Override
	public String getJseaAttrOptions() {
		return TagConstants.JSEA_ATTR_STEP_OPTIONS;
	}

	/**
	 * @return the header
	 */
	public String getHeader() {
		return header;
	}

	/**
	 * @param header
	 *            the header to set
	 */
	public void setHeader(String header) {
		this.header = header;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url
	 *            the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
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

	@Override
	public void doInitBody() {
		// do nothing
	}

	@Override
	protected int writeTagContent(TagWriter tagWriter) throws JspException {
		this.setTagWriter(tagWriter);
		tagWriter.startTag("li");	
		//<li jsea-step-options="url:'/step/1'">
		//  <span>Step 1</span>
		//  <span class="num">1</span>
		//  <span class="arrow"></span>
		//</li>	
		writeJseaOptionsAttribute(tagWriter);
		tagWriter.forceBlock();
		
		writeHeader(tagWriter);
		
		return EVAL_BODY_AGAIN;
	}

	@Override
	public int doEndTag() throws JspException {
		StepBody stepBody = new StepBody(getId(), resolveCssClass(), getBodyContent() == null ? Strings.EMPTY : getBodyContent().getString());
		// find a step body aware ancestor
		StepBodyAware stepBodyAware = (StepBodyAware)findAncestorWithClass(this, StepBodyAware.class);
		if (stepBodyAware == null) {
			throw new JspException("The Step tag must be a descendant of a tag that supports step");
		}
		stepBodyAware.addStepBody(stepBody);
		return EVAL_PAGE;
	}

	@Override
	public void doFinally() {
		super.doFinally();
		if (getBodyContent() != null) {
			getBodyContent().clearBody();
			setBodyContent(null);
		}
	}

	@Override
	protected String getDefaultCssClass() throws JspException { return"step-body"; }
	
	@Override
	protected String resolveJseaOptions() {
		JseaOptionsBuilder jsob = JseaOptionsBuilder.newBuilder();
		jsob.appendJseaOption(TagConstants.JSEA_OPTION_ID, getId());
		jsob.appendJseaOption(TagConstants.JSEA_OPTION_URL, getUrl());
		return jsob.toString();
	}

	/**
	 * write step header
	 * @param tagWriter
	 * @throws JspException
	 */
	private void writeHeader(TagWriter tagWriter) throws JspException {
		// find a step body aware ancestor
		StepBodyAware stepBodyAware = (StepBodyAware)findAncestorWithClass(this, StepBodyAware.class);
		if (stepBodyAware == null) {
			throw new JspException("The Step tag must be a descendant of a tag that supports step");
		}
		tagWriter.startTag("span");
		tagWriter.appendValue(localizeAttribute("header"));
		tagWriter.endTag();
		tagWriter.startTag("span");
		tagWriter.writeAttribute(CLASS_ATTRIBUTE, "num");
		tagWriter.appendValue(String.valueOf(stepBodyAware.size() + 1));
		tagWriter.endTag();
		tagWriter.startTag("span");
		tagWriter.writeAttribute(CLASS_ATTRIBUTE, "arrow");
		tagWriter.endTag();
	}

	/**
	 * A step body.
	 * 
	 * @author Aranjuez
	 * @version Oct 01, 2023
	 * @since Pyrube-WEA 1.1
	 */
	public static class StepBody {
		private String id;
		private String stylesheet = "step-body";
		private String content;
		
		/**
		 * Constructor
		 * @param id
		 * @param stylesheet
		 * @param content
		 */
		public StepBody(String id, String stylesheet, String content) {
			super();
			this.id = id;
			this.stylesheet = stylesheet;
			this.content = content;
		}
		
		/**
		 * @return the id
		 */
		public String getId() {
			return id;
		}
		/**
		 * @param id
		 *        the id to set
		 */
		public void setId(String id) {
			this.id = id;
		}
		/**
		 * @return the stylesheet
		 */
		public String getStylesheet() {
			return stylesheet;
		}
		/**
		 * @param stylesheet
		 *        the stylesheet to set
		 */
		public void setStylesheet(String stylesheet) {
			this.stylesheet = stylesheet;
		}
		/**
		 * @return the content
		 */
		public String getContent() {
			return content;
		}

		/**
		 * @param content the content to set
		 */
		public void setContent(String content) {
			this.content = content;
		}
		
	}

}
