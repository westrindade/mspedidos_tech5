package br.com.fiap.mspedidos.domain.dto;

public record ItemCarrinhoDtoRequest(
        Long idProduto,
        Long quantidade,
        double valor
) { }
