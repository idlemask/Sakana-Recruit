package name.sakanacatcher.recruit.gateway.service;

import name.sakanacatcher.recruit.common.core.entity.vo.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static name.sakanacatcher.recruit.common.core.entity.vo.Result.SUCCESSFUL_CODE;
import static name.sakanacatcher.recruit.common.core.entity.vo.Result.SUCCESSFUL_MESG;


@Component
@FeignClient(value = "authorization-server")
@RequestMapping( value = "auth/authorization")
public interface AuthorizationService {
    @GetMapping(value = "generate")
    public Result<String> generateToken(@RequestParam("username") String username );

    @GetMapping(value = "clear")
    public Result clearToken(@RequestParam("username") String username, @RequestParam(name = "device", required = false) String device);

    @GetMapping(value = "refresh")
    public Result<String> refreshToken(@RequestParam("tokenStr") String tokenStr, @RequestParam(name = "device", required = false) String device);

    @GetMapping("ignore/check/url")
    public Result<Boolean> isIgnore(@RequestParam("url") String url);
}
