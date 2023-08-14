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

package com.pyrube.wea;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.pyrube.one.app.AppException;
import com.pyrube.one.app.config.ConfigManager;
import com.pyrube.one.app.config.Configurator;
import com.pyrube.one.app.logging.Logger;
import com.pyrube.one.lang.Strings;

/**
 * WEA configurator <br>
 * <pre>
 * 		<WeaConfig>
 * 		   <!-- security management -->					
 * 			<security>
 * 				<!-- if required = true, then user must login first. otherwise user needs to login only when user tries to access a protected page. -->
 * 				<signon required="true" weaCookieName="weaid" weaCookiePath="/">
 * 					<signonListeners>
 * 						<listener class="com.pyrube.wea.sample.listener.SampleSignonListener"/>
 * 					</signonListeners>
 * 					<signoffListeners>
 * 						<listener class="com.pyrube.wea.sample.listener.SampleSignoffListener"/>
 * 					</signoffListeners>
 *					<localeCookie synchLocaleFromCookie="false">
 *						<name>wafLocale</name>
 *						<!-- default domain is current server name.  you may use the domain or sub-domain of the server.  if attribute useServerDomain = true, then use the domain of the server (for server host.dep.abc.com, domain is .dep.abc.com), or if attribute subDomainLevel(at least 2) is present, then use the sub-domain of the server on the given level (for server host.dep.abc.com, level 2 sub-domain is .abc.com). if both useServerDomain and subDomainLevel present, then use subDomainLevel. -->
 *						<domain useServerDomain="true" subDomainLevel="2"/>
 *						<!-- path supports variable $[APP_CONTEXT] -->
 *						<path>/</path>
 *						<!-- age in seconds. maxAge=-1 then set non-persistent cookie; maxAge=0 then delete cookie. -->
 *						<maxAge>2592000</maxAge>
 *					</localeCookie>
 * 					<cookies>
 * 						<cookie createOnSignon="true" deleteOnSignoff="true" encrypt="true">
 * 							<name>SSO-APP-SIGNOFF-WAF</name>
 * 							<!-- in cookie value you can use $[var] to refer predefined variables: SERVER_URL, SERVER_ROOT, SERVER_PROTOCOL, SERVER_HOST, SERVER_PORT, APP_CONTEXT, TIMESTAMP  -->
 * 							<value>$[SERVER_ROOT]/$[APP_CONTEXT]/signoff.do?reason=sso_signoff&amp;ts=$[TIMESTAMP]</value>
 * 							<!-- default domain is current server name.  you may use the domain or sub-domain of the server.  if attribute useServerDomain = true, then use the domain of the server (for server host.dep.abc.com, domain is .dep.abc.com), or if attribute subDomainLevel(at least 2) is present, then use the sub-domain of the server on the given level (for server host.dep.abc.com, level 2 sub-domain is .abc.com). if both useServerDomain and subDomainLevel present, then use subDomainLevel. -->
 * 							<domain useServerDomain="true" subDomainLevel="2"/>
 * 							<path>/</path>
 * 							<!-- maxAge=-1 then set non-persistent cookie. maxAge=0 then delete cookie. -->
 * 							<maxAge>-1</maxAge>
 * 						</cookie>
 * 					</cookies>
 * 				</signon>
 * 				<sso cookieName="wafsso">
 * 					<sessionTimeoutMinutes>60</sessionTimeoutMinutes>
 * 					<sessionTimeoutUrl>/jsp/sso_session_timeout.jsp</sessionTimeoutUrl>
 * 					<sessionErrorUrl>/jsp/sso_session_error.jsp</sessionErrorUrl>
 * 				</sso>
 * 				<captcha enabled="true">
 * 					<codeLength>4</codeLength>
 * 					<mimeType>image/png</mimeType>
 * 					<imageWidth>96</imageWidth>
 * 					<imageHeight>25</imageHeight>
 * 					<charWidth>16</charWidth>
 * 					<charPosY>21</charPosY>
 * 					<fontSize>23</fontSize>
 * 					<availableChars>ABCDEFGHIJKLMNOPQRSTUVWXYZ34678</availableChars>
 * 				</captcha>
 * 				<!-- Indicates to the browser whether cookies should only be sent using a secure protocol, such as HTTPS or SSL -->
 * 				<useSecureCookie>false</useSecureCookie>
 * 				<!-- Indicates to the browser whether cookies can only be set and retrieved by http server (browser can not view cookie using javascript) -->
 * 				<useHttpOnlyCookie>false</useHttpOnlyCookie>
 *              <!-- whether restrict http request http method -->
 *              <restrictHttpMethod>true</restrictHttpMethod>
 * 				<!-- Defense csrf attack -->
 *              <useCsrfValidate>false</useCsrfValidate>
 *              <csrfTokenName>_ct_</csrfTokenName>
 * 			</security>
 * 			<!-- web application filters -->
 * 			<filters>
 * 				<filter class="com.pyrube.wea.sample.filter.SampleFilter" enabled="false">
 * 					<param name="p1">v1</param>
 * 					<param name="disabledUrls">
 * 						<param name="url">/sample</param>
 * 						<param name="url">/sample1</param>
 * 					</param>
 * 				</filter>
 * 			</filters>
 * 			<pageCharEncoding>UTF-8</pageCharEncoding>
 * 			<defaultHtmlEscaping>true</defaultHtmlEscaping>
 * 		</WeaConfig>
 * </pre>
 *
 * @author Aranjuez
 * @version Dec 01, 2009
 * @since Pyrube-WEA 1.0
 */
