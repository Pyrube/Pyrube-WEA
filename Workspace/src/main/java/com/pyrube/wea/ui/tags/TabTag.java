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
 * JSEA Component tag for rendering a HTML 'li' element to be used as a JSEA tab header and a 
 * HTML 'div' element to be used as a JSEA tab body.
 * 
 * @author Aranjuez
 * @version Dec 01, 2009
 * @since Pyrube-WEA 1.0
 */
public class TabTag extends JseaElementSupportTag implements BodyTag  {

	/**
	 * serial version uid
	 */
	private static final long serialVersionUID = 7039940680271353107L;
	
	private String stylesheet = "tab-body";
	private String header;
	private String url;
	private BodyContent bodyContent;

	/**
	 * @return the jseaAttrOptions
	 */
	@Override
	public String getJseaAttrOptions() {
		return TagConstants.JSEA_ATTR_TAB_OPTIONS;
	}

	/**
	 * @return the stylesheet
	 */
	public String getStylesheet() {
		return stylesheet;
	}

	/**
	 * @param stylesheet
	 *            the stylesheet to set
	 */
	public void setStylesheet(String stylesheet) {
		this.stylesheet = stylesheet;
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
		//<li jsea-tab-options="url:'/search'"><a href="#ajaxTest">Sample Ajax Test</a></li>	
		writeJseaOptionsAttribute(tagWriter);
		tagWriter.forceBlock();
		
		writeLink(tagWriter);
		
		return EVAL_BODY_AGAIN;
	}
	
	@Override
	public int doEndTag() throws JspException {
		TabBody tabBody = new TabBody(getId(), resolveCssClass(), getBodyContent() == null ? Strings.EMPTY : getBodyContent().getString());
		// find a tab body aware ancestor
		TabBodyAware tabBodyAware = (TabBodyAware)findAncestorWithClass(this, TabBodyAware.class);
		if (tabBodyAware == null) {
			throw new JspException("The Tab tag must be a descendant of a tag that supports tab");
		}
		tabBodyAware.addTabBody(tabBody);
		return EVAL_PAGE;
	}
	
	@Override
	public void doFinally() {
		super.doFinally();
		this.bodyContent.clearBody();
		this.bodyContent = null;
	}

	@Override
	protected String resolveCssClass() throws JspException {
		return this.stylesheet;
	}
	
	@Override
	protected String resolveJseaOptions() {
		JseaOptionsBuilder jsob = JseaOptionsBuilder.newBuilder();
		jsob.appendJseaOption(TagConstants.JSEA_OPTION_URL, getUrl());
		return jsob.toString();
	}
	
	/**
	 * write link tag as tab header
	 * @param tabId
	 * @param header
	 * @throws JspException
	 */
	private void writeLink(TagWriter tagWriter) throws JspException {
		tagWriter.startTag("a");
		tagWriter.writeAttribute("href", "#" + getId());
		tagWriter.appendValue(localizeAttribute("header"));
		tagWriter.endTag();
	}

	/**
	 * A tab body.
	 * 
	 * @author Aranjuez
	 * @version Dec 01, 2009
	 * @since Pyrube-WEA 1.0
	 */
	public static class TabBody {
		private String id;
		private String stylesheet = "tab-body";
		private String content;
		
		/**
		 * Constructor
		 * @param id
		 * @param stylesheet
		 * @param content
		 */
		public TabBody(String id, String stylesheet, String content) {
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
