package com.example.Kaizer_Back.integration.ruc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
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
class RucServiceTest {

	// El mock nunca es invocado: la validación de formato lanza excepción
	// antes de llamar al cliente HTTP externo.
	@Mock RestClient decolectaClient;

	@InjectMocks RucService rucService;

	@ParameterizedTest(name = "RUC inválido: \"{0}\" debe retornar HTTP 400")
	@ValueSource(strings = { "2060103001", "206010300134", "2060103001A", "abcdefghijk", "" })
	@DisplayName("Formatos de RUC inválidos deben lanzar 400 Bad Request")
	void consultar_formatoInvalido_lanza400(String rucInvalido) {
		ResponseStatusException ex = assertThrows(ResponseStatusException.class,
				() -> rucService.consultar(rucInvalido));

		assertThat(ex.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
	}
}
