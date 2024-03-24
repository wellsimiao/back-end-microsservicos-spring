package com.wellington.microsservico.service;

import lombok.RequiredArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.wellington.microsservico.converter.DTOConverter;
import com.wellington.microsservico.model.User;
import com.wellington.microsservico.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserConstructor {

	private final UserRepository userRepository;

	public List<UserDTO> getAll() {

		List<User> users = userRepository.findAll();
		return users.stream().map(DTOConverter::convert).collect(Collectors.toList());

	}

	public UserDTO findById(long userId) {
		User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException());
		return DTOConverter.convert(user);
	}

	public UserDTO save(UserDTO userDTO) {
		userDTO.setKey(UUID.randomUUID().toString());
		userDTO.setDataCadastro(LocalDateTime.now());
		User user = userRepository.save(User.convert(userDTO));
		return DTOConverter.convert(user);
	}

	public void delete(long userId) throws UserNotFoundException {
		User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException());
		userRepository.delete(user);
	}

	public UserDTO findByCpf(String cpf, String key) {
		User user = userRepository.findByCpfAndKey(cpf, key);
		if (user != null) {
			return DTOConverter.convert(user);
		}
		throw new UserNotFoundException();
	}

	public List<UserDTO> queryByName(String name) {
		List<User> usuarios = userRepository.queryByNomeLike(name);
		return usuarios.stream().map(DTOConverter::convert).collect(Collectors.toList());
	}
	public UserDTO editUser(Long userId, UserDTO userDTO) {
		User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException());
		if (userDTO.getEmail() != null && !user.getEmail().equals(userDTO.getEmail())) {
			user.setEmail(userDTO.getEmail());
		}
		if (userDTO.getTelefone() != null && !user.getTelefone().equals(userDTO.getTelefone())) {
			user.setTelefone(userDTO.getTelefone());
		}
		if (userDTO.getEndereco() != null && !user.getEndereco().equals(userDTO.getEndereco())) {
			user.setEndereco(userDTO.getEndereco());
		}

		user = userRepository.save(user);
		return DTOConverter.convert(user);
	}
}

