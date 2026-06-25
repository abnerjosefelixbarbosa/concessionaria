package com.concessionaria.backend.model.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Dados de requisição da exceção.")
public record ExceptionResponseDTO(
		@Schema(description = "Data e hora da exceção.")
		@JsonFormat(pattern = "yyyy-MM-dd HH:ss")
		LocalDateTime localDateTime,
		@Schema(description = "Status da exceção.")
		Integer status,
		@Schema(description = "Messagem da exceção.")
		String message,
		@Schema(description = "Caminho da exceção.")
		String path
) {

}
