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

import com.example.demo.jwt.SecurityFilter;

@EnableMethodSecurity
//@EnableWebMvc
@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {

	@Autowired
	private SecurityFilter securityFilter;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//		return http
//				.csrf(csrf -> csrf.disable())
//				.formLogin(form -> form.disable())
//				.httpBasic(basic -> basic.disable())
//				.authorizeHttpRequests(auth -> auth
//						.requestMatchers(HttpMethod.POST, "api/v1/usuarios").permitAll()
//						.requestMatchers(HttpMethod.POST, "api/v1/auth").permitAll().anyRequest().authenticated())
//				
//				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)).build();

		return http.csrf(csrf -> csrf.disable())
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

				.authorizeHttpRequests(authorize -> authorize
						.requestMatchers(HttpMethod.POST, "api/v1/usuarios").permitAll()
						.requestMatchers(HttpMethod.POST, "api/v1/auth").permitAll()
						.requestMatchers(HttpMethod.POST, "api/v1/usuarios/login").permitAll()
						.requestMatchers(HttpMethod.POST, "api/v1/login").permitAll()
						.anyRequest().authenticated() //
				)

				.addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)

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
