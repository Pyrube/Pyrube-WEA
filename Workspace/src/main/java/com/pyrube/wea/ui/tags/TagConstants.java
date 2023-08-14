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

/**
 * The definition of constants pertaining to the WEA and JSEA tag library.
 * 
 * @author Aranjuez
 * @version Dec 01, 2009
 * @since Pyrube-WEA 1.0
 */
public interface TagConstants {
	/**
	 * HTML Attribute constants
	 */
	public static final String HTML_ATTR_ID = "id";
	public static final String HTML_ATTR_NAME = "name";
	public static final String HTML_ATTR_CLASS = "class";
	public static final String HTML_ATTR_TYPE = "type";
	public static final String HTML_ATTR_HREF = "href";
	public static final String HTML_ATTR_SRC = "src";
	public static final String HTML_ATTR_VALUE = "value";
	public static final String HTML_ATTR_SIZE = "size";
	public static final String HTML_ATTR_MAXLENGTH = "maxlength";
	public static final String HTML_ATTR_ALT = "alt";
	public static final String HTML_ATTR_PLACEHOLDER = "placeholder";
	public static final String HTML_ATTR_AUTOCAPITALIZE = "autocapitalize";
	public static final String HTML_ATTR_AUTOCOMPLETE = "autocomplete";
	public static final String HTML_ATTR_AUTOCORRECT = "autocorrect";
	/**
	 * HTML Event constants
	 */
	public static final String HTML_EVENT_ONSELECT = "onselect";
	/**
	 * JSEA Attribute constants
	 */
	public static final String JSEA_ATTR_DATA_VALUE = "data-value";
	public static final String JSEA_ATTR_FORMAT = "format";
	public static final String JSEA_ATTR_CCY_PROP = "ccyProp";
	public static final String JSEA_ATTR_BASENAME = "jsea-basename";
	public static final String JSEA_ATTR_FUNCNAME = "jsea-funcname";
	public static final String JSAF_ATTR_FIELD_TYPE = "jsea-field-type";
	public static final String JSEA_ATTR_FORM_TYPE = "jsea-form-type";
	public static final String JSAF_ATTR_COLUMN_OPERATIONS = "jsea-col-operations";
	public static final String JSEA_ATTR_VALID_TYPE = "jsea-valid-type";
	public static final String JSEA_ATTR_VALID_RULES = "jsea-valid-rules";
	public static final String JSEA_ATTR_SCRIPT = "jsea-script";
	/**
	 * JSEA Attribute constants for options
	 */
	public static final String JSEA_ATTR_FORM_OPTIONS = "jsea-form-options";
	public static final String JSEA_ATTR_GRID_OPTIONS = "jsea-grid-options";
	public static final String JSEA_ATTR_COLUMN_OPTIONS = "jsea-col-options";
	public static final String JSAF_OPTIONS_WIZARD = "jsea-wizard-options";
	public static final String JSAF_OPTIONS_STEP = "jsea-step-options";
	public static final String JSEA_ATTR_TABS_OPTIONS = "jsea-tabs-options";
	public static final String JSEA_ATTR_TAB_OPTIONS = "jsea-tab-options";
	public static final String JSEA_ATTR_TEXTFIELD_OPTIONS = "jsea-textfield-options";
	public static final String JSEA_ATTR_DATEFIELD_OPTIONS = "jsea-datefield-options";
	public static final String JSEA_ATTR_SELEFIELD_OPTIONS = "jsea-selefield-options";
	public static final String JSEA_ATTR_FALEFIELD_OPTIONS = "jsea-falefield-options";
	public static final String JSEA_ATTR_HIDFIELD_OPTIONS  = "jsea-hidfield-options";
	public static final String JSAF_OPTIONS_LOOKUP_FIELD = "jsea-lookup-options";
	public static final String JSEA_ATTR_UPLOAD_OPTIONS = "jsea-upload-options";
	public static final String JSEA_ATTR_CAPTCHA_OPTIONS = "jsea-captcha-options";
	public static final String JSEA_ATTR_CHECKBOXES_OPTIONS = "jsea-checkboxes-options";
	public static final String JSEA_ATTR_CHECKBOX_OPTIONS = "jsea-checkbox-options";
	public static final String JSEA_ATTR_PROPERTY_OPTIONS = "jsea-property-options";
	public static final String JSEA_ATTR_BUTTON_OPTIONS = "jsea-btn-options";
	public static final String JSEA_ATTR_LINK_OPTIONS = "jsea-lnk-options";
	/**
	 * JSEA Option Constants
	 */
	public static final String JSEA_OPTION_INFOBAR  = "infobar";
	public static final String JSEA_OPTION_BASENAME = "basename";
	public static final String JSEA_OPTION_FUNCNAME = "funcname";
	public static final String JSEA_OPTION_OPERATION = "operation";
	public static final String JSEA_OPTION_NAME = "name";
	public static final String JSEA_OPTION_RESULTSET = "rs";
	public static final String JSEA_OPTION_MODEL = "model";
	public static final String JSEA_OPTION_URL = "url";
	public static final String JSEA_OPTION_URL_PARAMS = "urlParams";
	public static final String JSEA_OPTION_MODE = "mode";
	public static final String JSEA_OPTION_SUCCESS = "success";
	public static final String JSEA_OPTION_TYPE = "type";
	public static final String JSAF_OPTION_PROPERTY = "property";
	public static final String JSAF_OPTION_DEFAULT_VALUE = "defaultValue";
	public static final String JSEA_OPTION_FORMAT = "format";
	public static final String JSEA_OPTION_LOCAL = "local";
	public static final String JSEA_OPTION_I18N_PREFIX = "i18nPrefix";
	public static final String JSEA_OPTION_I18N_KEY = "i18nKey";
	public static final String JSEA_OPTION_REQUIRED = "required";
	public static final String JSEA_OPTION_CONFIRM = "confirm";
	public static final String JSEA_OPTION_REASON = "reason";
	public static final String JSEA_OPTION_YESNO = "yesno";
	public static final String JSAF_OPTION_CALLBACK = "callback";
	public static final String JSEA_OPTION_DISABLED = "disabled";
	public static final String JSAF_OPTION_STYLESHEET = "stylesheet";
	public static final String JSAF_OPTION_RENDERS = "renders";
	public static final String JSEA_OPTION_HELP = "help";
	public static final String JSAF_OPTION_EVENT = "event";
	public static final String JSAF_OPTION_ACTIVE = "active";
	public static final String JSAF_OPTION_CURRENT = "current";
	public static final String JSAF_OPTION_DIALOG_TITLE = "dialogTitle";
	public static final String JSAF_OPTION_DIALOG_ARG = "dialogArg";
	public static final String JSEA_OPTION_RS_PROP = "rsProp";
	public static final String JSEA_OPTION_KEY_PROP = "keyProp";
	public static final String JSEA_OPTION_REF_PROP = "refProp";
	public static final String JSEA_OPTION_CCY_PROP = "ccyProp";
	public static final String JSEA_OPTION_TYPE_PROP = "typeProp";
	public static final String JSEA_OPTION_STAT_PROP = "statProp";
	public static final String JSEA_OPTION_FLAG_PROP = "flagProp";
	public static final String JSEA_OPTION_POST_PROPS = "postProps";
	public static final String JSAF_OPTION_RETURN_PROPS = "returnProps";
	public static final String JSEA_OPTION_PRE_HANDLER = "preHandler";
	public static final String JSEA_OPTION_PRE_HANDLERS = "preHandlers";
	public static final String JSEA_OPTION_POST_HANDLER = "postHandler";
	public static final String JSEA_OPTION_POST_HANDLERS = "postHandlers";
	public static final String JSEA_OPTION_FILTERABLE = "filterable";
	public static final String JSEA_OPTION_MULTIPLE = "multiple";
	public static final String JSEA_OPTION_PAGEABLE = "pageable";
	public static final String JSEA_OPTION_PAGE_SIZE = "pageSize";
	public static final String JSEA_OPTION_VALIDATABLE = "validatable";
	public static final String JSEA_OPTION_MODIFIABLE = "modifiable";
	public static final String JSEA_OPTION_BACKABLE = "backable";
	public static final String JSEA_OPTION_INACTIVE = "inactive";
	public static final String JSEA_OPTION_HIDDEN = "hidden";
	public static final String JSEA_OPTION_INVISIBLE = "invisible";
	public static final String JSEA_OPTION_GONE = "gone";
	public static final String JSEA_OPTION_SORTABLE = "sortable";
	public static final String JSAF_OPTION_AS_DEFAULT = "asDefault";
	public static final String JSAF_OPTION_ORDER = "order";
	public static final String JSAF_OPTION_COLLAPSIBLE = "collapsible";
	public static final String JSEA_OPTION_VISIBLE = "visible";
	public static final String JSAF_OPTION_EMPTYABLE = "emptyable";
	public static final String JSAF_OPTION_UPDATED = "updated";
	public static final String JSEA_OPTION_DORS = "dors";
	public static final String JSEA_OPTION_TOGGLEABLE = "toggleable";
	public static final String JSAF_OPTION_ASYNC = "async";
	public static final String JSAF_OPTION_PERMANENT = "permanent";
	
