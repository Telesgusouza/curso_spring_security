package com.example.demo.web.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.demo.entity.Usuario;
import com.example.demo.entity.Vaga;
import com.example.demo.jwt.TokenService;
import com.example.demo.services.UsuariosService;
import com.example.demo.services.VagaService;
import com.example.demo.web.Exceptions.ErrorMessage;
import com.example.demo.web.dto.LoginResponseDTO;
import com.example.demo.web.dto.UsuarioCretedDTO;
import com.example.demo.web.dto.UsuarioLoginDto;
import com.example.demo.web.dto.UsuarioResponseDto;
import com.example.demo.web.dto.UsuarioSenha1Dto;
import com.example.demo.web.dto.VagaCreateDto;
import com.example.demo.web.dto.VagaCreateDto1;
import com.example.demo.web.dto.VagaResponseDto;
import com.example.demo.web.dto.mapper.UsuarioMapper;
import com.example.demo.web.dto.mapper.VagaMapper;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Usuario", description = "Contemm todas as operações relativas aos recursos para cadastro, edição e leitura de um usuario")
@RestController
@RequestMapping("api/v1/usuarios")
public class UsuarioController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private TokenService tokenService;

	@Autowired
	private VagaService vagaService;

	@Autowired
	private UsuariosService usuariosService;

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

	@Operation(summary = "Recuperar usuario por id", description = "Recuperar usuario por id", security = @SecurityRequirement(name = "security"), responses = {

			// caso de sucesso
			@ApiResponse(responseCode = "201", description = "Recurso recuperado com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioResponseDto.class))),

			// caso de erro
			@ApiResponse(responseCode = "403", description = "Usuario sem permissão para acessar este conteudo.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),

			// caso de erro
			@ApiResponse(responseCode = "404", description = "Recurso não encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))

	})

	@GetMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN') OR ( hasRole('CLIENTE') AND #id == authentication.principal.id)") // apenas admins
																										// tem permissão
																										// para está
																										// rota
	public ResponseEntity<UsuarioResponseDto> getById(@PathVariable Long id) {
		Usuario user = usuariosService.buscarPorId(id);
		return ResponseEntity.status(HttpStatus.OK).body(UsuarioMapper.toDto(user));
	}

	@Operation(summary = "Atualizar senha", description = "Atualizar senha por id", security = @SecurityRequirement(name = "security"), responses = {

			// caso de sucesso
			@ApiResponse(responseCode = "204", description = "Senha atualizada com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = void.class))),

			// caso de erro
			@ApiResponse(responseCode = "403", description = "Usuario sem permissão para acessar este conteudo.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),

			// caso de erro
			@ApiResponse(responseCode = "404", description = "Recurso não encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
			// erro
			@ApiResponse(responseCode = "400", description = "Senha não confere", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))) })

	@PatchMapping("/{id}")
	@PreAuthorize("hasAnyRole('ADMIN', 'CLIENTE') AND (#id == authentication.principal.id)")
	public ResponseEntity<UsuarioResponseDto> updatePassword(@PathVariable Long id,
			@Valid @RequestBody UsuarioSenha1Dto dto) {

		Usuario user = usuariosService.editarSenha(id, dto.senhaAtual(), dto.novaSenha(), dto.confirmaSenha());
		return ResponseEntity.status(HttpStatus.OK).body(UsuarioMapper.toDto(user));
	}

	@Operation(summary = "Listar todos os usuarios", description = "Listar todos os usuarios cadastrados", security = @SecurityRequirement(name = "security"), responses = {
			@ApiResponse(responseCode = "200", description = "Lista com todos os usuarios cadastrados", content = @Content(mediaType = "application/json")
//							array = @ArraySchema(schema = @Schema(implementation = UsuarioResponseDto.class))
			),
			// caso de erro
			@ApiResponse(responseCode = "403", description = "Usuario sem permissão para acessar este conteudo.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))), })
	@GetMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<UsuarioResponseDto>> getAll() {
		List<Usuario> users = usuariosService.buscarTodos();
		return ResponseEntity.status(HttpStatus.OK).body(UsuarioMapper.toListDTO(users));
	}

	/////////////////////////
	// auth

	@Operation(summary = "Autenticar", description = "Recurso para Autenticar usuarios", responses = {

			// caso de sucesso
			@ApiResponse(responseCode = "201", description = "Recurso criado com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioResponseDto.class))),

			// caso de erro
			/* ele pode gerar uam resposta de erro */
			@ApiResponse(responseCode = "400", description = "Credenciais invalidas", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),

			// caso de erro
			@ApiResponse(responseCode = "422", description = "Campos invalidos", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))) })
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody @Valid UsuarioLoginDto data) {

		var usernamePasword = new UsernamePasswordAuthenticationToken(data.username(), data.password());
		var auth = this.authenticationManager.authenticate(usernamePasword);

		// prcisamos criar o novo token para usar durante a aplicação
		var token = tokenService.generateToken((Usuario) auth.getPrincipal());

		// depois de criado o dto
		return ResponseEntity.ok(new LoginResponseDTO(token));
	}

	////////////////////////////////
	// Vaga


	@GetMapping("/vagas/{codigo}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<VagaResponseDto> getByCode(@PathVariable String codigo) {
		Vaga vaga = vagaService.buscarPorCodigo(codigo);

		return ResponseEntity.ok().body(VagaMapper.toDto(vaga));
	}
}
