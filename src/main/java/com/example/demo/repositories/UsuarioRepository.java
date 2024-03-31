package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.demo.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

//	Optional<Usuario> findByUsername(String username);

	UserDetails findByUsername(String username);

	@Query("select u.role from Usuario u where u.username like :username")
	Usuario.Role findRoleByUsername(String username);

}