public class WeaConfig extends Configurator {
	/**
	 * logger
	 */
	private static Logger logger = Logger.getInstance(WeaConfig.class.getName());
	
	/**
	 * full java class name of the session attribute manager
	 */
	private String attrMgrClassName = null;
	
	/**
	 * captcha settings
	 */
	private Captcha captcha = null;

	/**
	 * web page request character encoding.
	 * null means using default encoding
	 */
	private String pageCharEncoding = null;
	
	/**
	 * Whether default HTML-escaping is enabled
	 */
	private Boolean defaultHtmlEscaping = null;
	
	/**
	 * the WEA configurator
	 */
	private static WeaConfig weaConfig = null;

	/**
	 * WAF Filters
	 */
	//private List<WafFilterInfo> wafFilters = null;

	/**
	 * the signon info
	 */
	//private SignonInfo signonInfo = null;

	/**
	 * SSO info
	 */
	//private SsoInfo ssoInfo = null;
	
	/**
	 * Indicates to the browser whether cookies should only be sent using a secure protocol, such as HTTPS or SSL
	 */
	private boolean useSecureCookie = false;

	/**
	 * Indicates to the browser whether cookies can only be set and retrieved by http server (browser can not view cookie using javascript)
	 */
	private boolean useHttpOnlyCookie = false;
	
	/**
	 * whether restrict request http method (POST, etc.)
	 */
	private boolean restrictHttpMethod = false;
	
	/**
	 * Defense csrf attack
	 */
	private boolean useCsrfValidate = false;
	/**
	 * csrfTokenName
	 */
	private String csrfTokenName = null;

	/**
	 * get the WEA configurator
	 * @return WeaConfig
	 */
	public static WeaConfig getWeaConfig() {
		if (weaConfig == null) {
			synchronized(WeaConfig.class) {
				if (weaConfig == null) {
					WeaConfig tmpCfg = (WeaConfig) getInstance("WeaConfig");
					if (tmpCfg == null) logger.warn("Configurator named WeaConfig is not found. Please check configuration file.");
					weaConfig = tmpCfg;
				}
			}
		}
		return (weaConfig);
	}

	/**
	 * constructor
	 */
	public WeaConfig() {
	}

	/**
	 * load WEA configuration
	 * @param cfgName the config name
	 * @param cfgNode the configuration Node
	 * @see Configurator#loadConfig(String, Node)
	 */
	public void loadConfig(String cfgName, Node cfgNode) throws AppException {
		// security info
		obtainSecurityInfo((Element) ConfigManager.getNode(cfgNode, "security"));

		// obtain waf filters
		//obtainWafFilters(ConfigManager.getNode(cfgNode, "filters"));

		// Resolve Web Page Character encoding
		pageCharEncoding = ConfigManager.getSingleValue(cfgNode, "pageCharEncoding");
		if (pageCharEncoding != null) {
			pageCharEncoding = pageCharEncoding.trim();
			if (pageCharEncoding.length() == 0) pageCharEncoding = null;
		}
		
		// Resolve default HTML-escaping
		String defaultHtmlEscapingParam = ConfigManager.getSingleValue(cfgNode, "defaultHtmlEscaping");
		if (defaultHtmlEscapingParam != null) {
			if (!Strings.isEmpty(defaultHtmlEscapingParam)) {
				defaultHtmlEscaping = Boolean.valueOf(defaultHtmlEscapingParam);
			}
		}
	}

