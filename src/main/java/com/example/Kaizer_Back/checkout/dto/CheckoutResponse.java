package com.example.Kaizer_Back.checkout.dto;

import java.math.BigDecimal;

public class CheckoutResponse {
	private Long orderId;
	private BigDecimal total;

	public CheckoutResponse(Long orderId, BigDecimal total) {
		this.orderId = orderId;
		this.total = total;
	}

	public Long getOrderId() {
		return orderId;
	}

	public BigDecimal getTotal() {
		return total;
	}
}

