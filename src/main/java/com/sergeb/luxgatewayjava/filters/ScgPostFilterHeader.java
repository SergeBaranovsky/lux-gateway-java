package com.sergeb.luxgatewayjava.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class ScgPostFilterHeader extends AbstractGatewayFilterFactory<ScgPostFilterHeader.Config> {

    private static final Logger log = LoggerFactory.getLogger(ScgPostFilterHeader.class);

    public ScgPostFilterHeader(){
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        log.debug("*** Enter PostFilter ***");

        return (exchange, chain) -> {

            ServerHttpResponse response = exchange.getResponse();

            response.beforeCommit(() -> {
                response.getHeaders().set("X-Custom-Header", "Powered by Gateway Service");
                return Mono.empty();
            });

            return chain.filter(exchange).then(Mono.fromRunnable(() -> {

            }));
        };

    }

    public static class Config {
        // ... we don't need fields/properties for this implementation
    }

}