	/**
	 * obtain security info
	 */
	private void obtainSecurityInfo(Element ctx) throws AppException {
		try {
			if (ctx == null) throw new AppException("message.error.wea.security.not-configured");
			/**
			Element signonElm = (Element) ConfigManager.getNode(ctx, "signon");
			signonInfo = new SignonInfo();
			//signonInfo.setSignonRequired(signonRequired);
			signonInfo.setWafCookieName(ConfigManager.getAttributeValue(signonElm, "wafCookieName"));
			String ckPath = ConfigManager.getAttributeValue(signonElm, "wafCookiePath");
			if (ckPath != null && ckPath.length() > 0) signonInfo.setWafCookiePath(ckPath);
			signonInfo.setSignonListenerClassNames(obtainSignonOffListeners(ConfigManager.getNode(signonElm, "signonListeners")));
			signonInfo.setSignoffListenerClassNames(obtainSignonOffListeners(ConfigManager.getNode(signonElm, "signoffListeners")));

			Element extSignoffInfoElm = (Element) ConfigManager.getNode(signonElm, "externalSsoSignoffUrl");
			boolean extSignoffRedir = Boolean.valueOf(ConfigManager.getSingleValue(extSignoffInfoElm, "redirectToUrl")).booleanValue();
			String extSignoffTsName = null;
			if (Boolean.valueOf(ConfigManager.getSingleValue(extSignoffInfoElm, "addTimestampToUrl")).booleanValue()) {
				extSignoffTsName = ConfigManager.getAttributeValue(extSignoffInfoElm, "addTimestampToUrl", "tsName");
				if (extSignoffTsName != null) extSignoffTsName = extSignoffTsName.trim();
				if (extSignoffTsName == null || extSignoffTsName.length() == 0) extSignoffTsName = "_ts";
			}
			String extSignoffDefUrl = ConfigManager.getSingleValue(extSignoffInfoElm, "url");
			String extSignonDefUrl = ConfigManager.getSingleValue(extSignoffInfoElm, "signonUrl");
			String extSignoffIdent = ConfigManager.getSingleValue(extSignoffInfoElm, "identifiedUrls/identifierClass");
			Map extSignoffParms = ConfigManager.getDeepParams(ConfigManager.getNode(extSignoffInfoElm, "identifiedUrls"));
			signonInfo.setExternalSsoSignoffUrl(extSignoffRedir, extSignoffTsName, extSignoffDefUrl, extSignonDefUrl, extSignoffIdent, extSignoffParms);
			
			String lckSynch = ConfigManager.getAttributeValue(signonElm, "localeCookie", "synchLocaleFromCookie");
			if (lckSynch == null || lckSynch.length() == 0) lckSynch = "true";
			signonInfo.setSynchCookieLocale(Boolean.valueOf(lckSynch).booleanValue());
			String lckName = ConfigManager.getSingleValue(signonElm, "localeCookie/name");
			String lckServerDomain = ConfigManager.getAttributeValue(signonElm, "localeCookie/domain", "useServerDomain");
			String lckDomainLevel = ConfigManager.getAttributeValue(signonElm, "localeCookie/domain", "subDomainLevel");
			String lckPath = ConfigManager.getSingleValue(signonElm, "localeCookie/path");
			String lckMaxAge = ConfigManager.getSingleValue(signonElm, "localeCookie/maxAge");
			if (lckName == null || lckName.length() == 0) lckName = signonInfo.getWafCookieName() + "Locale";
			CookieInfo localeCookieInfo = new CookieInfo(lckName, "");
			if (lckServerDomain != null) localeCookieInfo.setUseServerDomain(Boolean.valueOf(lckServerDomain).booleanValue());
			if (lckDomainLevel != null) localeCookieInfo.setSubDomainLevel(Integer.parseInt(lckDomainLevel));
			if (lckPath == null || lckPath.length() == 0) lckPath = "/";
			localeCookieInfo.setPath(lckPath);
			if (lckMaxAge == null) {
				localeCookieInfo.setMaxAge(30 * 24 * 60 * 60);
			} else {
				localeCookieInfo.setMaxAge(Integer.parseInt(lckMaxAge));
			}
			signonInfo.setWafLocaleCookieInfo(localeCookieInfo);
			
			signonInfo.setCookies(obtainSignonCookies(ConfigManager.getNode(signonElm, "cookies")));

			Element wlInfoCollElm = (Element) ConfigManager.getNode(signonElm, "whiteLabelInfoCollector");
			if(wlInfoCollElm != null){
				signonInfo.setWLInfoCollClass(ConfigManager.getAttributeValue(wlInfoCollElm, "class"));
				signonInfo.setWLInfoCollParams(ConfigManager.getDeepParams(wlInfoCollElm));
			}
			
			ssoInfo = new SsoInfo();
			Element ssoElm = (Element) ConfigManager.getNode(ctx, "sso");
			ssoInfo.setCookieName(ConfigManager.getAttributeValue(ssoElm, "cookieName"));
			//ssoInfo.setCookieDomain(ConfigManager.getSingleValue(ssoElm, "cookieDomain"));
			ssoInfo.setSessionTimeoutUrl(ConfigManager.getSingleValue(ssoElm, "sessionTimeoutUrl"));
			ssoInfo.setSessionErrorUrl(ConfigManager.getSingleValue(ssoElm, "sessionErrorUrl"));
			String tmpStr = ConfigManager.getSingleValue(ssoElm, "sessionTimeoutMinutes");
			if (tmpStr != null && tmpStr.length() > 0) {
				try {	// in Minutes, convert it to milliseconds
					ssoInfo.setSessionTimeout(Integer.parseInt(tmpStr) * 60000L);
				} catch (Exception e) {
					logger.warn("invalid sso session timeout (" + tmpStr + "). default timeout value will be used.");
				}
			}
			*/
			Element captchaElm = (Element) ConfigManager.getNode(ctx, "captcha");
			if (captchaElm != null) {
				captcha = new Captcha();
				captcha.setEnabled(Boolean.valueOf(ConfigManager.getAttributeValue(captchaElm, "enabled")).booleanValue());
				captcha.setCodeLength(Integer.parseInt(ConfigManager.getSingleValue(captchaElm, "codeLength")));
				captcha.setMimeType(ConfigManager.getSingleValue(captchaElm, "mimeType"));
				captcha.setImageWidth(Integer.parseInt(ConfigManager.getSingleValue(captchaElm, "imageWidth")));
				captcha.setImageHeight(Integer.parseInt(ConfigManager.getSingleValue(captchaElm, "imageHeight")));
				captcha.setCharWidth(Integer.parseInt(ConfigManager.getSingleValue(captchaElm, "charWidth")));
				captcha.setCharPosY(Integer.parseInt(ConfigManager.getSingleValue(captchaElm, "charPosY")));
				captcha.setFontSize(Integer.parseInt(ConfigManager.getSingleValue(captchaElm, "fontSize")));
				captcha.setAvailableChars(ConfigManager.getSingleValue(captchaElm, "availableChars").toCharArray());
			}
			useSecureCookie = Boolean.valueOf(ConfigManager.getSingleValue(ctx, "useSecureCookie")).booleanValue();
			useHttpOnlyCookie = Boolean.valueOf(ConfigManager.getSingleValue(ctx, "useHttpOnlyCookie")).booleanValue();
			restrictHttpMethod = Boolean.valueOf(ConfigManager.getSingleValue(ctx, "restrictHttpMethod")).booleanValue();
			useCsrfValidate = Boolean.valueOf(ConfigManager.getSingleValue(ctx, "useCsrfValidate")).booleanValue();
			csrfTokenName = ConfigManager.getSingleValue(ctx, "csrfTokenName");
		} catch (Exception e) {
			logger.error("error", e);
			throw e;
		}
	}

