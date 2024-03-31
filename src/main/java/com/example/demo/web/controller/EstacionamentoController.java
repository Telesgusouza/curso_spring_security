package com.example.demo.web.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.demo.entity.ClinteVaga;
import com.example.demo.entity.Usuario;
import com.example.demo.projection.ClienteVagaProjection;
import com.example.demo.services.ClienteVagaService;
import com.example.demo.services.EstacionamentoService;
import com.example.demo.web.dto.EstacionamentoCreateDto;
import com.example.demo.web.dto.EstacionamentoResponseDto;
import com.example.demo.web.dto.PagebleDto;
import com.example.demo.web.dto.mapper.ClienteVagaMapper;
import com.example.demo.web.dto.mapper.PagebleMapper;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/v1/estacionamentos")
public class EstacionamentoController {

	// 20240329-173514

	@Autowired
	private EstacionamentoService estacionamentoService;

	@Autowired
	private ClienteVagaService clienteVagaService;

	@PostMapping("/check-in")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<EstacionamentoResponseDto> checkin(@RequestBody @Valid EstacionamentoCreateDto dto) {

		System.out.println(dto);

		ClinteVaga clienteVaga = ClienteVagaMapper.toClienteVaga(dto);
		estacionamentoService.checkIn(clienteVaga);
		EstacionamentoResponseDto response = ClienteVagaMapper.toDto(clienteVaga);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{recibo}")
				.buildAndExpand(clienteVaga.getRecibo()).toUri();

		return ResponseEntity.created(uri).body(response);
	}

	@GetMapping("/check-in/{recibo}")
	@PreAuthorize("hasAnyRole('ADMIN', 'CLIENTE')")
	public ResponseEntity<EstacionamentoResponseDto> getByRecibo(@PathVariable String recibo) {

		ClinteVaga clienteVaga = clienteVagaService.buscarPorRecibo(recibo);
		EstacionamentoResponseDto dto = ClienteVagaMapper.toDto(clienteVaga);
		return ResponseEntity.ok().body(dto);
	}

	@PutMapping("/check-out/{recibo}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<EstacionamentoResponseDto> checkOut(@PathVariable String recibo) {

		ClinteVaga clienteVaga = estacionamentoService.checkOut(recibo);
		EstacionamentoResponseDto dto = ClienteVagaMapper.toDto(clienteVaga);
		return ResponseEntity.ok().body(dto);
	}

	@GetMapping("/cpf/{cpf}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<PagebleDto> getAllEstacionamentosPorCpf(@PathVariable String cpf,
			@PageableDefault(size = 5, sort = "dataEntrada", direction = Sort.Direction.ASC) Pageable pageable) {

		Page<ClienteVagaProjection> projection = clienteVagaService.buscarTodosPorClienteCpf(cpf, pageable);
		PagebleDto dto = PagebleMapper.toDto(projection);

		return ResponseEntity.ok(dto);
	}

	@GetMapping
	@PreAuthorize("hasRole('CLIENTE')")
	public ResponseEntity<PagebleDto> getAllEstacionamentosDoCliente(@AuthenticationPrincipal Usuario user,
			@PageableDefault(size = 5, sort = "dataEntrada", direction = Sort.Direction.ASC) Pageable pageable) {

		Page<ClienteVagaProjection> projection = clienteVagaService.buscarTodosPorUsuarioId(user.getId(), pageable);
		PagebleDto dto = PagebleMapper.toDto(projection);

		return ResponseEntity.ok(dto);
	}

}
