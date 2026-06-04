package com.example.Kaizer_Back.integration.ruc;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Kaizer_Back.integration.ruc.dto.RucResponse;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Validated
@RestController
@RequestMapping("/api/consulta")
public class RucController {

	private final RucService rucService;

	public RucController(RucService rucService) {
		this.rucService = rucService;
	}

	@GetMapping("/ruc/{ruc}")
	public RucResponse consultarRuc(
			@PathVariable
			@NotBlank
			@Pattern(regexp = "^\\d{11}$", message = "El RUC debe tener exactamente 11 dígitos numéricos")
			String ruc) {
		return rucService.consultar(ruc);
	}
}
