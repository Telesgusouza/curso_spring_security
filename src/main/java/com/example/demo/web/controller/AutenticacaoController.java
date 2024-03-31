package com.example.demo.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.services.UsuariosService;
import com.example.demo.web.dto.UsuarioLoginDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Authenticação (ocorreu um erro está rota está invalida)", description = "Rota está invalida")
@RestController
@RequestMapping("/api/v1")
public class AutenticacaoController {

	@Autowired
	private UsuariosService usuariosService;

//	@Autowired
//	private AuthenticationManager authenticationManager;
//
//	@Autowired
//	private TokenService tokenService;

	@Operation(summary = "(rota invalida)", description = "rota invalida")
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody @Valid UsuarioLoginDto data) {

		System.out.println("=======================");
		System.out.println(data);
		System.out.println(data.username());
		System.out.println(data.password());

		return ResponseEntity.noContent().build();
	}

}
