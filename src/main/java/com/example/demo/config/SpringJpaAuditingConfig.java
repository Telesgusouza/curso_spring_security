package com.example.demo.config;

import java.util.Optional;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/*
 
 import org.springframework.security.core.Authentication;
  
 */

@EnableJpaAuditing
@Configuration
public class SpringJpaAuditingConfig implements AuditorAware<String> {

	@Override
	public Optional<String> getCurrentAuditor() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); // ATENÇÃO: acesso aos dados do usuario authenticado
		
		// testar se o obj é diferente de null e authenticado
		// isAuthenticated() ver se esta authenticado
		if (authentication != null && authentication.isAuthenticated()) {
			return Optional.of(authentication.getName());
		}
		
		return null;
	}

}
