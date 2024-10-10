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

import java.util.Map;

import javax.servlet.jsp.JspException;

import org.springframework.lang.Nullable;
import org.springframework.web.servlet.tags.form.TagWriter;

import com.pyrube.one.lang.Strings;
import com.pyrube.wea.context.WebContextHolder;

/**
 * Base class for databinding-aware JSP tags that render JSEA validation-supported 
 * field element.
 *
 * <p>Provides a set of properties corresponding to the set of HTML attributes
 * that are common across form input elements.
 *
 * @author Aranjuez
 * @version Dec 01, 2009
 * @since Pyrube-WEA 1.0
 */
public abstract class FieldTag extends JseaElementSupportTag {

	/**
	 * searial version uid
	 */
	private static final long serialVersionUID = 8001660990333312463L;

	/**
	 * The name of the '{@code onfocus}' attribute.
	 */
	public static final String ONFOCUS_ATTRIBUTE = "onfocus";

	/**
	 * The name of the '{@code onblur}' attribute.
	 */
	public static final String ONBLUR_ATTRIBUTE = "onblur";

	/**
	 * The name of the '{@code onchange}' attribute.
	 */
	public static final String ONCHANGE_ATTRIBUTE = "onchange";

	/**
	 * The name of the '{@code accesskey}' attribute.
	 */
	public static final String ACCESSKEY_ATTRIBUTE = "accesskey";

	/**
	 * The name of the '{@code disabled}' attribute.
	 */
	public static final String DISABLED_ATTRIBUTE = "disabled";

	/**
	 * The name of the '{@code readonly}' attribute.
	 */
	public static final String READONLY_ATTRIBUTE = "readonly";

	/**
	 * The HTML '{@code input}' tag.
	 */
	protected static final String FIELD_TAG = "input";

	/**
	 * The HTML '{@code a}' tag.
	 */
	protected static final String TRIGGER_TAG = "a";

	/**
	 * The HTML '{@code img}' tag.
	 */
	protected static final String IMAGE_TAG = "img";

	private String type;
	
	private String value;

	private String minLen;

	private String maxLen;

	private String minVal;

	private String maxVal;
	
	private String equalTo;
	
	private String remote;
	
	private String validations;
	
	private boolean visible = true;
	
	private boolean emptiable = false;
	
	private String placeholder;
	
	private String help;
	
	private String onEmpty;
	
	private String onfocus;

	private String onblur;

	private String onchange;

	private String accesskey;

	private boolean readonly;

	private String size;

	private String maxlength;

	private String alt;

	private String onselect;

	private String autocapitalize = "off";

	private String autocomplete;

	private String autocorrect = "off";
	
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
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * @return the minLen
	 */
	public String getMinLen() {
		return minLen;
	}

	/**
	 * @param minLen the minLen to set
	 */
	public void setMinLen(String minLen) {
		this.minLen = minLen;
	}

	/**
	 * @return the maxLen
	 */
	public String getMaxLen() {
		return maxLen;
	}

	/**
	 * @param maxLen the maxLen to set
	 */
	public void setMaxLen(String maxLen) {
		this.maxLen = maxLen;
	}

	/**
	 * @return the minVal
	 */
	public String getMinVal() {
		return minVal;
	}

	/**
	 * @param minVal the minVal to set
	 */
	public void setMinVal(String minVal) {
		this.minVal = minVal;
	}

	/**
	 * @return the maxVal
	 */
	public String getMaxVal() {
		return maxVal;
	}

	/**
	 * @param maxVal the maxVal to set
	 */
	public void setMaxVal(String maxVal) {
		this.maxVal = maxVal;
	}

	/**
	 * @return the equalTo
	 */
	public String getEqualTo() {
		return equalTo;
	}

	/**
	 * @param equalTo the equalTo to set
	 */
	public void setEqualTo(String equalTo) {
		this.equalTo = equalTo;
	}

	/**
	 * @return the remote
	 */
	public String getRemote() {
		return remote;
	}

	/**
	 * @param remote the remote to set
	 */
	public void setRemote(String remote) {
		this.remote = remote;
	}

	/**
	 * @return the validations
	 */
	public String getValidations() {
		return validations;
	}

	/**
	 * @param validations the validations to set
	 */
	public void setValidations(String validations) {
		this.validations = validations;
	}
	
	/**
	 * @return the visible
	 */
	public boolean isVisible() {
		return visible;
	}
	
	/**
	 * @param visible the visible to set
	 */
	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	/**
	 * @return the emptiable
	 */
	public boolean isEmptiable() {
		return emptiable;
	}

	/**
	 * @param emptiable the emptiable to set
	 */
	public void setEmptiable(boolean emptiable) {
		this.emptiable = emptiable;
	}

	/**
	 * @return the placeholder
	 */
	public String getPlaceholder() {
		return placeholder;
	}

