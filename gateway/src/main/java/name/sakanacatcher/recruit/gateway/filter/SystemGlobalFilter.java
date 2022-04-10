package name.sakanacatcher.recruit.gateway.filter;


import name.sakanacatcher.recruit.common.core.entity.vo.Result;
import name.sakanacatcher.recruit.common.core.util.ServiceTokenUtil;
import name.sakanacatcher.recruit.gateway.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
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
import java.util.Map;

//全局过滤器
@Component
public class SystemGlobalFilter implements GlobalFilter {
    private static final String X_CLIENT_TOKEN_USER = "x-client-token-user";
    private static final String X_CLIENT_TOKEN = "x-client-token";
    @Value("${sign:123}")
    String sign;

    @Value("${spring.application.name:456}")
    String appName;

    @Autowired
    AuthenticationService authenticationService;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String authentication = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        String method = request.getMethodValue();
        String url = request.getPath().value();
        exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);
        System.out.println(url);
        // 鉴权模块
        // 忽略资源
        Result<Boolean> result = authenticationService.isIgnore(url);
        System.out.println(result.toMap().toString());
        if(result.getData()){
            System.out.println(url);
            ServerHttpRequest.Builder builder = request.mutate();
            builder.header("AUTHORIZATION", ServiceTokenUtil.generateToken(sign,appName));
            return chain.filter(exchange.mutate().request(builder.build()).build());
        }
        else if (authentication == null) {
            return noneToken(exchange);
        }
        else {
            ServerHttpRequest.Builder builder = request.mutate();
            builder.header(X_CLIENT_TOKEN_USER, authentication);
            builder.header("AUTHORIZATION", ServiceTokenUtil.generateToken(sign,appName));
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
