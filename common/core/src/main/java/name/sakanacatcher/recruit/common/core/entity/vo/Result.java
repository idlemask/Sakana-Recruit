package name.sakanacatcher.recruit.common.core.entity.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import name.sakanacatcher.recruit.common.core.exception.ErrorType;
import name.sakanacatcher.recruit.common.core.exception.SystemErrorType;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

@ApiModel(description = "rest请求的返回模型，所有rest正常都返回该类的对象")
@Getter
public class Result<T> {

    public static final String SUCCESSFUL_CODE = "000000";
    public static final String SUCCESSFUL_MESG = "处理成功";

    @ApiModelProperty(value = "处理结果code", required = true)
    private String code;
    @ApiModelProperty(value = "处理结果描述信息")
    private String mesg;
    @ApiModelProperty(value = "请求结果生成时间戳")
    private Instant time;
    @ApiModelProperty(value = "处理结果数据信息")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    public Result() {
        this.time = ZonedDateTime.now().toInstant();
    }

    /**
     * @param errorType
     */
    public Result(ErrorType errorType) {
        this.code = errorType.getCode();
        this.mesg = errorType.getMesg();
        this.time = ZonedDateTime.now().toInstant();
    }


    /**
     * @param errorType
     * @param data
     */
    public Result(ErrorType errorType, T data) {
        this(errorType);
        this.data = data;
    }

    /**
     * 内部使用，用于构造成功的结果
     *
     * @param code
     * @param mesg
     * @param data
     */
    public Result(String code, String mesg, T data) {
        this.code = code;
        this.mesg = mesg;
        this.data = data;
        this.time = ZonedDateTime.now().toInstant();
    }

    public static Result<Boolean> success(){
        return new Result<Boolean>(SUCCESSFUL_CODE, SUCCESSFUL_MESG, true);
    }

    public static Result<String> success(String data) {
        return new Result<String>(SUCCESSFUL_CODE, SUCCESSFUL_MESG, data);
    }

    /**
     * 系统异常类没有返回数据
     *
     * @return Result
     */
    public static Result<Boolean> fail() {
        return new Result<Boolean>(SystemErrorType.SYSTEM_ERROR,false);
    }



    public static Result<String> fail(Exception exception) {
        return new Result<String>(SystemErrorType.SYSTEM_NOT_FOUND_EXCEPTION, exception.toString());
    }


    public static Result<Boolean> fail(ErrorType errorType) {
        return Result.fail(errorType);
    }




    /**
     * 成功code=000000
     *
     * @return true/false
     */
    @JsonIgnore
    public boolean isSuccess() {
        return SUCCESSFUL_CODE.equals(this.code);
    }

    /**
     * 失败
     *
     * @return true/false
     */
    @JsonIgnore
    public boolean isFail() {
        return !isSuccess();
    }

    public Map<String,Object> toMap(){
        Map<String,Object> map = new HashMap<>();
        map.put("code",code);
        map.put("message",mesg);
        map.put("data",data);
        return map;
    }
}
