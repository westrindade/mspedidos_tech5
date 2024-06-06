package br.com.fiap.mspedidos.domain.entities;

import br.com.fiap.estrutura.exception.BusinessException;
import br.com.fiap.mspedidos.domain.dto.ItemPedidoDtoResponse;
import br.com.fiap.mspedidos.domain.dto.PedidoDtoResponse;
import jakarta.persistence.*;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Table(name = "tb_pedidos")
@Entity
public class PedidoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="cd_pedido", unique = true)
    private Long id;
    @Column(name="cd_usuario")
    private Long idUsuario;
    @Column(name = "dt_criacao")
    private LocalDateTime dataCriacao;
    @Column(name="nu_valor_total")
    private BigDecimal valorTotal;
    @Column(name = "ds_forma_Pagamento")
    private String formaPagamento;
    @Column(name = "ds_status_pedido")
    private String statusPedido;
    @Column(name = "dt_pagamento")
    private LocalDateTime dataPagamento;
    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<ItemEntity> itens;

    public PedidoEntity() { }

    public PedidoEntity(Long idUsuario, String formaPagamento, List<ItemEntity> itens)
            throws BusinessException {

        if (idUsuario == 0){
            throw new BusinessException("Usuário não informado");
        }

        if (itens == null || itens.size() == 0){
            throw new BusinessException("Itens do pedido não informado");
        }

        this.idUsuario = idUsuario;
        this.formaPagamento = formaPagamento;
        this.itens = itens;

        this.dataCriacao = LocalDateTime.now();
        this.dataPagamento = LocalDateTime.now();
        this.statusPedido = "PAGO";
        this.valorTotal = BigDecimal.ZERO;
    }

    public Long getId() {
        return id;
    }

    public List<ItemEntity> getItens() {
        return itens;
    }

    public PedidoDtoResponse toDto() {
        return new PedidoDtoResponse(
                this.id,
                this.idUsuario,
                this.dataCriacao,
                this.toListDto()
        );
    }

    private List<ItemPedidoDtoResponse> toListDto(){
        List<ItemPedidoDtoResponse> itens = new ArrayList<>();
        if (this.itens != null && this.itens.size() > 0) {
            this.itens.forEach(item -> {
                itens.add(new ItemPedidoDtoResponse(item.getIdProduto(), item.getQuantidade()));
            });
        }
        return itens;
    }

    @PrePersist
    public void prePersist() throws BusinessException {
        for (ItemEntity item : itens) {
            item.informarPedido(this);
        }
    }
}
