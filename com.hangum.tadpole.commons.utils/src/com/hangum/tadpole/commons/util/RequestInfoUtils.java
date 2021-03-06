/*******************************************************************************
 * Copyright (c) 2013 hangum.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v2.1
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 * 
 * Contributors:
 *     hangum - initial API and implementation
 ******************************************************************************/
package com.hangum.tadpole.commons.util;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.eclipse.rap.rwt.RWT;

import com.tadpole.common.define.core.define.PublicTadpoleDefine;

/**
 * <pre>
 * 사용자 정보를 보기 좋게
 * 
 * 1. email.
 * 2. accept ip
 * 3. os
 * 4. brower
 * 5. full version
 * </pre>
 * 
 * @author hangum
 *
 */
public class RequestInfoUtils {
	private static final Logger logger = Logger.getLogger(RequestInfoUtils.class);
	
	/**
	 * Return request locale
	 * 
	 * @return
	 */
	public static String getDisplayLocale() {
		HttpServletRequest request = RWT.getRequest();
		Locale locale = request.getLocale();
		return locale.getDisplayLanguage(Locale.ENGLISH);
	}
	
	/**
	 * browser support
	 * @return
	 */
	public static boolean isSupportBrowserIP() {
		ServletUserAgent sua = new ServletUserAgent();
		sua.detect(RWT.getRequest());

		String strBrowser = sua.getBrowserType().toString();
		if(logger.isDebugEnabled()) {
			logger.debug("Browser is " + strBrowser);
			logger.debug("version is " + sua.getMajorVersion());
		}
		
		for(PublicTadpoleDefine.TADPOLE_SUPPORT_BROWSERIP supportBrowser: PublicTadpoleDefine.TADPOLE_SUPPORT_BROWSERIP.values()) {
			if(strBrowser.equalsIgnoreCase(supportBrowser.toString())) return true;
		}
		
		return false;
	}
	
	/**
	 * 올챙이가 동작 할 수 없는 브라우저라면, 사용자 브라우저 를 리턴합니다.
	 * 
	 * @return
	 */
	public static boolean isSupportBrowser() {
		ServletUserAgent sua = new ServletUserAgent();
		sua.detect(RWT.getRequest());

		String strBrowser = sua.getBrowserType().toString();
		if(logger.isDebugEnabled()) {
			logger.debug("Browser is " + strBrowser);
			logger.debug("version is " + sua.getMajorVersion());
		}
		
		for(PublicTadpoleDefine.TADPOLE_SUPPORT_BROWSER supportBrowser: PublicTadpoleDefine.TADPOLE_SUPPORT_BROWSER.values()) {
			if("IE".equals(strBrowser)) {
				if(10 > sua.getMajorVersion()) return false;
				else return true;
			} else {
				if(strBrowser.equalsIgnoreCase(supportBrowser.toString())) return true;
			}
		}
		
		return false;
	}

	/**
	 * Get user browser and browser version information.
	 * 
	 * @return
	 */
	public static String getUserBrowser() {
		ServletUserAgent sua = new ServletUserAgent();
		sua.detect(RWT.getRequest());

		String strBrowser = sua.getBrowserType().toString();
		String strFullVersion = sua.getFullVersion() + sua.getMajorVersion();
		
		return strBrowser + " Ver" + strFullVersion;
	}

	/**
	 * 사용자 인포
	 * 
	 * @param prefixMsg
	 * @param email
	 * @return
	 */
	public static String requestInfo(String prefixMsg, String email) {
		StringBuffer retMsg = new StringBuffer(prefixMsg + "\r\n");
		
		ServletUserAgent sua = new ServletUserAgent();
		sua.detect(RWT.getRequest());
		String strOs = sua.getOSSimpleType().toString();
		String strBrowser = sua.getBrowserType().toString();
		String strFullVersion = sua.getFullVersion() + sua.getMajorVersion();
		
		retMsg.append("[email]" + email + "[os]" + strOs + "[browser]" + strBrowser + "[full version]" + strFullVersion);
		
		return retMsg.toString();
	}
	
	/**
	 * 사용자의 os를 얻습니다.
	 * 
	 * @return
	 */
	public static ServletUserAgent.OS_SIMPLE_TYPE findOSSimpleType() {
		ServletUserAgent sua = new ServletUserAgent();
		sua.detect(RWT.getRequest());
		
		return sua.getOSSimpleType();
	}
	
	/**
	 * user request ip
	 * 
	 * @return
	 */
	public static String getRequestIP() {
		HttpServletRequest request = RWT.getRequest();
		
		String strIP = request.getHeader("X-Forwarded-For");
//		if(logger.isDebugEnabled()) logger.debug("X-Forwarded-For: " + strIP);
		
		if (StringUtils.defaultString(strIP).length() == 0 || "unknown".equalsIgnoreCase(strIP)) { 
		    strIP = request.getHeader("Proxy-Client-IP"); 
//		    if(logger.isDebugEnabled()) logger.debug("Proxy-Client-IP[ip]" + strIP);
		} 
		if (StringUtils.defaultString(strIP).length() == 0 || "unknown".equalsIgnoreCase(strIP)) { 
		    strIP = request.getHeader("WL-Proxy-Client-IP");
//		    if(logger.isDebugEnabled()) logger.debug("WL-Proxy-Client-IP[ip]" + strIP);
		} 
		if (StringUtils.defaultString(strIP).length() == 0 || "unknown".equalsIgnoreCase(strIP)) { 
		    strIP = request.getHeader("HTTP_CLIENT_IP");
//		    if(logger.isDebugEnabled()) logger.debug("HTTP_CLIENT_IP[ip]" + strIP);
		} 
		if (StringUtils.defaultString(strIP).length() == 0 || "unknown".equalsIgnoreCase(strIP)) { 
		    strIP = request.getHeader("HTTP_X_FORWARDED_FOR");
//		    if(logger.isDebugEnabled()) logger.debug("HTTP_X_FORWARDED_FOR[ip]" + strIP);
		} 
		if (StringUtils.defaultString(strIP).length() == 0 || "unknown".equalsIgnoreCase(strIP)) { 
		    strIP = request.getRemoteAddr(); 
//		    if(logger.isDebugEnabled()) logger.debug("[ip]" + strIP);
		}
		
		return strIP;
	}
	
}
