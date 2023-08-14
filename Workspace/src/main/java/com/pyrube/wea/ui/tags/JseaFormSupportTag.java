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
import javax.servlet.jsp.PageContext;

import org.springframework.core.Conventions;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.tags.form.FormTag;
import org.springframework.web.servlet.tags.form.TagWriter;

import com.pyrube.one.app.AppConfig;
import com.pyrube.one.lang.Strings;
import com.pyrube.wea.WeaConstants;
/**
 * Super class for databinding-aware JSP tag for rendering an HTML <code>Form</code> whose
 * inner elements are bound to properties on a <em>form object</em>.
 * 
 * @author Aranjuez
 * @version Dec 01, 2009
 * @since Pyrube-WEA 1.0
 */
public abstract class JseaFormSupportTag extends FormTag {
	/**
	 * serial version uid
	 */
	private static final long serialVersionUID = 2156758217996256L;
	
	public static final String FUNCNAME_ATTRIBUTE_VARIABLE_NAME =
		Conventions.getQualifiedAttributeName(JseaFormSupportTag.class, "funcname");
	
	protected static final String FORM_TAG = "form";

	private String formType;
	
	/**
	 * 1, if null, default i18nkey is <funcname>.infobar.<funcname>-<operation>
	 * 2, i18nkey?{propname1}. the i18nkey defined in properties file should be i18nkey + 1. e.x. sample.infobar.sample-list1
	 * 3, i18nkey?{propname1},{propname2}. if propname 2 is null probably, it should define 2 i18nkeys in properties file as below
	 *                                     sample.infobar.sample-list1=Samples in {0}
	 *                                     sample.infobar.sample-list2=Samples in {0} - {1}
	 * 4, ?{propname1}
	 * 5, ?{propname1},{propname2}
	 */
	private String infobar;
	
	private String basename;
	
	private String funcname;
	private String operation;

	private String keyProp;
	private String statProp = (String) AppConfig.getAppConfig().getAppProperty("STAT_PROP_DEFAULT");

	private String mode;

	private String script;
	
	private boolean backable = false;

	/**
	 * @return the formType
	 */
	public String getFormType() {
		return formType;
	}

	/**
	 * @param formType the formType to set
	 */
	public void setFormType(String formType) {
		this.formType = formType;
	}

	/**
	 * @return the infobar
	 */
	public String getInfobar() {
		return infobar;
	}

	/**
	 * @param infobar the infobar to set
	 */
	public void setInfobar(String infobar) {
		this.infobar = infobar;
	}

	/**
	 * @return the basename
	 */
	public String getBasename() {
		return basename;
	}

