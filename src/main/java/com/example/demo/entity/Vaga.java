package com.example.demo.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table
@EntityListeners(AuditingEntityListener.class)
public class Vaga implements Serializable {
	private static final long serialVersionUID = 9032738436203150380L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "codigo", nullable = false, unique = true, length = 4)
	private String codigo;

	@Column(name = "status", nullable = false)
	@Enumerated(EnumType.STRING)
	private StatusVaga status;

	// campos para auditoria
	@CreatedDate
	@Column(name = "data_criacao")
	private LocalDateTime dataCriacao;

	@LastModifiedDate
	@Column(name = "data_modificacao")
	private LocalDateTime dataModificacao;

	@CreatedBy
	@Column(name = "criado_por")
	private String criadoPor;

	@LastModifiedBy
	@Column(name = "modificado_por")
	private String modificadoPor;

	public enum StatusVaga {
		LIVRE, OCUPADA
	}

	public Vaga() {
	}

	public Vaga(long id, String codigo, StatusVaga status) {
		super();
		this.id = id;
		this.codigo = codigo;
		this.status = status;
	}

	public Vaga(long id, String codigo, StatusVaga status, LocalDateTime dataCriacao, LocalDateTime dataModificacao,
			String criadoPor, String modificadoPor) {
		super();
		this.id = id;
		this.codigo = codigo;
		this.status = status;
		this.dataCriacao = dataCriacao;
		this.dataModificacao = dataModificacao;
		this.criadoPor = criadoPor;
		this.modificadoPor = modificadoPor;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public StatusVaga getStatus() {
		return status;
	}

	public void setStatus(StatusVaga status) {
		this.status = status;
	}

	public LocalDateTime getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(LocalDateTime dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public LocalDateTime getDataModificacao() {
		return dataModificacao;
	}

	public void setDataModificacao(LocalDateTime dataModificacao) {
		this.dataModificacao = dataModificacao;
	}

	public String getCriadoPor() {
		return criadoPor;
	}

	public void setCriadoPor(String criadoPor) {
		this.criadoPor = criadoPor;
	}

	public String getModificadoPor() {
		return modificadoPor;
	}

	public void setModificadoPor(String modificadoPor) {
		this.modificadoPor = modificadoPor;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Vaga other = (Vaga) obj;
		return id == other.id;
	}

	@Override
	public String toString() {
		return "Vaga [id=" + id + ", codigo=" + codigo + ", status=" + status + ", dataCriacao=" + dataCriacao
				+ ", dataModificacao=" + dataModificacao + ", criadoPor=" + criadoPor + ", modificadoPor="
				+ modificadoPor + "]";
	}
	
	

}
