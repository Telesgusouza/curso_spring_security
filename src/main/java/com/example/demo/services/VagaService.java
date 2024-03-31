package com.example.demo.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.example.demo.Exceptions.EntityNotFoundException;
import com.example.demo.entity.Vaga;
import com.example.demo.entity.Vaga.StatusVaga;
import com.example.demo.repositories.VagaRepository;
import com.example.demo.web.Exceptions.CodigoUniqueViolationException;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class VagaService {

	@Autowired
	private VagaRepository vagaRepository;

	@Transactional
	public Vaga salvar(Vaga vaga) {
		try {
			return vagaRepository.save(vaga);
		} catch (DataIntegrityViolationException ex) {
			throw new CodigoUniqueViolationException(
					String.format("Vaga com código %s já cadastrada", vaga.getCodigo()));
		} catch (RuntimeException ex) {
			throw new RuntimeException(ex.getMessage());
		}

	}

	@Transactional
	public Vaga buscarPorCodigo(String codigo) {
		Optional<Vaga> obj = vagaRepository.findByCodigo(codigo);
		return obj.orElseThrow(
				() -> new EntityNotFoundException(String.format("Vaga com código %s não foi encontrada", codigo)));

	}

	public Vaga buscarPorVagaLivre() {
		// aqui estamos pegando as vagas dispoviveis, e procurando pelas vagas que tem o
		// campo status = enum como LIVRE
		return vagaRepository.findFirstByStatus(StatusVaga.LIVRE)
				.orElseThrow(() -> new EntityNotFoundException("Nenhuma vaga livre encontrada"));
	}

}
