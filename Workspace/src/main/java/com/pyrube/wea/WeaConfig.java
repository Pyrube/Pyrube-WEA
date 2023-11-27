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
 *						<name>weaLocale</name>
 *						<!-- default domain is current server name.  you may use the domain or sub-domain of the server. 
 *						if attribute useServerDomain = true, then use the domain of the server (for server h.wea.pyrube.com, 
 *						domain is .wea.pyrube.com), or if attribute subDomainLevel(at least 2) is present, then use the sub-domain 
 *						of the server on the given level (for server h.wea.pyrube.com, level 2 sub-domain is .pyrube.com). 
 *						if both useServerDomain and subDomainLevel present, then use subDomainLevel. -->
 *						<domain useServerDomain="true" subDomainLevel="2"/>
 *						<!-- path supports variable $[APP_CONTEXT] -->
 *						<path>/</path>
 *						<!-- age in seconds. maxAge=-1 then set non-persistent cookie; maxAge=0 then delete cookie. -->
 *						<maxAge>2592000</maxAge>
 *					</localeCookie>
 * 					<cookies>
 * 						<cookie createOnSignon="true" deleteOnSignoff="true" encrypt="true">
 * 							<name>SSO-APP-SIGNOFF-WEA</name>
 * 							<!-- in cookie value you can use $[var] to refer predefined variables: SERVER_URL, SERVER_ROOT, 
 * 							SERVER_PROTOCOL, SERVER_HOST, SERVER_PORT, APP_CONTEXT, TIMESTAMP  -->
 * 							<value>$[SERVER_ROOT]/$[APP_CONTEXT]/signoff.do?reason=sso_signoff&amp;ts=$[TIMESTAMP]</value>
 * 							<!-- default domain is current server name.  you may use the domain or sub-domain of the server. 
 * 							if attribute useServerDomain = true, then use the domain of the server (for server h.wea.pyrube.com, 
 * 							domain is .wea.pyrube.com), or if attribute subDomainLevel(at least 2) is present, then use the sub-domain 
 * 							of the server on the given level (for server h.wea.pyrube.com, level 2 sub-domain is .pyrube.com). 
 * 							if both useServerDomain and subDomainLevel present, then use subDomainLevel. -->
 * 							<domain useServerDomain="true" subDomainLevel="2"/>
 * 							<path>/</path>
 * 							<!-- maxAge=-1 then set non-persistent cookie. maxAge=0 then delete cookie. -->
 * 							<maxAge>-1</maxAge>
 * 						</cookie>
 * 					</cookies>
 * 				</signon>
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
 * 				<!-- whether restrict http request http method -->
 * 				<restrictHttpMethod>true</restrictHttpMethod>
 * 				<!-- Defense csrf attack -->
 * 				<useCsrfValidate>false</useCsrfValidate>
 * 				<csrfTokenName>_ct_</csrfTokenName>
 * 			</security>
 * 			<servlet>
 * 				<theme system="DEFAULT">
 * 					<names>DEFAULT,DARK</names>
 * 					<cookie enabled="true">
 * 						<name>weaTheme</name>
 * 						<domain useServerDomain="false" />
 * 						<!-- maxAge=-1 then set non-persistent cookie. maxAge=0 then delete cookie. -->
 * 						<maxAge>2592000</maxAge>
 * 					</cookie>
 * 				</theme>
 * 			</servlet>
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
	 * theme configuration
	 */
	private String[] themeNames = null;
	private String defaultThemeName = null;
	private boolean syncThemeCookie = false;
	private Cookie themeCookie = null;

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
		// servlet info
		obtainServletInfo((Element) ConfigManager.getNode(cfgNode, "servlet"));

		// obtain wea filters
		//obtainWeaFilters(ConfigManager.getNode(cfgNode, "filters"));

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
	 * obtain servlet info
	 */
	private void obtainServletInfo(Element ctx) throws AppException {
		try {
			if (ctx == null) throw new AppException("message.error.wea.servlet.not-configured");
			Element themeElm = (Element) ConfigManager.getNode(ctx, "theme");
			if (themeElm != null) {
				defaultThemeName = ConfigManager.getAttributeValue(themeElm, "system");
				themeNames = ConfigManager.getSingleValue(themeElm, "names").split(",");
				syncThemeCookie = Boolean.valueOf(ConfigManager.getAttributeValue(themeElm, "cookie", "enabled")).booleanValue();
				themeCookie = new Cookie();
				themeCookie.setName(ConfigManager.getSingleValue(themeElm, "cookie/name"));
				themeCookie.setUseServerDomain(Boolean.valueOf(ConfigManager.getAttributeValue(themeElm, "cookie/domain", "useServerDomain")).booleanValue());
				themeCookie.setPath(ConfigManager.getSingleValue(themeElm, "cookie/path"));
				themeCookie.setMaxAge(Integer.parseInt(ConfigManager.getSingleValue(themeElm, "cookie/maxAge")));
			}
		} catch (Exception e) {
			logger.error("error", e);
			throw e;
		}
	}

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
	 * @return the themeNames
	 */
	public String[] getThemeNames() {
		return themeNames;
	}

	/**
	 * @return the defaultThemeName
	 */
	public String getDefaultThemeName() {
		return defaultThemeName;
	}

	/**
	 * returns whether the theme is synchronized to cookie
	 * @return
	 */
	public boolean isThemeCookieEnabled() {
		return(syncThemeCookie);
	}

	/**
	 * @return the themeCookie
	 */
	public Cookie getThemeCookie() {
		return(themeCookie);
	}

	/**
	 * returns whether the cookie should only be sent using a secure protocol, such as HTTPS or SSL
	 * @return
	 */
	public boolean isCookieSecure() {
		return(useSecureCookie);
	}
	
	/**
	 * return whether cookies can only be set and retrieved by HTTP server
	 * @return
	 */
	public boolean isCookieHttpOnly() {
		return(useHttpOnlyCookie);
	}
	
	/**
	 * return whether it is restrict HTTP method
	 * @return
	 */
	public boolean isHttpMethodRestrict() {
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
	 * Captcha Data
	 * @author Aranjuez
	 * @version Dec 01, 2009
	 * @since Pyrube-WEA 1.0
	 */
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

	/**
	 * Cookie Data
	 * @author Aranjuez
	 * @version Oct 01, 2023
	 * @since Pyrube-WEA 1.1
	 */
	public class Cookie {

		/**
		 * the cookie name
		 */
		private String name = null;

		/**
		 * the cookie value
		 */	
		private String value = null;

		/**
		 * the full server host name such as svr1.pyrube.com
		 */
		private String serverHost = null;

		/**
		 * cookie domain. Default domain is current server name.  
		 * you may use the domain or sub-domain of the server.  
		 * if useServerDomain = true, then use the domain of the 
		 * server (for server h.wea.pyrube.com, domain is .wea.pyrube.com), 
		 * or if subDomainLevel(at least 2) is present, then use the 
		 * sub-domain of the server on the given level (for server 
		 * h.wea.pyrube.com, level 2 sub-domain is .pyrube.com). 
		 * if both useServerDomain and subDomainLevel present, then use 
		 * subDomainLevel.
		 */	
		private boolean useServerDomain = false;

		/**
		 * if use server domain, this is sub-domain level. if it is less than 2, then ignore it.
		 */	
		private int subDomainLevel = 0;

		/**
		 * cookie path. default is /
		 */	
		private String path = "/";

		/**
		 * maximum age of the cookie. 
		 * If maxAge = -1 then set non-persistent cookie. 
		 * maxAge=0 then delete cookie.
		 */	
		private int maxAge = -1;

		/**
		 * whether value need to be encrypted
		 */
		private boolean encrypted = false;
			
		/**
		 * constructor
		 */
		public Cookie() {
		}

		/**
		 * constructor
		 */
		public Cookie(String name, String value) {
			this.name = name;
			this.value = value;
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
		 * @return the value
		 */
		public String getValue() {
			return value;
		}

		/**
		 * @param value the value to set
		 */
		public void setValue(String value) {
			this.value = value;
		}

		/**
		 * @return the serverHost
		 */
		public String getServerHost() {
			return serverHost;
		}

		/**
		 * @param serverHost the serverHost to set
		 */
		public void setServerHost(String serverHost) {
			this.serverHost = serverHost;
		}

		/**
		 * @return the useServerDomain
		 */
		public boolean doesUseServerDomain() {
			return useServerDomain;
		}

		/**
		 * @param useServerDomain the useServerDomain to set
		 */
		public void setUseServerDomain(boolean useServerDomain) {
			this.useServerDomain = useServerDomain;
		}

		/**
		 * @return the subDomainLevel
		 */
		public int getSubDomainLevel() {
			return subDomainLevel;
		}

		/**
		 * @param subDomainLevel the subDomainLevel to set
		 */
		public void setSubDomainLevel(int subDomainLevel) {
			this.subDomainLevel = subDomainLevel;
		}

		/**
		 * @return the path
		 */
		public String getPath() {
			return path;
		}

		/**
		 * @param path the path to set
		 */
		public void setPath(String path) {
			this.path = path;
		}

		/**
		 * @return the maxAge
		 */
		public int getMaxAge() {
			return maxAge;
		}

		/**
		 * @param maxAge the maxAge to set
		 */
		public void setMaxAge(int maxAge) {
			this.maxAge = maxAge;
		}

		/**
		 * @return the encrypted
		 */
		public boolean isEncrypted() {
			return encrypted;
		}

		/**
		 * @param encrypted the encrypted to set
		 */
		public void setEncrypted(boolean encrypted) {
			this.encrypted = encrypted;
		}

		/**
		 * returns the actual cookie domain. 
		 * Default domain is current server name.  
		 * you may use the domain or sub-domain of the server.  
		 * if useServerDomain = true, then use the domain of the 
		 * server (for server h.wea.pyrube.com, domain is .wea.pyrube.com), 
		 * or if subDomainLevel(at least 2) is present, then use the 
		 * sub-domain of the server on the given level (for server 
		 * h.wea.pyrube.com, level 2 sub-domain is .pyrube.com). 
		 * if both useServerDomain and subDomainLevel present, then use 
		 * subDomainLevel.
		 * 
		 * @return the domain, null if use default domain
		 */
		public String resolveDomain() {
			if (serverHost == null || serverHost.length() < 2) return(null);
			if (!useServerDomain) return(null);
			int iPos = 0;
			iPos = serverHost.indexOf('.');
			if (iPos < 0) return(null);	// use the default
			String domain = serverHost.substring(iPos);	// domain includes the first dot (.)
			if (subDomainLevel >= 2) {
				iPos = domain.length();
				for (int i = 0; i < subDomainLevel; ++i) {
					iPos = domain.lastIndexOf('.', iPos - 1);
					if (iPos <= 0) {
						break;
					}
				}
				if (iPos > 0) domain = domain.substring(iPos);
			}
			return(domain);
		}
	}

}