package org.nido.business.clients.dto;

import java.util.List;

import org.nido.business.clients.model.RoleClient;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@NoArgsConstructor
public class ClientsDto {
	
	private Long id;
	private String clientname;
	private String password;
	private String application_name;
	private String company;
	private List<RoleClient> roles;

}
