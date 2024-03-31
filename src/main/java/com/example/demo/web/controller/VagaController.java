package com.example.demo.web.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.demo.entity.Vaga;
import com.example.demo.services.VagaService;
import com.example.demo.web.dto.VagaCreateDto;
import com.example.demo.web.dto.VagaCreateDto1;
import com.example.demo.web.dto.VagaResponseDto;
import com.example.demo.web.dto.mapper.VagaMapper;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Vagas", description = "vagas do estacionamento")
@RestController
@RequestMapping("api/v1/vagas")
public class VagaController {

	@Autowired
	private VagaService vagaService;

	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping
	public ResponseEntity<VagaCreateDto1> create(@RequestBody @Valid VagaCreateDto dto) {

		Vaga vaga = VagaMapper.toVaga(dto);
		vagaService.salvar(vaga);

		URI location = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{codigo}")
				.buildAndExpand(vaga.getCodigo()).toUri();

		return ResponseEntity.created(location).build();
	}

	@GetMapping("/{codigo}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<VagaResponseDto> getByCode(@PathVariable String codigo) {
		Vaga vaga = vagaService.buscarPorCodigo(codigo);

		return ResponseEntity.ok().body(VagaMapper.toDto(vaga));
	}

}
