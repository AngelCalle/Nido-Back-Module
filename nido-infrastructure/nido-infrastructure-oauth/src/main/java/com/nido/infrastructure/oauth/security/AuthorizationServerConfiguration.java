package com.nido.infrastructure.oauth.security;

//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
//import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import com.nido.infrastructure.oauth.props.SecurityProperties;

import javax.sql.DataSource;
import java.security.KeyPair;

@Configuration
@EnableAuthorizationServer
@EnableConfigurationProperties(SecurityProperties.class)
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

    private final DataSource dataSource;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final SecurityProperties securityProperties;
    private final UserDetailsService userDetailsService;

//	@Autowired
//	private InfoAdicionalToken infoAdicionalToken;

    private JwtAccessTokenConverter jwtAccessTokenConverter;
    private TokenStore tokenStore;


    public AuthorizationServerConfiguration(final DataSource dataSource, final PasswordEncoder passwordEncoder,
                                            final AuthenticationManager authenticationManager, final SecurityProperties securityProperties,
                                            final UserDetailsService userDetailsService) {

        this.dataSource = dataSource;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.securityProperties = securityProperties;
        this.userDetailsService = userDetailsService;
    }

    // La JwtTokenStoreimplementación que solo lee datos de los propios tokens.
    // No es realmente una tienda, ya que nunca persiste nada y usa JwtAccessTokenConverte rpara generar y leer los tokens.
    @Bean
    public TokenStore tokenStore() {
        if (tokenStore == null) {
            tokenStore = new JwtTokenStore(jwtAccessTokenConverter());
        }
        return tokenStore;
    }
	// Se encarga de generar y guardar el token.
//	@Bean
//	public JwtTokenStore tokenStore() {
//		return new JwtTokenStore(accessTokenConverter());
//	}
//	

    // El DefaultTokenServicesusa TokenStore para conservar los tokens.
    @Bean
    public DefaultTokenServices tokenServices(final TokenStore tokenStore,
                                              final ClientDetailsService clientDetailsService) {
        DefaultTokenServices tokenServices = new DefaultTokenServices();
        tokenServices.setSupportRefreshToken(true);
        tokenServices.setTokenStore(tokenStore);
        tokenServices.setClientDetailsService(clientDetailsService);
        tokenServices.setAuthenticationManager(this.authenticationManager);
        return tokenServices;
    }

    // El JwtAccessTokenConverterutiliza el certificado autofirmado para firmar las fichas generadas.
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        if (jwtAccessTokenConverter != null) {
            return jwtAccessTokenConverter;
        }

        SecurityProperties.JwtProperties jwtProperties = securityProperties.getJwt();
        KeyPair keyPair = keyPair(jwtProperties, keyStoreKeyFactory(jwtProperties));

        jwtAccessTokenConverter = new JwtAccessTokenConverter();
        jwtAccessTokenConverter.setKeyPair(keyPair);
        return jwtAccessTokenConverter;
    }

    
    
    @Override
    public void configure(final ClientDetailsServiceConfigurer clients) throws Exception {
        clients.jdbc(this.dataSource);

        clients.inMemory()
        .withClient("clientId")
        .secret("{bcrypt}$2a$12$zbwa.i5FDsnSebgWHO72OOHhsO6q0cS00ntDIDfHqAOoafLQOv/2C")
//        .secret(encoder().encode("clientId"))
        .authorizedGrantTypes("password", "authorization_code", "refresh_token", "implicit", "client_credentials")
//        .authorities("ROLE_CLIENT", "ROLE_TRUSTED_CLIENT")
        .scopes("read", "write", "trust")
//        .redirectUris("http://localhost:8083/techgeeknext/login/oauth2/code/techgeeknextclient")
		.autoApprove(false)
        .accessTokenValiditySeconds(300)
        .refreshTokenValiditySeconds(6000);
        
    }


	@Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }
	
//    @Override
//    public void configure(final AuthorizationServerEndpointsConfigurer endpoints) {
//    	log.info("\n\n\n AuthorizationServerEndpointsConfigurer \n\n\n");
//        endpoints.authenticationManager(this.authenticationManager)
//                .accessTokenConverter(jwtAccessTokenConverter())
//                .userDetailsService(this.userDetailsService)
//                .tokenStore(tokenStore());
//    }
    

	// Método que se encarga de guardar cualquier tipo de información a los token.
	@Override
	public void configure(final AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
//		// Asigno la información adicional a la información por defecto.
//		TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
//		tokenEnhancerChain.setTokenEnhancers(Arrays.asList(infoAdicionalToken, jwtAccessTokenConverter()));
//		
//		endpoints
//			// Anular las asignaciones de puntos de entrada predeterminado
////			.pathMapping("/oauth/token", "/users/token")
////			.pathMapping("/oauth/authorize", "/users/authorize")
//			
//        	.authenticationManager(authenticationManager)
//			.tokenStore(tokenStore())
//			.accessTokenConverter(jwtAccessTokenConverter())
//			 // Añado la cadena con la información adicional.
//			.tokenEnhancer(tokenEnhancerChain);
		 endpoints.authenticationManager(this.authenticationManager)
         .accessTokenConverter(jwtAccessTokenConverter())
         .userDetailsService(this.userDetailsService)
         .tokenStore(tokenStore());
	}
	

	
    @Override
    public void configure(final AuthorizationServerSecurityConfigurer oauthServer) {
        oauthServer.passwordEncoder(this.passwordEncoder).tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()");
    }

    private KeyPair keyPair(SecurityProperties.JwtProperties jwtProperties, KeyStoreKeyFactory keyStoreKeyFactory) {
        return keyStoreKeyFactory.getKeyPair(jwtProperties.getKeyPairAlias(), jwtProperties.getKeyPairPassword().toCharArray());
    }

    private KeyStoreKeyFactory keyStoreKeyFactory(SecurityProperties.JwtProperties jwtProperties) {
        return new KeyStoreKeyFactory(jwtProperties.getKeyStore(), jwtProperties.getKeyStorePassword().toCharArray());
    }
}
