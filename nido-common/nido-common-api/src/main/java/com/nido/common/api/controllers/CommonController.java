package com.nido.common.api.controllers;

//https://bezkoder.com/spring-boot-controlleradvice-exceptionhandler/
//https://devwithus.com/exception-handling-for-rest-api-with-spring-boot/

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.Min;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nido.common.api.exception.ResourceNotFoundException;
import com.nido.common.api.services.ICommon;
import com.nido.common.api.web.ApiResponse;

import lombok.extern.slf4j.Slf4j;

/**
 * El objetivo de este controlador es contener los métodos principales del CRUD,
 * extendiendo este controlador en el resto de controladores del proyecto
 * evitare repetir código.
 * 
 * Como el controlador es genérico utilizo Java Generics mediante:
 * 
 * @param <E> Entity.
 * @param <S> Servicio. que también tiene el tipo genérico E que representa la
 *            entidad.
 */

// @CrossOrigin({"http://localhost:4200"}) Permite la conexion desde angular
// @CrossOrigin({"http://localhost:4200"})
@Slf4j
@Validated
@RequestMapping()
public class CommonController<E, S extends ICommon<E>> {

	// Inyecto el tipo genérico de la interfaz.
	// protected Para que se pueda reutilizar en las clases hijas.
	@Autowired
	protected S service;

	// Listar todos.
	@GetMapping
	// ? Tipo genérico. Para poder guardar en el tipo de la respuesta cualquier
	// cosa.
	public ResponseEntity<ApiResponse<Iterable<E>>> findAll() {

		ApiResponse<Iterable<E>> apiResponse = new ApiResponse<>(
				LocalDateTime.now(),
				HttpStatus.OK,
				HttpStatus.OK.value(),
				"Lista completa de items",
				service.findAll());
		
		return ResponseEntity.ok().body(apiResponse);

	}

	// Listar todos con paginacion.
	// Pageable pageable Los valores de pagina que se requieren.
	// La pagina y el tamaño de la pagina.
	@GetMapping("/page")
	public ResponseEntity<ApiResponse<?>> findAllPaged(
			@Valid @RequestBody Pageable pageable,
			BindingResult result) {

		// Valido antes de guardar que los campos sean correctos.
		if (result.hasErrors()) {
			return this.validate(result);
		} else {

		ApiResponse<Page<E>> apiResponse = new ApiResponse<>(
				LocalDateTime.now(),
				HttpStatus.OK,
				HttpStatus.OK.value(),
				"Lista paginada de items",
				service.findAll(pageable));

		return ResponseEntity.ok().body(apiResponse);
		}

	}

	// Buscar por id.
	// ("/{id}") Una ruta variable mediante el id que se pasa como parámetro
	// @PathVariable Extrae una variable de la URL
	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse<Optional<E>>> findById(
			@PathVariable("id") @Min(1) Long id) {
		
		// Optional<E> Obtiene el objeto, antes valida que exista en la consulta.
		Optional<E> objeto = Optional.of(service.findById(id)
				.orElseThrow(()->new ResourceNotFoundException("No Role with ID : " + id)));
		
		ApiResponse<Optional<E>> apiResponse = new ApiResponse<>(
				LocalDateTime.now(),
				HttpStatus.OK,
				HttpStatus.OK.value(),
				"Resultado por id",
				objeto);

		// En caso de que si exista construye la respuesta con los valores obtenidos.
		return ResponseEntity.ok().body(apiResponse);

	}

	// Crear.
	@PostMapping
	// @RequestBody Para obtener la variable del body que llega en la llamada.
	// @Valid Habilita la validación.
	// BindingResult result A través del resultado obtengo los mensajes de error.
	public ResponseEntity<ApiResponse<?>> save(
			@Valid @RequestBody E entity,
			BindingResult result) {

		// Valido antes de guardar que los campos sean correctos.
		if (result.hasErrors()) {
			return this.validate(result);
		} else {
			E entityDB = null;
			try {
				// Recojo la respuesta de la insercion en la BBDD.
				entityDB = service.save(entity);
				
			} catch (RuntimeException ex) {
				log.info("\n No creado: RuntimeException :  {}", ex.getCause().getMessage());
				return null;
			}

			if (entityDB == null) {

				ApiResponse<E> apiResponse = new ApiResponse<>(LocalDateTime.now(), HttpStatus.OK,
						HttpStatus.OK.value(), "No creado", entity);
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);

			} else {

				ApiResponse<E> apiResponse = new ApiResponse<>(LocalDateTime.now(), HttpStatus.OK,
						HttpStatus.OK.value(), "Creado", entityDB);

				return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
			}

		}

	}

	// Eliminar por id.
	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse<Long>> deleteById(
			@PathVariable("id") @Min(1) Long id) {

		E entityDB = service.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("No Role with ID: " + id));

		if (entityDB != null) {
			try {
				// Elimino el dato de la BBDD.
				service.deleteById(id);
				
				ApiResponse<Long> apiResponse = new ApiResponse<>(
						LocalDateTime.now(),
						HttpStatus.OK,
						HttpStatus.OK.value(),
						"Eliminado",
						id);
				
				// noContent Respuesta tipo 204.
				return ResponseEntity.status(HttpStatus.NO_CONTENT).body(apiResponse);
			} catch (Exception e) {
				log.error("my exception: {} ", e.getLocalizedMessage());
				return null;
			}
		} else {
			return null;
		}
		
	}

	// Implemento la respuesta con los mensajes de error,
	// mediante BindingResult result.
	protected ResponseEntity<ApiResponse<?>> validate(BindingResult result) {
		// Genera un JSON con los mensajes de error.

		// Obtengo la lista de los errores.
		Map<String, Object> errores = new HashMap<>();

		// Paso los errores detectados al cuerpo de la respuesta.
		result.getFieldErrors().forEach(err -> {
			// err.getField() retorna el nombre del campo que contiene el error.
			errores.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage());
		});

		ApiResponse<Map<String, Object>> apiResponse = new ApiResponse<>(LocalDateTime.now(), HttpStatus.OK,
				HttpStatus.OK.value(), "Existen errores", errores);

		// .badRequest() Respuesta tipo 400.
//		return ResponseEntity.badRequest().body(apiResponse);

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
	}

}
