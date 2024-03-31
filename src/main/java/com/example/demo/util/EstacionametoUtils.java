package com.example.demo.util;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EstacionametoUtils {

	public static String gerarRecibo() {
		LocalDateTime date = LocalDateTime.now();
		String recibo = date.toString().substring(0, 19);
		
		return recibo.replace("-", "")
				.replace(":", "")
				.replace("T", "-");
	}
	
	public static BigDecimal calcularCusto(LocalDateTime dataEntrada, LocalDateTime dataSaida) {
		return new BigDecimal("123.55");
	}

	public static BigDecimal calcularDesconto(BigDecimal value, long totalDeVezes) {
		// TODO Auto-generated method stub
		return new BigDecimal("23.55");
		
	}
	
}
