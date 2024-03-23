package com.example.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.Exceptions.EntityNotFoundException;
import com.example.demo.Exceptions.UsernameUniqueViolationException;
import com.example.demo.entity.Usuario;
import com.example.demo.repositories.UsuarioRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UsuariosService implements UserDetailsService {

	// ADICIONAR FINAL
	@Autowired
	private UsuarioRepository usuarioRepository;

	private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	@Transactional
	public Usuario salvar(Usuario usuario) {
		try {
			usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
			return usuarioRepository.save(usuario);
		} catch (org.springframework.dao.DataIntegrityViolationException ex) {
			throw new UsernameUniqueViolationException(
					String.format("Username '%s' já cadastrado", usuario.getUsername()));
		}
	}

	@Transactional
	public Usuario buscarPorId(Long id) {
		return usuarioRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException(String.format("Usuário id=%s não encontrado", id)));
	}

	@Transactional
	public Usuario editarSenha(Long id, String senhaAtual, String novaSenha, String confirmaSenha) {
		if (!novaSenha.equals(confirmaSenha)) {
			throw new RuntimeException("Nova senha não confere com confirmação de senha.");
		}

		Usuario user = buscarPorId(id);
		if (!passwordEncoder.matches(senhaAtual, user.getPassword())) {
			throw new RuntimeException("Sua senha não confere.");
		}

		user.setPassword(passwordEncoder.encode(novaSenha));
		return user;
	}

	@Transactional
	public List<Usuario> buscarTodos() {
		return usuarioRepository.findAll();
	}

	@Transactional
	public Usuario buscarPorUsername(String username) {
		Usuario obj = (Usuario) usuarioRepository.findByUsername(username);
		return obj;
//		return usuarioRepository.findByUsername(username).orElseThrow(
//				() -> new EntityNotFoundException(String.format("Usuario com '%s' não encontrado", username)));
	}

	@Transactional
	public Usuario.Role buscarRolePorUsername(String username) {
		return usuarioRepository.findRoleByUsername(username);
	}

	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		return usuarioRepository.findByUsername(username);
	}
}
