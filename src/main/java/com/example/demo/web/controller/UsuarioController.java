package com.example.demo.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Usuario;
import com.example.demo.services.UsuariosService;
import com.example.demo.web.dto.UsuarioCretedDTO;
import com.example.demo.web.dto.UsuarioResponseDto;
import com.example.demo.web.dto.mapper.UsuarioMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/usuarios")
public class UsuarioController {

	// ADICIONAR O FINAL final
	@Autowired
	private UsuariosService usuariosService;

	@PostMapping
	public ResponseEntity<UsuarioResponseDto> create(@RequestBody UsuarioCretedDTO cretedDTO) {
		Usuario user = usuariosService.salvar(UsuarioMapper.toUsuario(cretedDTO));
		return ResponseEntity.status(HttpStatus.CREATED).body(UsuarioMapper.toDto(user));
	}

	@GetMapping("/{id}")
	public ResponseEntity<Usuario> getById(@PathVariable Long id) {
		Usuario user = usuariosService.buscarPorId(id);
		return ResponseEntity.status(HttpStatus.OK).body(user);
	}

	@PatchMapping("/{id}")
	public ResponseEntity<Usuario> updatePassword(@PathVariable Long id, @RequestBody Usuario usuario) {
		Usuario user = usuariosService.editarSenha(id, usuario.getPassword());
		return ResponseEntity.status(HttpStatus.OK).body(user);
	}

	@GetMapping
	public ResponseEntity<List<Usuario>> getAll() {
		List<Usuario> users = usuariosService.buscarTodos();
		return ResponseEntity.status(HttpStatus.OK).body(users);
	}

}
