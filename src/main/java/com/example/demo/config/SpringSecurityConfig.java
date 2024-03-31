package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.demo.jwt.JwtAuthenticationEntryPoint;
import com.example.demo.jwt.SecurityFilter;

@EnableMethodSecurity
//@EnableWebMvc
@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {

	@Autowired
	private SecurityFilter securityFilter;
	
	private static final String[] DOCUMENT_OPENAPI = {
			"/docs/index.html",
			"/docs-park.html",
			"/docs-park/**",
			"/v3/api-docs/**",
			"/swagger-ui-custom.html",
			"/swagger-ui.html",
			"/swagger-ui/**",
			"/**.html", 
			"/webjars/**",
			"/configuration/**",
			"/swagger-resources/**"
	};

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		return http.csrf(csrf -> csrf.disable())
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

				.authorizeHttpRequests(authorize -> authorize
						.requestMatchers(HttpMethod.POST, "api/v1/usuarios").permitAll()
						.requestMatchers(HttpMethod.POST, "api/v1/auth").permitAll()
						.requestMatchers(HttpMethod.POST, "api/v1/usuarios/login").permitAll()
						
						.requestMatchers(DOCUMENT_OPENAPI).permitAll()
						
//						.requestMatchers(HttpMethod.GET, "api/v1/usuarios/{id}").authenticated() ///   /docs-park.html
						
						.anyRequest().authenticated() //
				)

				.addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
				
				.exceptionHandling(ex -> ex
						.authenticationEntryPoint(new JwtAuthenticationEntryPoint())
						)

				.build();
	}

//    @Bean
//    public JwtAuthorizationFilter jwtAuthorizationFilter() {
//        return new JwtAuthorizationFilter();
//    }

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
			throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
