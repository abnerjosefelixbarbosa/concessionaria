package com.concessionaria.backend.model.mapper;

import com.concessionaria.backend.model.dto.CustomerRequestDTO;
import com.concessionaria.backend.model.dto.CustomerResponseDTO;
import com.concessionaria.backend.model.entity.Customer;

public class CustomerMapper {
	public static Customer toCustomer(CustomerRequestDTO dto) {
		return new Customer(null, dto.name(), dto.document(), dto.email(), dto.phone(), dto.customerType(), null);
	}

	public static CustomerResponseDTO toCustomerResponseDTO(Customer customer) {
		return new CustomerResponseDTO(customer.getId(), customer.getName(), customer.getDocument(),
				customer.getEmail(), customer.getPhone(), customer.getCustomerType());
	}
}
