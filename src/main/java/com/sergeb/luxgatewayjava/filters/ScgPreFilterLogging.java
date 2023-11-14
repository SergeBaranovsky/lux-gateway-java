package com.sergeb.luxgatewayjava.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.OrderedGatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;

@Component
public class ScgPreFilterLogging extends AbstractGatewayFilterFactory<ScgPreFilterLogging.Config> {

    private static final Logger log = LoggerFactory.getLogger(ScgPreFilterLogging.class);

    public ScgPreFilterLogging(){
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(ScgPreFilterLogging.Config config) {

        log.info("*** Enter PreFilter - Logging ***");

        return new OrderedGatewayFilter((exchange, chain) -> {
            var request = exchange.getRequest();
            var headers = request.getHeaders();
            var bearer = headers.getValuesAsList("Authorization").toString();
            log.debug("Header - Authorization: {}", bearer);

            log.info("{} - {} - {} - {} - {} - {}",
                    request.getRemoteAddress().toString(),
                    request.getMethod().name(),
                    headers.getValuesAsList("Host").toString(),
                    request.getPath().value(),
                    request.getURI(),
                    headers.getValuesAsList("User-Agent").toString());
            log.info("Authorization: {}", bearer);

            return chain.filter(exchange);
        }, 0);

    }

    public static class Config {
        // ... we don't need fields/properties for this implementation
    }

}
