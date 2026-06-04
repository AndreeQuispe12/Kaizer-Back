package com.example.Kaizer_Back.producto.dto;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

import com.example.Kaizer_Back.producto.Pedido;

public record PedidoResponse(
		Long id,
		String estado,
		BigDecimal total,
		OffsetDateTime createdAt,
		List<PedidoItemResponse> items
) {
	public static PedidoResponse from(Pedido p) {
		return new PedidoResponse(
				p.getId(),
				p.getEstado(),
				p.getTotal(),
				p.getCreatedAt(),
				p.getItems().stream().map(PedidoItemResponse::from).toList()
		);
	}
}
