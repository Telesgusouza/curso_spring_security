package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

/*
 
 executionPhase = dira quando o arquivo sql devera ser executado
 
 */

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "sql/usuarios/usuario-insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
//@Sql(scripts = "/sql/usuarios/usuario-delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class UsuarioIT {

	@Autowired
	WebTestClient testClient;

	/*
	 * // para testar precisamos da anotação @Test tem que ser public e retornar
	 * void
	 * 
	 * @Test public void
	 * createUsuario_ComUsernamePasswordValidos_RetornarUsuarioCriadoComStatus201()
	 * { UsuarioResponseDto responseBody = testClient.post().uri("/api/v1/usuarios")
	 * .contentType(MediaType.APPLICATION_JSON).bodyValue(new
	 * UsuarioCretedDTO("Tody@gmail.com", "123456"))
	 * 
	 * // resposta esperada
	 * .exchange().expectStatus().isCreated().expectBody(UsuarioResponseDto.class).
	 * returnResult() .getResponseBody();
	 * 
	 * // aqui fazemos uma verificação se ele retorna null
	 * org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
	 * org.assertj.core.api.Assertions.assertThat(responseBody.getId()).isNotNull();
	 * org.assertj.core.api.Assertions.assertThat(responseBody.getUsername()).
	 * isEqualTo("Tody@gmail.com");
	 * org.assertj.core.api.Assertions.assertThat(responseBody.getRole()).isEqualTo(
	 * "ROLE_CLIENTE"); }
	 * 
	 * // para testar precisamos da anotação @Test tem que ser public e retornar
	 * void
	 * 
	 * @Test public void
	 * createUsuario_ComUsernameInvalid_RetornarErrorMessageStatus422() {
	 * ErrorMessage responseBody =
	 * testClient.post().uri("/api/v1/usuarios").contentType(MediaType.
	 * APPLICATION_JSON) .bodyValue(new UsuarioCretedDTO("", "123456"))
	 * 
	 * // resposta esperada
	 * .exchange().expectStatus().isEqualTo(422).expectBody(ErrorMessage.class).
	 * returnResult() .getResponseBody();
	 * 
	 * responseBody =
	 * testClient.post().uri("/api/v1/usuarios").contentType(MediaType.
	 * APPLICATION_JSON) .bodyValue(new UsuarioCretedDTO("@gmail.com", "123456"))
	 * 
	 * // resposta esperada
	 * .exchange().expectStatus().isEqualTo(422).expectBody(ErrorMessage.class).
	 * returnResult() .getResponseBody();
	 * 
	 * // aqui fazemos uma verificação se ele retorna null
	 * org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
	 * org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).
	 * isEqualTo(422); }
	 * 
	 * @Test public void
	 * createUsuario_ComPasswordInvalid_RetornarErrorMessageStatus422() {
	 * ErrorMessage responseBody =
	 * testClient.post().uri("/api/v1/usuarios").contentType(MediaType.
	 * APPLICATION_JSON) .bodyValue(new UsuarioCretedDTO("Tody@gmail.com", ""))
	 * 
	 * // resposta esperada
	 * .exchange().expectStatus().isEqualTo(422).expectBody(ErrorMessage.class).
	 * returnResult() .getResponseBody();
	 * 
	 * responseBody =
	 * testClient.post().uri("/api/v1/usuarios").contentType(MediaType.
	 * APPLICATION_JSON) .bodyValue(new UsuarioCretedDTO("Tody@gmail.com", "12345"))
	 * 
	 * // resposta esperada
	 * .exchange().expectStatus().isEqualTo(422).expectBody(ErrorMessage.class).
	 * returnResult() .getResponseBody();
	 * 
	 * // aqui fazemos uma verificação se ele retorna null
	 * org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
	 * org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).
	 * isEqualTo(422); }
	 * 
	 * @Test public void
	 * createUsuario_ComUsernameRepetido_RetornarErrorMessageStatus409() {
	 * ErrorMessage responseBody =
	 * testClient.post().uri("/api/v1/usuarios").contentType(MediaType.
	 * APPLICATION_JSON) .bodyValue(new UsuarioCretedDTO("ana@gmail.com", ""))
	 * 
	 * // resposta esperada
	 * .exchange().expectStatus().isEqualTo(409).expectBody(ErrorMessage.class).
	 * returnResult() .getResponseBody();
	 * 
	 * // aqui fazemos uma verificação se ele retorna null
	 * org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
	 * org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).
	 * isEqualTo(409); }
	 * 
	 * @Test public void buscarUsuario_ComIdExistente_RetornarUsuarioComStatus200()
	 * { UsuarioResponseDto responseBody =
	 * testClient.get().uri("/api/v1/usuarios/100")
	 * 
	 * // resposta esperada
	 * .exchange().expectStatus().isOk().expectBody(UsuarioResponseDto.class).
	 * returnResult().getResponseBody();
	 * 
	 * // aqui fazemos uma verificação se ele retorna null
	 * org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
	 * org.assertj.core.api.Assertions.assertThat(responseBody.getId()).isEqualTo(
	 * 100); org.assertj.core.api.Assertions.assertThat(responseBody.getUsername()).
	 * isEqualTo("Tody@gmail.com");
	 * org.assertj.core.api.Assertions.assertThat(responseBody.getRole()).isEqualTo(
	 * "ROLE_CLIENTE"); }
	 * 
	 * @Test public void buscarUsuario_ComIdInexistente_RetornarErrorComStatus404()
	 * { ErrorMessage responseBody = testClient.get().uri("/api/v1/usuarios/0")
	 * 
	 * // resposta esperada
	 * .exchange().expectStatus().isNotFound().expectBody(ErrorMessage.class).
	 * returnResult().getResponseBody();
	 * 
	 * // aqui fazemos uma verificação se ele retorna null
	 * org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
	 * org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).
	 * isEqualTo(404); }
	 * 
	 * @Test public void editarSenha_ComDadosValidados_RetornarStatus204() {
	 * testClient.patch().uri("/api/v1/usuarios/100").contentType(MediaType.
	 * APPLICATION_JSON) .bodyValue(new UsuarioSenhaDto("123456", "123456",
	 * "123456"))
	 * 
	 * // resposta esperada .exchange().expectStatus().isNoContent();
	 * 
	 * }
	 * 
	 * @Test public void editarSenha_ComIdInexistente_RetornarErrorComStatus404() {
	 * ErrorMessage responseBody =
	 * testClient.patch().uri("/api/v1/usuarios/0").contentType(MediaType.
	 * APPLICATION_JSON) .bodyValue(new UsuarioSenhaDto("123456", "123456",
	 * "123456"))
	 * 
	 * // resposta esperada
	 * .exchange().expectStatus().isNotFound().expectBody(ErrorMessage.class).
	 * returnResult().getResponseBody();
	 * 
	 * // aqui fazemos uma verificação se ele retorna null
	 * org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
	 * org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).
	 * isEqualTo(404); }
	 * 
	 * @Test public void editarSenha_ComCamposInvalidos_RetornarErrorComStatus422()
	 * { ErrorMessage responseBody = testClient.patch().uri("/api/v1/usuarios/100")
	 * .contentType(MediaType.APPLICATION_JSON).bodyValue(new UsuarioSenhaDto("",
	 * "", ""))
	 * 
	 * // resposta esperada
	 * .exchange().expectStatus().isEqualTo(422).expectBody(ErrorMessage.class).
	 * returnResult() .getResponseBody(); // aqui fazemos uma verificação se ele
	 * retorna null
	 * org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
	 * org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).
	 * isEqualTo(422);
	 * 
	 * responseBody =
	 * testClient.patch().uri("/api/v1/usuarios/100").contentType(MediaType.
	 * APPLICATION_JSON) .bodyValue(new UsuarioSenhaDto("12345", "12345", "12345"))
	 * 
	 * // resposta esperada
	 * .exchange().expectStatus().isEqualTo(422).expectBody(ErrorMessage.class).
	 * returnResult() .getResponseBody();
	 * 
	 * // aqui fazemos uma verificação se ele retorna null
	 * org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
	 * org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).
	 * isEqualTo(422);
	 * 
	 * responseBody =
	 * testClient.patch().uri("/api/v1/usuarios/100").contentType(MediaType.
	 * APPLICATION_JSON) .bodyValue(new UsuarioSenhaDto("123456789abcdabcdeee",
	 * "123456789abcdabcdeee", "123456789abcdabcdeee"))
	 * 
	 * // resposta esperada
	 * .exchange().expectStatus().isEqualTo(422).expectBody(ErrorMessage.class).
	 * returnResult() .getResponseBody();
	 * 
	 * // aqui fazemos uma verificação se ele retorna null
	 * org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
	 * org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).
	 * isEqualTo(422);
	 * 
	 * }
	 */
}
