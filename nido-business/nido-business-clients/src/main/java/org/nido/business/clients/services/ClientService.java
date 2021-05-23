package org.nido.business.clients.services;

import org.nido.business.clients.model.Client;
import org.nido.business.clients.repository.RepositoryClients;
import org.springframework.stereotype.Service;

import com.nido.common.api.services.CommonService;

@Service
public class ClientService extends CommonService<Client, RepositoryClients> implements IClient {

}
