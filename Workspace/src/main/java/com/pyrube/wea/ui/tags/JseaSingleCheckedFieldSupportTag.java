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

import com.pyrube.one.lang.Strings;
import com.pyrube.wea.ui.tags.core.WeaSelectedValueComparator;
import com.pyrube.wea.util.Weas;

/**
 * JSEA base class to provide common methods for implementing databinding-aware JSP tags for rendering single
 * checkable JSEA field of <code>checkbox</code> or <code>radio</code>.
 * 
 * @author Aranjuez
 * @version Dec 01, 2009
 * @since Pyrube-WEA 1.0
 */
public abstract class JseaSingleCheckedFieldSupportTag extends FieldTag {

	/**
	 * serial version uid
	 */
	private static final long serialVersionUID = 4930497022125074263L;

	/**
	 * The HTML '{@code span}' tag.
	 */
	private static final String SPAN_TAG = "span";

	/**
	 * The HTML '{@code label}' tag.
	 */
	private static final String LABEL_TAG = "label";

	/**
	 * The HTML '{@code input}' tag.
	 */
	private static final String FIELD_TAG = "input";

	/**
	 * The value of the '{@code value}' attribute.
	 */
	private String value;

	/**
	 * The value of the '{@code label}' attribute.
	 */
	private String label;
	
	/**
	 * The value of the '{@code i18nPrefix}' option
	 */
	private String i18nPrefix;

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}
	
	/**
	 * @param value the value to set
	 */
	public void setValue(Object value) {
		this.value = (String)value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * @param label the label to set
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * @param label the label to set
	 */
	public void setLabel(Object label) {
		this.label = (String)label;
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
	 * Determines whether the supplied value matched the selected value
	 * through delegating to {@link WeaSelectedValueComparator#isSelected}.
	 */
	protected boolean isOptionSelected(String value) throws JspException {
		return WeaSelectedValueComparator.isSelected(getBindStatus(), value);
	}

	/**
	 * Renders the '{@code input type="checkbox/radio"}' element with the
	 * configured {@link #setValue(Object) value}. Marks the element as checked
	 * if the value matches the {@link #getValue bound `value}.
	 */
	@Override
	protected int writeTagContent(TagWriter tagWriter) throws JspException {
		if (StringUtils.isEmpty(getPath())) {
			// find a param aware ancestor
			JseaSingleCheckedFieldAware singleCheckedFieldAwareTag = (JseaSingleCheckedFieldAware) findAncestorWithClass(this,
					JseaSingleCheckedFieldAware.class);
			if (singleCheckedFieldAwareTag != null) {
				String name = singleCheckedFieldAwareTag.getFieldName();
				this.setName(name);
				
				String path = singleCheckedFieldAwareTag.getRootPath();
				this.setPath(path);
				
				String labelPrefix = singleCheckedFieldAwareTag.getLabelPrefix();
				this.setI18nPrefix(labelPrefix);
			}
		}
		if (Strings.isEmpty(this.getPath())) {
			throw new JspException("One of attribute 'path' and tag 'checkboxes/radios' is required.");
		}
		
		this.setTagWriter(tagWriter);
		//write wrapper
		this.writeWrapper(tagWriter);
		// write field
		tagWriter.startTag(SPAN_TAG);
		writeDefaultAttributes(tagWriter);
		writeJseaOptionsAttribute(tagWriter);
		// write value
		tagWriter.writeOptionalAttributeValue(TagConstants.JSEA_ATTR_DATA_VALUE, value);
		tagWriter.endTag(true); // end of span
		// write label
		this.writeLabel(tagWriter);
		//write a hidden input with type="checkbox/radio"
		tagWriter.startTag(FIELD_TAG);
		writeDefaultAttributes(tagWriter);
		tagWriter.writeAttribute(TagConstants.HTML_ATTR_TYPE, resolveFieldType());
		String displayValue = convertToDisplayString(value);
		tagWriter.writeAttribute(TagConstants.HTML_ATTR_VALUE, processFieldValue(getName(), displayValue, this.resolveFieldType()));
		if (WeaSelectedValueComparator.isSelected(getBindStatus(), displayValue)) {
			tagWriter.writeAttribute("checked", "checked");
		}
		tagWriter.endTag(); // end of input
		
		return SKIP_BODY;
	}

	@Override
	protected void writeLabel(TagWriter tagWriter) throws JspException {
		String resolvedLabel = this.getLabel();
		String labelPrefix = this.getI18nPrefix();
		if (resolvedLabel == null && labelPrefix != null) {
			resolvedLabel = labelPrefix + "." + value;
		}
		if (resolvedLabel != null) {
			tagWriter.startTag(LABEL_TAG);
			tagWriter.appendValue(Weas.localizeMessage(resolvedLabel));
			tagWriter.endTag();
		}
	}

	@Override
	protected String resolveStylesheet() throws JspException {
		String stylesheet = super.resolveStylesheet();
		StringBuffer buf  = new StringBuffer();
		if (!Strings.isEmpty(stylesheet)) { buf.append(stylesheet + " "); }
		if (this.isOptionSelected(this.value)) { buf.append("checked "); }
		return(buf.toString().trim());
	}

	@Override
	protected String resolveCssClass() throws JspException {
		String cssClass  = super.resolveCssClass();
		StringBuffer buf = new StringBuffer();
		if (!Strings.isEmpty(cssClass)) { buf.append(cssClass + " "); }
		if (this.isOptionSelected(this.value)) { buf.append("checked "); }
		return(buf.toString().trim());
	}

	@Override
	protected void appendExtraOptions(JseaOptionsBuilder jsob) throws JspException { }
	
}
