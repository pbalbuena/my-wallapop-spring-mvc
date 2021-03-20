package com.uniovi.controllers;

import java.util.LinkedList;

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

import com.uniovi.entities.Usuario;
import com.uniovi.services.RolesService;
import com.uniovi.services.SecurityService;
import com.uniovi.services.UsuarioService;
import com.uniovi.validators.SignUpFormValidator;
import com.uniovi.validators.UserAddFormValidator;

@Controller
public class UsersController {
	@Autowired
	private UsuarioService usersService;

	@Autowired
	private SecurityService securityService;

	@Autowired
	private SignUpFormValidator signUpFormValidator;

	@Autowired
	private UserAddFormValidator userAddFormValidator;
	
	@Autowired
	private RolesService rolesService;

	@RequestMapping("/usuario/list")
	public String getListado(Model model, Pageable pageable, @RequestParam(value="", required = false) String searchText) {
		model.addAttribute("usersList", usersService.getUsers());
		Page<Usuario> users = new PageImpl<Usuario>(new LinkedList<Usuario>());
		if(searchText!=null && !searchText.isEmpty()) {
			users = usersService.searchUsersByNameAndLastname(pageable, searchText);
		} else {
			users = usersService.getUsers(pageable);
		}		
		
		model.addAttribute("usersList", users.getContent());
		model.addAttribute("page", users);
		return "usuario/list";
	}

	@RequestMapping(value = "/usuario/add")
	public String getUser(Model model) {
		model.addAttribute("user",new Usuario()); //para validacion
		model.addAttribute("rolesList", rolesService.getRoles());
		model.addAttribute("usersList", usersService.getUsers());
		return "user/add";
	}

	@RequestMapping(value = "/usuario/add", method = RequestMethod.POST)
	public String setUser(Model model, @Validated Usuario user, BindingResult result) {
		
		userAddFormValidator.validate(user, result);
		if (result.hasErrors()) {
			model.addAttribute("rolesList", rolesService.getRoles());
			return "usuario/add";
		}
		
		usersService.addUser(user);
		return "redirect:/usuario/list";
	}

	@RequestMapping("/usuario/details/{id}")
	public String getDetail(Model model, @PathVariable Long id) {
		model.addAttribute("user", usersService.getUser(id));
		return "usuario/details";
	}

	@RequestMapping("/usuario/delete/{id}")
	public String delete(@PathVariable Long id) {
		usersService.deleteUser(id);
		return "redirect:/usuario/list";
	}

	@RequestMapping(value = "/usuario/edit/{id}")
	public String getEdit(Model model, @PathVariable Long id) {
		Usuario user = usersService.getUser(id);
		model.addAttribute("user", user);
		return "usuario/edit";
	}

	@RequestMapping(value = "/usuario/edit/{id}", method = RequestMethod.POST)
	public String setEdit(Model model, @PathVariable Long id, @ModelAttribute Usuario user) {
		Usuario original = usersService.getUser(id);
		original.setEmail(user.getEmail());
		original.setName(user.getName());
		original.setLastName(user.getLastName());
		usersService.addUser(original);
		return "redirect:/usuario/details/" + id;
	}

	// students login
	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public String signup(Model model) {
		model.addAttribute("usuario", new Usuario());
		return "signup";
	}

	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public String signup(@Validated Usuario user, BindingResult result) {
		signUpFormValidator.validate(user, result);
		if (result.hasErrors()) {
			return "signup";
		}

		user.setRol(rolesService.getRoles()[0]);

		usersService.addUser(user);
		securityService.autoLogin(user.getEmail(), user.getPasswordConfirm());
		return "redirect:home";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(Model model) {
		return "login";
	}

	@RequestMapping(value = { "/home" }, method = RequestMethod.GET)
	public String home(Model model) {
		//Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		//String email = auth.getName();
		//Usuario activeUser = usersService.getUserByEmail(email);
		//model.addAttribute("markList", activeUser.getMarks());
		return "home";
	}

	@RequestMapping("/usuario/list/update")
	public String updateList(Model model) {
		model.addAttribute("usersList", usersService.getUsers());
		return "usuario/list :: tableUsers";
	}

}