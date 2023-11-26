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

import java.util.ArrayList;
import java.util.List;

import javax.servlet.jsp.JspException;

import org.springframework.web.servlet.tags.form.TagWriter;

import com.pyrube.wea.ui.tags.TabTag.TabBody;

/**
 * Renders an HTML 'div' element for the JSEA tag group component.
 * 
 * @author Aranjuez
 * @version Dec 01, 2009
 * @since Pyrube-WEA 1.0
 */
public class TabsTag extends JseaElementSupportTag implements TabBodyAware {

	/**
	 * serial version uid
	 */
	private static final long serialVersionUID = 1201357278602077405L;

	/**
	 * the index of the current tab
	 */
	private int current = 0;

	/**
	 * JSEA Event, on activate/deactivate a tab event
	 */
	private String onActivate;
	private String onDeactivate;

	private List<TabBody> tabBodies = new ArrayList<TabBody>();

	/**
	 * @return the jseaAttrOptions
	 */
	@Override
	public String getJseaAttrOptions() {
		return TagConstants.JSEA_ATTR_TABS_OPTIONS;
	}

	/**
	 * @return the current
	 */
	public int getCurrent() {
		return current;
	}

	/**
	 * @param current the current to set
	 */
	public void setCurrent(int current) {
		this.current = current;
	}

	/**
	 * @return the onActivate
	 */
	public String getOnActivate() {
		return onActivate;
	}

	/**
	 * @param onActivate the onActivate to set
	 */
	public void setOnActivate(String onActivate) {
		this.onActivate = onActivate;
	}

	/**
	 * @return the onDeactivate
	 */
	public String getOnDeactivate() {
		return onDeactivate;
	}

	/**
	 * @param onDeactivate the onDeactivate to set
	 */
	public void setOnDeactivate(String onDeactivate) {
		this.onDeactivate = onDeactivate;
	}

	@Override
	protected int writeTagContent(TagWriter tagWriter) throws JspException {
		this.setTagWriter(tagWriter);
		tagWriter.startTag("div");
		writeDefaultAttributes(tagWriter);
		writeJseaOptionsAttribute(tagWriter);
		tagWriter.forceBlock();
		
		tagWriter.startTag("ul");
		tagWriter.forceBlock();
		
		return EVAL_BODY_INCLUDE;
	}
	
	@Override
	public int doEndTag() throws JspException {
		TagWriter tagWriter = getTagWriter();
		//end the ul tag
		tagWriter.endTag();
		
		// write tab body
		for (TabBody tabBody : tabBodies) {
			tagWriter.startTag("div");
			//<div id="sampleInfo" class="tab-body"></div>
			tagWriter.writeAttribute(TagConstants.HTML_ATTR_ID, tabBody.getId());
			tagWriter.writeAttribute(TagConstants.HTML_ATTR_CLASS, tabBody.getStylesheet());
			tagWriter.appendValue(tabBody.getContent());
			tagWriter.endTag();
		}
		
		//end div tag.
		tagWriter.endTag();
		return EVAL_PAGE;
	}
	
	@Override
	public void doFinally() {
		super.doFinally();
		this.tabBodies = new ArrayList<TabBody>();
	}

	@Override
	public void addTabBody(TabBody tabBody) {
		tabBodies.add(tabBody);
	}

	@Override
	protected String getDefaultCssClass() throws JspException { return"tabs-container"; }
	
	@Override
	protected String resolveJseaOptions() {
		JseaOptionsBuilder jsob = JseaOptionsBuilder.newBuilder();
		jsob.appendJseaOption(TagConstants.JSEA_OPTION_CURRENT, getCurrent())
			.appendJseaOption(TagConstants.JSEA_EVENT_ONACTIVATE, getOnActivate(), JseaOptionsBuilder.JSEA_OPTION_TYPE_FUNCTION)
			.appendJseaOption(TagConstants.JSEA_EVENT_ONDEACTIVATE, getOnDeactivate(), JseaOptionsBuilder.JSEA_OPTION_TYPE_FUNCTION);
		return jsob.toString();
	}

}
