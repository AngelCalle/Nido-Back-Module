package com.nido.common.api;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
// @EnableAutoConfiguration Excluye la configuración de la conexión a la BBDD de la librería JPA.
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
public class NidoApi { }
