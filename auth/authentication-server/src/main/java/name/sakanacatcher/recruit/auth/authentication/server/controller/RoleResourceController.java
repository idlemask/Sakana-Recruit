package name.sakanacatcher.recruit.auth.authentication.server.controller;

import com.alibaba.fastjson.JSONObject;
import name.sakanacatcher.recruit.auth.authentication.server.entity.Resource;
import name.sakanacatcher.recruit.auth.authentication.server.entity.Role;
import name.sakanacatcher.recruit.auth.authentication.server.entity.RoleResouce;
import name.sakanacatcher.recruit.auth.authentication.server.repository.ResourceRepository;
import name.sakanacatcher.recruit.auth.authentication.server.repository.RoleRepository;
import name.sakanacatcher.recruit.auth.authentication.server.repository.RoleResourceRepository;
import name.sakanacatcher.recruit.common.core.entity.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/auth/authentication/role/")
public class RoleResourceController {
    @Autowired
    RoleRepository roleRepository;

    @Autowired
    ResourceRepository resourceRepository;

    @Autowired
    RoleResourceRepository rrRepository;

    @Transactional
    @PostMapping(value = "generate")
    Result<Boolean> generateRole(@RequestBody Map<String,String> form){
        Role role = new Role();
        role.setName(form.get("name"));
        role.setComment(form.get("comment"));
        roleRepository.save(role);
        return Result.success();
    }

    @Transactional
    @PostMapping(value = "update")
    Result<Boolean> updateRole(@RequestBody Map<String,String> form){
        JSONObject oRole = JSONObject.parseObject(roleRepository.findById(Integer.parseInt(form.get("id"))).toString());
        for(String key:form.keySet()){
            oRole.put(key,form.get(key));
        }
        Role nRole = JSONObject.parseObject(oRole.toJSONString(),Role.class);
        roleRepository.save(nRole);
        return  Result.success();
    }

    @Transactional
    @PostMapping(value = "accredit/resource/id")
    Result addRoleResourceById(@RequestBody Map<String,String> form){
        Role role = roleRepository.findById(Integer.parseInt(form.get("role_id")));
        Resource resource = resourceRepository.findById(Integer.parseInt(form.get("resource_id")));
        RoleResouce rr = new RoleResouce();
        rr.setRole(role);
        rr.setResource(resource);
        rrRepository.save(rr);
        return Result.success();
    }

    @Transactional
    @PostMapping(value = "accredit/resource/name")
    Result addRoleResourceByControllerName(@RequestBody Map<String,String> form){
        Role role = roleRepository.findById(Integer.parseInt(form.get("role_id")));
        List<Resource> resources = resourceRepository.findAllByName(form.get("name"));
        RoleResouce rr = new RoleResouce();
        rr.setRole(role);
        for(Resource resource:resources){
            rr.setResource(resource);
            rrRepository.save(rr);
        }
        return Result.success();
    }

    @Transactional
    @PostMapping(value = "accredit/resource/prefix")
    Result addRoleResourceByControllerNameStartWith(@RequestBody Map<String,String> form){
        Role role = roleRepository.findById(Integer.parseInt(form.get("role_id")));
        List<Resource> resources = resourceRepository.findAllByNameStartingWith(form.get("name"));
        RoleResouce rr = new RoleResouce();
        rr.setRole(role);
        for(Resource resource:resources){
            rr.setResource(resource);
            rrRepository.save(rr);
        }
        return Result.success();
    }

    @Transactional
    @PostMapping(value = "ban/resource")
    Result banRoleResourceById(@RequestBody Map<String,String> form){
        Role role = roleRepository.findById(Integer.parseInt(form.get("role_id")));
        Resource resource = resourceRepository.findById(Integer.parseInt(form.get("resource_id")));
        RoleResouce rr = new RoleResouce();
        rr.setRole(role);
        rr.setResource(resource);
        rr.setType("BLACK");
        rrRepository.save(rr);
        return Result.success();
    }

    @Transactional
    @PostMapping(value = "remove/rule/id")
    Result deleteRoleResourceRuleById(@RequestBody Map<String,String> form){
        Role role = roleRepository.findById(Integer.parseInt(form.get("role_id")));
        Resource resource = resourceRepository.findById(Integer.parseInt(form.get("resource_id")));
        RoleResouce rr = rrRepository.findByRoleEqualsAndAndResourceEquals(role,resource);
        rrRepository.delete(rr);
        return Result.success();
    }

}