	/**
	 * @param basename the basename to set
	 */
	public void setBasename(String basename) {
		this.basename = basename;
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
	 * @return the keyProp
	 */
	public String getKeyProp() {
		return keyProp;
	}

	/**
	 * @param keyProp the keyProp to set
	 */
	public void setKeyProp(String keyProp) {
		this.keyProp = keyProp;
	}

	/**
	 * @return the statProp
	 */
	public String getStatProp() {
		return statProp;
	}

	/**
	 * @param statProp the statProp to set
	 */
	public void setStatProp(String statProp) {
		this.statProp = statProp;
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
	 * @return the script
	 */
	public String getScript() {
		return script;
	}

	/**
	 * @param script the script to set
	 */
	public void setScript(String script) {
		this.script = script;
	}

	/**
	 * @return the backable
	 */
	public boolean isBackable() {
		return backable;
	}

	/**
	 * @param backable the backable to set
	 */
	public void setBackable(boolean backable) {
		this.backable = backable;
	}

	/**
	 * @see org.springframework.web.servlet.tags.form.FormTag#getModelAttribute()
	 */
	@Override
	protected String getModelAttribute() {
		String modelAttribute = super.getModelAttribute();
		if (StringUtils.isEmpty(modelAttribute) || DEFAULT_COMMAND_NAME.equals(modelAttribute)) {
			modelAttribute = this.getFuncname();
		}
		if (StringUtils.isEmpty(modelAttribute)) {
			modelAttribute = DEFAULT_COMMAND_NAME;
		}
		this.pageContext.setAttribute(FUNCNAME_ATTRIBUTE_VARIABLE_NAME, this.getFuncname(), PageContext.REQUEST_SCOPE);
		return modelAttribute;
	}

	@Override
	protected String getCssClass() {
		String cssClass = super.getCssClass();
		StringBuffer buf = new StringBuffer();
		if (!Strings.isEmpty(cssClass)) {
			buf.append(cssClass + " ");
		}
		String formCssClass = this.resolveFormStylesheet();
		if (!Strings.isEmpty(formCssClass)) {
			buf.append(formCssClass + " ");
		}
		return buf.toString();
	}

	/**
	 * constructor
	 */
	public JseaFormSupportTag() {
		super();
	}

	@Override
	protected void writeDefaultAttributes(TagWriter tagWriter)
		throws JspException {
		writeOptionalAttribute(tagWriter, TagConstants.HTML_ATTR_ID, resolveId());
		writeOptionalAttribute(tagWriter, TagConstants.HTML_ATTR_NAME, getName());
		writeOptionalAttribute(tagWriter, TagConstants.HTML_ATTR_CLASS, getCssClass());
		writeOptionalAttribute(tagWriter, TagConstants.JSEA_ATTR_BASENAME, getBasename());
		writeOptionalAttribute(tagWriter, TagConstants.JSEA_ATTR_FUNCNAME, getFuncname());
		writeOptionalAttribute(tagWriter, TagConstants.JSEA_ATTR_FORM_TYPE, getFormType());
		writeOptionalAttribute(tagWriter, TagConstants.JSEA_ATTR_FORM_OPTIONS, resolveJseaFormOptions());
		if (!Strings.isEmpty(getScript())) writeOptionalAttribute(tagWriter, TagConstants.JSEA_ATTR_SCRIPT, getScript());
		//for dynamic attributes
		this.writeOptionalAttributes(tagWriter);
	}

	/**
	 * Concrete subclass could implement this method to supply the corresponding JSEA option parameter
	 * in JSON format, that will be used in the JSEA javascript plugin. 
	 * @return
	 */
	protected abstract String resolveJseaFormOptions() throws JspException;

	/**
	 * Appends default options. Subclass must call this method in <code>resolveJseaFormOptions</code>
	 * @param jsob
	 */
	protected final void appendDefaultOptions(JseaOptionsBuilder jsob) throws JspException {
		String infobar = this.getInfobar();
		if (!Strings.isEmpty(infobar)) jsob.appendJseaOption(TagConstants.JSEA_OPTION_INFOBAR, infobar);
		String basename = this.getBasename();
		if (!Strings.isEmpty(basename)) jsob.appendJseaOption(TagConstants.JSEA_OPTION_BASENAME, basename);
		String funname = this.getFuncname();
		if (!Strings.isEmpty(funname)) jsob.appendJseaOption(TagConstants.JSEA_OPTION_FUNCNAME, funname);
		String keyProp = this.getKeyProp();
		if (!Strings.isEmpty(keyProp)) jsob.appendJseaOption(TagConstants.JSEA_OPTION_KEY_PROP, keyProp);
		String mode = this.getMode();
		if (!Strings.isEmpty(mode)) jsob.appendJseaOption(TagConstants.JSEA_OPTION_MODE, mode);
		// find a model object (the default rule is that funcname is just modelname)
		Object model  = this.pageContext.getRequest().getAttribute(this.getFuncname());
		if (model != null) jsob.appendJseaOption(TagConstants.JSEA_OPTION_MODEL, model);
		if (this.isBackable()) jsob.appendJseaOption(TagConstants.JSEA_OPTION_BACKABLE, this.isBackable());
		Object messages  = this.pageContext.getRequest().getAttribute(WeaConstants.REQUEST_ATTRNAME_MESSAGES);
		if (messages != null) {
			jsob.appendJseaOption("messages", messages);
		}
	}

	/**
	 * Subclass can override this method to append some other option for specific requirements.
	 * @param jsob
	 */
	protected abstract void appendExtraOptions(JseaOptionsBuilder jsob) throws JspException;

	/**
	 * Concrete subclass could implement this method to supply the corresponding form stylesheet
	 * @return
	 */
	protected abstract String resolveFormStylesheet();

}