package com.example.demo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.example.demo.Exceptions.EntityNotFoundException;
import com.example.demo.Exceptions.UsernameUniqueViolationException;
import com.example.demo.entity.Usuario;
import com.example.demo.repositories.UsuarioRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UsuariosService {

	// ADICIONAR FINAL
	@Autowired
	private UsuarioRepository usuarioRepository;

	// isso indica que o Spring ficara responsavel pela transação, sejá abrir
	// gerenciar ou fechar a transação
	@Transactional
	public Usuario salvar(Usuario usuario) {
		try {
			return usuarioRepository.save(usuario);
		} catch (DataIntegrityViolationException e) {
			throw new UsernameUniqueViolationException(
					String.format("Username {%s} já cadastrado ", usuario.getUsername()));
		} catch (com.example.demo.Exceptions.UsernameUniqueViolationException e) {
			throw new UsernameUniqueViolationException(
					String.format("Username {%s} já cadastrado ", usuario.getUsername()));
		}
	}

	// indica que esse método é exclusivo para uma consulta no banco de dados
	// adicionar depois | readOnly = true
	@Transactional
	public Usuario buscarPorId(Long id) {
		Optional<Usuario> obj = usuarioRepository.findById(id);
		return obj.orElseThrow(() -> new EntityNotFoundException("Error, usuario não encontrado"));
	}

	@Transactional
	public Usuario editarSenha(Long id, String senhaAtual, String novaSenha, String confirmaSenha) {
		if (!novaSenha.equals(confirmaSenha)) {
			throw new RuntimeException("Nova senha não confere com confirmação de senha.");
		}

		Usuario user = buscarPorId(id);

		if (!user.getPassword().equals(senhaAtual)) {
			throw new RuntimeException("Sua senha não confere.");
		}

		user.setPassword(novaSenha);
		return usuarioRepository.save(user);
	}

	public List<Usuario> buscarTodos() {
		return usuarioRepository.findAll();
	}

}
