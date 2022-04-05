package name.sakanacatcher.recruit.auth.authorization.controller;

import name.sakanacatcher.recruit.auth.authorization.entity.Token;
import name.sakanacatcher.recruit.auth.authorization.repository.TokenRepository;
import name.sakanacatcher.recruit.common.core.entity.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth/authorization/")
public class AuthorizationServerController {
    @Autowired
    TokenRepository tokenRepository;

    @Transactional
    @GetMapping(value = "generate")
    public Result<String> generateToken(@RequestParam("username") String username) {
        Token token = new Token(username);
        System.out.println(token.getUsername());
        System.out.println(token.getCreateTime());
        System.out.println(token.getExpiredTime());
        tokenRepository.deleteAllByUsername(username);
        tokenRepository.save(token);
        return Result.success(token.toString());
    }

    @Transactional
    @GetMapping(value = "clear")
    public Result clearToken(@RequestParam("username") String username, @RequestParam(name = "device", required = false) String device) {
        Boolean flag;
        if (device != null) {
            flag = tokenRepository.deleteByUsernameAndDevice(username, device) > 0;
        } else {
            flag = tokenRepository.deleteAllByUsername(username) > 0;
        }
        tokenRepository.deleteAllByUsername(username);
        return flag ? Result.success() : Result.fail();
    }

    @Transactional
    @GetMapping(value = "refresh")
    public Result<String> refreshToken(@RequestParam("tokenStr") String tokenStr, @RequestParam(name = "device", required = false) String device) {
        Token token = Token.parseString(tokenStr);
        if (token.refreshToken()) {
            tokenRepository.save(token);
            return Result.success(token.toString());
        } else {
            return Result.fail(token.toString());
        }
    }

}
