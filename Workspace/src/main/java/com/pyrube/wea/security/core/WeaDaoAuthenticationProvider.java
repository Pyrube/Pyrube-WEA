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

package com.pyrube.wea.security.core;

import java.util.HashSet;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import com.pyrube.one.app.Apps;
import com.pyrube.one.app.user.User;
import com.pyrube.wea.WeaConstants;
import com.pyrube.wea.context.WebContextHolder;

/**
 * WEA DAO authentication provider. It's based on external DaoAuthenticationProvider, 
 * matcher added by a requestMatcher
 * 
 * @author Aranjuez
 * @version Dec 01, 2009
 * @since Pyrube-WEA 1.0
 */
public class WeaDaoAuthenticationProvider extends DaoAuthenticationProvider {

	/**
	 * provider matcher. null means for all
	 */
	private WeaAuthenticationRequestMatcher requestMatcher = null;

	private UserOnboardingService userOnboardingService;

	private AccountProfileService accountProfileService;

	/**
	 * constructor
	 */
	public WeaDaoAuthenticationProvider() {
		super();
	}

	@Override
	public boolean supports(Class<?> authentication) {
		if (requestMatcher != null) {
			requestMatcher.setAuthentication(authentication);
			if (!requestMatcher.matches(WebContextHolder.getWebContext().getRequest())) {
				return false;
			}
		}
		return super.supports(authentication);
	}

	@Override
	protected Authentication createSuccessAuthentication(Object principal,
			Authentication authentication, UserDetails user) {
		if (this.userOnboardingService != null) {
			WeaAuthenticationDetails authenticationDetails = (WeaAuthenticationDetails) authentication.getDetails();
			if (WeaConstants.AUTHENTICATION_USED_FOR_PASSWORD.equals(authenticationDetails.getUsedFor())) {
				user = this.userOnboardingService.forceChangePassword(user, authenticationDetails.getPassword());
				if (principal != null && principal instanceof WeaUserDetails) {
					WeaUserDetails userDetails = (WeaUserDetails) principal;
					userDetails.getUser().setCredentials(((WeaUserDetails) user).getUser().getCredentials());
				}
			}
		}
		if (this.accountProfileService != null) {
			SecurityContext contextBeforeProfileModification = SecurityContextHolder.getContext();
			Object principalBeforeProfileModification = contextBeforeProfileModification.getAuthentication().getPrincipal();
			HashSet<String> rightsBeforeProfileModification = new HashSet<String>();
			if (principalBeforeProfileModification != null && principalBeforeProfileModification instanceof WeaUserDetails) {
				WeaUserDetails userDetailsBeforeProfileModification = (WeaUserDetails) principalBeforeProfileModification;
				User userBeforeProfileModification = ((WeaUserDetails) userDetailsBeforeProfileModification).getUser();
				rightsBeforeProfileModification = userBeforeProfileModification.getRights();
			}
			if (principal != null && principal instanceof WeaUserDetails) {
				WeaUserDetails userDetails = (WeaUserDetails) principal;
				userDetails.moreUserRights(rightsBeforeProfileModification);
			}
			WeaAuthenticationDetails authenticationDetails = (WeaAuthenticationDetails) authentication.getDetails();
			if (WeaConstants.AUTHENTICATION_USED_FOR_PASSWORD.equals(authenticationDetails.getUsedFor())) {
				user = this.accountProfileService.changePassword(user, authenticationDetails.getPassword());
				if (principal != null && principal instanceof WeaUserDetails) {
					WeaUserDetails userDetails = (WeaUserDetails) principal;
					userDetails.getUser().setCredentials(((WeaUserDetails) user).getUser().getCredentials());
				}
			} else if (WeaConstants.AUTHENTICATION_USED_FOR_MOBILE.equals(authenticationDetails.getUsedFor())) {
				user = this.accountProfileService.changeMobile(user, authenticationDetails.getMobile());
				if (principal != null && principal instanceof WeaUserDetails) {
					WeaUserDetails userDetails = (WeaUserDetails) principal;
					userDetails.getUser().setMobile(authenticationDetails.getMobile());
				}
			} else if (WeaConstants.AUTHENTICATION_USED_FOR_EMAIL.equals(authenticationDetails.getUsedFor())) {
				user = this.accountProfileService.changeEmail(user, authenticationDetails.getEmail());
				if (principal != null && principal instanceof WeaUserDetails) {
					WeaUserDetails userDetails = (WeaUserDetails) principal;
					userDetails.getUser().setEmail(authenticationDetails.getEmail());
				}
			}
			((WeaUserDetails) user).moreUserRights(rightsBeforeProfileModification);
		}
		return super.createSuccessAuthentication(principal, authentication, user);
	}

	/**
	 * additional authentication checks:
	 * if maximum attempts reached, TooManyAttemptsException will be thrown.
	 */
	@Override
	protected void additionalAuthenticationChecks(UserDetails userDetails,
			UsernamePasswordAuthenticationToken authentication)
			throws AuthenticationException {
		try {
			super.additionalAuthenticationChecks(userDetails, authentication);
		} catch (BadCredentialsException e) {
			WeaUserDetails weaUserDetails = (WeaUserDetails) userDetails;
			if (!Apps.the.sys_default.pass_policy().checks.attempts(weaUserDetails.getUser().attempt_times() + 1)) {
				throw new TooManyAttemptsException("Too many attempts.");
			}
			throw e;
		}
	}
	
	/**
	 * 
	 * @param requestMatcher
	 */
	public void setRequestMatcher(WeaAuthenticationRequestMatcher requestMatcher) {
		this.requestMatcher = requestMatcher;
	}

	/**
	 * @return the userOnboardingService
	 */
	public UserOnboardingService getUserOnboardingService() {
		return userOnboardingService;
	}

	/**
	 * @param userOnboardingService the userOnboardingService to set
	 */
	public void setUserOnboardingService(UserOnboardingService userOnboardingService) {
		this.userOnboardingService = userOnboardingService;
	}

	/**
	 * @return the accountProfileService
	 */
	public AccountProfileService getAccountProfileService() {
		return accountProfileService;
	}

	/**
	 * @param accountProfileService the accountProfileService to set
	 */
	public void setAccountProfileService(AccountProfileService accountProfileService) {
		this.accountProfileService = accountProfileService;
	}

}
