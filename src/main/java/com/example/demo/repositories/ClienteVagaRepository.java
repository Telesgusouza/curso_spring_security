package com.example.demo.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.ClinteVaga;
import com.example.demo.projection.ClienteVagaProjection;

public interface ClienteVagaRepository extends JpaRepository<ClinteVaga, Long> {

	Optional<ClinteVaga> findByReciboAndDataSaidaIsNull(String recibo);

	long countByClienteCpfAndDataSaidaIsNotNull(String cpf);

	Page<ClienteVagaProjection> findAllByClienteCpf(String cpf, Pageable pageable);

	Page<ClienteVagaProjection> findAllByClienteUsuarioId(Long id, Pageable pageable);

}
