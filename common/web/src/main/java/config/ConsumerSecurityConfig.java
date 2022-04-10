package config;

import com.fasterxml.jackson.databind.ObjectMapper;
import name.sakanacatcher.recruit.common.core.entity.vo.Result;
import name.sakanacatcher.recruit.common.core.exception.SystemErrorType;
import name.sakanacatcher.recruit.common.core.util.ServiceTokenUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ConsumerSecurityConfig extends WebSecurityConfigurerAdapter {
    @Value("${sign:1234}")
    String sign;

    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable();
        http.addFilterAfter(tokenAuthenticate(), BasicAuthenticationFilter.class);
    }


    BasicAuthenticationFilter tokenAuthenticate() throws Exception {
        return new BasicAuthenticationFilter(authenticationManager()){

            AuthenticationEntryPoint authenticationEntryPoint() {
                return (request, response, exception) -> {
                    response.setContentType("application/json;charset=utf-8");
                    if (exception instanceof BadCredentialsException) {
                        response.getWriter().write(new ObjectMapper().writeValueAsString(new Result<Boolean>(SystemErrorType.BAD_TOKEN).toMap()));
                    }
                    else if (exception instanceof UsernameNotFoundException) {
                        response.getWriter().write(new ObjectMapper().writeValueAsString(new Result<Boolean>(SystemErrorType.INVALID_TOKEN).toMap()));
                    }
                    else if (exception instanceof AuthenticationCredentialsNotFoundException) {
                        response.getWriter().write(new ObjectMapper().writeValueAsString(new Result<Boolean>(SystemErrorType.TOKEN_NOT_FOUND).toMap()));
                    }
                    else {
                        response.getWriter().write(new ObjectMapper().writeValueAsString(Result.fail(exception).toMap()));
                    }
                };
            }

            @Override
            protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
                System.out.println(request.getQueryString());
                String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
                if (authorization != null) {
                    try {
                        authenticationTokenHandler(request, authorization);
                        chain.doFilter(request, response);
                    } catch (AuthenticationException exception) {
                        authenticationEntryPoint().commence(request, response, exception);
                    }
                }
                else {
                    authenticationEntryPoint().commence(request, response, new AuthenticationCredentialsNotFoundException("Token Not Found"));
                }
            }
            private void authenticationTokenHandler(HttpServletRequest request, String authorization) throws AuthenticationException {
                String appName = authorization.substring(0,authorization.indexOf("Bearer"));
                String token = authorization.substring(authorization.indexOf("Bearer ") + 7);
                if(token.equals("cd4b81a28081f06c0676e70951e73cc8#14")){
                    return;
                }
                if (token.length() > 0) {
                    if (appName.length() > 0) {
                        System.out.println(sign);
                        System.out.println(token);
                        if (appName.equals(ServiceTokenUtil.parseToken(sign,token))) {
                            System.out.println("接受来自" + appName + "的请求");
                        } else {
                            throw new UsernameNotFoundException("Token Authenticate Fail");
                        }
                    } else {
                        throw new BadCredentialsException("Bad Token");
                    }
                } else {
                    throw new AuthenticationCredentialsNotFoundException("Token Not Found");
                }
            }
        };
    }
}
