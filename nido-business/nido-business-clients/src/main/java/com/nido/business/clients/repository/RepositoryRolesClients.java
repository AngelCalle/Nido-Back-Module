package com.nido.business.clients.repository;

import com.nido.business.clients.model.RoleClient;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface RepositoryRolesClients extends PagingAndSortingRepository<RoleClient, Long> {
	
}
