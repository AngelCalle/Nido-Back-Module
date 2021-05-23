package com.nido.business.clients.services;

import com.nido.business.clients.model.RoleClient;
import com.nido.business.clients.repository.RepositoryRolesClients;
import org.springframework.stereotype.Service;

import com.nido.common.api.services.CommonService;

@Service
public class RoleClientService extends CommonService<RoleClient, RepositoryRolesClients> implements IRoleClient {

}