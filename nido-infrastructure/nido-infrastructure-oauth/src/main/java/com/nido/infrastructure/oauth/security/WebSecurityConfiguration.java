package com.nido.infrastructure.oauth.security;


import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;


//La anotación @EnableWebSecurity y WebSecurityConfigurerAdapter trabajan juntos para proporcionar seguridad basada en web.

//La anotación @EnableWebSecurity y WebSecurityConfigurerAdapter trabajan juntos para proporcionar seguridad basada en web.
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {

//    	http
//		.cors()
//	.and()
//		.csrf()
//		.disable()
//        .authorizeRequests()
//        // / Todos Permitidos (No securiza inguno), lo correcto es indicar solo los que deven ser libres
//        .antMatchers(HttpMethod.POST, "/users/token").permitAll()
//        .antMatchers("/").authenticated();

    }

	// Método para crear un acceso alternativo y usar un método POST para el login.
//	@Override
//    public void configure(HttpSecurity http) throws Exception {

//		http
//			.cors()
//		.and()
//			.csrf()
//			.disable()
//			.requestMatcher(EndpointRequest.toAnyEndpoint())
//	    	.authorizeRequests()
//	      .antMatchers(HttpMethod.GET,
//	        "/resource-api/privado",
//	        "/privado"
//	        ).permitAll()
//	    .antMatchers("/caramelo").authenticated()
//	    .and()
//        	.formLogin()
//        	.loginPage("/users")
//        	.permitAll()
//    	.and()
//            .sessionManagement()
//            .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//    }
	
	/**
	 * El DataSourceobjeto se configurará automáticamente y puede inyectarlo
	 * a la clase en lugar de definirlo usted mismo.
	 * debe inyectarse en el UserDetailsServiceque se utilizará el 
	 * JdbcDaoImplproporcionado por Spring Security.
	 */
//	DataSource objeto se configurará automáticamente
//	debe inyectarse en el UserDetailsService que se utilizará el JdbcDaoImpl
//	proporcionado por Spring Security, si es necesario, puede reemplazarlo con su propia implementación.
    private final DataSource dataSource;

    private PasswordEncoder passwordEncoder;
    private UserDetailsService userDetailsService;

    public WebSecurityConfiguration(final DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService())
                .passwordEncoder(passwordEncoder());
    }

//    Como Spring Security AuthenticationManageres requerido por algunos Spring configurados automáticamente
//    @Bean, es necesario anular el authenticationManagerBean método y anotarlo como @Bean.
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    
//    El PasswordEncoderserá manejado por PasswordEncoderFactories.
//    createDelegatingPasswordEncoder()
//    en el cual maneja algunos codificadores de contraseñas
//    y delegados basados ​​en un prefijo, en nuestro ejemplo estamos anteponiendo las contraseñas con {bcrypt}.
    @Bean
    public PasswordEncoder passwordEncoder() {
        if (passwordEncoder == null) {
            passwordEncoder = DefaultPasswordEncoderFactories.createDelegatingPasswordEncoder();
        }
        return passwordEncoder;
    }

    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        if (userDetailsService == null) {
            userDetailsService = new JdbcDaoImpl();
            ((JdbcDaoImpl) userDetailsService).setDataSource(dataSource);
        }
        return userDetailsService;
    }

}
