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

import java.lang.reflect.InvocationTargetException;
import java.math.BigInteger;
import java.text.Format;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.core.Conventions;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.tags.form.AbstractHtmlElementTag;
import org.springframework.web.servlet.tags.form.TagWriter;

import com.pyrube.one.app.i18n.format.FormatManager;
import com.pyrube.one.lang.Strings;
import com.pyrube.wea.util.Weas;

/**
 * Super class for databinding-aware JSP tag for rendering an HTML <code>Element</code> whose
 * inner elements are bound to properties on a <em>element object</em>.
 * 
 * @author Aranjuez
 * @version Dec 01, 2009
 * @since Pyrube-WEA 1.0
 */
public abstract class JseaElementSupportTag extends AbstractHtmlElementTag {
	/**
	 * serial version uid
	 */
	private static final long serialVersionUID = 6896177191186180451L;

	protected static final String WRAPPER_TAG = "li";

	protected static final String LABEL_TAG = "label";

	/**
	 * The name of the '{@code disabled}' attribute.
	 */
	public static final String DISABLED_ATTRIBUTE = "disabled";

	/**
	 * tag writer
	 */
	private TagWriter tagWriter;

	/**
	 * name
	 */
	private String name = null;

	/**
	 * whether it is wrapping
	 */
	private boolean wrapping = true;

	/**
	 * whether HTML escaping is enabled
	 */
	@SuppressWarnings("unused")
	private boolean htmlEscaping = false;

	/**
	 * label
	 */
	private String label = String.valueOf(true);

	/**
	 * required
	 */
	private boolean required;

	/**
	 * format
	 */
	private String format;

	/**
	 * class 'disabled' and attribute 'disabled' will be added if it is true
	 */
	private boolean disabled;

	/**
	 * stylesheet(cssClass) for label
	 */
	private String stylesheet;

	/**
	 * JSEA attribute name for JSEA init options
	 */
	private String jseaAttrOptions = null;

	/**
	 * JSEA attribute name for JSEA validation rules
	 */
	private String jseaAttrValidRules = TagConstants.JSEA_ATTR_VALID_RULES;

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

