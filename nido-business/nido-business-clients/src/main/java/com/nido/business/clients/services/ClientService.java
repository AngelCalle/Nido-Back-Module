package com.nido.business.clients.services;

import com.nido.business.clients.model.Client;
import com.nido.business.clients.repository.RepositoryClients;
import org.springframework.stereotype.Service;

import com.nido.common.api.services.CommonService;

@Service
public class ClientService extends CommonService<Client, RepositoryClients> implements IClient {

}
