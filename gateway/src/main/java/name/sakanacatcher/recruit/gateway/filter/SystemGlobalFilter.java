package name.sakanacatcher.recruit.gateway.filter;


import name.sakanacatcher.recruit.gateway.service.AuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

//全局过滤器
@Component
public class SystemGlobalFilter implements GlobalFilter {
    private static final String X_CLIENT_TOKEN_USER = "x-client-token-user";
    private static final String X_CLIENT_TOKEN = "x-client-token";

    @Autowired
    AuthorizationService authorizationService;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String authentication = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        String method = request.getMethodValue();
        String url = request.getPath().value();
        exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);
        // 鉴权模块
        // 忽略资源
        if(authorizationService.isIgnore(url).getData()){
            return chain.filter(exchange);
        }
        else if (authentication == null) {
            return noneToken(exchange);
        }
        else {
            ServerHttpRequest.Builder builder = request.mutate();
            builder.header(X_CLIENT_TOKEN_USER, authentication);
            return chain.filter(exchange.mutate().request(builder.build()).build());
        }
    }

    private Mono<Void> unauthorized(ServerWebExchange serverWebExchange) {
        serverWebExchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);

        DataBuffer buffer = serverWebExchange.getResponse()
                .bufferFactory().wrap("无权限访问".getBytes(StandardCharsets.UTF_8));
        return serverWebExchange.getResponse().writeWith(Flux.just(buffer));
    }


    private Mono<Void> noneToken(ServerWebExchange serverWebExchange) {
        serverWebExchange.getResponse().setStatusCode(HttpStatus.TEMPORARY_REDIRECT);
        DataBuffer buffer = serverWebExchange.getResponse()
                .bufferFactory().wrap(new String("请先登录".getBytes(StandardCharsets.UTF_8)).getBytes(StandardCharsets.UTF_8));
        return serverWebExchange.getResponse().writeWith(Flux.just(buffer));
    }


}
