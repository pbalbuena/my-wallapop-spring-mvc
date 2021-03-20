package com.uniovi.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.uniovi.entities.Usuario;

public interface UsuarioRepository extends CrudRepository<Usuario, Long> {
	Usuario findByEmail(String dni);

	@Query("SELECT r from Usuario r WHERE (LOWER(r.name) LIKE LOWER(?1) or LOWER(r.lastName) LIKE LOWER(?1))")
	Page<Usuario> searchByNameAndLastname(Pageable p, String str);

	Page<Usuario> findAll(Pageable p);
	
	@Query("SELECT r from Usuario r WHERE r.rol = 'ROLE_USUARIO'")
	Page<Usuario> findAllStandardUsers(Pageable p);
	
	@Query("SELECT r from Usuario r WHERE r.rol = 'ROLE_USUARIO' and ( (LOWER(r.name) LIKE LOWER(?1) or LOWER(r.lastName) LIKE LOWER(?1)) )")
	Page<Usuario> searchStandardUsersByNameAndLastname(Pageable p, String str);
}
