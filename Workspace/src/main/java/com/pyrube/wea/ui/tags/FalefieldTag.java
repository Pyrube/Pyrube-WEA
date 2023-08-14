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
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.jsp.JspException;

import org.springframework.util.ObjectUtils;
import org.springframework.web.servlet.tags.form.TagWriter;

import com.pyrube.one.lang.Strings;
import com.pyrube.wea.ui.tags.OptionTag.Option;
import com.pyrube.wea.ui.tags.core.WeaOptionWriter;

/**
 * JSEA FAst-seLECT(falect) field element. In my own opinion, select-field is NOT comfortable
 * and bad on user inputing continuity for mobile applications. 
 * Falect field (drag-next field), a totally new design, may improve user inputing experience. 
 * Easy coding, easy using. Change a little bit, may make a big difference.
 * 
 * @author Aranjuez
 * @version May 11, 2022
 * @since Pyrube-WEA 1.0
 */
public class FalefieldTag extends TextfieldTag implements OptionAware {
	/**
	 * serial version uid
	 */
	private static final long serialVersionUID = 7850513990359081415L;

	private static final String FALEFIELD_TRIGGER_PREFIX = "FALEFIELD-TRIGGER-";
	private static final String FALEFIELD_TRIGGER2_PREFIX = "FALEFIELD-TRIGGER2-";
	
	private static final String FALEFIELD_DRAGPANEL_PREFIX = "FALEFIELD-DRAGPANEL-";
	
	/**
	 * Marker object for items that have been specified but resolve to null.
	 * Allows to differentiate between 'set but null' and 'not set at all'.
	 */
	private static final Object EMPTY = new Object();
	
	/**
	 * The {@link Collection}, {@link Map} or array of objects used to generate the inner
	 * '{@code option}' tags.
	 */
	private Object items;
	
	/**
	 * JSEA Optional Option, to load drop-down items
	 */
	private String url;
	
	/**
	 * JSEA Optional Option, to depend on another field 'Change' event to load drag-next items
	 */
	private String depends;

	/**
	 * The name of the property mapped to the '{@code value}' attribute
	 * of the '{@code option}' tag.
	 */
	private String itemValue;

	/**
	 * The name of the property mapped to the inner text of the
	 * '{@code option}' tag.
	 */
	private String itemLabel;
	
	/**
	 * JSEA Optional Option. i18nPrefix, label for option item.
	 */
	private String i18nPrefix;

	/**
	 * optiononly
	 */
	private boolean optiononly = true;
	
	/**
	 * indicates whether to trigger change event manually only (not to trigger
	 * via javascript value setter)
	 */
	private boolean manualonly = false;
	
	/**
	 * JSEA Event Attribute
	 */
	private String onChange;
	
	/**
	 * option items, contained under this tag block.
	 */
	private List<Option> options;
	
	/**
	 * random trigger id
	 */
	private String triggerId;
	
	/**
	 * random trigger-2 id
	 */
	private String trigger2Id;

	/**
	 * random drag-panel id
	 */
	private String dragpanelId;

	/**
	 * @return the jseaAttrOptions
	 */
	@Override
	public String getJseaAttrOptions() {
		return TagConstants.JSEA_ATTR_FALEFIELD_OPTIONS;
	}
	
	/**
	 * @return the items
	 */
	public Object getItems() {
		return items;
	}

