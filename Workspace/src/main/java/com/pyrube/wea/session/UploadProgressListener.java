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

package com.pyrube.wea.session;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.ProgressListener;

import com.pyrube.one.app.Apps;
import com.pyrube.one.app.logging.Logger;
import com.pyrube.wea.WeaConstants;

/**
 * This listener is used to keep upload status information in session.
 * 
 * @author Aranjuez
 * @version Sep 20, 2023
 * @since Pyrube-WEA 1.1
 */
public class UploadProgressListener implements ProgressListener {
	/**
	 * logger
	 */
	private static Logger logger = Apps.a.logger.named(UploadProgressListener.class.getName());
	/**
	 * request
	 */
	private HttpServletRequest request = null;

	/**
	 * constructor
	 * @param request
	 */
	public UploadProgressListener(HttpServletRequest request) {
		this.request = request;
		HttpSession session = request.getSession();
		session.removeAttribute(WeaConstants.SESSION_ATTRNAME_UPLOAD_PROGRESS);
	}

	@Override
	public void update(long bytesRead, long contentLength, int itemsCount) {
		double readByte = bytesRead;
		double totalSize = contentLength;
		if (contentLength == -1) {
			if (logger.isDebugEnabled()) logger.debug("Item index[" + itemsCount + "] " + bytesRead + " bytes have been read.");
		} else {
			if (logger.isDebugEnabled()) logger.debug("Item index[" + itemsCount + "] " + bytesRead + " of " + contentLength + " bytes have been read.");
			String percent = String.valueOf(Math.round((readByte / totalSize) * 100));
			HttpSession session = request.getSession();
			session.setAttribute(WeaConstants.SESSION_ATTRNAME_UPLOAD_PROGRESS, percent);
		}
	}
}
