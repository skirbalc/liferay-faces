/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */
package com.liferay.faces.bridge.container.liferay;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.BaseURL;

import com.liferay.faces.bridge.BridgeConstants;
import com.liferay.faces.bridge.util.URLParameter;


/**
 * @author  Neil Griffin
 */
public class ParsedBaseURL {

	// Private Data Members
	private String prefix;
	private String portalAuthToken;
	private String portletAuthToken;
	private String resourceId;
	private List<URLParameter> wsrpParameters;

	public ParsedBaseURL(BaseURL baseURL) {

		String toStringValue = baseURL.toString();
		wsrpParameters = new ArrayList<URLParameter>();

		String queryString = toStringValue;
		int queryPos = toStringValue.indexOf(BridgeConstants.CHAR_QUESTION_MARK);

		if (queryPos > 0) {
			prefix = toStringValue.substring(0, queryPos + 1);
			queryString = toStringValue.substring(queryPos + 1);
		}

		String[] nameValuePairs = queryString.split("[&]");

		if (nameValuePairs != null) {

			for (String nameValuePair : nameValuePairs) {

				if ((portalAuthToken == null) && nameValuePair.startsWith(LiferayConstants.P_AUTH)) {
					int equalsPos = nameValuePair.indexOf(BridgeConstants.CHAR_EQUALS);

					if (equalsPos > 0) {
						portalAuthToken = nameValuePair.substring(equalsPos + 1);
					}
				}

				if ((portletAuthToken == null) && nameValuePair.startsWith(LiferayConstants.P_P_AUTH)) {
					int equalsPos = nameValuePair.indexOf(BridgeConstants.CHAR_EQUALS);

					if (equalsPos > 0) {
						portletAuthToken = nameValuePair.substring(equalsPos + 1);
					}
				}

				if (nameValuePair.startsWith(BridgeConstants.WSRP)) {
					int equalsPos = nameValuePair.indexOf(BridgeConstants.CHAR_EQUALS);

					if (equalsPos > 0) {
						String name = nameValuePair.substring(0, equalsPos);
						String value = nameValuePair.substring(equalsPos + 1);
						URLParameter urlParameter = new URLParameter(name, value);
						wsrpParameters.add(urlParameter);
					}
				}
			}
		}
	}

	public String getPortalAuthToken() {
		return portalAuthToken;
	}

	public void setPortalAuthToken(String portalAuthToken) {
		this.portalAuthToken = portalAuthToken;
	}

	public String getPortletAuthToken() {
		return portletAuthToken;
	}

	public void setPortletAuthToken(String portletAuthToken) {
		this.portletAuthToken = portletAuthToken;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getResourceId() {
		return resourceId;
	}

	public List<URLParameter> getWsrpParameters() {
		return wsrpParameters;
	}
}
