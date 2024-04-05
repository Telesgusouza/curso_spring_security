package com.example.demo.services;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

/*

o arquivo está no seguinte caminho
src/main/resources/reports/Estacionamento,jasper

*/

@Service
public class JasperService {

	// ler arquivos/recurso do projeto
	@Autowired
	private ResourceLoader resourceLoader;

	// conecção com o banco de dados
	// ele já terá as informações do application.properties
	private DataSource dataSource;

	// armazenar parametro que serão usados no relatório
	private Map<String, Object> params = new HashMap<>();

	// aonde ficarão os diretórios
	private static String JASPER_DIRETORIO = "classpath:reports/";
//	private static String JASPER_DIRETORIO = "reports/";

	@Autowired
	public JasperService(ResourceLoader resourceLoader) {
		this.resourceLoader = resourceLoader;
	}

	// Adicionar valores e parametros
	public void addParams(String key, Object value) {

		this.params.put("IMAGEM_DIRETORIO", JASPER_DIRETORIO);
		this.params.put("REPORT_LOCALE", new Locale("pt", "BR"));
		this.params.put(key, value);

	}

	// método que trabalhara com relatório
	public byte[] gerarPdf() {
		byte[] bytes = null;

		try {
			// buscar relatório
			Resource resource = resourceLoader.getResource(JASPER_DIRETORIO.concat("estacionameto.jasper"));
			// ler relatório
			InputStream stream = resource.getInputStream();
			// gerar relatório
			JasperPrint print = JasperFillManager.fillReport(stream, params, dataSource.getConnection());

			bytes = JasperExportManager.exportReportToPdf(print);

		} catch (IOException e) {
			throw new RuntimeException(e);
		} catch (JRException e) {
			throw new RuntimeException(e);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return bytes;
	}

}
