package com.nido.infrastructure.oauth.security;

//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

//import brave.Tracer;
//import feign.FeignException;
//import ms.jwt.commons.usuarios.models.entity.Usuario;
//import ms.jwt.oauth.services.IUsuarioService;


// Clase encargada de manejar los eventos después de la autenticacion tanto los errores como
// cuando la autenticacion a sido satisfactoria.
@Component
public class AuthenticationSuccessErrorHandler implements AuthenticationEventPublisher {
	
//	@Autowired
//	private Environment env;
	
//	
//	@Autowired
//	private IUsuarioService usuarioService;
//	
//	// Añade información del error a la traza.
//	@Autowired
//	private Tracer tracer;
//	
	
	// Si se registra y el numero de intentos fallidos es mayor que cero lo pongo a cero.
	@Override
	public void publishAuthenticationSuccess(Authentication authentication) {

//		if(authentication.getName().equalsIgnoreCase(env.getProperty("config.security.oauth.client.id"))){
//            return;
//        }
//		
//		// Obtengo los datos del usuario.
//		UserDetails user = (UserDetails) authentication.getPrincipal();	
//	
//		// Guardar la traza en algun log.
//		String mensaje = "Success Login: " + user.getUsername();
//		System.out.println(mensaje);
//		log.info(mensaje);
//		
//		// Si se registra i el numero de intentos fallidos es mayor que cero lo restaura.
//		Usuario usuario = usuarioService.findByUsername(authentication.getName());
//		if(usuario.getIntentos() != null && usuario.getIntentos() > 0) {
//			usuario.setIntentos(0);
//			usuarioService.update(usuario, usuario.getId());
//		}
		
	}

	// Máximo tres fallos al tercer fallo bloquea al usuario pudiendo mandar un Mali que aun no esta implementado.
	@Override
	public void publishAuthenticationFailure(AuthenticationException exception, Authentication authentication) {
//		// Imprimo el error de intento de registro.
//		String mensaje = "Error en el Login: " + exception.getMessage();
//		log.error(mensaje);
//		System.out.println(mensaje);
//	 
//		try {
//
//			StringBuilder errors = new StringBuilder();
//			errors.append(mensaje);
//			
//			Usuario usuario = usuarioService.findByUsername(authentication.getName());
//			// Máximo tres fallos al tercer fallo bloquea al usuario pudiendo mandar un Mali que aun no esta implementado.
//			if (usuario.getIntentos() == null) {
//				usuario.setIntentos(0);
//			}
//			
//			log.info("Intentos actual es de: " + usuario.getIntentos());
//			
//			usuario.setIntentos(usuario.getIntentos()+1);
//			
//			log.info("Intentos después es de: " + usuario.getIntentos());
//			
//			errors.append(" - Intentos del login: " + usuario.getIntentos());
//			
//			// TODO: En este punto es donde se a de mandar el Mail al usuario.
//			if(usuario.getIntentos() >= 3) {
//				String errorMaxIntentos = String.format("El usuario %s des-habilitado por máximos intentos.", usuario.getUsername());
//				log.error(errorMaxIntentos);
//				errors.append(" - " + errorMaxIntentos);
//				usuario.setEnabled(false);
//			}
//			
//			usuarioService.update(usuario, usuario.getId());
//			
//			// Añado el error que se a ido concatenando a la traza.
//			tracer.currentSpan().tag("error.mensaje", errors.toString());
//			
//			// catch Si el usuario no existe.
//		} catch (FeignException e) {
//			// TODO: No debe iniciar que el usuario no existio si no que el usuario o la contraseña no coincide.
//			log.error(String.format("El usuario %s no existe en el sistema", authentication.getName()));
//		}

	}
	
}
