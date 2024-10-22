package com.pmsoluciones.innovacion.security;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class UsuarioLdapDto implements UserDetails, Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8195436136250592118L;
	private String nombreCompleto;
	private String usuarioDominio;
	private String email;
	private String description;
	private String sAMAccountName;
	private Long idRol;
	private String desRol;
	@JsonIgnore
	private String cveRol;
	
	private String token;
	private List<Menu> accesos;
	

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<Authority> authorityList = new ArrayList<>();
		Authority authority = new Authority();
		authority.setAuthority(cveRol);

		authorityList.add(authority);

		return authorityList;
	}

	@Override
	@JsonIgnore
	public String getPassword() {
		return sAMAccountName;
	}

	@Override
	public String getUsername() {
		return email;
	}

}
