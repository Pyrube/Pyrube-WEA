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

/**
 * The <code>seaco:option</code> tag is used to support operations 
 * inside a <code>ColumnTag</code> tag.
 * <p>This tag must be nested under an operation aware tag.
 * <p>As of the release Pyrube-WEA 1.1, revised superclass to
 * <code>JseaElementSupportTag</code>
 *
 * @author Aranjuez
 * @version Dec 01, 2009
 * @since Pyrube-WEA 1.0
 */
public class OperationTag extends JseaActionElementTag {
	
	/**
	 * serial version uid
	 */
	private static final long serialVersionUID = 8796434762753623023L;

	private boolean permanent;

	/**
	 * @return the permanent
	 */
	public boolean isPermanent() {
		return permanent;
	}

	/**
	 * @param permanent the permanent to set
	 */
	public void setPermanent(boolean permanent) {
		this.permanent = permanent;
	}

	@Override
	protected String resolveElementTag() throws JspException { return null; }

	@Override
	protected String resolveDefaultText() throws JspException { return null; }

	@Override
	protected String resolveDefaultTitle() throws JspException { return null; }

	@Override
	protected int writeTagContent(TagWriter tagWriter) throws JspException { return SKIP_BODY; }

	@Override
	public int doEndTag() throws JspException {
		if (this.hasAccess(getAccess())) {
			Operation operation = new Operation(getName());
			operation.setJseaOptions(this.resolveJseaOptions(true));
			// find a parameter aware ancestor
			OperationAware operationAwareTag = (OperationAware) findAncestorWithClass(this,
					OperationAware.class);
			if (operationAwareTag == null) {
				throw new JspException(
						"The Operation tag must be a descendant of a tag that supports Operation");
			}
			operationAwareTag.addOperation(operation);
		}
		return EVAL_PAGE;
	}

	@Override
	protected void appendJseaOptions(JseaOptionsBuilder jsob) throws JspException {
		super.appendJseaOptions(jsob);
		if (isPermanent()) jsob.appendJseaOption(TagConstants.JSEA_OPTION_PERMANENT, isPermanent());
	}

	/**
	 * An operation class.
	 * 
	 * @author Aranjuez
	 * @version Dec 01, 2009
	 * @since Pyrube-WEA 1.0
	 */
	public static class Operation {

		private String name;

		private String jseaOptions;

		public Operation(String name) {
			this.name = name;
		}
		
		/**
		 * @return the name
		 */
		public String getName() {
			return name;
		}

		/**
		 * @param name the name to set
		 */
		public void setName(String name) {
			this.name = name;
		}

		/**
		 * @return the jseaOptions
		 */
		public String getJseaOptions() {
			return jseaOptions;
		}

		/**
		 * @param jseaOptions the jseaOptions to set
		 */
		public void setJseaOptions(String jseaOptions) {
			this.jseaOptions = jseaOptions;
		}

		@Override
		public boolean equals(Object obj) {
			if (obj == this) {
				return true;
			}
			if (!(obj instanceof Operation)) {
				return false;
			}
			Operation otherOperation = (Operation)obj;
			return ObjectUtils.nullSafeEquals(this.getName(), otherOperation.getName());
		}

		@Override
		public int hashCode() {
			int result = 37;
			if (this.name != null) {
				result += this.name.hashCode();
			}
			return result;
		}
		
	}
}
