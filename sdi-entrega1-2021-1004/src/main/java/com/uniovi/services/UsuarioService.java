package com.uniovi.services;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.uniovi.entities.Usuario;
import com.uniovi.repositories.UsuarioRepository;

@Service
public class UsuarioService {
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public Page<Usuario> getUsers(Pageable p) {
		Page<Usuario> users = usuarioRepository.findAllStandardUsers(p);
		return users;
	}
	
	public List<Usuario> getUsers() {
		List<Usuario> users = new ArrayList<Usuario>();
		usuarioRepository.findAll().forEach(users::add);
		return users;
	}

	public Usuario getUser(Long id) {
		return usuarioRepository.findById(id).get();
	}
	
	public Usuario getUserByEmail(String email) {
		return usuarioRepository.findByEmail(email);
		}

	public void addUser(Usuario user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		usuarioRepository.save(user);
	}

	public void deleteUser(Long id) {
		usuarioRepository.deleteById(id);
	}
	
	public Page<Usuario> searchUsersByNameAndLastname (Pageable p, String searchText){
		Page<Usuario> users = new PageImpl<Usuario>(new LinkedList<Usuario>());
		searchText = "%"+searchText+"%";
		users = usuarioRepository.searchStandardUsersByNameAndLastname(p, searchText);
		return users;
	}

	public void deleteAllUsers() {
		usuarioRepository.deleteAll();
	}

	
}
