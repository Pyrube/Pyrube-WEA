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

import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.TagSupport;

import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.tags.ArgumentAware;
import org.springframework.web.util.HtmlUtils;

import com.pyrube.one.app.logging.Logger;
import com.pyrube.wea.context.WebContextHolder;
import com.pyrube.wea.util.Weas;

/**
 * The <code>MessageTag</code> looks up a message in the scope of this page.
 * Messages are resolved using the ApplicationContext and thus support
 * internationalization.
 * 
 * @author Aranjuez
 * @version Dec 01, 2009
 * @since Pyrube-WEA 1.0
 */
public class MessageTag extends TagSupport implements ArgumentAware{

	/**
	 * serial version uid
	 */
	static private final long serialVersionUID = 1L;
	
	/**
	 * the logger
	 */
	private static Logger logger = Logger.getInstance(MessageTag.class.getName());
	
	/**
	 * the message code in the i18n resources
	 */
	protected String code = null;
	
	/**
	 * arguments for the message code
	 */
	private Object arguments;
	
	/**
	 * Default separator for splitting an arguments String: a comma (",")
	 */
	public static final String DEFAULT_ARGUMENT_SEPARATOR = ",";
	
	/**
	 * argument separator for splitting an arguments String
	 */
	private String argumentSeparator = DEFAULT_ARGUMENT_SEPARATOR;
	
	/**
	 * whether it is HTML-escaping
	 */
	private Boolean htmlEscaping;
	
	/**
	 * 
	 */
	private List<Object> nestedArguments;
	
	/**
	 * constructor
	 */
	public MessageTag() {
		super();
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the arguments
	 */
	public Object getArguments() {
		return arguments;
	}

	/**
	 * @param arguments the arguments to set
	 */
	public void setArguments(Object arguments) {
		this.arguments = arguments;
	}

	/**
	 * @return the argumentSeparator
	 */
	public String getArgumentSeparator() {
		return argumentSeparator;
	}

	/**
	 * @param argumentSeparator the argumentSeparator to set
	 */
	public void setArgumentSeparator(String argumentSeparator) {
		this.argumentSeparator = argumentSeparator;
	}

	/**
	 * Return the HTML-escaping setting for this tag,
	 * or the default setting if not overridden.
	 * @return the htmlEscaping
	 */
	public Boolean isHtmlEscaping() {
		if (this.htmlEscaping != null) {
			return this.htmlEscaping.booleanValue();
		}
		else {
			return WebContextHolder.getWebContext().isDefaultHtmlEscaping();
		}
	}

	/**
	 * @param htmlEscaping the htmlEscaping to set
	 */
	public void setHtmlEscaping(Boolean htmlEscaping) {
		this.htmlEscaping = htmlEscaping;
	}

	/**
	 * @return the nestedArguments
	 */
	public List<Object> getNestedArguments() {
		return nestedArguments;
	}

	/**
	 * @param nestedArguments the nestedArguments to set
	 */
	public void setNestedArguments(List<Object> nestedArguments) {
		this.nestedArguments = nestedArguments;
	}

	/**
	 * doStartTag
	 */
	public int doStartTag() throws JspException {
		// check attributes
		if (code == null) {
			logger.error("Attribute (code) is not set.");
			throw new JspException("Attribute (code) is not set.");
		}
		this.nestedArguments = new LinkedList<Object>();
		return(SKIP_BODY);
	}

	/**
	 * Resolves the message, escapes it,
	 * and writes it to the page (or exposes it as variable).
	 * 
	 */
	@Override
	public int doEndTag() throws JspException {
		try {
			// Localize the unescaped message.
			String message = localizeMessage();

			// HTML escaping
			message = htmlEscape(message);

			this.pageContext.getOut().write(message);

			return EVAL_PAGE;
		} catch (IOException ex) {
			throw new JspTagException(ex.getMessage(), ex);
		}
	}

	/**
	 * Release all allocated resources.
	 */
	public void release() {
		super.release();
		code = null;
		arguments = null;
	}
	
	@Override
	public void addArgument(Object argument)
		throws JspTagException {
		this.nestedArguments.add(argument);
	}

	/**
	 * localize message for a given code
	 * 
	 * @return
	 */
	protected String localizeMessage() throws JspException{
		Object[] argumentsArray = resolveArguments(this.arguments);
		if (!this.nestedArguments.isEmpty()) {
			argumentsArray = appendArguments(argumentsArray,
					this.nestedArguments.toArray());
		}
		return Weas.localizeMessage(code, argumentsArray);
	}

	/**
	 * Resolve the given arguments Object into an arguments array.
	 * @param arguments the specified arguments Object
	 * @return the resolved arguments as array
	 * @throws JspException if argument conversion failed
	 * @see #setArguments
	 */
	protected Object[] resolveArguments(Object arguments) throws JspException {
		if (arguments instanceof String) {
			String[] stringArray =
					StringUtils.delimitedListToStringArray((String) arguments, this.argumentSeparator);
			if (stringArray.length == 1) {
				Object argument = stringArray[0];
				if (argument != null && argument.getClass().isArray()) {
					return ObjectUtils.toObjectArray(argument);
				} else {
					return new Object[] {argument};
				}
			} else {
				return stringArray;
			}
		} else if (arguments instanceof Object[]) {
			return (Object[]) arguments;
		} else if (arguments instanceof Collection) {
			return ((Collection<?>) arguments).toArray();
		} else if (arguments != null) {
			// Assume a single argument object.
			return new Object[] {arguments};
		} else {
			return null;
		}
	}
	
	/**
	 * HTML-encodes the given String, only if the "htmlEscape" setting is enabled.
	 * @param unescaped
	 * @return
	 */
	protected String htmlEscape(String unescaped) {
		String s = unescaped;
		if (isHtmlEscaping()) {
				s = HtmlUtils.htmlEscape(unescaped, this.pageContext.getResponse().getCharacterEncoding());
		}
		return s;
	}
	
	/**
	 * append arguments
	 * @param sourceArguments
	 * @param additionalArguments
	 * @return
	 */
	private Object[] appendArguments(Object[] sourceArguments, Object[] additionalArguments) {
		if (ObjectUtils.isEmpty(sourceArguments)) {
			return additionalArguments;
		}
		Object[] arguments = new Object[sourceArguments.length + additionalArguments.length];
		System.arraycopy(sourceArguments, 0, arguments, 0, sourceArguments.length);
		System.arraycopy(additionalArguments, 0, arguments, sourceArguments.length, additionalArguments.length);
		return arguments;
	}
}
