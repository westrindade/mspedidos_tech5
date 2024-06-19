package br.com.fiap.mspedidos.domain.dto;

public record LoginDtoRequest (
        String email,
        String password
) {
}
