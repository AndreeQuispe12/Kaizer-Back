package com.example.Kaizer_Back.auth;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

// Extiende UserDetails con el ID primario del usuario para evitar consultas
// redundantes a la BD cuando los controladores necesitan mapear usuario_id.
public class UsuarioPrincipal implements UserDetails {

	private final Long id;
	private final String email;
	private final String nombre;
	private final String passwordHash;
	private final Collection<? extends GrantedAuthority> authorities;

	public UsuarioPrincipal(Long id, String email, String nombre, String passwordHash,
			Collection<? extends GrantedAuthority> authorities) {
		this.id = id;
		this.email = email;
		this.nombre = nombre;
		this.passwordHash = passwordHash;
		this.authorities = authorities;
	}

	public Long getId() { return id; }
	public String getNombre() { return nombre; }

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return passwordHash;
	}

	@Override
	public String getUsername() {
		return email;
	}

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
}
