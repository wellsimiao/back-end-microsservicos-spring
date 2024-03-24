package com.wellington.microsservico.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wellington.microsservico.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	User findByCpfAndKey(String cpf, String key);

	List<User> queryByNomeLike(String name);

}
