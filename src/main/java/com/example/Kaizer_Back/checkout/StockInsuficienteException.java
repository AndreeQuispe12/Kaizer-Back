package com.example.Kaizer_Back.checkout;

public class StockInsuficienteException extends RuntimeException {
	public StockInsuficienteException(Long productId, int stockActual, int solicitado) {
		super("Stock insuficiente para producto " + productId + ". Disponible: " + stockActual + ", solicitado: " + solicitado);
	}
}

