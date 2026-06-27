package com.concessionaria.backend.model.service;

import com.concessionaria.backend.model.dto.CustomerRequestDTO;
import com.concessionaria.backend.model.dto.CustomerResponseDTO;

public interface CustomerService {

	CustomerResponseDTO registerCustomer(CustomerRequestDTO dto);

}
