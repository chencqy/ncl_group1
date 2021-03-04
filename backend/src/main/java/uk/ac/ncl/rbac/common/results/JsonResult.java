package uk.ac.ncl.rbac.common.results;

import java.io.Serializable;

/**
 * Unified return entity
 */
public class JsonResult<T> implements Serializable {

    private static final long serialVersionUID = 3670753785298502625L;

    private Boolean success;
    private Integer code;
    private String massage;
    private T data;

    public JsonResult() {
    }

    public JsonResult(boolean success) {
        this.success = success;
        this.code = success ? ResultCode.SUCCESS.getCode() : ResultCode.FAIL.getCode();
        this.massage = success ? ResultCode.SUCCESS.getMessage() : ResultCode.FAIL.getMessage();
    }

    public JsonResult(boolean success, ResultCode resultEnum) {
        this.success = success;
        this.code = success ? ResultCode.SUCCESS.getCode() :
                         (resultEnum == null ? ResultCode.FAIL.getCode() : resultEnum.getCode());
        this.massage = success ? ResultCode.SUCCESS.getMessage() :
                        (resultEnum == null ? ResultCode.FAIL.getMessage() : resultEnum.getMessage());
    }

    public JsonResult(boolean success, T data) {
        this.success = success;
        this.code = success ? ResultCode.SUCCESS.getCode() : ResultCode.FAIL.getCode();
        this.massage = success ? ResultCode.SUCCESS.getMessage() : ResultCode.FAIL.getMessage();
        this.data = data;
    }

    public JsonResult(boolean success, ResultCode resultEnum, T data) {
        this.success = success;
        this.code = success ? ResultCode.SUCCESS.getCode() :
                         (resultEnum == null ? ResultCode.FAIL.getCode() : resultEnum.getCode());
        this.massage = success ? ResultCode.SUCCESS.getMessage() :
                        (resultEnum == null ? ResultCode.FAIL.getMessage() : resultEnum.getMessage());
        this.data = data;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMassage() {
        return massage;
    }

    public void setMassage(String massage) {
        this.massage = massage;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
