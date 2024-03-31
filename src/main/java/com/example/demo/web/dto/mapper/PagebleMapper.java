package com.example.demo.web.dto.mapper;

import org.springframework.data.domain.Page;

import com.example.demo.web.dto.PagebleDto;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PagebleMapper {

	public static PagebleDto toDto(Page page) {
		PagebleDto obj = new PagebleDto();

		obj.setContent(page.getContent());
		obj.setFirst(page.isFirst());
		obj.setLast(page.isLast());

		obj.setNumber(page.getNumber());
		obj.setSize(page.getSize());

		obj.setNumberOfElements(page.getNumberOfElements());
		obj.setTotalPages(page.getTotalPages());
		obj.setTotalElements(page.getTotalElements());

		return obj;
	}

}
