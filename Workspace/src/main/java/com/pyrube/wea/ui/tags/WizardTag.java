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

import java.util.ArrayList;
import java.util.List;

import javax.servlet.jsp.JspException;

import org.springframework.web.servlet.tags.form.TagWriter;

import com.pyrube.wea.ui.tags.StepTag.StepBody;

/**
 * Renders an HTML 'div' tag for the JSEA wizard component.
 * 
 * @author Aranjuez
 * @version Oct 01, 2023
 * @since Pyrube-WEA 1.1
 */
public class WizardTag extends JseaElementSupportTag implements StepBodyAware {
	/**
	 * serial version uid
	 */
	private static final long serialVersionUID = 5665018447217253329L;
	
	private List<StepBody> stepBodies = new ArrayList<StepBody>();

	/**
	 * @return the jseaAttrOptions
	 */
	@Override
	public String getJseaAttrOptions() {
		return TagConstants.JSEA_ATTR_WIZARD_OPTIONS;
	}

	@Override
	protected int writeTagContent(TagWriter tagWriter) throws JspException {
		this.setTagWriter(tagWriter);
		tagWriter.startTag("div");
		writeDefaultAttributes(tagWriter);
		writeJseaOptionsAttribute(tagWriter);
		tagWriter.forceBlock();
		
		// write step index
		tagWriter.startTag("ul");
		writeOptionalAttribute(tagWriter, CLASS_ATTRIBUTE, "steps");
		tagWriter.forceBlock();
		
		return EVAL_BODY_INCLUDE;
	}
	
	@Override
	public int doEndTag() throws JspException {
		TagWriter tagWriter = getTagWriter();
		//end the ul tag
		tagWriter.endTag();
		
		// write step body
		for (StepBody stepBody : stepBodies) {
			tagWriter.startTag("div");
			//<div id="step1" class="step-body"></div>
			tagWriter.writeAttribute(TagConstants.HTML_ATTR_ID, stepBody.getId());
			tagWriter.writeAttribute(TagConstants.HTML_ATTR_CLASS, stepBody.getStylesheet());
			tagWriter.appendValue(stepBody.getContent());
			tagWriter.endTag();
		}
		
		//end div tag.
		tagWriter.endTag();
		return EVAL_PAGE;
	}
	
	@Override
	public void doFinally() {
		super.doFinally();
		this.stepBodies = new ArrayList<StepBody>();
	}

	@Override
	public void addStepBody(StepBody stepBody) { this.stepBodies.add(stepBody); }

	@Override
	public int size() { return this.stepBodies.size(); }

	@Override
	protected String getDefaultCssClass() throws JspException { return"wizard-container"; }
	
	@Override
	protected String resolveJseaOptions() { 
		JseaOptionsBuilder jsob = JseaOptionsBuilder.newBuilder().setRenderingWithBraces(true);
		return jsob.toString(); 
	}

}
