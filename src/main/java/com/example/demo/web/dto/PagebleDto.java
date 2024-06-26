package com.example.demo.web.dto;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

// agora esse é o objeto de resposta

public class PagebleDto {

	private List content = new ArrayList<>();
	private boolean first;
	private boolean last;

	// na resposta padrão temos o campo number, mas queremos mudar o nome dela para
	// ṕage
	@JsonProperty("page")
	private int number;
	private int size;

	// novamente iremos mudar o nome, do padrão para um novo
	@JsonProperty("pageElements")
	private int numberOfElements;
	private int totalPages;
	private long totalElements;

	public List getContent() {
		return content;
	}

	public void setContent(List content) {
		this.content = content;
	}

	public boolean isFirst() {
		return first;
	}

	public void setFirst(boolean first) {
		this.first = first;
	}

	public boolean isLast() {
		return last;
	}

	public void setLast(boolean last) {
		this.last = last;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getNumberOfElements() {
		return numberOfElements;
	}

	public void setNumberOfElements(int numberOfElements) {
		this.numberOfElements = numberOfElements;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	public long getTotalElements() {
		return totalElements;
	}

	public void setTotalElements(long l) {
		this.totalElements = l;
	}

}