	/**
	 * obtain listeners
	 * @return String[]
	 */
	/**private String[] obtainSignonOffListeners(Node ctx) throws Exception {
		if (ctx == null) return(null);
		String[] lsns = null;
		NodeList nl = ConfigManager.getNodeList(ctx, "listener");
		if (nl != null && nl.getLength() > 0) {
			ArrayList<String> list = new ArrayList<String>();
			for (int i = 0; i < nl.getLength(); ++i) {
				String clsName = ConfigManager.getAttributeValue((Element) nl.item(i), "class");
				if (clsName != null && clsName.length() > 0) list.add(clsName);
			}
			if (list.size() > 0) {
				lsns = new String[list.size()];
				list.toArray(lsns);
			}
		}
		return(lsns);
	}*/
	
	/**
	 * obtain the cookies info
	 * @param ctx
	 * @return
	 */
	/**private CookieInfo[] obtainSignonCookies(Node ctx) throws Exception {
		if (ctx == null) return(null);
		CookieInfo[] cks = null;
		NodeList nl = ConfigManager.getNodeList(ctx, "cookie");
		if (nl != null && nl.getLength() > 0) {
			ArrayList list = new ArrayList();
			for (int i = 0; i < nl.getLength(); ++i) {
				Element nod = (Element) nl.item(i);
				boolean createOnSignon = Boolean.valueOf(ConfigManager.getAttributeValue(nod, "createOnSignon")).booleanValue();
				boolean deleteOnSignoff = Boolean.valueOf(ConfigManager.getAttributeValue(nod, "deleteOnSignoff")).booleanValue();
				boolean encrypt = Boolean.valueOf(ConfigManager.getAttributeValue(nod, "encrypt")).booleanValue();
				String name = ConfigManager.getSingleValue(nod, "name");
				String value = ConfigManager.getSingleValue(nod, "value");
				String path = ConfigManager.getSingleValue(nod, "path");
				String ageStr = ConfigManager.getSingleValue(nod, "maxAge");
				String serverDomain = ConfigManager.getAttributeValue(nod, "domain", "useServerDomain");
				String domainLevel = ConfigManager.getAttributeValue(nod, "domain", "subDomainLevel");
				
				if (name != null && value != null) {
					CookieInfo ck = new CookieInfo(name, value);
					ck.setCreateOnSignon(createOnSignon);
					ck.setDeleteOnSignoff(deleteOnSignoff);
					ck.setEncrypted(encrypt);
					if (path != null) ck.setPath(path);
					if (ageStr != null) {
						try {
							ck.setMaxAge(Integer.parseInt(ageStr));
						} catch (Exception e) {
							logger.warn("invalid cookie age. default will be used.");
						}
					}
					if (serverDomain != null) ck.setUseServerDomain(Boolean.valueOf(serverDomain).booleanValue());
					if (domainLevel != null) {
						try {
							ck.setSubDomainLevel(Integer.parseInt(domainLevel));
						} catch (Exception e) {
							logger.warn("invalid sub-domain level. default will be used.");
						}
					}
					list.add(ck);
				}
			}
			if (list.size() > 0) {
				cks = new CookieInfo[list.size()];
				list.toArray(cks);
			}
		}
		return(cks);
	}*/
	