	/**
	 * @param placeholder the placeholder to set
	 */
	public void setPlaceholder(String placeholder) {
		this.placeholder = placeholder;
	}

	/**
	 * @return the help
	 */
	public String getHelp() {
		return help;
	}

	/**
	 * @param help the help to set
	 */
	public void setHelp(String help) {
		this.help = help;
	}
	
	/**
	 * @return the onEmpty
	 */
	public String getOnEmpty() {
		return onEmpty;
	}

	/**
	 * @param onEmpty the onEmpty to set
	 */
	public void setOnEmpty(String onEmpty) {
		this.onEmpty = onEmpty;
	}

	/**
	 * Set the value of the '{@code onfocus}' attribute.
	 * May be a runtime expression.
	 */
	public void setOnfocus(String onfocus) {
		this.onfocus = onfocus;
	}

	/**
	 * Get the value of the '{@code onfocus}' attribute.
	 */
	@Nullable
	protected String getOnfocus() {
		return this.onfocus;
	}

	/**
	 * Set the value of the '{@code onblur}' attribute.
	 * May be a runtime expression.
	 */
	public void setOnblur(String onblur) {
		this.onblur = onblur;
	}

	/**
	 * Get the value of the '{@code onblur}' attribute.
	 */
	@Nullable
	protected String getOnblur() {
		return this.onblur;
	}

	/**
	 * Set the value of the '{@code onchange}' attribute.
	 * May be a runtime expression.
	 */
	public void setOnchange(String onchange) {
		this.onchange = onchange;
	}

	/**
	 * Get the value of the '{@code onchange}' attribute.
	 */
	@Nullable
	protected String getOnchange() {
		return this.onchange;
	}

	/**
	 * Set the value of the '{@code accesskey}' attribute.
	 * May be a runtime expression.
	 */
	public void setAccesskey(String accesskey) {
		this.accesskey = accesskey;
	}

	/**
	 * Get the value of the '{@code accesskey}' attribute.
	 */
	@Nullable
	protected String getAccesskey() {
		return this.accesskey;
	}

	/**
	 * Sets the value of the '{@code readonly}' attribute.
	 */
	public void setReadonly(boolean readonly) {
		this.readonly = readonly;
	}

	/**
	 * Gets the value of the '{@code readonly}' attribute.
	 */
	protected boolean isReadonly() {
		return this.readonly;
	}

	/**
	 * @return the size
	 */
	public String getSize() {
		return size;
	}

	/**
	 * @param size the size to set
	 */
	public void setSize(String size) {
		this.size = size;
	}

	/**
	 * @return the maxlength
	 */
	public String getMaxlength() {
		return maxlength;
	}

	/**
	 * @param maxlength the maxlength to set
	 */
	public void setMaxlength(String maxlength) {
		this.maxlength = maxlength;
	}

	/**
	 * @return the alt
	 */
	public String getAlt() {
		return alt;
	}

	/**
	 * @param alt the alt to set
	 */
	public void setAlt(String alt) {
		this.alt = alt;
	}

	/**
	 * @return the onselect
	 */
	public String getOnselect() {
		return onselect;
	}

	/**
	 * @param onselect the onselect to set
	 */
	public void setOnselect(String onselect) {
		this.onselect = onselect;
	}

	/**
	 * @return the autocapitalize
	 */
	public String getAutocapitalize() {
		return autocapitalize;
	}

	/**
	 * @param autocapitalize the autocapitalize to set
	 */
	public void setAutocapitalize(String autocapitalize) {
		this.autocapitalize = autocapitalize;
	}

	/**
	 * @return the autocomplete
	 */
	public String getAutocomplete() {
		return autocomplete;
	}

	/**
	 * @param autocomplete the autocomplete to set
	 */
	public void setAutocomplete(String autocomplete) {
		this.autocomplete = autocomplete;
	}

	/**
	 * @return the autocorrect
	 */
	public String getAutocorrect() {
		return autocorrect;
	}

	/**
	 * @param autocorrect the autocorrect to set
	 */
	public void setAutocorrect(String autocorrect) {
		this.autocorrect = autocorrect;
	}

	@Override
	protected int writeTagContent(TagWriter tagWriter) throws JspException {
		this.setTagWriter(tagWriter);
		this.decideWrapping();
		//write wrapper
		this.writeWrapper(tagWriter);
		//write label
		this.writeLabel(tagWriter);
		//write field
		this.writeField(tagWriter);
		
		this.writeExtraTagContent(tagWriter);
		
		if (this.isSkipBody()) {
			return SKIP_BODY;
		} else {
			return EVAL_BODY_INCLUDE;
		}
	}
	
	/**
	 * Mark that tag body content parse or not. Sub tags such as 
	 * {@link RadiosTag} may override this method to allow 
	 * body content parsing.
	 * @return
	 */
	protected boolean isSkipBody() {
		return true;
	}
	