	/**
	 * @param items the items to set
	 */
	public void setItems(Object items) {
		this.items = items;
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
	 * @return the depends
	 */
	public String getDepends() {
		return depends;
	}

	/**
	 * @param depends the depends to set
	 */
	public void setDepends(String depends) {
		this.depends = depends;
	}

	/**
	 * @return the itemValue
	 */
	public String getItemValue() {
		return itemValue;
	}

	/**
	 * @param itemValue the itemValue to set
	 */
	public void setItemValue(String itemValue) {
		this.itemValue = itemValue;
	}

	/**
	 * @return the itemLabel
	 */
	public String getItemLabel() {
		return itemLabel;
	}

	/**
	 * @param itemLabel the itemLabel to set
	 */
	public void setItemLabel(String itemLabel) {
		this.itemLabel = itemLabel;
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
	 * @return the optiononly
	 */
	public boolean isOptiononly() {
		return optiononly;
	}

	/**
	 * @param optiononly the optiononly to set
	 */
	public void setOptiononly(boolean optiononly) {
		this.optiononly = optiononly;
	}

	/**
	 * @return the manualonly
	 */
	public boolean isManualonly() {
		return manualonly;
	}

	/**
	 * @param manualonly the manualonly to set
	 */
	public void setManualonly(boolean manualonly) {
		this.manualonly = manualonly;
	}

	/**
	 * @return the onChange
	 */
	public String getOnChange() {
		return onChange;
	}

	/**
	 * @param onChange the onChange to set
	 */
	public void setOnChange(String onChange) {
		this.onChange = onChange;
	}

	/**
	 * @return the options
	 */
	public List<Option> getOptions() {
		return options;
	}

	/**
	 * @param options the options to set
	 */
	public void setOptions(List<Option> options) {
		this.options = options;
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
	
	/**
	 * @return the triggerId2
	 */
	public String getTrigger2Id() {
		return trigger2Id;
	}

	/**
	 * @param trigger2Id the trigger2Id to set
	 */
	public void setTrigger2Id(String trigger2Id) {
		this.trigger2Id = trigger2Id;
	}

	/**
	 * @return the dragpanelId
	 */
	public String getDragpanelId() {
		return dragpanelId;
	}

	/**
	 * @param dragpanelId the dragpanelId to set
	 */
	public void setDragpanelId(String dragpanelId) {
		this.dragpanelId = dragpanelId;
	}
	
	@Override
	public int doEndTag() throws JspException {
		TagWriter tagWriter = this.getTagWriter();
		tagWriter.startTag("div");
		this.writeOptionalAttribute(tagWriter, TagConstants.HTML_ATTR_ID, this.getDragpanelId());
		this.writeOptionalAttribute(tagWriter, TagConstants.HTML_ATTR_CLASS, "falect-panel");
		tagWriter.startTag("ul");
		this.writeOptionalAttribute(tagWriter, TagConstants.HTML_ATTR_CLASS, "falect-options");
		Object items = getItems();
		if (items == null || items == EMPTY) {
			Map<String, String> optionMap = new LinkedHashMap<String, String>();
			for (Option option : options) {
				String value = option.getValue();
				String label = option.getLabel();
				optionMap.put(value, label);
			}
			items = optionMap;
		}
		
		Object itemsObject = evaluate("items", items);
		if (!(itemsObject instanceof String)) {
			if (itemsObject != null) {
				final String selectName = this.getName();
				String valueProperty = (getItemValue() != null ?
						ObjectUtils.getDisplayString(evaluate("itemValue", getItemValue())) : null);
				String labelProperty = (getItemLabel() != null ?
						ObjectUtils.getDisplayString(evaluate("itemLabel", getItemLabel())) : null);
				WeaOptionWriter optionWriter =
						new WeaOptionWriter(itemsObject, (Strings.isEmpty(this.getPath())) ? null : getBindStatus(), valueProperty, labelProperty, "falect-option", isHtmlEscape()) {
							@Override
							protected String processOptionValue(String resolvedValue) {
								return processFieldValue(selectName, resolvedValue, "option");
							}
						};
				optionWriter.writeOptions(tagWriter);
			}
		}
		
		tagWriter.endTag();
		tagWriter.endTag();
		super.doEndTag();
		return EVAL_PAGE;
	}
	
	@Override
	public void doFinally() {
		super.doFinally();
		this.options = null;
	}

	@Override
	protected String getDefaultCssClass() { return "falect"; }

	/**
	 * append options parameter for select javascript plugin using.
	 * @return
	 * @throws JspException 
	 */
	@Override
	protected void appendExtraOptions(JseaOptionsBuilder jsob) throws JspException {
		this.setTriggerId(FALEFIELD_TRIGGER_PREFIX + UUID.randomUUID().toString());
		this.setTrigger2Id(FALEFIELD_TRIGGER2_PREFIX + UUID.randomUUID().toString());
		this.setDragpanelId(FALEFIELD_DRAGPANEL_PREFIX + UUID.randomUUID().toString());
		String value = (Strings.isEmpty(this.getPath())) ? Strings.EMPTY : getDisplayString(getBoundValue(), getPropertyEditor());
		jsob.appendJseaOption(TagConstants.JSEA_OPTION_NAME, this.getName())
			.appendJseaOption("value", value)
			.appendJseaOption(TagConstants.JSEA_OPTION_URL, this.getUrl())
			.appendJseaOption("depends", this.getDepends())
			.appendJseaOption("itemLabel", this.getItemLabel())
			.appendJseaOption("itemValue", this.getItemValue())
			.appendJseaOption(TagConstants.JSEA_OPTION_I18N_PREFIX, this.getI18nPrefix())
			.appendJseaOption("readonly", this.isReadonly())
			.appendJseaOption("optiononly", this.isOptiononly())
			.appendJseaOption("manualonly", this.isManualonly())
			.appendJseaOption("autocomplete", this.getAutocomplete())
			.appendJseaOption("onChange", this.getOnChange())
			.appendJseaOption("triggerId", this.getTriggerId())
			.appendJseaOption("trigger2Id", this.getTrigger2Id())
			.appendJseaOption("panelId", this.getDragpanelId());
		Object items = getItems();
		if (items == null) jsob.appendJseaOption("autoincrement", Boolean.TRUE.booleanValue());
		if (items instanceof String) {
			jsob.appendJseaOption("dragnextItems", items, JseaOptionsBuilder.JSEA_OPTION_TYPE_JS_OBJECT);
		}
	}

	@Override
	protected void appendExtraValidRules(JseaOptionsBuilder jsob) throws JspException { }

	@Override
	protected void writeExtraTagContent(TagWriter tagWriter) throws JspException {
		this.options = new ArrayList<Option>();
		tagWriter.startTag(TRIGGER_TAG);
		this.writeOptionalAttribute(tagWriter, TagConstants.HTML_ATTR_ID, this.getTriggerId());
		this.writeOptionalAttribute(tagWriter, "href", "javascript:void(0);");
		this.writeOptionalAttribute(tagWriter, TagConstants.HTML_ATTR_CLASS, "dragnext");
		tagWriter.appendValue("&nbsp;");
		tagWriter.endTag();
		tagWriter.startTag(TRIGGER_TAG);
		this.writeOptionalAttribute(tagWriter, TagConstants.HTML_ATTR_ID, this.getTrigger2Id());
		this.writeOptionalAttribute(tagWriter, "href", "javascript:void(0);");
		this.writeOptionalAttribute(tagWriter, TagConstants.HTML_ATTR_CLASS, "dragprev");
		tagWriter.appendValue("&nbsp;");
		tagWriter.endTag();
	}
	
	@Override
	protected boolean isSkipBody() {
		return false;
	}

	@Override
	public void addOption(Option option) {
		this.options.add(option);
	}
}
