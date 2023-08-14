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

import java.io.Serializable;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.SortedSet;
import java.util.TreeSet;

import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.pyrube.one.app.user.SecurityStatus;
import com.pyrube.one.app.user.User;

/**
 * WEA user details
 * 
 * @author Aranjuez
 * @version Dec 01, 2009
 * @since Pyrube-WEA 1.0
 */
public class WeaUserDetails implements UserDetails, CredentialsContainer {

	/**
	 * serial version uid
	 */
	private static final long serialVersionUID = 8067109239320567174L;

	/**
	 * the user
	 */
	private final User user;
	
	/**
	 * granted rights
	 */
	private final SortedSet<GrantedAuthority> authorities;
	
	/**
	 * default is for a GUEST user.
	 */
	public WeaUserDetails() {
		this(User.GUEST());
	}
	
	/**
	 * user must not be null
	 * @param user
	 */
	public WeaUserDetails(User user) {
		this.user = user;
		HashSet<String> rights = user.getRights();
		authorities = new TreeSet<GrantedAuthority>(new AuthorityComparator());
		if (rights != null) {
			for (String r : rights) {
				authorities.add(new SimpleGrantedAuthority(r));
			}
		};
		
	}
	
	/**
	 * add more user rights
	 * @param rights
	 * @return
	 */
	public WeaUserDetails moreUserRights(HashSet<String> rights) {
		this.user.rights(rights);
		if (rights != null) {
			for (String r : rights) {
				authorities.add(new SimpleGrantedAuthority(r));
			}
		};
		return this;
	}
	
	/**
	 * get user
	 * @return
	 */
	public User getUser() {
		return(user);
	}
	
	/**
	 * (implement) get authorities
	 */
	public Collection<GrantedAuthority> getAuthorities() {
		return authorities;
	}

	/**
	 * (implement) get user password
	 */
	public String getPassword() {
		return user.getCredentials();
	}

	/**
	 * (implement) get user name for login
	 */
	public String getUsername() {
		return user.loginame();
	}

	/**
	 * (implement) user is enabled
	 */
	public boolean isEnabled() {
		return user.getStatus() == null || !user.is(SecurityStatus.DISABLED);
	}

	/**
	 * (implement) user is not expired (user is active)
	 */
	public boolean isAccountNonExpired() {
		return user.getStatus() == null || (
				!user.is(SecurityStatus.INACTIVE) && 
				!user.is(SecurityStatus.EXPIRED)
				);
	}

	/**
	 * (implement) user is not locked
	 */
	public boolean isAccountNonLocked() {
		return user.getStatus() == null || !user.is(SecurityStatus.LOCKED);
	}

	/**
	 * (implement) password is not expired
	 */
	public boolean isCredentialsNonExpired() {
		return user.getStatus() == null || !user.is(SecurityStatus.PWD_EXPIRED);
	}

	/**
	 * password is not initialized
	 * @return
	 */
	public boolean isCredentialsNonInitialized() {
		return user.getStatus() == null || !user.is(SecurityStatus.PWD_INITIALIZED);
	}

	/**
	 * (implement) erase credentials
	 */
	public void eraseCredentials() {
		user.setCredentials(null);
	}

	/**
	 * authority comparator
	 */
	private static class AuthorityComparator implements Comparator<GrantedAuthority>, Serializable {
		
		/**
		 * serial version uid
		 */
		private static final long serialVersionUID = 5479712100085687108L;

		/**
		 * 
		 */
		public int compare(GrantedAuthority a1, GrantedAuthority a2) {
			if (a2.getAuthority() == null) return -1;
			if (a1.getAuthority() == null) return 1;
			return a1.getAuthority().compareTo(a2.getAuthority());
		}
	}
}