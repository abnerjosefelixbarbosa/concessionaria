package com.concessionaria.backend.model.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.concessionaria.backend.model.dto.CustomerRequestDTO;
import com.concessionaria.backend.model.dto.CustomerResponseDTO;
import com.concessionaria.backend.model.entity.Customer;
import com.concessionaria.backend.model.entity.enums.CustomerType;

import jakarta.validation.Valid;

public interface CustomerService {
	CustomerResponseDTO registerCustomer(CustomerRequestDTO dto);

	CustomerResponseDTO updateCustomerById(String id, @Valid CustomerRequestDTO dto);

	CustomerResponseDTO findCustomerById(String id);

	Page<CustomerResponseDTO> listCustomers(String name, CustomerType customerType, Pageable pageable);

	Customer findCustomerByDocument(String document);
}