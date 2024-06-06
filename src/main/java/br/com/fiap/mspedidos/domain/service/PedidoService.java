package br.com.fiap.mspedidos.domain.service;

import br.com.fiap.mspedidos.domain.adapter.EstoqueProdutoProducer;
import br.com.fiap.mspedidos.domain.dto.*;
import br.com.fiap.mspedidos.domain.entities.PedidoEntity;
import br.com.fiap.estrutura.exception.BusinessException;
import br.com.fiap.mspedidos.domain.repositories.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PedidoService {

    @Autowired
    private final PedidoRepository pedidoRepository;

    private final EstoqueProdutoProducer estoqueProdutoProducer;

    public PedidoService(PedidoRepository pedidoRepository, EstoqueProdutoProducer estoqueProdutoProducer){
        this.pedidoRepository = pedidoRepository;
        this.estoqueProdutoProducer = estoqueProdutoProducer;
    }

    private PedidoEntity buscarPedidoEntity(Long id) throws BusinessException {
        PedidoEntity pedido = pedidoRepository.findById(id).orElse(null);
        if(pedido == null){
            throw new BusinessException("Pedido não encontrado");
        }
        return pedido;
    }
    public List<PedidoDtoResponse> listarPedidosPorUsuario(Long id) throws BusinessException {
        return pedidoRepository.findByIdUsuario(id).stream().map(PedidoEntity::toDto).toList();
    }

    public PedidoDtoResponse buscarPedidoPorId(Long id) throws BusinessException {
        return buscarPedidoEntity(id).toDto();
    }

    public PedidoDtoResponse criar(CarrinhoDtoRequest carrinho) throws BusinessException {
        final PedidoEntity pedido = new PedidoEntity(
                carrinho.idUsuario(),
                carrinho.formaPagamento(),
                carrinho.ToEntityListItem()
        );
        this.removerEstoqueProduto(pedido);
        try {
            PedidoEntity pedidoRetorno = pedidoRepository.save(pedido);
            return pedidoRetorno.toDto();
        } catch (Exception ex) {
            this.devolverAoEstoqueProduto(pedido);
            throw new BusinessException(ex.getMessage());
        }
    }

    private void removerEstoqueProduto(PedidoEntity pedido) throws BusinessException {
        try{
            pedido.getItens().forEach(item -> {
                this.estoqueProdutoProducer.removerEstoque(item.getIdProduto(),item.getQuantidade());
            });
        } catch (Exception ex) {
            throw new BusinessException("Conexão com msProduto não estabelecida");
        }
    }

    private void devolverAoEstoqueProduto(PedidoEntity pedido) throws BusinessException {
        try{
            pedido.getItens().forEach(item -> {
                this.estoqueProdutoProducer.devolverAoEstoque(item.getIdProduto(),item.getQuantidade());
            });
        } catch (Exception ex) {
            throw new BusinessException("Conexão com msProduto não estabelecida");
        }
    }
}
