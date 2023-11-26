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

import com.pyrube.wea.ui.tags.StepTag.StepBody;

/**
 * Allows implementing tag to utilize nested <code>seaco:step</code> tags.
 * 
 * @author Aranjuez
 * @version Oct 01, 2023
 * @since Pyrube-WEA 1.1
 */
public interface StepBodyAware {

	/**
	 * Callback hook for nested <code>seaco:step</code> tags to append their 
	 * body to the parent tag.
	 * @param stepBody the result of the nested <code>seaco:step</code> tag
	 */
	public void addStepBody(StepBody stepBody);

	/**
	 * returns the number of nested <code>seaco:step</code> tags it contains
	 */
	public int size();

}
