package com.example.demo.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Cliente;
import com.example.demo.entity.Usuario;
import com.example.demo.projection.ClienteProjection;
import com.example.demo.services.ClienteService;
import com.example.demo.web.dto.ClienteCreateDto;
import com.example.demo.web.dto.ClienteResponseDto;
import com.example.demo.web.dto.PagebleDto;
import com.example.demo.web.dto.mapper.ClienteMapper;
import com.example.demo.web.dto.mapper.PagebleMapper;

import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;

@RestController
@RequestMapping("api/v1/clientes")
public class ClienteController {

	@Autowired
	private ClienteService clienteService;

	@PostMapping
	@PreAuthorize("hasRole('CLIENTE')")
	public ResponseEntity<ClienteResponseDto> create(@RequestBody @Valid ClienteCreateDto dto,
			@AuthenticationPrincipal Usuario userDetails) {
		Cliente cliente = ClienteMapper.toClienteDto(dto);
		cliente.setUsuario(userDetails);

		clienteService.salvar(cliente);
		return ResponseEntity.status(201).body(ClienteMapper.toDto(cliente));
	}

	@GetMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<ClienteResponseDto> getById(@PathVariable Long id) {

		Cliente cliente = clienteService.buscarPorId(id);
		return ResponseEntity.ok().body(ClienteMapper.toDto(cliente));
	}


	@GetMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<PagebleDto> getAll(@Parameter(hidden = true) @PageableDefault(size = 2, sort = {"nome"}) Pageable pageable) {

		Page<ClienteProjection> clientes = clienteService.buscarTodos(pageable);

		return ResponseEntity.ok().body(PagebleMapper.toDto(clientes));
	}


	@GetMapping("/detalhes")
	@PreAuthorize("hasRole('CLIENTE')")
	public ResponseEntity<ClienteResponseDto> getDetalhes(@AuthenticationPrincipal Usuario userDetails) {

		Cliente cliente = clienteService.buscarPorUsuarioId(userDetails.getId());

		return ResponseEntity.ok().body(ClienteMapper.toDto(cliente));
	}

}
