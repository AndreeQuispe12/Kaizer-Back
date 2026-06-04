package com.example.Kaizer_Back.checkout;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.Kaizer_Back.checkout.dto.CheckoutRequest;
import com.example.Kaizer_Back.producto.Pedido;
import com.example.Kaizer_Back.producto.PedidoRepository;
import com.example.Kaizer_Back.producto.Producto;
import com.example.Kaizer_Back.producto.ProductoRepository;
import com.example.Kaizer_Back.usuario.UsuarioRepository;

@ExtendWith(MockitoExtension.class)
class CheckoutServiceTest {

	@Mock ProductoRepository productoRepository;
	@Mock PedidoRepository pedidoRepository;
	@Mock UsuarioRepository usuarioRepository;

	@InjectMocks CheckoutService checkoutService;

	@Test
	@DisplayName("Debe lanzar StockInsuficienteException cuando el stock disponible es 0")
	void checkout_stockCero_lanzaExcepcion() {
		Producto producto = Producto.builder()
				.id(1L).nombre("Test").precio(BigDecimal.valueOf(100)).stock(0).build();
		given(productoRepository.findByIdForUpdate(1L)).willReturn(Optional.of(producto));

		assertThatThrownBy(() -> checkoutService.checkout(buildRequest(1L, 1), null))
				.isInstanceOf(StockInsuficienteException.class);
	}

	@Test
	@DisplayName("Debe lanzar StockInsuficienteException cuando la cantidad supera el stock disponible")
	void checkout_cantidadSuperaStock_lanzaExcepcion() {
		Producto producto = Producto.builder()
				.id(1L).nombre("Test").precio(BigDecimal.valueOf(100)).stock(2).build();
		given(productoRepository.findByIdForUpdate(1L)).willReturn(Optional.of(producto));

		assertThatThrownBy(() -> checkoutService.checkout(buildRequest(1L, 5), null))
				.isInstanceOf(StockInsuficienteException.class)
				.hasMessageContaining("Disponible: 2")
				.hasMessageContaining("solicitado: 5");
	}

	// --- helpers ---

	private CheckoutRequest buildRequest(Long productId, int quantity) {
		CheckoutRequest request = new CheckoutRequest();
		CheckoutRequest.Item item = new CheckoutRequest.Item();
		item.setProductId(productId);
		item.setQuantity(quantity);
		request.setItems(List.of(item));
		return request;
	}
}
