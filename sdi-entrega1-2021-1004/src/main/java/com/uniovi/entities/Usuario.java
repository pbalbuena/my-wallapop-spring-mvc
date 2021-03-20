package com.uniovi.entities;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="usuario")
public class Usuario {
	@Id @GeneratedValue
	private Long id;
	

	@Column(unique = true)
	private String email;
	private String name;
	private String lastName;
	private String password;
	@Transient
	private String passwordConfirm;
	private float money;
	
	@OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
	private Set<Oferta> ofertas;
	
	@OneToMany(mappedBy = "u1", cascade = CascadeType.ALL)
	private Set<Conversacion> conversaciones;
	
	@OneToMany(mappedBy = "autor", cascade = CascadeType.ALL)
	private Set<Mensaje> mensajes;
	
	
	//rol administrador o usuario
	private String rol;
	
	
	public Usuario() {
		
	}
	
	public Usuario(String email, String name, String lastName, String password) {
		super();
		this.email = email;
		this.name = name;
		this.lastName = lastName;
		this.password = password;
	}
	
	public Usuario(String email, String name, String lastName, String password, String passwordConfirm) {
		super();
		this.email = email;
		this.name = name;
		this.lastName = lastName;
		this.password = password;
		this.passwordConfirm = passwordConfirm;
	}
	
	public Usuario(String email, String name, String lastName, String password, String passwordConfirm, float money) {
		super();
		this.email = email;
		this.name = name;
		this.lastName = lastName;
		this.password = password;
		this.passwordConfirm = passwordConfirm;
		this.money = money;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPasswordConfirm() {
		return passwordConfirm;
	}
	public void setPasswordConfirm(String passwordConfirm) {
		this.passwordConfirm = passwordConfirm;
	}
	public float getMoney() {
		return money;
	}
	public void setMoney(float money) {
		this.money = money;
	}

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}

	public Set<Oferta> getOfertas() {
		return ofertas;
	}

	public void setOfertas(Set<Oferta> ofertas) {
		this.ofertas = ofertas;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Set<Conversacion> getConversaciones() {
		return conversaciones;
	}

	public void setConversaciones(Set<Conversacion> conversaciones) {
		this.conversaciones = conversaciones;
	}
	
}
