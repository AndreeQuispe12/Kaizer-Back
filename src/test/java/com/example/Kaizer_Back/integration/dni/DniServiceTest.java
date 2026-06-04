package com.example.Kaizer_Back.integration.dni;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.RestClient;
import org.springframework.web.server.ResponseStatusException;

@ExtendWith(MockitoExtension.class)
class DniServiceTest {

	// El mock nunca es invocado en estos tests: la validación de formato lanza
	// ResponseStatusException antes de llamar al cliente HTTP externo.
	@Mock RestClient decolectaClient;

	@InjectMocks DniService dniService;

	@ParameterizedTest(name = "DNI inválido: \"{0}\" debe retornar HTTP 400")
	@ValueSource(strings = { "1234567", "123456789", "1234567A", "abcdefgh", "", "  " })
	@DisplayName("Formatos de DNI inválidos deben lanzar 400 Bad Request")
	void consultar_formatoInvalido_lanza400(String dniInvalido) {
		ResponseStatusException ex = assertThrows(ResponseStatusException.class,
				() -> dniService.consultar(dniInvalido));

		assertThat(ex.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
	}
}
