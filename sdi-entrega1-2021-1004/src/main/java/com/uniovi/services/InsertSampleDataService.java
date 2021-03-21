package com.uniovi.services;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uniovi.entities.Oferta;
import com.uniovi.entities.Usuario;

@Service
public class InsertSampleDataService {

	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private RolesService rolesService;
	
	/*
	@Autowired
	private MensajeService mensajeService;
	
	@Autowired
	private OfertaService ofertaService;
	
	@Autowired
	private ConversacionService conversacionService;
	*/
	
	public void deleteAll() {
		usuarioService.deleteAllUsers();
	}
	
	@PostConstruct
	public void init() {
		
		Usuario admin = new Usuario("admin@email.com", "Ad", "Min", "admin");
		admin.setRol(rolesService.roles[1]);
		Usuario u1 = new Usuario("uo239394@uniovi.es", "Pablo", "Glez", "123456");
		System.out.println(u1.getMoney());
		u1.setRol(rolesService.roles[0]);
		Usuario u2 = new Usuario("uo111111@uniovi.es", "Juan", "Martinez", "123456");
		u2.setRol(rolesService.roles[0]);
		Usuario u3 = new Usuario("uo222222@uniovi.es", "Pedro", "Garcia", "123456");
		u3.setRol(rolesService.roles[0]);
		Usuario u4 = new Usuario("uo333333@uniovi.es", "María", "Herrero", "123456");
		u4.setRol(rolesService.roles[0]);
		Usuario u5 = new Usuario("uo444444@uniovi.es", "Laura", "Ramos", "123456");
		u5.setRol(rolesService.roles[0]);
		
		Oferta o1 = new Oferta("Pelota de baloncesto", "Buen estado", 10);
		Oferta o2 = new Oferta("Coche de rally", "Usado", 4500);
		Oferta o3 = new Oferta("Vestido de boda", "Como nuevo", 100);
		Oferta o4 = new Oferta("Cosa", "Como nuevo", 1);
		Oferta o5 = new Oferta("Móvil", "Está mejorable", 5);
		o5.setVendido(true);
		Oferta o6 = new Oferta("Cartera", "No me gusta", 7);
		Oferta o7 = new Oferta("Llaves", "Es feo", 23);
		
		Set<Oferta> ofs = new HashSet<Oferta>();
		
		ofs.add(o1);
		ofs.add(o2);
		ofs.add(o3);
		ofs.add(o4);
		ofs.add(o5);
		ofs.add(o6);
		ofs.add(o7);
		
		for (Oferta oferta : ofs) {
			oferta.setUsuario(u1);
		}
		
		u1.setOfertas(ofs);
		
		usuarioService.addUser(admin);
		usuarioService.addUser(u1);
		usuarioService.addUser(u2);
		usuarioService.addUser(u3);
		usuarioService.addUser(u4);
		usuarioService.addUser(u5);
	}
}