	@Override
	public int doEndTag() throws JspException {
		if (isWrapping()) {
			getTagWriter().endTag();
		}
		this.writeJavascript(getTagWriter());
		return super.doEndTag();
	}

	@Override
	protected String resolveStylesheet() throws JspException {
		String stylesheet = super.resolveStylesheet();
		return("element" + (Strings.isEmpty(stylesheet) ? Strings.EMPTY : " " + stylesheet));
	}
	
	/**
	 * Write input field, subclass can override it for special need.
	 * @param tagWriter
	 * @throws JspException
	 */
	protected void writeField(TagWriter tagWriter) throws JspException {
		tagWriter.startTag(this.resolveFieldTag());
		writeDefaultAttributes(tagWriter);
		tagWriter.writeAttribute(TagConstants.HTML_ATTR_TYPE, this.resolveFieldType());
		if (!Strings.isEmpty(this.getPath())) {
			writeValue(tagWriter);
		}
		writeJseaOptionsAttribute(tagWriter);
		writeJseaValidRulesAttribute(tagWriter);
		// JSEA extra optional attributes
		writeExtraFieldAttributes(tagWriter);
		
		// custom optional attributes
		writeOptionalAttribute(tagWriter, TagConstants.HTML_ATTR_PLACEHOLDER, this.localizeAttribute("placeholder"));
		writeOptionalAttribute(tagWriter, TagConstants.HTML_ATTR_SIZE, getSize());
		writeOptionalAttribute(tagWriter, TagConstants.HTML_ATTR_MAXLENGTH, getMaxlength());
		writeOptionalAttribute(tagWriter, TagConstants.HTML_ATTR_ALT, getAlt());
		writeOptionalAttribute(tagWriter, TagConstants.HTML_EVENT_ONSELECT, getOnselect());
		writeOptionalAttribute(tagWriter, TagConstants.HTML_ATTR_AUTOCAPITALIZE, getAutocapitalize());
		writeOptionalAttribute(tagWriter, TagConstants.HTML_ATTR_AUTOCOMPLETE, getAutocomplete());
		writeOptionalAttribute(tagWriter, TagConstants.HTML_ATTR_AUTOCORRECT, getAutocorrect());
		tagWriter.endTag();
	}
	
	/**
	 * JSEA field tag, for subclass to override it.
	 * @return
	 */
	protected String resolveFieldTag() throws JspException {
		return(FIELD_TAG);
	}
	
	/**
	 * JSEA field type, for subclass to override it.
	 * @return
	 */
	protected abstract String resolveFieldType() throws JspException;

	/**
	 * Resolve JSEA options
	 * @throws JspException
	 */
	protected String resolveJseaOptions() throws JspException {
		JseaOptionsBuilder jsob = JseaOptionsBuilder.newBuilder();
		this.appendExtraOptions(jsob);
		String i18nHelp = this.localizeAttribute(TagConstants.JSEA_OPTION_HELP); 
		if (!Strings.isEmpty(i18nHelp)) {
			jsob.appendJseaOption(TagConstants.JSEA_OPTION_HELP, i18nHelp);
		}
		jsob.appendJseaOption(TagConstants.JSEA_OPTION_VISIBLE, this.isVisible())
			.appendJseaOption(TagConstants.JSEA_OPTION_EMPTIABLE, this.isEmptiable())
			.appendJseaOption(TagConstants.JSEA_EVENT_ONEMPTY, this.getOnEmpty(), JseaOptionsBuilder.JSEA_OPTION_TYPE_FUNCTION);
		return jsob.toString();
	}

	/**
	 * Subclass can override this method to append some other option for specific requirements.
	 * @param jsob
	 */
	protected abstract void appendExtraOptions(JseaOptionsBuilder jsob) throws JspException;

	@Override
	protected String resolveJseaValue() throws JspException {
		String value = this.getValue();
		return !Strings.isEmpty(value) ? value : super.resolveJseaValue();
	}

	/**
	 * Write JSEA validation type and rules attributes
	 * @param tagWriter
	 * @throws JspException
	 */
	protected void writeJseaValidRulesAttribute(TagWriter tagWriter) throws JspException {
		writeOptionalAttribute(tagWriter, TagConstants.JSEA_ATTR_VALID_TYPE, this.getType());
		writeOptionalAttribute(tagWriter, TagConstants.JSEA_ATTR_VALID_RULES, this.resolveJseaValidRules());
	}

