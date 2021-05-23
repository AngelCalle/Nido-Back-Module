package org.nido.business.clients.repository;

import org.nido.business.clients.model.Client;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface RepositoryClients extends PagingAndSortingRepository<Client, Long> {
	
}
