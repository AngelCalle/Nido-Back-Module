package org.nido.business.clients.controllers;

import java.io.Serializable;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/client")
public class ClientController implements Serializable {

	private static final long serialVersionUID = -8589768952231133273L;

}
