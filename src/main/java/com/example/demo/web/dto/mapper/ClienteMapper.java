package com.example.demo.web.dto.mapper;

import com.example.demo.entity.Cliente;
import com.example.demo.web.dto.ClienteCreateDto;
import com.example.demo.web.dto.ClienteResponseDto;

public class ClienteMapper {

	public static Cliente toClienteDto(ClienteCreateDto dto) {
		Cliente user = new Cliente();

		user.setNome(dto.getNome());
		user.setCpf(dto.getCpf());
		;

		return user;
	}

	public static ClienteResponseDto toDto(Cliente cliente) {

		ClienteResponseDto obj = new ClienteResponseDto(cliente.getId(), cliente.getNome(), cliente.getCpf());

		return obj;
	}

}
