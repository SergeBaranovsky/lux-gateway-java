package com.sergeb.luxgatewayjava.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;

@Component
public class ScgPreFilterAuth extends AbstractGatewayFilterFactory<ScgPreFilterAuth.Config> {

    private static final Logger log = LoggerFactory.getLogger(ScgPreFilterAuth.class);

    public ScgPreFilterAuth(){
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(ScgPreFilterAuth.Config config) {

        log.info("*** Enter PreFilter - Authentication ***");

        return ((exchange, chain) -> {
            var request = exchange.getRequest();
            var headers = request.getHeaders();
            headers.forEach((key, value) -> {
                log.debug("Header - {}: {}", key, value);
            });
            var bearer = headers.getValuesAsList("Authorization").toString();
            log.debug("Header - Authorization: {}", bearer);
            return chain.filter(exchange);
        });
    }

    public static class Config {
        // ... we don't need fields/properties for this implementation
    }

}
