package name.sakanacatcher.recruit.gateway.filter;

import name.sakanacatcher.recruit.common.web.vo.Result;
import name.sakanacatcher.recruit.gateway.service.AuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

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
        // 鉴权模块
        if (authentication == null) {
            return unauthorized(exchange);
        } else {
            ServerHttpRequest.Builder builder = request.mutate();
            builder.header(X_CLIENT_TOKEN_USER, authentication);
            builder.header(X_CLIENT_TOKEN_USER);
            return chain.filter(exchange.mutate().request(builder.build()).build());
        }
    }

    private Mono<Void> unauthorized(ServerWebExchange serverWebExchange) {
        serverWebExchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        authorizationService.generateToken("visitor").toString();
        Map<String, Object> result = Result.quickSuccess(
        ).toJSON();
        DataBuffer buffer = serverWebExchange.getResponse()
                .bufferFactory().wrap(result.toString().getBytes());
        return serverWebExchange.getResponse().writeWith(Flux.just(buffer));
    }


}
