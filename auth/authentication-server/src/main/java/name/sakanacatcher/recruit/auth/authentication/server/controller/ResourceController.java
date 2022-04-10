package name.sakanacatcher.recruit.auth.authentication.server.controller;
import com.alibaba.fastjson.JSONObject;
import name.sakanacatcher.recruit.auth.authentication.server.entity.Resource;
import name.sakanacatcher.recruit.auth.authentication.server.entity.ResourceIgnore;
import name.sakanacatcher.recruit.auth.authentication.server.repository.ResourceIgnoreRepository;
import name.sakanacatcher.recruit.auth.authentication.server.repository.ResourceRepository;
import name.sakanacatcher.recruit.common.core.entity.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static name.sakanacatcher.recruit.common.core.entity.vo.Result.SUCCESSFUL_CODE;
import static name.sakanacatcher.recruit.common.core.entity.vo.Result.SUCCESSFUL_MESG;

@RestController
@RequestMapping( value = "/auth/authentication/resource/")
public class ResourceController {

    @Autowired
    ResourceRepository resourceRepository;

    @Autowired
    ResourceIgnoreRepository ignoreRepository;

    @Transactional
    @PostMapping("register")
    public Result<Boolean> registerResource(@RequestBody Map<String, Object> formData){
        String name = (String) formData.get("name");
        System.out.println(formData.get("data"));
        List<Map<String,Object>> data = (List<Map<String, Object>>) formData.get("resources");
        System.out.println(data);
        List<String> controllers = (List<String>) formData.get("controllers");
        Map<String,List<Resource>> olds = new HashMap<>();
        Map<String,Boolean> temp= new HashMap<>();
        for(String s:controllers){
            if(beforeRegisterService(s).isSuccess()){
                olds.put(s,resourceRepository.findAllByName(s));
            }
        }
        for(Object o:data){
            Resource resource = JSONObject.parseObject(JSONObject.toJSONString(o),Resource.class);
            if(olds.get(resource.getName()).stream().anyMatch(r ->{ return r.getUrl().equals(resource.getUrl()); })){
                resourceRepository.updateEnableByUrl(resource.getUrl(),true);
            }
            else {
                if(!temp.containsKey(resource.getUrl())){
                    temp.put(resource.getUrl(),true);
                    System.out.println(resource);
                    resourceRepository.save(resource);
                }
            }
        }
        return Result.success();
    }

    @PostMapping("register/before")
    public Result<Boolean> beforeRegisterService(String name){
        resourceRepository.updateEnableByName(name,false);
        return Result.success();
    }

    @GetMapping("ignore/check/url")
    public Result<Boolean> isIgnore(@RequestParam("url") String url){
        List<ResourceIgnore> ignores = ignoreRepository.findAllByEnableEquals(true);
        ignores = ignores.stream().filter(
                ignore->{
                    return url.contains(ignore.getUrl());
                }
        ).collect(Collectors.toList());
        return new Result<Boolean>(SUCCESSFUL_CODE,SUCCESSFUL_MESG, ignores.size() > 0);
    }

    @PostMapping("ignore/add")
    @Transactional
    public Result<Boolean> addIgnore(@RequestBody Map<String,Object> formData){
        ResourceIgnore resourceIgnore = JSONObject.parseObject(JSONObject.toJSONString(formData),ResourceIgnore.class);
        ignoreRepository.save(resourceIgnore);
        return  Result.success();
    }

    @PostMapping("ignore/update")
    @Transactional
    public Result<Boolean> updateIgnore(@RequestBody Map<String,Object> formData){
        JSONObject old = JSONObject.parseObject(ignoreRepository.findById((Integer) formData.get("id")).toString());
        for(String key:formData.keySet()){
            old.put(key,formData.get(key));
        }
        ResourceIgnore resourceIgnore = JSONObject.parseObject(old.toJSONString(), ResourceIgnore.class);
        ignoreRepository.save(resourceIgnore);
        return  Result.success();
    }



}
