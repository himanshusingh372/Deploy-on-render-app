package com.example.blog.entities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@SuppressWarnings("serial")
@Entity
@Table(name = "users")
@NoArgsConstructor
@Getter
@Setter

public class User implements UserDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO) // auto is also usable
	private Integer id;

	@Column(name = "name", nullable = false, length = 100)
	private String name;

	@Column(nullable = false, unique = true)
	
	private String email;

	@Column(name = "password", nullable = false, length = 100)
	private String password;

	private String about;
//
//	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//	private List<Post> posts = new ArrayList<>();
//	
//	@ManyToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
//	@JoinTable(name="user_role",
//	joinColumns = @JoinColumn(name="user", referencedColumnName="id"),
//	inverseJoinColumns = @JoinColumn(name="role", referencedColumnName="roleId")
//	)
//	private Set<Role> roles=new HashSet<>();
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<Post> posts = new ArrayList<>();

	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.EAGER)
	@JoinTable(
	    name = "user_role",
	    joinColumns = @JoinColumn(name = "user"),
	    inverseJoinColumns = @JoinColumn(name = "role")
	)
	private Set<Role> roles = new HashSet<>();

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		 List<SimpleGrantedAuthority> auth=this.roles.stream()
		.map((role)->new SimpleGrantedAuthority(role.getName()))
		.collect(Collectors.toList());
		return auth;
	} 

	@Override
	public String getUsername() {
		return this.getEmail();
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
