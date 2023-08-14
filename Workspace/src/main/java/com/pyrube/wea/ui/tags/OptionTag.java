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
import javax.servlet.jsp.tagext.BodyTagSupport;

/**
 * The <code>wone:option</code> tag is used to support options 
 * inside a <code>SelefieldTag</code> tag.
 *<p>This tag must be nested under an option aware tag.
 *
 * @author Aranjuez
 * @version Dec 01, 2009
 * @since Pyrube-WEA 1.0
 */
public class OptionTag extends BodyTagSupport {
	
	/**
	 * serial version uid
	 */
	private static final long serialVersionUID = 6847654506036624691L;
	
	/**
	 *  value attribute
	 */
	private String value;

	/**
	 * label attribute
	 */	
	private String label;

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

	@Override
	public int doStartTag() throws JspException {
		return EVAL_BODY_INCLUDE;
	}

	@Override
	public int doEndTag() throws JspException {
		Option option = new Option(value, label);
		// find a param aware ancestor
		OptionAware optionAwareTag = (OptionAware) findAncestorWithClass(this,
				OptionAware.class);
		if (optionAwareTag == null) {
			throw new JspException(
					"The Option tag must be a descendant of a tag that supports Option");
		}
		optionAwareTag.addOption(option);
		return EVAL_PAGE;
	}

	/**
	 * An option.
	 * 
	 * @author Aranjuez
	 * @version Dec 01, 2009
	 * @since Pyrube-WEA 1.0
	 */
	public static class Option {
		
		/**
		 * value
		 */
		private String value;

		/**
		 * label
		 */
		private String label;
		
		/**
		 * Constructor 
		 * 
		 * @param value
		 * @param label
		 */
		public Option(String value, String label) {
			this.value = value;
			this.label = label;
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
		
	}
	
}
