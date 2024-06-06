package br.com.fiap.mspedidos.domain.dto;

import br.com.fiap.estrutura.exception.BusinessException;
import br.com.fiap.mspedidos.domain.entities.ItemEntity;

import java.util.ArrayList;
import java.util.List;

public record CarrinhoDtoRequest(
        Long idUsuario,
        List<ItemCarrinhoDtoRequest> itens,
        String formaPagamento,
        int quantidadeItens,
        double valorTotal
) {
    public List<ItemEntity> ToEntityListItem() throws BusinessException {
        List<ItemEntity> itemEntityList = new ArrayList<>();
        itens.forEach(item -> {
            try {
                itemEntityList.add(new ItemEntity(item.idProduto(),item.quantidade()));
            } catch (BusinessException e) {
                throw new RuntimeException(e);
            }
        });
        return itemEntityList;
    }
}
