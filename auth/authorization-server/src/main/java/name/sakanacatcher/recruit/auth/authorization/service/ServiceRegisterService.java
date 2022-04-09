package name.sakanacatcher.recruit.auth.authorization.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import name.sakanacatcher.recruit.common.core.entity.vo.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.transaction.Transactional;
import java.util.Map;

@FeignClient( value = "authentication-server")
@RequestMapping( value = "auth/authentication/resource")
@Component
public interface ServiceRegisterService {

    @PostMapping("register")
    Result<String> registerResource(@RequestBody Map<String,Object> formData);

    @PostMapping("register/before")
    Result<String> beforeRegisterService(@RequestBody Map<String,Object> formData);
}
