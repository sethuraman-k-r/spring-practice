package com.learn.practice.config;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private CustomAuthProvider authProvider;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		auth.inMemoryAuthentication().withUser("user1").password(passwordEncoder().encode("user1")).roles("USER").and()
//				.withUser("admin").password(passwordEncoder().encode("admin")).roles("admin");
		auth.authenticationProvider(authProvider);
	}

	@Override
	public void configure(WebSecurity web) {
		web.ignoring().antMatchers("/public/**");

	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
//		http.authorizeRequests().antMatchers("/resources/**").permitAll().anyRequest().authenticated().and()
//				.formLogin().loginPage("/login").permitAll().and().logout().permitAll();
		http.authorizeRequests().antMatchers("/home/**").hasRole("USER").and()
		.formLogin().loginPage("/login").defaultSuccessUrl("/",true).permitAll().and().logout().permitAll();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
