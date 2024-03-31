package com.example.demo.web.dto.mapper;

import org.modelmapper.ModelMapper;

import com.example.demo.entity.ClinteVaga;
import com.example.demo.web.dto.EstacionamentoCreateDto;
import com.example.demo.web.dto.EstacionamentoResponseDto;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ClienteVagaMapper {

	// converte de dto para entity
	public static ClinteVaga toClienteVaga(EstacionamentoCreateDto dto) {
		return new ModelMapper().map(dto, ClinteVaga.class);
	}

	// converte de entity para dto
	public static EstacionamentoResponseDto toDto(ClinteVaga clienteVaga) {
		return new ModelMapper().map(clienteVaga, EstacionamentoResponseDto.class);
	}

}
