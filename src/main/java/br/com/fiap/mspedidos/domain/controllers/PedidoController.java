package br.com.fiap.mspedidos.domain.controllers;

import br.com.fiap.estrutura.swagger.annotations.ApiResponseSwaggerCreate;
import br.com.fiap.estrutura.swagger.annotations.ApiResponseSwaggerOk;
import br.com.fiap.estrutura.utils.SpringControllerUtils;
import br.com.fiap.mspedidos.domain.dto.CarrinhoDtoRequest;
import br.com.fiap.mspedidos.domain.service.PedidoService;
import io.swagger.v3.oas.annotations.Operation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pedido")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @GetMapping("/{id}")
    @Operation(summary = "Buscar pedido por ID")
    @ApiResponseSwaggerOk
    public ResponseEntity<?> buscarPedidoPorId(@PathVariable Long id) {
        return SpringControllerUtils.response(HttpStatus.OK,
                () -> pedidoService.buscarPedidoPorId(id)
        );
    }

    @GetMapping("/usuario/{id}")
    @Operation(summary = "Buscar pedido por ID de usuario")
    @ApiResponseSwaggerOk
    public ResponseEntity<?> listarPedidosPorCliente(@PathVariable Long id) {
        return SpringControllerUtils.response(HttpStatus.OK,
                () -> pedidoService.listarPedidosPorUsuario(id)
        );
    }

    @PostMapping
    @Operation(summary = "Cadastrar um novo pedido")
    @ApiResponseSwaggerCreate
    public ResponseEntity<?> criarPedido(@RequestBody CarrinhoDtoRequest carrinho){
        return SpringControllerUtils.response(HttpStatus.CREATED,
                () -> pedidoService.criar(carrinho)
        );
    }

}

