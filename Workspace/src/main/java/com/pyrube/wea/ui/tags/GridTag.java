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

import org.springframework.util.StringUtils;
import org.springframework.web.servlet.tags.form.TagWriter;

import com.pyrube.one.app.inquiry.SearchCriteria;
import com.pyrube.wea.util.Weas;
/**
 * Renders an HTML 'div' element for the JSEA grid component.
 * 
 * @author Aranjuez
 * @version Dec 01, 2009
 * @since Pyrube-WEA 1.0
 */
public class GridTag extends JseaElementSupportTag {
	
	/**
	 * serial version uid
	 */
	private static final long serialVersionUID = 1989713681891838518L;
	
	/**
	 * stylesheet for Grid
	 */
	private static final String STYLESHEET_GRID_CONTAINER = "grid-container";
	private static final String STYLESHEET_GRID_HEADER = "header";
	private static final String STYLESHEET_GRID_AREA = "list-area";
	private static final String STYLESHEET_GRID_AREA_HEADER = "header";
	
	private String stylesheet = STYLESHEET_GRID_CONTAINER;
	private String header;
	private String funcname;
	private String url;
	private String rsProp;
	private Object rs;
	private boolean async = false;
	private String renders;
	private Object postProps;
	private boolean multiple = false;
	private boolean sortable = true;
	private boolean pageable = true;
	private int pageSize = SearchCriteria.DEFAULT_PAGE_SIZE;
	
	private String nonnullCols;
	private String uniqueIndex;
	
	private String onWrite;
	private String onSelect;
	private String onDeselect;

