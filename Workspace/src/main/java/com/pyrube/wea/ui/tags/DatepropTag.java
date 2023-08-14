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

import java.text.Format;
import java.util.Date;
import java.util.TimeZone;

import javax.servlet.jsp.JspException;

import com.pyrube.one.app.i18n.format.FormatManager;
import com.pyrube.one.lang.Strings;
import com.pyrube.wea.context.WebContextHolder;

/**
 * JSEA Date-data element.
 * 
 * @author Aranjuez
 * @version Dec 01, 2009
 * @since Pyrube-WEA 1.0
 */
public class DatepropTag extends TextpropTag {

	/**
	 * serial version uid
	 */
	private static final long serialVersionUID = 5162150178655959706L;
	
	/**
	 * Whether convert datetime (in GMT:00) to local timestamp.
	 */
	protected boolean local = false;

	/**
	 * @return the local
	 */
	public boolean isLocal() {
		return local;
	}

	/**
	 * @param local the local to set
	 */
	public void setLocal(boolean local) {
		this.local = local;
	}
	
	@Override
	protected String resolveFormatPattern() throws JspException {
		String fmtName = this.getFormat();
		String localeCode = WebContextHolder.getWebContext().getLocale().toString();
		String fmtPattern = FormatManager.getFormatBuilder(localeCode, FormatManager.FORMAT_CATEGORY_DATE).whichPattern(fmtName);
		return Strings.isEmpty(fmtPattern) ? this.resolveFormat() : fmtPattern;
	}

	@Override
	protected String formatValue(String localeCode, String fmtNameOrPattern, Object unformated) throws JspException {
		if (unformated == null) return Strings.EMPTY;
		Format format = null;
		if (unformated instanceof Date) {
			TimeZone localTimeZone = null;
			if (local) {
				localTimeZone = WebContextHolder.getWebContext().getTimezone();
			}
			format = FormatManager.dateFormatOf(localeCode, fmtNameOrPattern, localTimeZone);
		}
		return (format == null) ? unformated.toString() : format.format(unformated);
	}

}