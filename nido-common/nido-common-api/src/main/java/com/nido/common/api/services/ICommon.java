package com.nido.common.api.services;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * El objetivo de esta interfaz genérica es declarar los métodos que se podrán implementar en CommonServiceImpl
 * con el que se construirán todos los microservicios, iniciándolos con los métodos básicos principales del CRUD,
 * el objetivo es evitara repetir código.
 *
 * Como la interfaz es genérica utilizo Java Generics mediante:
 * @param <E> Entity.
 */
public interface ICommon<E> {
	
	// Listar todos.
	public Iterable<E> findAll();
	
	// Listar todos con paginacion.
	// Realiza dos consulta una con un count a la BBDD y otra que trae los datos solicitados.
	public Page<E> findAll(Pageable pageable);
	
	// Buscar por id.
	// Optional<E> Recibe  una entidad y permite manejar el resultado de la consulta,
	// preguntando si se encontró el objeto, es null o viene vacía.
	public Optional<E> findById(Long id);
	
	// Crear.
	// devuelve el objeto guardado en BBDD.
	public E save(E entity);
	
	// Eliminar por id.
	public void deleteById(Long id);
}
