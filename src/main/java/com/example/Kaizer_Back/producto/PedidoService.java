package com.example.Kaizer_Back.producto;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.Kaizer_Back.producto.dto.PedidoResponse;

@Service
public class PedidoService {

	private final PedidoRepository pedidoRepository;

	public PedidoService(PedidoRepository pedidoRepository) {
		this.pedidoRepository = pedidoRepository;
	}

	// Solo lectura para evitar flush innecesario al final de la transacción
	@Transactional(readOnly = true)
	public List<PedidoResponse> obtenerMisPedidos(Long usuarioId) {
		return pedidoRepository.findByUsuarioIdWithItems(usuarioId)
				.stream()
				.map(PedidoResponse::from)
				.toList();
	}
}
