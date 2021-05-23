package com.nido.business.clients.controllers;

import java.io.Serializable;

import com.nido.business.clients.model.RoleClient;
import com.nido.business.clients.services.IRoleClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nido.common.api.controllers.CommonController;

@Validated
@RestController
@RequestMapping("/role-client")
public class RoleClientController extends CommonController<RoleClient, IRoleClient> implements Serializable {

	private static final long serialVersionUID = -9038347490820797851L;

}