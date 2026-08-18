package com.tanish.ecommerce.api_gateway.filters;


import com.tanish.ecommerce.api_gateway.service.JwtService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AuthenticationGatewayFilterFactory extends AbstractGatewayFilterFactory<AuthenticationGatewayFilterFactory.Config> {
    private final JwtService jwtService;

    public AuthenticationGatewayFilterFactory(JwtService jwtService) {
        super(Config.class);
        this.jwtService = jwtService;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            if(!config.enabled) {
                //if authentication is not enabled, just pass the request to the next filter in the chain
                log.info("AuthenticationGatewayFilterFactory is disabled, skipping authentication for request: {}", exchange.getRequest().getURI());
                return chain.filter(exchange);
            }
            // Filter logic here
            log.info("AuthenticationGatewayFilterFactory executed for request: {}", exchange.getRequest().getURI());

            String authorizationHeader = exchange.getRequest().getHeaders().get("Authorization").getFirst();
            if(authorizationHeader==null){
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }

            String token= authorizationHeader != null ? authorizationHeader.replace("Bearer ", "") : null;

            Long userId = jwtService.getUserIdFromToken(token);

            exchange.getRequest()
                    .mutate()
                    .header("X-User-ID", userId.toString())
                    .build();
            return chain.filter(exchange);
        };
    }

    @Data
    public static class Config {
        // Configuration properties for the filter
        private boolean enabled;
    }
}
