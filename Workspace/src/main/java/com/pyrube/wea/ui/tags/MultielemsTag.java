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

import org.springframework.util.ObjectUtils;
import org.springframework.web.servlet.tags.form.TagWriter;

import com.pyrube.one.lang.Strings;

/**
 * The <code>MultielemsTag</code> is used for multiple elements as a container
 *
 * @author Aranjuez
 * @version Dec 01, 2009
 * @since Pyrube-WEA 1.0
 */
public abstract class MultielemsTag extends JseaElementSupportTag {
	
	/**
	 * serial version uid
	 */
	private static final long serialVersionUID = 5661299442957911303L;

	@Override
	protected String getCssClass() {
		StringBuffer buf = new StringBuffer(getDefaultCssClass());
		String cssClass = super.getCssClass();
		if (!Strings.isEmpty(cssClass)) {
			buf.append(" " + cssClass);
		}
		if (this.isRequired()) {
			buf.append(" required");
		}
		return buf.toString();
	}

	@Override
	protected int writeTagContent(TagWriter tagWriter) throws JspException {
		this.setTagWriter(tagWriter);
		//write multi-elements wrapper
		this.writeWrapper(tagWriter);
		//write label
		this.writeLabel(tagWriter);
		
		return EVAL_BODY_INCLUDE;
	}
	
	@Override
	public int doEndTag() throws JspException {
		getTagWriter().endTag();
		return super.doEndTag();
	}
	
	@Override
	protected String resolveCssClass() throws JspException {
		return ObjectUtils.getDisplayString(evaluate("cssClass", getCssClass()));
	}

	/**
	 * writeCellWrapping, subclass can override it for special need.
	 * @throws JspException
	 */
	@Override
	protected void writeWrapper(TagWriter tagWriter) throws JspException {
		tagWriter.startTag("li");
		writeDefaultAttributes(tagWriter);
		tagWriter.forceBlock();
	}

	@Override
	protected String resolveJseaOptions() {
		return null;
	}

	/**
	 * The default CSS class to use for the HTML element.
	 */
	protected abstract String getDefaultCssClass();
}
