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
import javax.servlet.jsp.tagext.BodyTag;

import com.pyrube.one.lang.Strings;
import com.pyrube.wea.util.Weas;
/**
 * Superclass for tags that access controlling is provided with permission 
 * and user roles.
 * 
 * @author Aranjuez
 * @version Dec 01, 2009
 * @since Pyrube-WEA 1.0
 */
public abstract class AccessControllingTag extends JseaElementSupportTag implements BodyTag {
	
	/**
	 * serial version uid
	 */
	private static final long serialVersionUID = 9169246654428043810L;
	private String access;
	private String inactive;
	private String hidden;
	private String invisible;
	private String gone;

	/**
	 * @return the access
	 */
	public String getAccess() {
		return access;
	}

	/**
	 * @param access the access to set
	 */
	public void setAccess(String access) {
		this.access = access;
	}

	/**
	 * @return the inactive
	 */
	public String getInactive() {
		return inactive;
	}

	/**
	 * @param inactive the inactive to set
	 */
	public void setInactive(String inactive) {
		this.inactive = inactive;
	}

	/**
	 * @return the hidden
	 */
	public String getHidden() {
		return hidden;
	}

	/**
	 * @param hidden the hidden to set
	 */
	public void setHidden(String hidden) {
		this.hidden = hidden;
	}

	/**
	 * @return the invisible
	 */
	public String getInvisible() {
		return invisible;
	}

	/**
	 * @param invisible the invisible to set
	 */
	public void setInvisible(String invisible) {
		this.invisible = invisible;
	}

	/**
	 * @return the gone
	 */
	public String getGone() {
		return gone;
	}

	/**
	 * @param gone the gone to set
	 */
	public void setGone(String gone) {
		this.gone = gone;
	}

	@Override
	protected String resolveJseaOptions() throws JspException {
		JseaOptionsBuilder jsob = JseaOptionsBuilder.newBuilder();
		appendExtraOptions(jsob);
		String inactiveRules = this.getInactive();
		if (!Strings.isEmpty(inactiveRules)) {
			jsob.appendJseaOption(TagConstants.JSEA_OPTION_INACTIVE, "[" + inactiveRules + "]", JseaOptionsBuilder.JSEA_OPTION_TYPE_JS_OBJECT);
		}
		String hiddenRules = this.getHidden();
		if (!Strings.isEmpty(hiddenRules)) {
			jsob.appendJseaOption(TagConstants.JSEA_OPTION_HIDDEN, "[" + hiddenRules + "]", JseaOptionsBuilder.JSEA_OPTION_TYPE_JS_OBJECT);
		}
		String invisibleRules = this.getInvisible();
		if (!Strings.isEmpty(invisibleRules)) {
			jsob.appendJseaOption(TagConstants.JSEA_OPTION_INVISIBLE, "[" + invisibleRules + "]", JseaOptionsBuilder.JSEA_OPTION_TYPE_JS_OBJECT);
		}
		String goneRules = this.getGone();
		if (!Strings.isEmpty(goneRules)) {
			jsob.appendJseaOption(TagConstants.JSEA_OPTION_GONE, "[" + goneRules + "]", JseaOptionsBuilder.JSEA_OPTION_TYPE_JS_OBJECT);
		}
		return jsob.toString();
	}

	/**
	 * appends extra JSEA options
	 * @param paramJseaOptionsBuilder
	 * @throws JspException
	 */
	protected abstract void appendExtraOptions(JseaOptionsBuilder paramJseaOptionsBuilder) throws JspException;

	/**
	 * check access
	 * @param accessExpression
	 * @return true if has access
	 */
	protected boolean hasAccess(String accessExpression) {
		try {
			if (Strings.isEmpty(accessExpression)) return(true);
			return (Weas.evaluateUserAccessExpression(accessExpression));
		} catch (Exception e) {
			return(false);
		}
	}

}
