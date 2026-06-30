package com.concessionaria.backend.model.service;

import com.concessionaria.backend.model.dto.CustomerRequestDTO;
import com.concessionaria.backend.model.dto.CustomerResponseDTO;

import jakarta.validation.Valid;

public interface CustomerService {
	CustomerResponseDTO registerCustomer(CustomerRequestDTO dto);

	CustomerResponseDTO updateCustomerById(String id, @Valid CustomerRequestDTO dto);

	CustomerResponseDTO findCustomerById(String id);
}
