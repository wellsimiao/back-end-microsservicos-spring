package com.wellington.microsservico.controller;

import java.util.List;

import java.time.LocalDateTime;

import org.hibernate.query.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;

	@GetMapping("/pageable")
	public Page<UserDTO> getUsersPage(Pageable pageable) {
		return userService.getAllPage(pageable);
	}

	@GetMapping
	public List<UserDTO> getUsers() {
		return userService.getAll();
	}

	@GetMapping("/{id}")
	public UserDTO findById(@PathVariable Long id) {
		return userService.findById(id);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public UserDTO newUser(@RequestBody UserDTO userDTO) {
		return userService.save(userDTO);
	}

	@PostMapping("/{id}")
	public UserDTO editUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
		return userService.editUser(id, userDTO);
	}

	@GetMapping("/{cpf}/cpf")
	public UserDTO findByCpf(@RequestParam(name = "key") String key, @PathVariable String cpf) {
		return userService.findByCpf(cpf, key);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) throws UserNotFoundException {
		userService.delete(id);
	}

	@GetMapping("/search")
	public List<UserDTO> queryByName(@RequestParam(name = "nome", required = true) String nome) {
		return userService.queryByName(nome);
	}

}
