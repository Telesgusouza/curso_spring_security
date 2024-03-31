package com.example.demo.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.Exceptions.CpfUniqueViolationException;
import com.example.demo.Exceptions.EntityNotFoundException;
import com.example.demo.entity.Cliente;
import com.example.demo.projection.ClienteProjection;
import com.example.demo.repositories.ClienteRepository;

import jakarta.transaction.Transactional;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;

	@Transactional
	public Cliente salvar(Cliente cliente) {
		try {
			return clienteRepository.save(cliente);
		} catch (DataIntegrityViolationException ex) {
			throw new CpfUniqueViolationException(
					String.format("CPF '%s' não pode ser cadastrado, já existe no sistema ", cliente.getCpf()));
		}

	}

	@Transactional
	public Cliente buscarPorId(Long id) {

		Optional<Cliente> obj = clienteRepository.findById(id);
		return obj.orElseThrow(
				() -> new EntityNotFoundException(String.format("Cliente id=%s não encontrado no sistema ", id)));
	}

	@Transactional
	public Page<ClienteProjection> buscarTodos(Pageable pageable) {
		return clienteRepository.findAllPageable(pageable);
	}

	@Transactional
	public Cliente buscarPorUsuarioId(Long id) {
		return clienteRepository.findByUsuarioId(id);
	}

	public Cliente BuscarPorCpf(String cpf) {
		return clienteRepository.findByCpf(cpf).orElseThrow(() -> new EntityNotFoundException(String.format("Cliente '%s' não encontrado", cpf)));
	}

}
