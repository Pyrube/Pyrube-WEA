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

import org.springframework.util.ObjectUtils;

import com.pyrube.one.lang.Strings;
import com.pyrube.wea.util.Weas;

/**
 * The <code>seaco:option</code> tag is used to support operations 
 * inside a <code>ColumnTag</code> tag.
 *<p>This tag must be nested under an operation aware tag.
 *
 * @author Aranjuez
 * @version Dec 01, 2009
 * @since Pyrube-WEA 1.0
 */
public class OperationTag extends BodyTagSupport  {
	
	/**
	 * serial version uid
	 */
	private static final long serialVersionUID = 8796434762753623023L;

	private String name;
	
	private String url;
	
	private String mode;
	
	private String confirm;
	
	private String access;
	
	private String inactive;
	
	private String hidden;
	
	private String invisible;
	
	private String gone;
	
	private String permanent;
	
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
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return the mode
	 */
	public String getMode() {
		return mode;
	}

	/**
	 * @param mode the mode to set
	 */
	public void setMode(String mode) {
		this.mode = mode;
	}

	/**
	 * @return the confirm
	 */
	public String getConfirm() {
		return confirm;
	}

	/**
	 * @param confirm the confirm to set
	 */
	public void setConfirm(String confirm) {
		this.confirm = confirm;
	}

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

	/**
	 * @return the permanent
	 */
	public String getPermanent() {
		return permanent;
	}

	/**
	 * @param permanent the permanent to set
	 */
	public void setPermanent(String permanent) {
		this.permanent = permanent;
	}

	@Override
	public int doStartTag() throws JspException {
		return EVAL_BODY_INCLUDE;
	}

	@Override
	public int doEndTag() throws JspException {
		Operation operation = new Operation(name);
		operation.setUrl(url);
		operation.setMode(mode);
		operation.setConfirm(confirm);
		operation.setAccess(access);
		operation.setInactive(inactive);
		operation.setHidden(hidden);
		operation.setInvisible(invisible);
		operation.setGone(gone);
		operation.setPermanent(permanent);
		// find a parameter aware ancestor
		OperationAware operationAwareTag = (OperationAware) findAncestorWithClass(this,
				OperationAware.class);
		if (operationAwareTag == null) {
			throw new JspException(
					"The Operation tag must be a descendant of a tag that supports Operation");
		}
		if(this.hasAccess(access)){
			operationAwareTag.addOperation(operation);
		}
		return EVAL_PAGE;
	}

	@Override
	public void release() {
		super.release();
		this.name = null;
	}

	/**
	 * check whether user has the given access
	 * @param accessExpression
	 * @return true if user has access
	 */
	private boolean hasAccess(String accessExpression) {
		try {
			if (Strings.isEmpty(accessExpression)) return(true);
			return (Weas.evaluateUserAccessExpression(accessExpression));
		} catch (Exception e) {
			return(false);
		}
	}

	public static class Operation {

		private String name;
		
		private String url;
		
		private String mode;
		
		private String confirm;
		
		private String access;
		
		private String inactive;
		
		private String hidden;
		
		private String invisible;
		
		private String gone;
		
		private String permanent;
		
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
		 * @return the url
		 */
		public String getUrl() {
			return url;
		}

		/**
		 * @param url the url to set
		 */
		public void setUrl(String url) {
			this.url = url;
		}

		/**
		 * @return the mode
		 */
		public String getMode() {
			return mode;
		}

		/**
		 * @param mode the mode to set
		 */
		public void setMode(String mode) {
			this.mode = mode;
		}

		/**
		 * @return the confirm
		 */
		public String getConfirm() {
			return confirm;
		}

		/**
		 * @param confirm the confirm to set
		 */
		public void setConfirm(String confirm) {
			this.confirm = confirm;
		}

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

		/**
		 * @return the permanent
		 */
		public String getPermanent() {
			return permanent;
		}

		/**
		 * @param permanent the permanent to set
		 */
		public void setPermanent(String permanent) {
			this.permanent = permanent;
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
		
		/**
		 * Returns a <code>JseaOptionsBuilder</code> representation of the object.
		 * @return
		 */
		public JseaOptionsBuilder toJsonBuilder() {
			JseaOptionsBuilder jsob = JseaOptionsBuilder.newBuilder().setRenderingWithBraces(true);
			jsob.appendJseaOption(TagConstants.JSEA_OPTION_URL, this.getUrl())
				.appendJseaOption(TagConstants.JSEA_OPTION_MODE, this.getMode());
			String confirm = this.getConfirm();
			if (Boolean.TRUE.toString().equalsIgnoreCase(confirm)) confirm = this.getName();
			else if (Boolean.FALSE.toString().equalsIgnoreCase(confirm)) confirm = null;
			jsob.appendJseaOption(TagConstants.JSEA_OPTION_CONFIRM, confirm);
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
			jsob.appendJseaOption(TagConstants.JSAF_OPTION_PERMANENT, this.getPermanent());
			return jsob;
		}
	} 
}
