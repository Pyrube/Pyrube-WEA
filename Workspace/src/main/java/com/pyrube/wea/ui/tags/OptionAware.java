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

import com.pyrube.wea.ui.tags.OptionTag.Option;

/**
 * Allows implementing tag to utilize nested <code>seael:option</code> tags.
 * 
 * @author Aranjuez
 * @version Dec 01, 2009
 * @since Pyrube-WEA 1.0
 */
public interface OptionAware {	
	
	/**
	 * Callback hook for nested seael:option tags to pass them {value, label}
	 * to the parent tag.
	 * @param option the result of the nested <code>seael:option</code> tag
	 */
	public void addOption(Option option);

}
