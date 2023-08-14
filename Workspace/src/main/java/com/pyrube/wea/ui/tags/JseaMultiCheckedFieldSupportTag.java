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

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.jsp.JspException;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;
import org.springframework.web.servlet.tags.form.TagWriter;

import com.pyrube.one.lang.Strings;
import com.pyrube.wea.ui.tags.core.WeaSelectedValueComparator;

/**
 * JSEA base class to provide common methods for implementing databinding-aware JSP tags for rendering multiple
 * checkable JSEA fields of <code>checkbox</code> or <code>radio</code>.
 *
 * @author Aranjuez
 * @version Dec 01, 2009
 * @since Pyrube-WEA 1.0
 */
public abstract class JseaMultiCheckedFieldSupportTag extends FieldTag implements JseaSingleCheckedFieldAware {

	/**
	 * serial version uid
	 */
	private static final long serialVersionUID = 1932861901180703735L;

	/**
	 * The HTML '{@code ul}' tag.
	 */
	private static final String UL_TAG = "ul";

	/**
	 * The HTML '{@code li}' tag.
	 */
	private static final String LI_TAG = "li";

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
	 * The {@link java.util.Collection}, {@link java.util.Map} or array of objects
	 * used to generate the '{@code input type="checkbox/radio"}' tags.
	 */
	private Object items;

	/**
	 * The name of the property mapped to the '{@code value}' attribute
	 * of the '{@code input type="checkbox/radio"}' tag.
	 */
	private String itemValue;

	/**
	 * The value to be displayed as part of the '{@code input type="checkbox/radio"}' tag.
	 */
	private String itemLabel;

	/**
	 * The value to be localized as prefix of the '{@code input type="checkbox/radio"}' tag.
	 */
	private String i18nPrefix;

	/**
	 * The HTML element used to enclose the '{@code input type="checkbox/radio"}' tag.
	 */
	private String element = SPAN_TAG;

	/**
	 * Delimiter to use between each '{@code input type="checkbox/radio"}' tags.
	 */
	private String delimiter;
	
	/**
	 * onCheck event 
	 */
	private String onCheck;

	/**
	 * Set the {@link java.util.Collection}, {@link java.util.Map} or array of objects
	 * used to generate the '{@code input type="checkbox/radio"}' tags.
	 * <p>Typically a runtime expression.
	 * @param items said items
	 */
	public void setItems(Object items) {
		Assert.notNull(items, "'items' must not be null");
		this.items = items;
	}

	/**
	 * Get the {@link java.util.Collection}, {@link java.util.Map} or array of objects
	 * used to generate the '{@code input type="checkbox/radio"}' tags.
	 */
	protected Object getItems() {
		return this.items;
	}

	/**
	 * Set the name of the property mapped to the '{@code value}' attribute
	 * of the '{@code input type="checkbox/radio"}' tag.
	 * <p>May be a runtime expression.
	 */
	public void setItemValue(String itemValue) {
		Assert.hasText(itemValue, "'itemValue' must not be empty");
		this.itemValue = itemValue;
	}

	/**
	 * Get the name of the property mapped to the '{@code value}' attribute
	 * of the '{@code input type="checkbox/radio"}' tag.
	 */
	protected String getItemValue() {
		return this.itemValue;
	}

	/**
	 * Set the value to be displayed as part of the
	 * '{@code input type="checkbox/radio"}' tag.
	 * <p>May be a runtime expression.
	 */
	public void setItemLabel(String itemLabel) {
		Assert.hasText(itemLabel, "'itemLabel' must not be empty");
		this.itemLabel = itemLabel;
	}