	/**
	 * @return the jseaAttrOptions
	 */
	@Override
	public String getJseaAttrOptions() {
		return TagConstants.JSEA_ATTR_GRID_OPTIONS;
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
	 * @return the rsProp
	 */
	public String getRsProp() {
		return rsProp;
	}

	/**
	 * @param rsProp the rsProp to set
	 */
	public void setRsProp(String rsProp) {
		this.rsProp = rsProp;
	}

	/**
	 * @return the rs
	 */
	public Object getRs() {
		return rs;
	}

	/**
	 * @param rs the rs to set
	 */
	public void setRs(Object rs) {
		this.rs = rs;
	}
	
	/**
	 * @return the async
	 */
	public boolean isAsync() {
		return async;
	}

	/**
	 * @param async the async to set
	 */
	public void setAsync(boolean async) {
		this.async = async;
	}

	/**
	 * @return the renders
	 */
	public String getRenders() {
		return renders;
	}

	/**
	 * @param renders the renders to set
	 */
	public void setRenders(String renders) {
		this.renders = renders;
	}

	/**
	 * @return the postProps
	 */
	public Object getPostProps() {
		return postProps;
	}

	/**
	 * @param postProps the postProps to set
	 */
	public void setPostProps(Object postProps) {
		this.postProps = postProps;
	}

	/**
	 * @return the multiple
	 */
	public boolean isMultiple() {
		return multiple;
	}

	/**
	 * @param multiple the multiple to set
	 */
	public void setMultiple(boolean multiple) {
		this.multiple = multiple;
	}

	/**
	 * @return the sortable
	 */
	public boolean isSortable() {
		return sortable;
	}

	/**
	 * @param sortable the sortable to set
	 */
	public void setSortable(boolean sortable) {
		this.sortable = sortable;
	}

	/**
	 * @return the pageable
	 */
	public boolean isPageable() {
		return pageable;
	}

	/**
	 * @param pageable the pageable to set
	 */
	public void setPageable(boolean pageable) {
		this.pageable = pageable;
	}

	/**
	 * @return the pageSize
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * @param pageSize the pageSize to set
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * @return the nonnullCols
	 */
	public String getNonnullCols() {
		return nonnullCols;
	}

	/**
	 * @param nonnullCols the nonnullCols to set
	 */
	public void setNonnullCols(String nonnullCols) {
		this.nonnullCols = nonnullCols;
	}

	/**
	 * @return the uniqueIndex
	 */
	public String getUniqueIndex() {
		return uniqueIndex;
	}

	/**
	 * @param uniqueIndex the uniqueIndex to set
	 */
	public void setUniqueIndex(String uniqueIndex) {
		this.uniqueIndex = uniqueIndex;
	}

	/**
	 * @return the onWrite
	 */
	public String getOnWrite() {
		return onWrite;
	}

	/**
	 * @param onWrite the onWrite to set
	 */
	public void setOnWrite(String onWrite) {
		this.onWrite = onWrite;
	}

	/**
	 * @return the onSelect
	 */
	public String getOnSelect() {
		return onSelect;
	}

	/**
	 * @param onSelect the onSelect to set
	 */
	public void setOnSelect(String onSelect) {
		this.onSelect = onSelect;
	}
	
	/**
	 * @return the onDeselect
	 */
	public String getOnDeselect() {
		return onDeselect;
	}
	
	/**
	 * @param onDeselect
	 *        the onDeselect to set
	 */
	public void setOnDeselect(String onDeselect) {
		this.onDeselect = onDeselect;
	}

	@Override
	protected int writeTagContent(TagWriter tagWriter) throws JspException {
		this.setTagWriter(tagWriter);
		tagWriter.startTag("div");		
		writeDefaultAttributes(tagWriter);
		writeJseaOptionsAttribute(tagWriter);
		writeJseaValidRulesAttribute(tagWriter);
		if (!StringUtils.isEmpty(this.getHeader())) {
			// sample HTML code: <ul class="header"><li>Sample Exts</li></ul>
			tagWriter.startTag("ul");
			this.writeOptionalAttribute(tagWriter, CLASS_ATTRIBUTE, STYLESHEET_GRID_HEADER);
			tagWriter.startTag("li");
			tagWriter.appendValue(Weas.localizeMessage(this.getHeader()));
			tagWriter.endTag(); // end of li
			tagWriter.endTag(); // end of ul
		}
		tagWriter.forceBlock();
		
		tagWriter.startTag("div");
		this.writeOptionalAttribute(tagWriter, CLASS_ATTRIBUTE, STYLESHEET_GRID_AREA);
		tagWriter.forceBlock();
		
		tagWriter.startTag("ul");		
		this.writeOptionalAttribute(tagWriter, CLASS_ATTRIBUTE, STYLESHEET_GRID_AREA_HEADER);
		tagWriter.forceBlock();
		
		return EVAL_BODY_INCLUDE;
	}
	
	@Override
	public int doEndTag() throws JspException {
		TagWriter tagWriter = getTagWriter();
		tagWriter.endTag();
		tagWriter.endTag();
		tagWriter.endTag();
		return EVAL_PAGE;
	}

	@Override
	protected String resolveCssClass() throws JspException {
		return this.stylesheet;
	}

	@Override
	protected String resolveJseaOptions() {
		JseaOptionsBuilder jsob = JseaOptionsBuilder.newBuilder();
		jsob.appendJseaOption(TagConstants.JSEA_OPTION_FUNCNAME, getFuncname())
			.appendJseaOption(TagConstants.JSEA_OPTION_URL, getUrl())
			.appendJseaOption(TagConstants.JSEA_OPTION_RS_PROP, getRsProp())
			.appendJseaOption(TagConstants.JSEA_OPTION_RESULTSET, getRs())
			.appendJseaOption(TagConstants.JSAF_OPTION_ASYNC, isAsync())
			.appendJseaOption(TagConstants.JSAF_OPTION_RENDERS, getRenders(), JseaOptionsBuilder.JSEA_OPTION_TYPE_JS_OBJECT);
		appendPostProps(jsob);
		jsob.appendJseaOption(TagConstants.JSEA_OPTION_MULTIPLE, isMultiple())
			.appendJseaOption(TagConstants.JSEA_OPTION_SORTABLE, isSortable())
			.appendJseaOption(TagConstants.JSEA_OPTION_PAGEABLE, isPageable())
			.appendJseaOption(TagConstants.JSEA_OPTION_PAGE_SIZE, getPageSize())
			.appendJseaOption(TagConstants.JSAF_EVENT_ONSELECT, getOnSelect(), JseaOptionsBuilder.JSEA_OPTION_TYPE_JS_FUNCTION)
			.appendJseaOption(TagConstants.JSAF_EVENT_ONDESELECT, getOnDeselect(), JseaOptionsBuilder.JSEA_OPTION_TYPE_JS_FUNCTION)
			.appendJseaOption(TagConstants.JSAF_EVENT_ONWRITE, getOnWrite(), JseaOptionsBuilder.JSEA_OPTION_TYPE_JS_FUNCTION);
		return jsob.toString();
	}
	
	@Override
	protected String resolveJseaValidRules() {
		JseaOptionsBuilder jsob = JseaOptionsBuilder.newBuilder();
		jsob.appendJseaOption(TagConstants.JSEA_VALID_RULE_NONNULL_COLS, getNonnullCols(), JseaOptionsBuilder.JSEA_OPTION_TYPE_JS_OBJECT)
			.appendJseaOption(TagConstants.JSEA_VALID_RULE_UNIQUE_INDEX, getUniqueIndex());
		return jsob.toString();
	}
	
	/**
	 * Cascades builder
	 * @return
	 */
	private JseaOptionsBuilder appendPostProps(JseaOptionsBuilder builder) {
		Object postProps = this.getPostProps();
		if (String.class.isInstance(postProps)) {
			builder.appendJseaOption(TagConstants.JSEA_OPTION_POST_PROPS, postProps, JseaOptionsBuilder.JSEA_OPTION_TYPE_JS_OBJECT);
		} else {
			builder.appendJseaOption(TagConstants.JSEA_OPTION_POST_PROPS, postProps);
		}
		return builder;
	}
}
