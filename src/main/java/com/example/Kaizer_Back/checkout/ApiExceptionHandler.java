package com.example.Kaizer_Back.checkout;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {

	@ExceptionHandler(StockInsuficienteException.class)
	public ResponseEntity<String> handleStock(StockInsuficienteException ex) {
		return ResponseEntity.badRequest().body(ex.getMessage());
	}
}

