package name.sakanacatcher.recruit.common.web.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.cglib.beans.BeanMap;

import java.util.HashMap;
import java.util.Map;

@Data
@ApiModel(description = "web请求的返回模型")
public class Result {
    private static final int SUCCESS = 200;
    private static final int AUTH_ERROR = 403;
    private static final int UNAUTHORIZED = 401;
    private static final int BAD_REQUEST = 400;
    private static final int TO_LARGE = 413;
    private static final int OVERLOADED = 421;

    @ApiModelProperty(value = "处理结果code", required = true)
    private int code;

    @ApiModelProperty(value = "处理结果描述信息")
    private String msg;
    @ApiModelProperty(value = "请求结果生成时间戳")

    private Object data = null;

    public static Result quickSuccess() {
        Result result = new Result();
        result.setCode(SUCCESS);
        result.setMsg("操作成功");
        return result;
    }

    public static Result quickSuccess(String data) {
        Result result = new Result();
        result.setCode(SUCCESS);
        result.setMsg("操作成功");
        result.setData(data);
        return result;
    }

    public static Result quickFail() {
        Result result = new Result();
        result.setCode(SUCCESS);
        result.setMsg("操作失败");
        return result;
    }

    public static Result quickFail(String data) {
        Result result = new Result();
        result.setCode(SUCCESS);
        result.setMsg("操作失败");
        result.setData(data);
        return result;
    }

    public static Result success(String msg) {
        Result result = new Result();
        result.setCode(SUCCESS);
        result.setMsg(msg);
        return result;
    }

    public static Result success(String msg, String data) {
        Result result = new Result();
        result.setCode(SUCCESS);
        result.setMsg(msg);
        result.setData(data);
        return result;
    }

    public static Result fail(String msg) {
        Result result = new Result();
        result.setCode(SUCCESS);
        result.setMsg(msg);
        return result;
    }

    public static Result fail(String msg, String data) {
        Result result = new Result();
        result.setCode(SUCCESS);
        result.setMsg(msg);
        return result;
    }

    public Map<String, Object> toJSON() {
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> dataMap = new HashMap();
        if (data != null) {
            BeanMap beanMap = BeanMap.create(data);
            for (Object key : beanMap.keySet()) {
                dataMap.put(key + "", beanMap.get(key));
            }
        }
        map.put("code", code);
        map.put("message", msg);
        map.put("data", dataMap);
        return map;
    }


}

