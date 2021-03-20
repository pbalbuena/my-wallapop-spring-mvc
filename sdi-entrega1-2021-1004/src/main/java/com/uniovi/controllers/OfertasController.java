package com.uniovi.controllers;

import java.security.Principal;
import java.util.LinkedList;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.uniovi.entities.Oferta;
import com.uniovi.entities.Usuario;
import com.uniovi.services.OfertaService;
import com.uniovi.services.UsuarioService;
import com.uniovi.validators.OfertaAddValidator;

@Controller
public class OfertasController {

	@Autowired // Inyectar el servicio
	private OfertaService ofertaService;

	@Autowired
	private UsuarioService usersService;

	@Autowired
	private OfertaAddValidator validator;

	@Autowired
	private HttpSession httpSession;

	/**
	 * Obtener la lista de ofertas propias + busqueda + paginacion
	 * @param model
	 * @param pageable
	 * @param principal
	 * @param searchText
	 * @return
	 */
	@RequestMapping("/oferta/listVender")
	public String getListVender(Model model, Pageable pageable, Principal principal, @RequestParam(value="", required = false) String searchText) {
		String email = principal.getName(); // DNI es el name de la autenticación
		Usuario user = usersService.getUserByEmail(email);
		Page<Oferta> marks = new PageImpl<Oferta>(new LinkedList<Oferta>());
		if(searchText!=null && !searchText.isEmpty()) {
			marks = ofertaService.searchMisOfertasByTituloOrDetalle(pageable, searchText, user);
		} else {
			marks = ofertaService.getOfertasForUser(pageable, user);
		}

		model.addAttribute("markList", marks.getContent());
		model.addAttribute("page", marks);

		return "oferta/listVender";
	}
	
	/**
	 * Obtemer la lista de otras ofertas para comprar + busqueda + paginacion
	 * @param model
	 * @param pageable
	 * @param principal
	 * @param searchText
	 * @return
	 */
	@RequestMapping("/oferta/listComprar")
	public String getListComprar(Model model, Pageable pageable, Principal principal, @RequestParam(value="", required = false) String searchText) {
		String email = principal.getName(); // DNI es el name de la autenticación
		Usuario user = usersService.getUserByEmail(email);
		Page<Oferta> marks = new PageImpl<Oferta>(new LinkedList<Oferta>());
		if(searchText!=null && !searchText.isEmpty()) {
			marks = ofertaService.searchOtrasOfertasByTituloOrDetalle(pageable, searchText, user);
		} else {
			marks = ofertaService.getOtrasOfertasForUser(pageable, user);
		}

		model.addAttribute("markList", marks.getContent());
		model.addAttribute("page", marks);

		return "oferta/listComprar";
	}

	@RequestMapping(value = "/oferta/add")
	public String getOferta(Model model) {
		model.addAttribute("usersList", usersService.getUsers());
		model.addAttribute("oferta", new Oferta());
		return "oferta/add";
	}

	@RequestMapping(value = "/oferta/add", method = RequestMethod.POST)
	public String setOferta(Model model, @Validated Oferta offer, BindingResult result, Principal principal) {
		validator.validate(offer, result);
		if (result.hasErrors()) {
			model.addAttribute("usersList", usersService.getUsers());
			return "oferta/add";
		}
		String email = principal.getName();
		Usuario actual = usersService.getUserByEmail(email);
		offer.setUsuario(actual);
		ofertaService.addOferta(offer);
		return "redirect:/oferta/listVender";
	}

	@RequestMapping("/oferta/delete/{id}")
	public String deleteOferta(@PathVariable Long id) {
		ofertaService.deleteOferta(id);
		return "redirect:/oferta/list";
	}

	@RequestMapping("/oferta/details/{id}")
	public String getDetail(Model model, @PathVariable Long id) {
		model.addAttribute("of", ofertaService.getOferta(id));
		return "oferta/details";
	}

	@RequestMapping(value = "/oferta/edit/{id}")
	public String getEdit(Model model, @PathVariable Long id) {
		model.addAttribute("of", ofertaService.getOferta(id));
		model.addAttribute("usersList", usersService.getUsers());
		return "oferta/edit";
	}

	@RequestMapping(value = "/oferta/edit/{id}", method = RequestMethod.POST)
	public String setEdit(Model model, @PathVariable Long id, @ModelAttribute Oferta mark) {
		Oferta original = ofertaService.getOferta(id);
		// modificar solo titulo, precio y detalle
		original.setTitulo(mark.getTitulo());
		original.setDetalle(mark.getDetalle());
		original.setPrecio(mark.getPrecio());
		ofertaService.addOferta(original);

		return "redirect:/oferta/details/" + id;
	}
	/*
	@RequestMapping("/mark/list/update")
	public String updateList(Model model, Pageable pageable, Principal principal) {
		String dni = principal.getName(); // DNI es el name de la autenticación
		User user = usersService.getUserByDni(dni);
		Page<Mark> marks = marksService.getMarksForUser(pageable, user);
		model.addAttribute("markList",marks);
		return "mark/list :: tableMarks";
	}
	*/

	@RequestMapping(value = "/oferta/{id}/resend", method = RequestMethod.GET)
	public String setVendidaTrue(Model model, @PathVariable Long id) {
		ofertaService.setOfertaVendida(true, id);
		return "redirect:/oferta/listComprar";
	}
	
	/*
	@RequestMapping(value = "/mark/{id}/noresend", method = RequestMethod.GET)
	public String setResendFalse(Model model, @PathVariable Long id) {
		ofertaService.setMarkResend(false, id);
		return "redirect:/mark/list";
	}
	*/
}
