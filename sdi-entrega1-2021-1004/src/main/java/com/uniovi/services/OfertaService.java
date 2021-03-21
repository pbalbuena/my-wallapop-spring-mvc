package com.uniovi.services;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.uniovi.entities.Oferta;
import com.uniovi.entities.Usuario;
import com.uniovi.repositories.OfertaRepository;
import com.uniovi.repositories.UsuarioRepository;

@Service
public class OfertaService {
	@Autowired
	private OfertaRepository ofertaRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;

	public Page<Oferta> getOfertas(Pageable p) {
		Page<Oferta> ofs = ofertaRepository.findAll(p);
		return ofs;
	}
	
	public List<Oferta> getOfertas() {
		List<Oferta> ofs = new ArrayList<Oferta>();
		ofertaRepository.findAll().forEach(ofs::add);
		return ofs;
	}

	public Oferta getOferta(Long id) {
		return ofertaRepository.findById(id).get();
	}
	

	public void addOferta(Oferta of) {
		ofertaRepository.save(of);
	}

	public void deleteOferta(Long id) {
		ofertaRepository.deleteById(id);
	}
	
	
	/**
	 * Obtener MIS ofertas SIN buscar
	 * @param p
	 * @param user
	 * @return
	 */
	public Page<Oferta> getOfertasForUser(Pageable p, Usuario user) {
		Page<Oferta> marks = new PageImpl<Oferta>(new LinkedList<Oferta>());
		
		if (user.getRol().equals("ROLE_USUARIO")) {
			marks = ofertaRepository.findAllByUser(p, user);
		}
		if (user.getRol().equals("ROLE_ADMIN")) {
			marks = getOfertas(p);
		}
		return marks;
	}
	
	/**
	 * Buscar MIS ofertas
	 * @param p
	 * @param searchText
	 * @param user
	 * @return
	 */
	public Page<Oferta> searchMisOfertasByTituloOrDetalle (Pageable p, String searchText, Usuario user){
		Page<Oferta> marks = new PageImpl<Oferta>(new LinkedList<Oferta>());
		searchText = "%"+searchText+"%";

		if ( user.getRol().equals("ROLE_USUARIO")) {
		marks = ofertaRepository.searchByTituloOrDetalleAndUser(p, searchText, user);
		}
		if ( user.getRol().equals("ROLE_ADMIN")){
		marks = ofertaRepository.searchByTituloOrDetalle(p, searchText);
		}
		return marks;
	}
	
	
	/**
	 * Obtener ofertas de OTROS USUARIOS para comprar SIN BUSCAR 
	 * @param p
	 * @param user
	 * @return
	 */
	public Page<Oferta> getOtrasOfertasForUser(Pageable p, Usuario user) {
		Page<Oferta> marks = new PageImpl<Oferta>(new LinkedList<Oferta>());
		marks = ofertaRepository.findAllOtherOffers(p, user);
		return marks;
	}
	
	/**
	 * Buscar ofertas para COMPRAR
	 * @param p
	 * @param searchText
	 * @param user
	 * @return
	 */
	public Page<Oferta> searchOtrasOfertasByTituloOrDetalle (Pageable p, String searchText, Usuario user){
		Page<Oferta> marks = new PageImpl<Oferta>(new LinkedList<Oferta>());
		searchText = "%"+searchText+"%";

		marks = ofertaRepository.searchOtherOffersByTituloOrDetalle(p, searchText, user);
		return marks;
	}

	/**
	 * marcar una oferta como vendida si el usuario tiene suficiente dinero
	 * @param b
	 * @param id
	 */
	public boolean setOfertaVendida(boolean b, Long id) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		Oferta of = ofertaRepository.findById(id).get();
		Usuario actual = usuarioRepository.findByEmail(email);
		if(actual.getMoney() >= of.getPrecio()) {
			ofertaRepository.setComprador(id, actual);
			ofertaRepository.updateVenta(b, id);
			usuarioRepository.updateMonederoFromUsuario(of.getPrecio(), actual.getId());
			System.out.println(actual.getMoney());
			return true;
		} else {
			System.out.println("El usuario no tiene suficiente dinero");
			return false;
		}
	}

	/**
	 * busca las ofertas que ha comprado un usuario
	 * @param pageable
	 * @param user
	 * @return
	 */
	public Page<Oferta> getOfertasCompradasForUser(Pageable pageable, Usuario user) {
		Page<Oferta> marks = new PageImpl<Oferta>(new LinkedList<Oferta>());
		marks = ofertaRepository.findOfertasCompradasForUser(pageable, user);
		return marks;
	}	
	
	
	
}
