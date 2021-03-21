package com.uniovi.repositories;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.uniovi.entities.Oferta;
import com.uniovi.entities.Usuario;

public interface OfertaRepository extends CrudRepository<Oferta, Long>{
	
	@Modifying
	@Transactional
	@Query("UPDATE Oferta SET vendido = ?1 WHERE id = ?2")
	void updateVenta(Boolean resend, Long id);
	
	@Modifying
	@Transactional
	@Query("UPDATE Oferta SET comprador = ?2 WHERE id = ?1")
	void setComprador(Long idOferta, Usuario comprador);
	
	Page<Oferta> findAll(Pageable p);
	
	@Query("SELECT r FROM Oferta r WHERE r.usuario = ?1 ORDER BY r.id ASC ")
	Page<Oferta> findAllByUser(Pageable p, Usuario user);
	
	@Query("SELECT r from Oferta r WHERE (LOWER(r.titulo) LIKE LOWER(?1) or LOWER(r.detalle) LIKE LOWER(?1))")
	Page<Oferta> searchByTituloOrDetalle(Pageable p, String str);
	
	@Query("SELECT r from Oferta r WHERE (LOWER(r.titulo) LIKE LOWER(?1) or LOWER(r.detalle) LIKE LOWER(?1)) AND r.usuario =?2")
	Page<Oferta> searchByTituloOrDetalleAndUser(Pageable p, String str, Usuario user);
	
	@Query("SELECT r FROM Oferta r WHERE r.usuario != ?1 ORDER BY r.id ASC ")
	Page<Oferta> findAllOtherOffers(Pageable p, Usuario user);
	
	@Query("SELECT r from Oferta r WHERE (LOWER(r.titulo) LIKE LOWER(?1) or LOWER(r.detalle) LIKE LOWER(?1)) AND r.usuario !=?2")
	Page<Oferta> searchOtherOffersByTituloOrDetalle(Pageable p, String str, Usuario user);

	@Query("SELECT r FROM Oferta r WHERE r.comprador = ?1 ORDER BY r.id ASC ")
	Page<Oferta> findOfertasCompradasForUser(Pageable pageable, Usuario user);


	
}