	@Override
	protected String getName() throws JspException {
		if (!Strings.isEmpty(this.name)) {
			return this.name;
		}
		if (StringUtils.isEmpty(this.getPath())) {
			return name;
		}
		return super.getName();
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the wrapping
	 */
	public boolean isWrapping() {
		return wrapping;
	}

	/**
	 * @param wrapping the wrapping to set
	 */
	public void setWrapping(boolean wrapping) {
		this.wrapping = wrapping;
	}

	/**
	 * @return the htmlEscaping
	 */
	public boolean isHtmlEscaping() {
		return this.isHtmlEscape();
	}

	/**
	 * @param htmlEscaping the htmlEscaping to set
	 */
	public void setHtmlEscaping(boolean htmlEscaping) throws JspException {
		this.htmlEscaping = htmlEscaping;
		this.setHtmlEscape(htmlEscaping);
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
	 * @return the required
	 */
	public boolean isRequired() {
		return required;
	}

	/**
	 * @param required the required to set
	 */
	public void setRequired(boolean required) {
		this.required = required;
	}


	/**
	 * @return the format
	 */
	public String getFormat() {
		return format;
	}

	/**
	 * @param format the format to set
	 */
	public void setFormat(String format) {
		this.format = format;
	}

	/**
	 * @return the disabled
	 */
	public boolean isDisabled() {
		return disabled;
	}

	/**
	 * @param disabled the disabled to set
	 */
	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
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
	 * @return the jseaAttrOptions
	 */
	public String getJseaAttrOptions() {
		return jseaAttrOptions;
	}

	/**
	 * @param jseaAttrOptions the jseaAttrOptions to set
	 */
	public void setJseaAttrOptions(String jseaAttrOptions) {
		this.jseaAttrOptions = jseaAttrOptions;
	}

	/**
	 * @return the jseaAttrValidRules
	 */
	public String getJseaAttrValidRules() {
		return jseaAttrValidRules;
	}

	/**
	 * @param jseaAttrValidRules the jseaAttrValidRules to set
	 */
	public void setJseaAttrValidRules(String jseaAttrValidRules) {
		this.jseaAttrValidRules = jseaAttrValidRules;
	}

	/**
	 * Decides whether label/wrapping renders
	 */
	protected void decideWrapping() {
		MultielemsTag multielemsTag = (MultielemsTag)findAncestorWithClass(this, MultielemsTag.class);
		String label = this.getLabel();
		if (String.valueOf(false).equalsIgnoreCase(label) || 
			String.valueOf(true).equalsIgnoreCase(label)) {
			this.setLabel(String.valueOf(multielemsTag == null));
		}
		this.setWrapping(multielemsTag == null);
	}

	/**
	 * Write wrapper, subclass can override it for special need.
	 * @param tagWriter
	 * @throws JspException
	 */
	protected void writeWrapper(TagWriter tagWriter) throws JspException {
		if (this.isWrapping()) {
			tagWriter.startTag(WRAPPER_TAG);
			StringBuffer buf = new StringBuffer();
			String stylesheet = resolveStylesheet();
			if (!Strings.isEmpty(stylesheet)){ buf.append(stylesheet + " "); }
			if (this.isRequired()) { buf.append("required"); }
			String wrapperClass = buf.toString().trim();
			if (!Strings.isEmpty(wrapperClass)) {
				tagWriter.writeAttribute(TagConstants.HTML_ATTR_CLASS, wrapperClass);
			}
		}
	}

	/**
	 * Resolve the stylesheet of this <code>Wrapper</code>
	 * @return
	 */
	protected String resolveStylesheet() throws JspException {
		return(ObjectUtils.getDisplayString(evaluate("stylesheet", getStylesheet())));
	}

	/**
	 * Write label element.
	 * @param tagWriter
	 * @throws JspException
	 */
	protected void writeLabel(TagWriter tagWriter) throws JspException {
		if (!String.valueOf(false).equalsIgnoreCase(this.getLabel())) {
			tagWriter.startTag(LABEL_TAG);
			StringBuffer buf = new StringBuffer();
			if (this.isRequired()) { buf.append("required"); }
			String labelClass = buf.toString().trim();
			if (!Strings.isEmpty(labelClass)) {
				tagWriter.writeAttribute(TagConstants.HTML_ATTR_CLASS, labelClass);
			}
			tagWriter.appendValue(localizeAttribute("label"));
			tagWriter.endTag();
		}
	}

	/**
	 * Determine the <code>id</code> attribute value for this tag,
	 * autogenerating one if none specified.
	 * @see #getId()
	 * @see #autogenerateId()
	 */
	protected String resolveId() throws JspException {
		Object id = evaluate("id", getId());
		if (id != null) {
			String idString = id.toString();
			return (StringUtils.hasText(idString) ? idString : null);
		}
		return autogenerateId();
	}

	/**
	 * Autogenerate the '{@code id}' attribute value for this tag.
	 * <p>The default implementation simply delegates to {@link #getName()},
	 * deleting invalid characters (such as "[" or "]").
	 */
	protected String autogenerateId() throws JspException {
		return StringUtils.deleteAny(getName(), "[]");
	}

	/**
	 * WEA Field format, for subclass to override it.
	 * @return
	 */
	protected String resolveFormat() throws JspException {
		String format = this.getFormat();
		if (Strings.isEmpty(format)) {
		} else {
			int idx = format.indexOf("|");
			if (idx >= 0) { format = format.substring(0, idx); }
		}
		return format;
	}

	/**
	 * WEA Field format pattern, for subclass to override it.
	 * @return
	 */
	protected String resolveFormatPattern() throws JspException {
		return resolveFormat();
	}
	
	/**
	 * returns the default css class of this concrete <code>Element</code>
	 * @return
	 */
	protected String getDefaultCssClass() throws JspException { return Strings.EMPTY; }

	@Override
	protected String resolveCssClass() throws JspException {
		StringBuffer buf = new StringBuffer();
		String defaultCssClass = this.getDefaultCssClass();
		if (!Strings.isEmpty(defaultCssClass)) { buf.append(defaultCssClass + " "); }
		String cssClass 
			= (Strings.isEmpty(this.getPath()))
				? ObjectUtils.getDisplayString(evaluate("cssClass", getCssClass()))
				: super.resolveCssClass();
		if (!Strings.isEmpty(cssClass)) { buf.append(cssClass + " "); }
		if (this.isDisabled()) { buf.append("disabled "); }
		return buf.toString().trim();
	}

	/**
	 * determines the JSEA options value for this element.
	 * concrete subclass can override this method to supply the corresponding JSEA option parameters
	 * in JSON format, that will be used in the JSEA javascript plugin. 
	 */
	protected String resolveJseaOptions() throws JspException {
		return resolveJseaOptions(false);
	}

	/**
	 * determines the JSEA options value for this element.
	 * concrete subclass can override this method to supply the corresponding JSEA option parameters
	 * in JSON format, that will be used in the JSEA javascript plugin. 
	 * @param renderingWithBraces boolean
	 */
	protected String resolveJseaOptions(boolean renderingWithBraces) throws JspException {
		JseaOptionsBuilder jsob = JseaOptionsBuilder.newBuilder().setRenderingWithBraces(renderingWithBraces);
		appendJseaDefaultOptions(jsob);
		appendJseaOptions(jsob);
		return jsob.toString();
	}

	/**
	 * appends the default option set of JSEA Options to the given JseaOptionsBuilder.
	 * further abstract sub-classes should override this method to add in any additional 
	 * default options but must remember to call the super method.
	 * concrete sub-classes should call this method when/if they want to render default
	 * options.
	 * @param jsob JseaOptionsBuilder
	 * @throws JspException
	 */
	protected void appendJseaDefaultOptions(JseaOptionsBuilder jsob) throws JspException {
		jsob.appendJseaOption(TagConstants.JSEA_OPTION_NAME, getName());
	}

	/**
	 * appends the option set of JSEA Options to the given JseaOptionsBuilder.
	 * Further abstract sub-classes should override this method to add in any JSEA
	 * additional options.
	 * @param jsob JseaOptionsBuilder
	 * @throws JspException
	 */
	protected void appendJseaOptions(JseaOptionsBuilder jsob) throws JspException {}

	/**
	 * writes JSEA options attribute
	 * @param tagWriter
	 * @throws JspException
	 */
	protected void writeJseaOptionsAttribute(TagWriter tagWriter) throws JspException {	
		this.writeOptionalAttribute(tagWriter, getJseaAttrOptions(), resolveJseaOptions());
	}

	/**
	 * concrete subclass could implement this method to supply the corresponding JSEA validation rules
	 * in JSON format, that will be used in the JSEA javascript plugin. 
	 */
	protected String resolveJseaValidRules() throws JspException { return null; }

	/**
	 * writes JSEA validation rules attribute
	 * @param tagWriter
	 * @throws JspException
	 */
	protected void writeJseaValidRulesAttribute(TagWriter tagWriter) throws JspException {
		this.writeOptionalAttribute(tagWriter, getJseaAttrValidRules(), resolveJseaValidRules());
	}

	@Override
	protected void writeOptionalAttributes(TagWriter tagWriter) throws JspException {
		super.writeOptionalAttributes(tagWriter);
		
		if (isDisabled()) {
			tagWriter.writeAttribute(DISABLED_ATTRIBUTE, "true");
		}
	}

	/**
	 * localizes a given attribute value
	 * @param attrName
	 * @return
	 * @throws JspException
	 */
	protected String localizeAttribute(String attrName) throws JspException {
		try {
			String attrValue = BeanUtils.getProperty(this, attrName);
			String code = null;
			if (String.valueOf(true).equalsIgnoreCase(attrValue)) {
				Object funcnameAttr =
					this.pageContext.getAttribute(
						Conventions.getQualifiedAttributeName(JseaFormSupportTag.class, "funcname"),
						PageContext.REQUEST_SCOPE);
				if (funcnameAttr != null) {
					String funcname = (String)funcnameAttr;
					code = funcname + "." + attrName + "." + this.getName();
				} else {
					code = "global." + attrName + "." + this.getName();
				}
			} else if (!String.valueOf(false).equalsIgnoreCase(attrValue)) {
				code = attrValue;
			}
			return Weas.localizeMessage(code);
		} catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
			logger.error("Failed to find attribute: " + attrName, e);
			throw new JspException("Failed to find attribute: " + attrName, e);
		}
	}

	/**
	 * cConcrete subclass could implement this method to format the corresponding value
	 * @param localeCode
	 * @param nameOrPattern
	 * @param unformated
	 * @return
	 * @throws JspException
	 */
	protected String formatValue(String localeCode, String nameOrPattern, Object unformated) throws JspException {
		if (unformated instanceof String) {
			return (String) unformated;
		}
		Format format = null;
		if (unformated instanceof Number) {
			if (Strings.isEmpty(nameOrPattern)) {
				if ((unformated instanceof Byte)
						|| (unformated instanceof Short)
						|| (unformated instanceof Integer)
						|| (unformated instanceof Long)
						|| (unformated instanceof BigInteger)) {
					format = FormatManager.numberFormatOf(localeCode, FormatManager.NFN_INTEGER);
				} else {
					format = FormatManager.numberFormatOf(localeCode, FormatManager.NFN_FLOAT);
				}
			} else {
				format = FormatManager.numberFormatOf(localeCode, nameOrPattern);
			}
		}
		return format != null ? format.format(unformated) : unformated != null ? (String) unformated : Strings.EMPTY;
	}

}