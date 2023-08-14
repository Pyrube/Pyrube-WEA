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
/**
 * Allows implementing tag to utilize nested <code>seael:checkbox</code> or 
 * <code>seael:radio</code> tags.
 * 
 * @author Aranjuez
 * @version Dec 01, 2009
 * @since Pyrube-WEA 1.0
 */
public interface JseaSingleCheckedFieldAware {
	
	/**
	 * return field name (checkbox/radio)
	 * @return
	 * @throws JspException
	 */
	public String getFieldName() throws JspException;
	
	/**
	 * return root path
	 * @return
	 * @throws JspException
	 */
	public String getRootPath() throws JspException;
	
	/**
	 * return i18n prefix for label (checkbox/radio)
	 * @return
	 * @throws JspException
	 */
	public String getLabelPrefix() throws JspException;
	
	/**
	 * whether the field (checkbox/radio) is checked
	 * @param value
	 * @return
	 * @throws JspException
	 */
	public boolean isFieldChecked(String value) throws JspException;
	
}
