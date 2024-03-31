package com.example.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.Exceptions.EntityNotFoundException;
import com.example.demo.entity.ClinteVaga;
import com.example.demo.projection.ClienteVagaProjection;
import com.example.demo.repositories.ClienteVagaRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ClienteVagaService {

	@Autowired
	private ClienteVagaRepository repo;

	public ClinteVaga salvar(ClinteVaga clienteVaga) {
		return repo.save(clienteVaga);
	}

	public ClinteVaga buscarPorRecibo(String recibo) {

		return repo.findByReciboAndDataSaidaIsNull(recibo).orElseThrow(
				() -> new EntityNotFoundException("Recibo não encontrado no sistema ou check-out já realizado"));
	}

	public long getTotalDeVezesEstacionamentoCompleto(String cpf) {
		return repo.countByClienteCpfAndDataSaidaIsNotNull(cpf);
	}

	public Page<ClienteVagaProjection> buscarTodosPorClienteCpf(String cpf, Pageable pageable) {
		// TODO Auto-generated method stub
		return repo.findAllByClienteCpf(cpf, pageable);
	}

	public Page<ClienteVagaProjection> buscarTodosPorUsuarioId(Long id, Pageable pageable) {
		
		return repo.findAllByClienteUsuarioId(id, pageable);
	}

}
