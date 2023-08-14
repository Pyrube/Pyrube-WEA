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

package com.pyrube.wea.ui.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pyrube.one.app.Apps;
import com.pyrube.one.app.i18n.I18nConfig;
import com.pyrube.one.app.i18n.I18nManager;
import com.pyrube.one.app.inquiry.SearchCriteria;
import com.pyrube.one.app.logging.Logger;
import com.pyrube.one.app.memo.Note;
import com.pyrube.one.app.security.SecurityManagerFactory;
import com.pyrube.one.app.user.UserHolder;
import com.pyrube.one.util.Currency;
import com.pyrube.one.util.Option;
import com.pyrube.wea.context.WebContextHolder;

/**
 * JSEA initialization
 * 
 * @author Aranjuez
 * @version Dec 01, 2009
 * @since Pyrube-WEA 1.0
 */
@Controller
@RequestMapping("jsea")
public class JseaController {
	
	/**
	 * logger
	 */
	private static Logger logger = Logger.getInstance(JseaController.class.getName());

	@ResponseBody
	@RequestMapping("init")
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map<String, Object> init() {
		if (logger.isDebugEnabled()) {
			logger.debug("Initializing JSEA parameters...");
		}
		Map<String, Object> initParams = new ConcurrentHashMap<String, Object>();
		
		I18nConfig i18nCfg = I18nConfig.getI18nConfig();
		Locale locale = WebContextHolder.getWebContext().getLocale();
		// Resolve i18n messages
		ResourceBundle rb = I18nManager.getResourceBundle(locale);
		if (rb != null) {
			Map<String, String> i18nMessages = new ConcurrentHashMap<String, String>();
			Enumeration<String> keys = rb.getKeys();
			while (keys.hasMoreElements()) {
				String key = keys.nextElement();
				String value = rb.getString(key);
				i18nMessages.put(key, value);
			}
			initParams.put("i18nMessages", i18nMessages);
		}
		// Resolve application properties
		Map<String, ?> mapAppProperties = Apps.config.properties();
		initParams.put("APP_PROPERTIES", mapAppProperties);
		// Resolve currencies
		HashMap<String, Currency> mapCurrencies = (HashMap<String, Currency>) Apps.some.objects.cached("mapCurrencies");
		initParams.put("CURRENCIES", mapCurrencies);
		// Resolve formats
		Map localeFormatPatters = (Map) i18nCfg.getFormatPatternParams().get(locale.toString());
		if (localeFormatPatters == null) {
			localeFormatPatters = (Map) i18nCfg.getFormatPatternParams().get(i18nCfg.getSysFormatLocaleCode());
		}
		if (localeFormatPatters != null) {
			Map<String, String> mapFormats = new ConcurrentHashMap<String, String>();
			Iterator<String> it = ((Set<String>) localeFormatPatters.keySet()).iterator();
			while (it.hasNext()) {
				mapFormats.putAll((Map) localeFormatPatters.get(it.next()));
			}
			initParams.put("FORMATS", mapFormats);
		}
		// Resolve options in common
		Map<String, ArrayList<Option>> mapOptions = new ConcurrentHashMap<String, ArrayList<Option>>();
		ArrayList<Option> listCurrencies = (ArrayList<Option>) Apps.some.objects.cached("listCurrencies");
		mapOptions.put("CURRENCIES", listCurrencies);
		
		initParams.put("OPTIONS", mapOptions);
		
		return initParams;
	}
	
	@ResponseBody
	@RequestMapping("holiday/{year}")
	public List<Date> holidaysIn(@PathVariable("year") int year) {
		String countryCode = UserHolder.getUser().getCountry();
		List<Date> holidays = SecurityManagerFactory.getSecurityManager().findHolidays(countryCode, year);
		return holidays;
	}

	@RequestMapping("notes")
	public String openNotesPad(@ModelAttribute Note note, Model model) {
		model.addAttribute("note", note);
		return "memo.notes_pad";
	}

	@ResponseBody
	@RequestMapping("note/list")
	public SearchCriteria<Note> searchNotes(@RequestBody SearchCriteria<Note> searchCriteria) {
		searchCriteria.setSortBy("noteTime");
		searchCriteria.setSortDir(SearchCriteria.ASC);
		List<Note> notes = SecurityManagerFactory.getSecurityManager().findNotes(searchCriteria);
		searchCriteria.setResults(notes);
		return searchCriteria;
	}

	@ResponseBody
	@RequestMapping(value = "note/leave/save")
	public Note leaveSave(@ModelAttribute Note model) {
		model.setEventCode(Apps.event.manual.NOTE);
		Note note = SecurityManagerFactory.getSecurityManager().leaveNote(model);
		return note;
	}
	
	@RequestMapping("comments")
	public String comments() {
		return "memo.comments_box";
	}
}
