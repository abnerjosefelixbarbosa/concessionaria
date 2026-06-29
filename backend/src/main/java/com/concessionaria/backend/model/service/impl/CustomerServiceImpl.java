package com.concessionaria.backend.model.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.concessionaria.backend.model.dto.CustomerRequestDTO;
import com.concessionaria.backend.model.dto.CustomerResponseDTO;
import com.concessionaria.backend.model.entity.Customer;
import com.concessionaria.backend.model.exception.ApplicationException;
import com.concessionaria.backend.model.exception.NotFoundException;
import com.concessionaria.backend.model.mapper.CustomerMapper;
import com.concessionaria.backend.model.repository.CustomerRepository;
import com.concessionaria.backend.model.service.CustomerService;

import br.com.caelum.stella.validation.CNPJValidator;
import br.com.caelum.stella.validation.CPFValidator;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@Service
public class CustomerServiceImpl implements CustomerService {
	private CustomerRepository customerRepository;

	public CustomerServiceImpl(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}

	@Transactional
	public CustomerResponseDTO registerCustomer(CustomerRequestDTO dto) {
		Customer customer = CustomerMapper.toCustomer(dto);

		validateCustomer(customer);

		Customer customerSaved = customerRepository.save(customer);

		return CustomerMapper.toCustomerResponseDTO(customerSaved);
	}

	public CustomerResponseDTO updateCustomerById(String id, @Valid CustomerRequestDTO dto) {
		Customer customer = CustomerMapper.toCustomer(dto);

		validateCustomer(customer);

		Customer customerFound = customerRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Id deve ser existente."));
		
		BeanUtils.copyProperties(customer, customerFound, "id");
		
		customerRepository.save(customerFound);

		return CustomerMapper.toCustomerResponseDTO(customerFound);
	}

	private void validateCustomer(Customer customer) {
		if (!validateDocument(customer.getDocument())) {
			throw new ApplicationException("Documento deve ser CPF ou CNPJ valido.");
		}

		if (isExistsByNameOrDocumentOrEmailOrPhone(customer)) {
			throw new ApplicationException("Nome, documento, email ou telefone não deve ser repetido.");
		}
	}

	private boolean validateDocument(String document) {
		return isCPF(document) || isCNPJ(document);
	}

	private boolean isCPF(String cpf) {
		CPFValidator validator = new CPFValidator();

		try {
			validator.assertValid(cpf);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	private boolean isCNPJ(String cnpj) {
		CNPJValidator validator = new CNPJValidator();

		try {
			validator.assertValid(cnpj);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	private boolean isExistsByNameOrDocumentOrEmailOrPhone(Customer customer) {
		return customerRepository.existsByNameOrDocumentOrEmailOrPhone(customer.getName(), customer.getDocument(),
				customer.getEmail(), customer.getPhone());
	}
}