	/**
	 * obtain filters info
	 */
	/**private void obtainWafFilters(Node ctx) throws Exception {
		try {
			if (ctx == null) return;
			boolean enabled = true;
			String enStr = ConfigManager.getAttributeValue((Element)ctx, "enabled");
			if (enStr != null && enStr.length() > 0) enabled = Boolean.valueOf(enStr).booleanValue();
			NodeList fNodes = ConfigManager.getNodeList(ctx, "filter");
			if (enabled && fNodes != null && fNodes.getLength() > 0) {
				List<WafFilterInfo> filters = new ArrayList<WafFilterInfo>();
				for (int i = 0; i < fNodes.getLength(); ++i) {
					enabled = true;
					enStr = ConfigManager.getAttributeValue((Element)fNodes.item(i), "enabled");
					if (enStr != null && enStr.length() > 0) enabled = Boolean.valueOf(enStr).booleanValue();
					String clsName = ConfigManager.getAttributeValue((Element)fNodes.item(i), "class");
					if (enabled && clsName != null && clsName.length() > 0) {
						Map<String, ?> props = ConfigManager.getDeepParams(fNodes.item(i));
						filters.add(new WafFilterInfo(clsName, props));
					}
				}
				if (filters.size() > 0) wafFilters = filters;
			}
		} catch (Exception e) {
			logger.error("error", e);
			throw e;
		}
	}*/
	
	/**
	 * get attribute manager class name
	 * @return the attribute manager class name
	 */
	public String getAttributeManagerClassName() {
		return (attrMgrClassName);
	}
	
	/**
	 * @return the captcha
	 */
	public Captcha getCaptcha() {
		return captcha;
	}

	/**
	 * get whether the cookie should only be sent using a secure protocol, such as HTTPS or SSL
	 * @return
	 */
	public boolean isSecureCookie() {
		return(useSecureCookie);
	}
	
	/**
	 * return whether cookies can only be set and retrieved by HTTP server
	 * @return
	 */
	public boolean isHttpOnlyCookie() {
		return(useHttpOnlyCookie);
	}
	
	/**
	 * return whether it is restrict HTTP method
	 * @return
	 */
	public boolean isRestrictHttpMethod() {
		return(restrictHttpMethod);
	}
	
