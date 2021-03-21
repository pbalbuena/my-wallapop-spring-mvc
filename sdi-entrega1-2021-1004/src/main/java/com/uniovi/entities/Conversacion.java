package com.uniovi.entities;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Conversacion {
	@Id @GeneratedValue
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "usuario_id")
	private Usuario u1;
	
	
	@ManyToOne
	@JoinColumn(name = "usuario_id_2")
	private Usuario u2;
	
	
	@ManyToOne
	@JoinColumn(name = "oferta_id")
	private Oferta oferta;
	
	@OneToMany(mappedBy = "conversacion", cascade = CascadeType.ALL)
	private Set<Mensaje> mensajes;

	
	
	public Conversacion() {
		
	}
	
	public Usuario getU1() {
		return u1;
	}

	public void setU1(Usuario u1) {
		this.u1 = u1;
	}

	public Oferta getOferta() {
		return oferta;
	}

	public void setOferta(Oferta oferta) {
		this.oferta = oferta;
	}

	public Set<Mensaje> getMensajes() {
		return mensajes;
	}

	public void setMensajes(Set<Mensaje> mensajes) {
		this.mensajes = mensajes;
	}
	
	
	
}
