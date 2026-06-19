package com.conssecionaria.conssecionaria_backend.model.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Dados de requisição da exceção.")
public class ExceptionResponseDTO {
	@Schema(description = "Data e hora da exceção.")
	@JsonFormat(pattern = "yyyy-MM-dd HH:ss")
	private LocalDateTime localDateTime;
	@Schema(description = "Status da exceção.")
	private Integer status;
	@Schema(description = "Messagem da exceção.")
	private String message;
	@Schema(description = "Caminho da exceção.")
	private String path;
}