package name.sakanacatcher.recruit.common.core.util;

import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.lang.reflect.Method;
import java.util.*;

@Component
public class RegisterUtil implements ApplicationContextAware, CommandLineRunner {
    private static ApplicationContext applicationContext;

    public Map<String,Object> form;
    public RegisterUtil(ApplicationContext applicationContext) {
        setApplicationContext(applicationContext);
    }


    @Override
    public void run(String... args) throws Exception {
        Map<String, Object> controllers = applicationContext.getBeansWithAnnotation(RestController.class);
        System.out.println("---------------注册资源----------------");
        form = new HashMap<>();
        List<Map<String,Object>> list = new ArrayList<>();
        List<String> controllerList = new ArrayList<>();
        for (Map.Entry<String, Object> entry : controllers.entrySet()) {
            // 获取Controller
            Object value = entry.getValue();
            Class<?> aClass = AopUtils.getTargetClass(value);
            //获取对应注解
            String canonicalName = aClass.getCanonicalName();
            String controllerName = canonicalName.substring(
                    canonicalName.indexOf("recruit") + 8,
                    canonicalName.indexOf(".controller")
            ) +
                    canonicalName.substring(canonicalName.lastIndexOf("." ));

            RequestMapping reqAnnotation = aClass.getAnnotation(RequestMapping.class);
            RequestMapping declaredAnnotation = aClass.getDeclaredAnnotation(RequestMapping.class);
            controllerList.add(controllerName);

            List<Method> declaredMethods = Arrays.asList(aClass.getDeclaredMethods());
            System.out.println(canonicalName + ":");
            for(Method method:declaredMethods){
                Map<String,Object> map = new HashMap<>();
                GetMapping getMapping = method.getAnnotation(GetMapping.class);
                PostMapping postMapping = method.getDeclaredAnnotation(PostMapping.class);
                StringBuilder url = new StringBuilder().append(reqAnnotation.value()[0]);
                if(getMapping != null){
                    url.append(getMapping.value()[0]);
                }
                else if(postMapping != null) {
                    url.append(postMapping.value()[0]);
                }
                map.put("name",controllerName);
                map.put("url",url);
                list.add(map);
                System.out.println(url);
            }
            form.put("resources",list);
            form.put("controllers",controllerList);
        }
    }
    @Override
    public void setApplicationContext(ApplicationContext arg0) throws BeansException {
        applicationContext = arg0;
    }
    public static ApplicationContext getApplicationContext(){
        return applicationContext;
    }
    public static String getApplicationName() {
        return getApplicationContext().getApplicationName();
    }

}
