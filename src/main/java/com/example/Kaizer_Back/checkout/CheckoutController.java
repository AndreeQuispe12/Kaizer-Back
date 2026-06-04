package com.example.Kaizer_Back.checkout;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.Kaizer_Back.auth.UsuarioPrincipal;
import com.example.Kaizer_Back.checkout.dto.CheckoutRequest;
import com.example.Kaizer_Back.checkout.dto.CheckoutResponse;

import jakarta.validation.Valid;

@RestController
public class CheckoutController {

	private final CheckoutService checkoutService;

	public CheckoutController(CheckoutService checkoutService) {
		this.checkoutService = checkoutService;
	}

	@PostMapping("/api/checkout")
	public CheckoutResponse checkout(
			// El endpoint es público; principal es null si el usuario no está autenticado.
			@AuthenticationPrincipal UsuarioPrincipal principal,
			@Valid @RequestBody CheckoutRequest request) {
		Long userId = principal != null ? principal.getId() : null;
		return checkoutService.checkout(request, userId);
	}
}

