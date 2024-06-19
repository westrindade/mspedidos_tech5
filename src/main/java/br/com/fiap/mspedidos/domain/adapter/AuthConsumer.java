package br.com.fiap.mspedidos.domain.adapter;

import br.com.fiap.mspedidos.domain.dto.AuthTokenDtoResponse;
import br.com.fiap.mspedidos.domain.dto.LoginDtoRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "auth", url = "${auth.url}")
public interface AuthConsumer {
    @PostMapping(value = "/auth/login")
    AuthTokenDtoResponse obterToken(@RequestBody LoginDtoRequest login);
}
