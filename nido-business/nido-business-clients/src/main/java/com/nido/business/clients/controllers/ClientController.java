package com.nido.business.clients.controllers;

import java.io.Serializable;

import com.nido.business.clients.model.Client;
import com.nido.business.clients.services.IClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nido.common.api.controllers.CommonController;

@Validated
@RestController
@RequestMapping("/client")
public class ClientController extends CommonController<Client, IClient> implements Serializable {

	private static final long serialVersionUID = -8589768952231133273L;

}
