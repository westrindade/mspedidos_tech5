package br.com.fiap.mspedidos.domain.adapter.config;

import br.com.fiap.mspedidos.domain.service.TokenService;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {

    private final TokenService tokenService;

    public FeignConfig(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Bean
    public RequestInterceptor requestInterceptor() {
        return new RequestInterceptor() {
            @Override
            public void apply(RequestTemplate template) {
                template.header("Authorization", "Bearer " + getJWT());
            }
        };
    }

    private String getJWT() {
        return tokenService.generateToken();
    }
}