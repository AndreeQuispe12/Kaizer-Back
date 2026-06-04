package com.example.Kaizer_Back.checkout;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class ApiExceptionHandlerTest {

	private final ApiExceptionHandler handler = new ApiExceptionHandler();

	@Test
	@DisplayName("StockInsuficienteException debe retornar HTTP 400 Bad Request")
	void handleStock_returns400BadRequest() {
		StockInsuficienteException ex = new StockInsuficienteException(1L, 0, 3);

		ResponseEntity<String> response = handler.handleStock(ex);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
		assertThat(response.getBody()).contains("Stock insuficiente");
	}

	@Test
	@DisplayName("El mensaje de error debe incluir el stock disponible y el solicitado")
	void handleStock_mensajeContieneDetalle() {
		StockInsuficienteException ex = new StockInsuficienteException(5L, 2, 10);

		ResponseEntity<String> response = handler.handleStock(ex);

		assertThat(response.getBody())
				.contains("5")
				.contains("2")
				.contains("10");
	}
}
