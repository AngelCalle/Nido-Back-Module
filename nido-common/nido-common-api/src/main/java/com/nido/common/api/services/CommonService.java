package com.nido.common.api.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
// import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

/**
 * Continuar escribiendo El objetivo de este servicio genérico es construir los
 * métodos que realizaran la llamada a la BBDD, con los métodos básicos
 * principales del CRUD, el objetivo es evitara repetir código.
 * 
 * Como el servicio es genérico utilizo Java Generics mediante:
 * 
 * @param <E> Entity.
 * 
 *            Implementa la interfaz genérica CommonService que indica los
 *            posibles métodos.
 */
@Slf4j
@Service
//Como parámetros lleva el tipo de dato que va a gestionar -Usuario- y el tipo de clave primaria, en nuestro caso Long.
public class CommonService<E, R extends PagingAndSortingRepository<E, Long>> implements ICommon<E> {

	// @Autowired Importa el repositorio, interfaz que ya cuenta con los métodos de
	// CRUD
	// protected para que se pueda reutilizar en las clases hijas y tener métodos
	// personalizados.
	@Autowired
	protected R repository;

	// Listar todos.
	@Override
	// Los métodos de lectura siempre se anotan con @Transactional de Spring.
	// @Transactional Permite modificar la tabla.
	@Transactional(readOnly = true)
	public Iterable<E> findAll() {
		return repository.findAll();
	}

	// Listar todos con paginacion.
	@Override
	@Transactional(readOnly = true)
	public Page<E> findAll(Pageable pageable) {
		return repository.findAll(pageable);
	}

	// Buscar por id.
	@Override
	@Transactional(readOnly = true)
	public Optional<E> findById(Long id) {
		return repository.findById(id);
	}

	// Crear.
	@Override
	@Transactional
	public E save(E entity) {
		return repository.save(entity);
	}

	// Eliminar por id.
	@Override
	@Transactional
	public void deleteById(Long id) {
		repository.deleteById(id);
	}

}
