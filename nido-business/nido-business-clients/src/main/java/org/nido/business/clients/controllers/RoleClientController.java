package org.nido.business.clients.controllers;

import java.io.Serializable;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/role-client")
public class RoleClientController implements Serializable {
	
	private static final long serialVersionUID = -9038347490820797851L;

}
