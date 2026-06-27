package com.concessionaria.backend.model.dto;

public record CustomerResponseDTO(
		String id,
		String name,
		String document,
		String email,
		String phone
) {

}
