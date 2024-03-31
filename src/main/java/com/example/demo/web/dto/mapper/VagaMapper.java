package com.example.demo.web.dto.mapper;

import org.modelmapper.ModelMapper;

import com.example.demo.entity.Vaga;
import com.example.demo.web.dto.VagaCreateDto;
import com.example.demo.web.dto.VagaResponseDto;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class VagaMapper {

	public enum StatusVaga {
		LIVRE, OCUPADA
	}

	public static Vaga toVaga(VagaCreateDto dto) {

		System.out.println("=================");
		System.out.println(dto);

//		Vaga obj = new Vaga(null, dto.getCodigo(), dto.getStatus());
//		
//		obj.setCodigo(dto.getCodigo());
//		obj.setStatus(dto.getStatus());

		return new ModelMapper().map(dto, Vaga.class);
	}

	public static VagaResponseDto toDto(Vaga vaga) {
		return new ModelMapper().map(vaga, VagaResponseDto.class);
	}

}
