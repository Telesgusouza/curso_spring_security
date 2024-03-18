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
import com.example.demo.web.Exceptions.ErrorMessage;
import com.example.demo.web.dto.UsuarioCretedDTO;
import com.example.demo.web.dto.UsuarioResponseDto;
import com.example.demo.web.dto.UsuarioSenhaDto;
import com.example.demo.web.dto.mapper.UsuarioMapper;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "Usuario", description = "Contemm todas as operações relativas aos recursos para cadastro, edição e leitura de um usuario")
@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/usuarios")
public class UsuarioController {

	// ADICIONAR O FINAL final
	@Autowired
	private UsuariosService usuariosService;

	// summary - um resumo do que ele fará
	// description - uma descrição do que ele fara
	// responses- se trata das respostas do recurso
	@Operation(summary = "Criar novo usuario", description = "Recurso para criar um novo usuario", responses = {

			// caso de sucesso
			@ApiResponse(responseCode = "201", description = "Recurso criado com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioResponseDto.class))),

			// caso de erro
			/* ele pode gerar uam resposta de erro */
			@ApiResponse(responseCode = "409", description = "Usuario e-mail já cadastrado no sistema", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),

			// caso de erro
			@ApiResponse(responseCode = "422", description = "Recurso não processado por dados de entrada invalidos", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))) })
	@PostMapping
	public ResponseEntity<UsuarioResponseDto> create(@Valid @RequestBody UsuarioCretedDTO cretedDTO) {

		Usuario user = usuariosService.salvar(UsuarioMapper.toUsuario(cretedDTO));
		return ResponseEntity.status(HttpStatus.CREATED).body(UsuarioMapper.toDto(user));
	}

	@Operation(summary = "Recuperar usuario por id", description = "Recuperar usuario por id", responses = {

			// caso de sucesso
			@ApiResponse(responseCode = "201", description = "Recurso recuperado com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioResponseDto.class))),

			// caso de erro
			@ApiResponse(responseCode = "404", description = "Recurso não encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))) })

	@GetMapping("/{id}")
	public ResponseEntity<UsuarioResponseDto> getById(@PathVariable Long id) {
		Usuario user = usuariosService.buscarPorId(id);
		return ResponseEntity.status(HttpStatus.OK).body(UsuarioMapper.toDto(user));
	}
	
	@Operation(summary = "Atualizar senha", description = "Atualizar senha por id", responses = {

			// caso de sucesso
			@ApiResponse(responseCode = "204", 
					description = "Senha atualizada com sucesso", 
					content = @Content(mediaType = "application/json", schema = @Schema(implementation = void.class))),

			// caso de erro
			@ApiResponse(responseCode = "404", description = "Recurso não encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
			// erro
			@ApiResponse(responseCode = "400", description = "Senha não confere", 
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))})

	@PatchMapping("/{id}")
	public ResponseEntity<UsuarioResponseDto> updatePassword(@PathVariable Long id,
			@Valid @RequestBody UsuarioSenhaDto dto) {
		Usuario user = usuariosService.editarSenha(id, dto.getSenhaAtual(), dto.getNovaSenha(), dto.getConfimaSenha());
		return ResponseEntity.status(HttpStatus.OK).body(UsuarioMapper.toDto(user));
	}

	@GetMapping
	public ResponseEntity<List<UsuarioResponseDto>> getAll() {
		List<Usuario> users = usuariosService.buscarTodos();
		return ResponseEntity.status(HttpStatus.OK).body(UsuarioMapper.toListDTO(users));
	}

}
