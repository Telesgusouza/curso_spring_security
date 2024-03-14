package com.example.demo.web.dto.mapper;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

import com.example.demo.entity.Usuario;
import com.example.demo.web.dto.UsuarioCretedDTO;
import com.example.demo.web.dto.UsuarioResponseDto;


public class UsuarioMapper {

	public static Usuario toUsuario(UsuarioCretedDTO createDto) {
		return new ModelMapper().map(createDto, Usuario.class);
	}

	public static UsuarioResponseDto toDto(Usuario usuario) {
		String role = usuario.getRole().name().substring("ROLE_".length());
		PropertyMap<Usuario, UsuarioResponseDto> props = new PropertyMap<Usuario, UsuarioResponseDto>() {

			@Override
			protected void configure() {
				map().setRole(role);
			}
		};
		ModelMapper mapper = new ModelMapper();
		mapper.addMappings(props);
		return mapper.map(usuario, UsuarioResponseDto.class);
//		usuario;
//		return null;
	}

}