	/**
	 * Resolve JSEA validation rules, such as: jsea-valid-rules="{minLength:'5', maxLength:'30'}"
	 * 
	 * @return
	 */
	protected String resolveJseaValidRules() throws JspException {
		JseaOptionsBuilder jsob = JseaOptionsBuilder.newBuilder().setRenderingWithBraces(true);
		jsob.appendJseaOption(TagConstants.JSEA_VALID_RULE_REQUIRED, isRequired())
			.appendJseaOption(TagConstants.JSEA_VALID_RULE_MINLENGTH, getMinLen())
			.appendJseaOption(TagConstants.JSEA_VALID_RULE_MAXLENGTH, getMaxLen())
			.appendJseaOption(TagConstants.JSEA_VALID_RULE_MINVALUE, getMinVal())
			.appendJseaOption(TagConstants.JSEA_VALID_RULE_MAXVALUE, getMaxVal())
			.appendJseaOption(TagConstants.JSEA_VALID_RULE_EQUALTO, getEqualTo())
			.appendJseaOption(TagConstants.JSEA_VALID_RULE_REMOTE, getRemote());
		appendExtraValidRules(jsob);
		return jsob.toString();
	}
	
	/**
	 * Subclass can override this method to append some other validation rules for specific requirements.
	 * @param jsob
	 * @throws JspException
	 */
	protected void appendExtraValidRules(JseaOptionsBuilder jsob) throws JspException {
		String validations = this.getValidations();
		jsob.appendJseaOptions(validations);
	}

	/**
	 * Subclass can override this method to write extra field attributes for specific requirements.
	 * @param tagWriter
	 * @throws JspException
	 */
	protected void writeExtraFieldAttributes(TagWriter tagWriter) throws JspException {
		writeOptionalAttribute(tagWriter, TagConstants.JSEA_ATTR_FORMAT, this.resolveFormat());
	}

	@Override
	protected String resolveCssClass() throws JspException {
		StringBuffer buf = new StringBuffer();
		String cssClass = super.resolveCssClass();
		if (!Strings.isEmpty(cssClass)) { buf.append(cssClass + " "); }
		if (this.isRequired()) { buf.append("required "); }
		if (this.isReadonly()) { buf.append("readonly "); }
		return buf.toString().trim();
	}
	
	/**
	 * Writes the <code>value</code> attribute to the supplied TagWriter.
	 * Subclasses may choose to override this implementation to control exactly
	 * when the value is written.
	 * @param tagWriter
	 * @throws JspException
	 */
	protected void writeValue(TagWriter tagWriter) throws JspException {
		String value = this.getValue();
		String type = null;
		if (!Strings.isEmpty(value)) {
			tagWriter.writeAttribute(TagConstants.HTML_ATTR_VALUE, value);
		} else {
			String fmtPattern = this.resolveFormatPattern();
			if (Strings.isEmpty(fmtPattern)) {
				value = getDisplayString(getBoundValue(), getPropertyEditor());
			} else {
				String localeCode = WebContextHolder.getWebContext().getLocale().toString();
				Object actualValue = getBindStatus().getActualValue();
				value = formatValue(localeCode, fmtPattern, actualValue);
			}
			Map<String, Object> attributes = getDynamicAttributes();
			if (attributes != null) {
				type = (String) attributes.get("type");
			}
			if (type == null) {
				type = getType();
			}
			tagWriter.writeAttribute(TagConstants.HTML_ATTR_VALUE, processFieldValue(getName(), value, type));
		}
	}
	/**
	 * Adds input-specific optional attributes as defined by this base class.
	 */
	@Override
	protected void writeOptionalAttributes(TagWriter tagWriter) throws JspException {
		super.writeOptionalAttributes(tagWriter);

		writeOptionalAttribute(tagWriter, ONFOCUS_ATTRIBUTE, getOnfocus());
		writeOptionalAttribute(tagWriter, ONBLUR_ATTRIBUTE, getOnblur());
		writeOptionalAttribute(tagWriter, ONCHANGE_ATTRIBUTE, getOnchange());
		writeOptionalAttribute(tagWriter, ACCESSKEY_ATTRIBUTE, getAccesskey());
		if (isReadonly()) {
			writeOptionalAttribute(tagWriter, READONLY_ATTRIBUTE, "readonly");
		}
	}

	/**
	 * Write extra content for tag, subclass can override this method to supply other information needed
	 */
	protected void writeExtraTagContent(TagWriter tagWriter) throws JspException { }
	
	/**
	 * Write javascript for specific field.
	 * @throws JspException
	 */
	protected void writeJavascript(TagWriter tagWriter) throws JspException {	
		String script = this.resolveJavascript();
		if (!Strings.isEmpty(script)) {
			tagWriter.startTag("script");
			tagWriter.writeOptionalAttributeValue("type", "text/javascript");
			tagWriter.forceBlock();
			tagWriter.appendValue(script);
			tagWriter.endTag();
		}
	}
	
	/**
	 * Resolve javascript content for specific field.
	 * @return
	 */
	protected String resolveJavascript() throws JspException { return null; }

}
