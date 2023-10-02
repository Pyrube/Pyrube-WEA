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

package com.pyrube.wea.ui.resolvers.core;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.pyrube.wea.session.UploadProgressListener;

/**
 * WEA multipart resolver
 * <pre>
 * configure it in spring servlet context file:
 * 
 * <beans:bean id="multipartResolver" class="com.pyrube.wea.ui.resolvers.core.WeaMultipartResolver">
 *   <beans:property name="defaultEncoding" value="UTF-8" />
 *   <beans:property name="maxUploadSize" value="#{1*1024*1024}" />
 *   <beans:property name="maxInMemorySize" value="#{1*1024*1024}" />
 *   <beans:property name="uploadTempDir" value="temp/upload" />
 * </beans:bean>
 * </pre>
 * 
 * @author Aranjuez
 * @version Sep 20, 2023
 * @since Pyrube-WEA 1.1
 */
public class WeaMultipartResolver extends CommonsMultipartResolver {
	/**
	 * request
	 */
	private HttpServletRequest request;

	@Override
	protected FileUpload newFileUpload(FileItemFactory fileItemFactory) {
		ServletFileUpload upload = new ServletFileUpload(fileItemFactory);
		upload.setSizeMax(-1);
		if (this.request != null) {
			UploadProgressListener listener = new UploadProgressListener(this.request);
			upload.setProgressListener(listener);
		}
		return upload;
	}

	@Override
	public MultipartHttpServletRequest resolveMultipart(HttpServletRequest request)
		throws MultipartException {
		this.request = request;
		return super.resolveMultipart(request);
	}

	@Override
	public MultipartParsingResult parseRequest(HttpServletRequest request)
		throws MultipartException {
		String encoding = determineEncoding(this.request);
		FileUpload fileUpload = prepareFileUpload(encoding);
		UploadProgressListener listener = new UploadProgressListener(this.request);
		fileUpload.setProgressListener(listener);
		try {
			List<FileItem> fileItems = ((ServletFileUpload) fileUpload).parseRequest(this.request);
			return parseFileItems(fileItems, encoding);
		} catch (FileUploadBase.SizeLimitExceededException ex) {
			throw new MaxUploadSizeExceededException(fileUpload.getSizeMax(), ex);
		} catch (FileUploadException ex) {
			throw new MultipartException("Could not parse multipart servlet request", ex);
		}
	}
}
