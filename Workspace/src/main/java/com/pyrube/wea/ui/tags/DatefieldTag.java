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
import java.util.UUID;

import javax.servlet.jsp.JspException;

import org.springframework.web.servlet.tags.form.TagWriter;

import com.pyrube.one.app.i18n.format.FormatManager;
import com.pyrube.one.lang.Strings;
import com.pyrube.wea.context.WebContextHolder;

/**
 * JSEA Date-field element.
 * 
 * @author Aranjuez
 * @version Dec 01, 2009
 * @since Pyrube-WEA 1.0
 */
public class DatefieldTag extends TextfieldTag {

	/**
	 * serial version uid
	 */
	private static final long serialVersionUID = 412830277763523058L;

	private static final String DATEFIELD_TRIGGER_PREFIX = "DATEFIELD-TRIGGER-";
	
	private boolean holidayPickable = true;
	
	private boolean timepicker;
	
	private int step = 5;
	
	private String onPick;
	
	/**
	 * Whether convert datetime (in GMT:00) to local timestamp.
	 */
	protected boolean local = false;
	
	/**
	 * random trigger id
	 */
	private String triggerId;

	/**
	 * @return the jseaAttrOptions
	 */
	@Override
	public String getJseaAttrOptions() {
		return TagConstants.JSEA_ATTR_DATEFIELD_OPTIONS;
	}
	
	@Override
	public String getType() { return "date"; }
	
	/**
	 * @return the holidayPickable
	 */
	public boolean isHolidayPickable() {
		return holidayPickable;
	}

	/**
	 * @param holidayPickable the holidayPickable to set
	 */
	public void setHolidayPickable(boolean holidayPickable) {
		this.holidayPickable = holidayPickable;
	}

	/**
	 * @return the timepicker
	 */
	public boolean isTimepicker() {
		return timepicker;
	}

	/**
	 * @param timepicker the timepicker to set
	 */
	public void setTimepicker(boolean timepicker) {
		this.timepicker = timepicker;
	}

	/**
	 * @return the step
	 */
	public int getStep() {
		return step;
	}

	/**
	 * @param step the step to set
	 */
	public void setStep(int step) {
		this.step = step;
	}

	/**
	 * @return the onPick
	 */
	public String getOnPick() {
		return onPick;
	}

	/**
	 * @param onPick the onPick to set
	 */
	public void setOnPick(String onPick) {
		this.onPick = onPick;
	}

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
	
	/**
	 * @return the triggerId
	 */
	public String getTriggerId() {
		return triggerId;
	}

	/**
	 * @param triggerId the triggerId to set
	 */
	public void setTriggerId(String triggerId) {
		this.triggerId = triggerId;
	}
	
	@Override
	protected String getDefaultCssClass() throws JspException { return !this.isTimepicker() ? "date" : "timestamp"; }

	/**
	 * Append options parameter for calendar javascript plugin using.
	 * @return
	 * @throws JspException 
	 */
	@Override
	protected void appendExtraOptions(JseaOptionsBuilder jsob) throws JspException {
		this.setTriggerId(DATEFIELD_TRIGGER_PREFIX + UUID.randomUUID().toString());
		jsob.appendJseaOption("holidayPickable", holidayPickable)
			.appendJseaOption("onPick", onPick, JseaOptionsBuilder.JSEA_OPTION_TYPE_FUNCTION);
		//build xdsoft options
		JseaOptionsBuilder xdsoftBuilder = JseaOptionsBuilder.newBuilder().setRenderingWithBraces(true);
		String formatPattern = this.resolveFormatPattern();
		if (!Strings.isEmpty(formatPattern)) {
			xdsoftBuilder.appendJseaOption("format", this.convert2XdsoftPattern(formatPattern));
		}
		xdsoftBuilder.appendJseaOption("timepicker", timepicker);
		if (timepicker) xdsoftBuilder.appendJseaOption("step", step);
		jsob.appendJseaOption("xdsoft", xdsoftBuilder.toString(), JseaOptionsBuilder.JSEA_OPTION_TYPE_OBJECT)
			.appendJseaOption("triggerId", this.getTriggerId());
	}

	@Override
	protected void appendExtraValidRules(JseaOptionsBuilder jsob) throws JspException {
		super.appendExtraValidRules(jsob);
		jsob.appendJseaOption(TagConstants.JSEA_OPTION_FORMAT, this.resolveFormat());
	}

	@Override
	protected void writeExtraTagContent(TagWriter tagWriter) throws JspException {
		tagWriter.startTag(TRIGGER_TAG);
		this.writeOptionalAttribute(tagWriter, TagConstants.HTML_ATTR_ID, this.triggerId);
		this.writeOptionalAttribute(tagWriter, TagConstants.HTML_ATTR_HREF, "javascript:void(0);");
		if (!this.isTimepicker()) {
			this.writeOptionalAttribute(tagWriter, TagConstants.HTML_ATTR_CLASS, "calendar");
		} else {
			this.writeOptionalAttribute(tagWriter, TagConstants.HTML_ATTR_CLASS, "calendar clock");
		}
		tagWriter.endTag(true);
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
	
	/**
	 * Convert jdk's date format ('yyyyMMdd') to xdsoft support date format('Ymd').
	 * @param pattern
	 * @return
	 */
	private String convert2XdsoftPattern(String pattern) {
		pattern = pattern.replaceAll("y+", "Y");
		pattern = pattern.replaceAll("d+", "d");
		pattern = pattern.replaceAll("H+", "H");
		pattern = pattern.replaceAll("m+", "i");
		pattern = pattern.replaceAll("s+", "s");
		pattern = pattern.replaceAll("S+", "");
		pattern = pattern.replaceAll("Z+", "sO");
		pattern = pattern.replaceAll("M+", "m");
		return pattern;
	}
}
