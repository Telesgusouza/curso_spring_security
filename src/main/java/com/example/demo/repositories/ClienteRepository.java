package com.example.demo.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.entity.Cliente;
import com.example.demo.projection.ClienteProjection;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

	@Query("SELECT c FROM Cliente c")
	Page<ClienteProjection> findAllPageable(Pageable pageable);

	Cliente findByUsuarioId(Long id);
	
	Optional<Cliente> findByCpf(String cpf);

}
