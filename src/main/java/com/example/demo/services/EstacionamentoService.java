package com.example.demo.services;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Cliente;
import com.example.demo.entity.ClinteVaga;
import com.example.demo.entity.Vaga;
import com.example.demo.entity.Vaga.StatusVaga;
import com.example.demo.util.EstacionametoUtils;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class EstacionamentoService {

	@Autowired
	private ClienteVagaService clienteVagaService;

	@Autowired
	private ClienteService clienteService;

	@Autowired
	private VagaService vagaService;

	public ClinteVaga checkIn(ClinteVaga clienteVaga) {
		Cliente cliente = clienteService.BuscarPorCpf(((Cliente) clienteVaga.getCliente()).getCpf());
		clienteVaga.setCliente(cliente);
		
		Vaga vaga = vagaService.buscarPorVagaLivre();
		vaga.setStatus(StatusVaga.OCUPADA);
		clienteVaga.setVaga(vaga);
		
		clienteVaga.setDataEntrada(LocalDateTime.now());
		
		clienteVaga.setRecibo(EstacionametoUtils.gerarRecibo());
		
		return clienteVagaService.salvar(clienteVaga);
	}
	
	public ClinteVaga checkOut(String recibo) {
		ClinteVaga clienteVaga = clienteVagaService.buscarPorRecibo(recibo);
		LocalDateTime dataSaida = LocalDateTime.now();
		
		BigDecimal value = EstacionametoUtils.calcularCusto(clienteVaga.getDataEntrada(), dataSaida);
		clienteVaga.setValor(value);
		
		long totalDeVezes = clienteVagaService.getTotalDeVezesEstacionamentoCompleto(clienteVaga.getCliente().getCpf());
		
		BigDecimal desconto = EstacionametoUtils.calcularDesconto(value, totalDeVezes);
		clienteVaga.setDesconto(desconto);
		
		clienteVaga.setDataSaida(dataSaida);
		clienteVaga.getVaga().setStatus(StatusVaga.LIVRE);
		
		return clienteVagaService.salvar(clienteVaga);
	}

}
