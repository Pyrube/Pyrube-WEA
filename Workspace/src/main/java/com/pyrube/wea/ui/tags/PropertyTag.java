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

import org.springframework.web.servlet.tags.form.TagWriter;

import com.pyrube.one.lang.Strings;
import com.pyrube.wea.context.WebContextHolder;

/**
 * Base class for databinding-aware JSP tags that render JSEA data element.
 *
 * <p>Provides a set of properties corresponding to the set of HTML attributes
 * that are common across form data elements.
 *
 * @author Aranjuez
 * @version Dec 01, 2009
 * @since Pyrube-WEA 1.0
 */
public abstract class PropertyTag extends JseaElementSupportTag {

	/**
	 * searial version uid
	 */
	private static final long serialVersionUID = 305883487584202397L;

	protected static final String DATA_TAG = "span";

	private String type;

	private String value;

	private String i18nPrefix;

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

	@Override
	protected String resolveCssClass() throws JspException {
		StringBuffer buf = new StringBuffer();
		String cssClass = super.resolveCssClass();
		if (!Strings.isEmpty(cssClass)) { buf.append(cssClass + " "); }
		if (this.isRequired()) { buf.append("required"); }
		return buf.toString();
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
		return super.doEndTag();
	}

	@Override
	protected String resolveStylesheet() throws JspException {
		String stylesheet = super.resolveStylesheet();
		return("data" + (Strings.isEmpty(stylesheet) ? Strings.EMPTY : " " + stylesheet));
	}
	
	/**
	 * Write field, subclass can override it for special need.
	 * @param tagWriter
	 * @throws JspException
	 */
	protected void writeField(TagWriter tagWriter) throws JspException {
		tagWriter.startTag(DATA_TAG);
		writeDefaultAttributes(tagWriter);
		writeJseaOptionsAttribute(tagWriter);
		writeValue(tagWriter);
		
		tagWriter.endTag();
	}
	
	/**
	 * Resolve JSEA options
	 * @throws JspException
	 */
	protected String resolveJseaOptions() throws JspException {
		JseaOptionsBuilder jsob = JseaOptionsBuilder.newBuilder();
		jsob.appendJseaOption(TagConstants.JSEA_OPTION_TYPE, getType())
			.appendJseaOption(TagConstants.JSEA_OPTION_I18N_PREFIX, getI18nPrefix())
			.appendJseaOption(TagConstants.JSEA_OPTION_STYLESHEET, getStylesheet());
		appendExtraOptions(jsob);
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
	 * Writes the <code>data-value</code> attribute to the supplied TagWriter.
	 * Subclasses may choose to override this implementation to control exactly
	 * when the value is written.
	 * @param tagWriter
	 * @throws JspException
	 */
	protected void writeValue(TagWriter tagWriter) throws JspException {
		String value = this.getValue();
		if (!Strings.isEmpty(value)) {
			tagWriter.writeAttribute(TagConstants.JSEA_ATTR_DATA_VALUE, value);
		} else if (!Strings.isEmpty(this.getPath())) {
			String fmtPattern = this.resolveFormatPattern();
			if (Strings.isEmpty(fmtPattern)) {
				value = getDisplayString(getBoundValue(), getPropertyEditor());
			} else {
				String localeCode = WebContextHolder.getWebContext().getLocale().toString();
				Object actualValue = getBindStatus().getActualValue();
				value = formatValue(localeCode, fmtPattern, actualValue);
			}
			tagWriter.writeAttribute(TagConstants.JSEA_ATTR_DATA_VALUE, value);
		}
		writeText(tagWriter, processFieldValue(getName(), value, "data"));
	}
	
	/**
	 * 
	 * @param tagWriter
	 * @param text
	 * @throws JspException
	 */
	protected void writeText(TagWriter tagWriter, String text) throws JspException {
		tagWriter.appendValue(text);
	}

	/**
	 * Write extra content for tag, subclass can override this method to supply other information needed
	 */
	protected void writeExtraTagContent(TagWriter tagWriter) throws JspException { }

}
