package com.nido.infrastructure.oauth.props;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.io.Resource;

/**
 * Clase para vincular nuestras propiedades de configuración antes de firmar los JWTtokens 
 * generados.
 * El servidor de autorización valida los  client y las user credenciales
 * y proporciona los símbolos, en este tutorial vamos a estar generand JSON Web Tokens aka  JWT.
 *
 */
// Para firmar los JWT tokens generados usaremos un certificado autofirmado
// y para hacerlo antes de comenzar con la Configuración de Spring,
// creemos una  @ConfigurationProperties clase para vincular nuestras propiedades de configuración.
@ConfigurationProperties("security")
public class SecurityProperties {

    private JwtProperties jwt;

    public JwtProperties getJwt() {
        return jwt;
    }

    public void setJwt(JwtProperties jwt) {
        this.jwt = jwt;
    }

    public static class JwtProperties {

        private Resource keyStore;
        private String keyStorePassword;
        private String keyPairAlias;
        private String keyPairPassword;

        public Resource getKeyStore() {
            return keyStore;
        }

        public void setKeyStore(Resource keyStore) {
            this.keyStore = keyStore;
        }

        public String getKeyStorePassword() {
            return keyStorePassword;
        }

        public void setKeyStorePassword(String keyStorePassword) {
            this.keyStorePassword = keyStorePassword;
        }

        public String getKeyPairAlias() {
            return keyPairAlias;
        }

        public void setKeyPairAlias(String keyPairAlias) {
            this.keyPairAlias = keyPairAlias;
        }

        public String getKeyPairPassword() {
            return keyPairPassword;
        }

        public void setKeyPairPassword(String keyPairPassword) {
            this.keyPairPassword = keyPairPassword;
        }
    }
}
