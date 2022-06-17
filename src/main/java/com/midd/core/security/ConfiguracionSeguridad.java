package com.midd.core.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ConfiguracionSeguridad extends WebSecurityConfigurerAdapter{
	
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		http
			.csrf().disable()
			.authorizeRequests()
			.antMatchers("/asociados/**",
							"/index",
							"/login",
							"/logout",
							"/registro",
							"/success",
							"/activos/**",
							"/perfil/**",
							"/asignaciones-proyectos/**",
							"/equipos/**",
							"/reportes/**").permitAll()
			.anyRequest()
			.authenticated()			
			;
	}
	
}