	/**
	 * Get the value to be displayed as part of the
	 * '{@code input type="checkbox/radio"}' tag.
	 */
	protected String getItemLabel() {
		return this.itemLabel;
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
	 * Set the delimiter to be used between each
	 * '{@code input type="checkbox/radio"}' tag.
	 * <p>By default, there is <em>no</em> delimiter.
	 */
	public void setDelimiter(String delimiter) {
		this.delimiter = delimiter;
	}

	/**
	 * Return the delimiter to be used between each
	 * '{@code input type="radio"}' tag.
	 */
	public String getDelimiter() {
		return this.delimiter;
	}

	/**
	 * Set the HTML element used to enclose the
	 * '{@code input type="checkbox/radio"}' tag.
	 * <p>Defaults to an HTML '{@code &lt;span/&gt;}' tag.
	 */
	public void setElement(String element) {
		Assert.hasText(element, "'element' cannot be null or blank");
		this.element = element;
	}

	/**
	 * Get the HTML element used to enclose
	 * '{@code input type="checkbox/radio"}' tag.
	 */
	public String getElement() {
		return this.element;
	}
	
	/**
	 * @return the onCheck
	 */
	public String getOnCheck() {
		return onCheck;
	}

	/**
	 * @param onCheck the onCheck to set
	 */
	public void setOnCheck(String onCheck) {
		this.onCheck = onCheck;
	}
	
	public abstract String getJseaAttrSingleFieldOptions();

	@Override
	public String getRootPath() throws JspException {
		return this.getPath();
	}

	@Override
	public String getFieldName() throws JspException {
		return this.getName();
	}

	@Override
	public String getLabelPrefix() throws JspException {
		return this.getI18nPrefix();
	}

	@Override
	public boolean isFieldChecked(String value) throws JspException {
		return isOptionSelected(value);
	}

	@Override
	protected boolean isSkipBody() {
		if (this.items != null) {
			return true;
		}
		return false;
	}
	
	@Override
	public int doEndTag() throws JspException {
		this.getTagWriter().endTag(); //end ul
		return super.doEndTag();
	}
	
	@Override
	protected void writeField(TagWriter tagWriter) throws JspException {
		tagWriter.startTag(UL_TAG);
		writeDefaultAttributes(tagWriter);
		writeJseaOptionsAttribute(tagWriter);
		writeJseaValidRulesAttribute(tagWriter);
		tagWriter.forceBlock();
		writeElementTags(tagWriter);
	}
	
	@Override
	protected void appendExtraOptions(JseaOptionsBuilder jsob) throws JspException {
		String value = getDisplayString(getBoundValue(), getPropertyEditor());
		jsob.appendJseaOption("value", value)
			.appendJseaOption(TagConstants.JSEA_OPTION_NAME, this.getName())
			.appendJseaOption(TagConstants.JSEA_OPTION_I18N_PREFIX, this.getI18nPrefix())
			.appendJseaOption(TagConstants.JSEA_EVENT_ONCHECK, this.onCheck, JseaOptionsBuilder.JSEA_OPTION_TYPE_JS_FUNCTION);
	}

	/**
	 * Renders the '{@code input type="checkbox/radio"}' element with the configured
	 * {@link #setItems(Object)} values. Marks the element as checked if the
	 * value matches the bound value.
	 */
	protected void writeElementTags(TagWriter tagWriter) throws JspException {
		Object items = getItems();
		if (items != null) {
			Object itemsObject = (items instanceof String ? evaluate("items", items) : items);
	
			String itemValue = getItemValue();
			String itemLabel = getItemLabel();
			String valueProperty =
					(itemValue != null ? ObjectUtils.getDisplayString(evaluate("itemValue", itemValue)) : null);
			String labelProperty =
					(itemLabel != null ? ObjectUtils.getDisplayString(evaluate("itemLabel", itemLabel)) : null);
	
			Class<?> boundType = getBindStatus().getValueType();
			if (itemsObject == null && boundType != null && boundType.isEnum()) {
				itemsObject = boundType.getEnumConstants();
			}
	
			if (itemsObject == null) {
				throw new IllegalArgumentException("Attribute 'items' is required and must be a Collection, an Array or a Map");
			}
	
			if (itemsObject.getClass().isArray()) {
				Object[] itemsArray = (Object[]) itemsObject;
				for (int i = 0; i < itemsArray.length; i++) {
					Object item = itemsArray[i];
					writeObjectEntry(tagWriter, valueProperty, labelProperty, item, i);
				}
			}
			else if (itemsObject instanceof Collection) {
				final Collection<?> optionCollection = (Collection<?>) itemsObject;
				int itemIndex = 0;
				for (Iterator<?> it = optionCollection.iterator(); it.hasNext(); itemIndex++) {
					Object item = it.next();
					writeObjectEntry(tagWriter, valueProperty, labelProperty, item, itemIndex);
				}
			}
			else if (itemsObject instanceof Map) {
				final Map<?, ?> optionMap = (Map<?, ?>) itemsObject;
				int itemIndex = 0;
				for (@SuppressWarnings("rawtypes")
				Iterator it = optionMap.entrySet().iterator(); it.hasNext(); itemIndex++) {
					@SuppressWarnings("rawtypes")
					Map.Entry entry = (Map.Entry) it.next();
					writeMapEntry(tagWriter, valueProperty, labelProperty, entry, itemIndex);
				}
			}
			else {
				throw new IllegalArgumentException("Attribute 'items' must be an array, a Collection or a Map");
			}
		}
	}

	private void writeObjectEntry(TagWriter tagWriter, String valueProperty,
			String labelProperty, Object item, int itemIndex) throws JspException {

		BeanWrapper wrapper = PropertyAccessorFactory.forBeanPropertyAccess(item);
		Object renderValue;
		if (valueProperty != null) {
			renderValue = wrapper.getPropertyValue(valueProperty);
		}
		else if (item instanceof Enum) {
			renderValue = ((Enum<?>) item).name();
		}
		else {
			renderValue = item;
		}
		Object renderLabel = (labelProperty != null ? wrapper.getPropertyValue(labelProperty) : item);
		writeElementTag(tagWriter, item, renderValue, renderLabel, itemIndex);
	}

	private void writeMapEntry(TagWriter tagWriter, String valueProperty,
			String labelProperty, Map.Entry<?, ?> entry, int itemIndex) throws JspException {

		Object mapKey = entry.getKey();
		Object mapValue = entry.getValue();
		BeanWrapper mapKeyWrapper = PropertyAccessorFactory.forBeanPropertyAccess(mapKey);
		BeanWrapper mapValueWrapper = PropertyAccessorFactory.forBeanPropertyAccess(mapValue);
		Object renderValue = (valueProperty != null ?
				mapKeyWrapper.getPropertyValue(valueProperty) : mapKey.toString());
		Object renderLabel = (labelProperty != null ?
				mapValueWrapper.getPropertyValue(labelProperty) : mapValue.toString());
		writeElementTag(tagWriter, mapKey, renderValue, renderLabel, itemIndex);
	}

	private void writeElementTag(TagWriter tagWriter, Object item, Object value, Object label, int itemIndex)
			throws JspException {
		tagWriter.startTag(LI_TAG);
		tagWriter.startTag(SPAN_TAG);
		String id = resolveId();
		//writeOptionalAttribute(tagWriter, "id", id);
		super.writeOptionalAttributes(tagWriter);
		tagWriter.writeAttribute(this.getJseaAttrSingleFieldOptions(), Strings.EMPTY);
		String itemValue = convertToDisplayString(value);
		tagWriter.writeOptionalAttributeValue(TagConstants.JSEA_ATTR_DATA_VALUE, itemValue);
		
		if (isFieldChecked(itemValue)) {			
			tagWriter.writeAttribute(TagConstants.HTML_ATTR_CLASS, this.getDefaultCssClass() + " checked");
		} else {
			tagWriter.writeAttribute(TagConstants.HTML_ATTR_CLASS, this.getDefaultCssClass());
		}
		tagWriter.endTag(true);
		// write label
		String resolvedLabel = convertToDisplayString(label);
		String i18nPrefix = convertToDisplayString(this.i18nPrefix);
		if (resolvedLabel == null && i18nPrefix != null) {
			resolvedLabel = i18nPrefix + "." + itemValue;
		}
		if (resolvedLabel != null) {
			tagWriter.startTag(LABEL_TAG);
			tagWriter.writeAttribute("for", id);
			tagWriter.appendValue(resolvedLabel);
			tagWriter.endTag();
		}
		//write input
		tagWriter.startTag(FIELD_TAG);
		writeOptionalAttribute(tagWriter, TagConstants.HTML_ATTR_NAME, getName());
		
		tagWriter.writeAttribute(TagConstants.HTML_ATTR_TYPE, resolveFieldType());
		String displayValue = convertToDisplayString(value);
		tagWriter.writeAttribute("value", processFieldValue(getName(), displayValue, this.resolveFieldType()));
		if (WeaSelectedValueComparator.isSelected(getBindStatus(), displayValue)) {
			tagWriter.writeAttribute("checked", "checked");
		}
		
		tagWriter.endTag();
		tagWriter.endTag();	
	}

	/**
	 * Determines whether the supplied value matched the selected value
	 * through delegating to {@link SelectedValueComparator#isSelected}.
	 */
	private boolean isOptionSelected(String value) throws JspException {
		return WeaSelectedValueComparator.isSelected(getBindStatus(), value);
	}

}
