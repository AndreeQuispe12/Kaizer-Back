package com.example.Kaizer_Back.usuario.dto;

import jakarta.validation.constraints.Size;

public record UsuarioProfileRequest(
		@Size(max = 100) String nombre,
		@Size(max = 20) String telefono,
		@Size(max = 255) String direccion,
		@Size(max = 100) String ciudad
) {
}
