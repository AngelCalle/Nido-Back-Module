package com.nido.infrastructure.oauth.security;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

@Component
public class InfoAdicionalToken implements TokenEnhancer {

//	@Autowired
//	private IUsuarioService usuarioService;
	
	// Potenciado de token que se utiliza para agregar mas información al mismo.
	// en formato clave valor.
	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		Map<String, Object> info = new HashMap<String, Object>();
		
//		// Obtengo al usuario del autentication.
//		Usuario usuario = usuarioService.findByUsername(authentication.getName());
		
		// Agrego información al token.
		info.put("nombre", "masinfo para caramelo");
//		info.put("apellido", usuario.getApellido());
//		info.put("correo", usuario.getEmail());
		
		// Asigno la nueva información al token.
		((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(info);
		
		return accessToken;
	}
	
}
