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
import java.util.Map;

import javax.servlet.jsp.JspException;

import com.pyrube.one.app.cache.CacheManager;
import com.pyrube.one.app.i18n.format.FormatManager;
import com.pyrube.one.lang.Strings;
import com.pyrube.one.util.Currency;
import com.pyrube.wea.context.WebContextHolder;

/**
 * JSEA Amount-data element.
 * 
 * @author Aranjuez
 * @version Dec 01, 2009
 * @since Pyrube-WEA 1.0
 */
public class AmountpropTag extends TextpropTag {

	/**
	 * serial version uid
	 */
	private static final long serialVersionUID = 2880665940028848177L;

	@Override
	protected String resolveFormatPattern() throws JspException {
		String fmtName = this.getFormat();
		if (Strings.isEmpty(fmtName)) {
		} else {
			Integer scale = null;
			int idx = fmtName.indexOf("|");
			if (idx >= 0) {
				String ccyCode = fmtName.substring(idx + 1, fmtName.length());
				@SuppressWarnings("unchecked")
				Map<String, Currency> mapCurrencies = (Map<String, Currency>) CacheManager.applicationGet("mapCurrencies");
				if (mapCurrencies != null) {
					Currency currency = mapCurrencies.get(ccyCode);
					if (currency != null) scale = currency.getScale();
				}
				fmtName = fmtName.substring(0, idx);
			}
			fmtName = fmtName + (scale != null ? String.valueOf(scale) : Strings.EMPTY);
		}
		String localeCode = WebContextHolder.getWebContext().getLocale().toString();
		String fmtPattern = FormatManager.getFormatBuilder(localeCode, FormatManager.FORMAT_CATEGORY_NUMBER).whichPattern(fmtName);
		return Strings.isEmpty(fmtPattern) ? this.resolveFormat() : fmtPattern;
	}

	@Override
	protected String formatValue(String localeCode, String fmtNameOrPattern, Object unformated) throws JspException {
		if (unformated == null) return Strings.EMPTY;
		Format format = null;
		if (unformated instanceof Number) {
			format = FormatManager.numberFormatOf(localeCode, fmtNameOrPattern);
		}
		return (format == null) ? unformated.toString() : format.format(unformated);
	}
	
}