	/**
	 * return whether cookies can only be set and retrieved by HTTP server
	 * @return
	 */
	public boolean isCsrfValidate() {
		return(useCsrfValidate);
	}
	
	/**
	 * get csrf tokenName
	 * @return
	 */
	public String getCsrfTokenName() {
		return csrfTokenName;
	}

	/**
	 * get Waf Filters.
	 * @return List the list of WafFilterInfo
	 */
	//public List<WafFilterInfo> getWafFilters() {
	//	return(wafFilters);
	//}

	/**
	 * get the pageCharEncoding of the http request
	 * @return String
	 */
	public String getPageCharEncoding() {
		return pageCharEncoding;
	}
	
	/**
	 * Return whether default HTML-escaping is enabled
	 * @return whether default HTML escaping is enabled. null if no parameter given
	 */
	public Boolean isDefaultHtmlEscaping() {
		return (defaultHtmlEscaping);
	}

	/**
	 * get signon info
	 * @return SignonInfo
	 */
	//public SignonInfo getSignonInfo() {
	//	return(signonInfo);	
	//}
	
	/**
	 * get SSO info
	 * @return SsoInfo
	 */
	//public SsoInfo getSsoInfo() {
	//	return(ssoInfo);	
	//}
	
	public class Captcha {
		
		/**
		 * whether captcha is enabled
		 */
		private boolean enabled = false;

		/**
		 * the captcha code length (number of letters)
		 */
		private int codeLength = 4;
		
		/**
		 * image MIME type such as image/png which will be used to send it to browser
		 */
		private String mimeType = "image/png";
		
		/**
		 * image width
		 */
		private int imageWidth = 96;

		/**
		 * image height
		 */
		private int imageHeight = 25;
		
		/**
		 * char width
		 */
		private int charWidth = 16;
		
		/**
		 * char position-y
		 */
		private int charPosY = 21;
		
		/**
		 * font size
		 */
		private int fontSize = 23;
		
		/**
		 * available chars
		 */
		private char[] availableChars = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 
										'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V',
										'W', 'X', 'Y', 'Z', '3', '4', '6', '7', '8' };

		/**
		 * @return the enabled
		 */
		public boolean isEnabled() {
			return enabled;
		}

		/**
		 * @param enabled the enabled to set
		 */
		public void setEnabled(boolean enabled) {
			this.enabled = enabled;
		}

		/**
		 * @return the codeLength
		 */
		public int getCodeLength() {
			return codeLength;
		}

		/**
		 * @param codeLength the codeLength to set
		 */
		public void setCodeLength(int codeLength) {
			this.codeLength = codeLength;
		}

		/**
		 * @return the mimeType
		 */
		public String getMimeType() {
			return mimeType;
		}

		/**
		 * @param mimeType the mimeType to set
		 */
		public void setMimeType(String mimeType) {
			this.mimeType = mimeType;
		}

		/**
		 * @return the imageWidth
		 */
		public int getImageWidth() {
			return imageWidth;
		}

		/**
		 * @param imageWidth the imageWidth to set
		 */
		public void setImageWidth(int imageWidth) {
			this.imageWidth = imageWidth;
		}

		/**
		 * @return the imageHeight
		 */
		public int getImageHeight() {
			return imageHeight;
		}

		/**
		 * @param imageHeight the imageHeight to set
		 */
		public void setImageHeight(int imageHeight) {
			this.imageHeight = imageHeight;
		}

		/**
		 * @return the charWidth
		 */
		public int getCharWidth() {
			return charWidth;
		}

		/**
		 * @param charWidth the charWidth to set
		 */
		public void setCharWidth(int charWidth) {
			this.charWidth = charWidth;
		}

		/**
		 * @return the charPosY
		 */
		public int getCharPosY() {
			return charPosY;
		}

		/**
		 * @param charPosY the charPosY to set
		 */
		public void setCharPosY(int charPosY) {
			this.charPosY = charPosY;
		}

		/**
		 * @return the fontSize
		 */
		public int getFontSize() {
			return fontSize;
		}

		/**
		 * @param fontSize the fontSize to set
		 */
		public void setFontSize(int fontSize) {
			this.fontSize = fontSize;
		}

		/**
		 * @return the availableChars
		 */
		public char[] getAvailableChars() {
			return availableChars;
		}

		/**
		 * @param availableChars the availableChars to set
		 */
		public void setAvailableChars(char[] availableChars) {
			this.availableChars = availableChars;
		}
	}
	
}