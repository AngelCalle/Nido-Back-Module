package com.nido.business.users.dto;

import org.springframework.data.domain.Sort;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@NoArgsConstructor
public class PageableDTO<T> {

	private Integer page;
	private Integer size;
	private String sort;
	private Sort.Direction order;
	private T searchDTO;

}
