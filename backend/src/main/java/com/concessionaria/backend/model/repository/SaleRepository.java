package com.concessionaria.backend.model.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.concessionaria.backend.model.entity.Sale;
import com.concessionaria.backend.model.entity.enums.PaymentType;

@Repository
public interface SaleRepository extends JpaRepository<Sale, String> {

	@Query("""
			select s
			from Sale s
			where (:paymentType is null or s.paymentType = :paymentType)
	""")
	Page<Sale> listSales(@Param("paymentType") PaymentType paymentType, Pageable pageable);
}