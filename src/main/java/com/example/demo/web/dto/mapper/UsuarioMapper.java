package com.example.demo.web.dto.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.example.demo.entity.Usuario;
import com.example.demo.web.dto.UsuarioCretedDTO;
import com.example.demo.web.dto.UsuarioResponseDto;

public class UsuarioMapper {

	public enum Role {
		ROLE_ADMIN, ROLE_CLIENTE
	}

	public static Usuario toUsuario(UsuarioCretedDTO createDto) {

		Usuario obj = new Usuario();
		obj.setUsername(createDto.getUsername());
		obj.setPassword(createDto.getPassword());
		return obj;
//		return new ModelMapper().map(createDto, Usuario.class);
	}

	public static UsuarioResponseDto toDto(Usuario usuario) {

		UsuarioResponseDto obj = new UsuarioResponseDto();
		obj.setId(usuario.getId());
		obj.setUsername(usuario.getUsername());
		obj.setRole(usuario.getRole().toString());

		return obj;
		/*
		 * 
		 * // String role = usuario.getRole().name().substring("ROLE_".length()); //
		 * PropertyMap<Usuario, UsuarioResponseDto> props = new PropertyMap<Usuario,
		 * UsuarioResponseDto>() { // // @Override // protected void configure() { //
		 * map().setRole(role); // } // }; // ModelMapper mapper = new ModelMapper(); //
		 * mapper.addMappings(props); // return mapper.map(usuario,
		 * UsuarioResponseDto.class);
		 */

	}

	public static List<UsuarioResponseDto> toListDTO(List<Usuario> usuarios) {
		
		
		return usuarios.stream().map(user -> toDto(user)).collect(Collectors.toList());
	}

}





















