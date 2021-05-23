package org.nido.business.clients.services;

import org.nido.business.clients.model.RoleClient;
import org.nido.business.clients.repository.RepositoryRolesClients;
import org.springframework.stereotype.Service;

import com.nido.common.api.services.CommonService;

@Service
public class RoleClientService extends CommonService<RoleClient, RepositoryRolesClients> implements IRoleClient {

}