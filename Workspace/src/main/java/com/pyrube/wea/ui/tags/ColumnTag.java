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

import com.pyrube.wea.ui.tags.OperationTag.Operation;

/**
 * Tag for rendering an HTML 'li' element to be used as the JSEA grid column.
 * 
 * @author Aranjuez
 * @version Dec 01, 2009
 * @since Pyrube-WEA 1.0
 */
public class ColumnTag extends JseaElementSupportTag implements OperationAware {

	/**
	 * serial version uid
	 */
	private static final long serialVersionUID = 5410004108594791790L;
	
	private String header = String.valueOf(true);
	private String type;
	private String operation;
	private String url;
	private String mode;
	private String defaultValue;
	private String ccyProp;
	private boolean local = false; // Whether convert datetime (in GMT:00) to local timestamp.
	private String i18nPrefix;
	private String i18nKey;
	private boolean sortable = true;
	private String order;
	
	private List<Operation> operations = new ArrayList<Operation>();

	/**
	 * @return the jseaAttrOptions
	 */
	@Override
	public String getJseaAttrOptions() {
		return TagConstants.JSEA_ATTR_COLUMN_OPTIONS;
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
	 * @return the operation
	 */
	public String getOperation() {
		return operation;
	}

	/**
	 * @param operation the operation to set
	 */
	public void setOperation(String operation) {
		this.operation = operation;
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
	 * @return the defaultValue
	 */
	public String getDefaultValue() {
		return defaultValue;
	}

	/**
	 * @param defaultValue the defaultValue to set
	 */
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	/**
	 * @return the ccyProp
	 */
	public String getCcyProp() {
		return ccyProp;
	}

	/**
	 * @param ccyProp the ccyProp to set
	 */
	public void setCcyProp(String ccyProp) {
		this.ccyProp = ccyProp;
	}

	/**
	 * @return the local
	 */
	public boolean isLocal() {
		return local;
	}

	/**
	 * @param local the local to set
	 */
	public void setLocal(boolean local) {
		this.local = local;
	}

	/**
	 * @return the i18nPrefix
	 */
	public String getI18nPrefix() {
		return i18nPrefix;
	}

	/**
	 * @param i18nPrefix the i18nPrefix to set
	 */
	public void setI18nPrefix(String i18nPrefix) {
		this.i18nPrefix = i18nPrefix;
	}

	/**
	 * @return the i18nKey
	 */
	public String getI18nKey() {
		return i18nKey;
	}

	/**
	 * @param i18nKey the i18nKey to set
	 */
	public void setI18nKey(String i18nKey) {
		this.i18nKey = i18nKey;
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
	 * @return the order
	 */
	public String getOrder() {
		return order;
	}

	/**
	 * @param order the order to set
	 */
	public void setOrder(String order) {
		this.order = order;
	}

	/**
	 * @return the operations
	 */
	public List<Operation> getOperations() {
		return operations;
	}

	/**
	 * @param operations the operations to set
	 */
	public void setOperations(List<Operation> operations) {
		this.operations = operations;
	}

	@Override
	protected int writeTagContent(TagWriter tagWriter) throws JspException {
		this.setTagWriter(tagWriter);
		return EVAL_BODY_INCLUDE;
	}
	
	@Override
	public int doEndTag() throws JspException {
		TagWriter tagWriter = getTagWriter();
		tagWriter.startTag("li");
		this.writeDefaultAttributes(tagWriter);
		this.writeJseaOptionsAttribute(tagWriter);
		if (this.operations.size() > 0) {
			this.writeOptionalAttributes(tagWriter);
			this.writeOptionalAttribute(tagWriter, TagConstants.JSEA_ATTR_COLUMN_OPERATIONS, resolveJseaOperations());
			this.operations.clear();
		}
		tagWriter.forceBlock();
		tagWriter.appendValue(this.localizeAttribute("header"));
		tagWriter.endTag();
		return EVAL_PAGE;
	}

	@Override
	protected String resolveCssClass() throws JspException {
		return getStylesheet();
	}

	@Override
	protected String resolveJseaOptions() throws JspException {
		JseaOptionsBuilder jsob = JseaOptionsBuilder.newBuilder();
		jsob.appendJseaOption(TagConstants.JSEA_OPTION_NAME, getName())
			.appendJseaOption(TagConstants.JSEA_OPTION_TYPE, getType())
			.appendJseaOption(TagConstants.JSEA_OPTION_OPERATION, getOperation())
			.appendJseaOption(TagConstants.JSEA_OPTION_URL, getUrl())
			.appendJseaOption(TagConstants.JSEA_OPTION_MODE, getMode())
			.appendJseaOption(TagConstants.JSEA_OPTION_DEFAULT_VALUE, getDefaultValue())
			.appendJseaOption(TagConstants.JSEA_OPTION_FORMAT, getFormat())
			.appendJseaOption(TagConstants.JSEA_OPTION_CCY_PROP, getCcyProp())
			.appendJseaOption(TagConstants.JSEA_OPTION_LOCAL, isLocal())
			.appendJseaOption(TagConstants.JSEA_OPTION_I18N_PREFIX, getI18nPrefix())
			.appendJseaOption(TagConstants.JSEA_OPTION_I18N_KEY, getI18nKey())
			.appendJseaOption(TagConstants.JSEA_OPTION_REQUIRED, isRequired())
			.appendJseaOption(TagConstants.JSEA_OPTION_SORTABLE, isSortable())
			.appendJseaOption(TagConstants.JSEA_OPTION_ORDER, getOrder())
			.appendJseaOption(TagConstants.JSEA_OPTION_STYLESHEET, getStylesheet());
		return jsob.toString();
	}

	@Override
	public void addOperation(Operation operation) {
		this.operations.add(operation);
	}
	
	/**
	 * Resolve the JSON data of the JSEA operations as HTML attribute value
	 * @return
	 */
	private String resolveJseaOperations() {
		JseaOptionsBuilder jsob = JseaOptionsBuilder.newBuilder();
		for (Operation operation : operations) {
			jsob.appendJseaOption(operation.getName(), operation.getJseaOptions(), JseaOptionsBuilder.JSEA_OPTION_TYPE_OBJECT);
		}
		return jsob.toString();
	}
}
