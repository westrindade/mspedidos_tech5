package br.com.fiap.mspedidos.domain.dto;

import java.time.LocalDateTime;
import java.util.List;

public record PedidoDtoResponse(
        Long id,
        Long idUsuario,
        LocalDateTime dataCriacao,
        List<ItemPedidoDtoResponse> itens
) {
}
