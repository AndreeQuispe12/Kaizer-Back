package com.example.Kaizer_Back.producto.dto;

import java.math.BigDecimal;

import com.example.Kaizer_Back.producto.PedidoItem;

public record PedidoItemResponse(
		Long productoId,
		String nombreProducto,
		Integer cantidad,
		BigDecimal precioUnitario,
		BigDecimal subtotal
) {
	public static PedidoItemResponse from(PedidoItem item) {
		return new PedidoItemResponse(
				item.getProducto().getId(),
				item.getProducto().getNombre(),
				item.getCantidad(),
				item.getPrecioUnitario(),
				// subtotal es columna generada por PostgreSQL (cantidad * precio_unitario)
				item.getSubtotal()
		);
	}
}
