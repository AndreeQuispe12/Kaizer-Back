package com.example.Kaizer_Back.producto;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Kaizer_Back.auth.UsuarioPrincipal;
import com.example.Kaizer_Back.producto.dto.PedidoResponse;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

	private final PedidoService pedidoService;

	public PedidoController(PedidoService pedidoService) {
		this.pedidoService = pedidoService;
	}

	@GetMapping("/mis-pedidos")
	public List<PedidoResponse> misPedidos(@AuthenticationPrincipal UsuarioPrincipal principal) {
		return pedidoService.obtenerMisPedidos(principal.getId());
	}
}
