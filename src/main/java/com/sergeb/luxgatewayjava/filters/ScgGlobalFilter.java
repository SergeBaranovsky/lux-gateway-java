package com.sergeb.luxgatewayjava.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
// Test GlobalFilter
public class ScgGlobalFilter implements GlobalFilter, Ordered {
    private static final Logger log = LoggerFactory.getLogger(ScgGlobalFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("*** Enter GlobalFilter ***");
        var request = exchange.getRequest();
        var headers = request.getHeaders();
        headers.forEach((key, value) -> {
            log.debug("Header - {}: {}", key, value);
        });
        var bearer = headers.getValuesAsList("Authorization").toString();
        log.debug("Header - Authorization: {}", bearer);
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return -1;
    }
}