	/**
	 * JSEA Validation rule constants
	 */
	public static final String JSEA_VALID_RULE_NONNULL_COLS = "nonnullCols";
	public static final String JSEA_VALID_RULE_UNIQUE_INDEX = "uniqueIndex";
	public static final String JSEA_VALID_RULE_REQUIRED = "required";
	public static final String JSEA_VALID_RULE_MINLENGTH = "minLength";
	public static final String JSEA_VALID_RULE_MAXLENGTH = "maxLength";
	public static final String JSEA_VALID_RULE_MINVALUE = "minValue";
	public static final String JSEA_VALID_RULE_MAXVALUE = "maxValue";
	public static final String JSEA_VALID_RULE_EQUALTO = "equalTo";
	public static final String JSEA_VALID_RULE_REMOTE = "remote";
	/**
	 * JSEA Event constants
	 */
	public static final String JSAF_EVENT_ONSELECT = "onSelect";
	public static final String JSAF_EVENT_ONDESELECT = "onDeselect";
	public static final String JSAF_EVENT_ONWRITE = "onWrite";
	public static final String JSAF_EVENT_ONACTIVATE = "onActivate";
	public static final String JSAF_EVENT_ONCHOOSE = "onChoose";
	public static final String JSEA_EVENT_ONCHECK = "onCheck";
}
