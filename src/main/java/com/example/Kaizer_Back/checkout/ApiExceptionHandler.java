package com.example.Kaizer_Back.checkout;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {

	@ExceptionHandler(StockInsuficienteException.class)
	public ResponseEntity<String> handleStock(StockInsuficienteException ex) {
		return ResponseEntity.badRequest().body(ex.getMessage());
	}

	// Maneja las violaciones de @Pattern, @NotBlank, etc. en path variables y params
	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<String> handleConstraintViolation(ConstraintViolationException ex) {
		String mensaje = ex.getConstraintViolations().stream()
				.map(cv -> cv.getMessage())
				.findFirst()
				.orElse("Parámetro inválido");
		return ResponseEntity.badRequest().body(mensaje);
	}
}

