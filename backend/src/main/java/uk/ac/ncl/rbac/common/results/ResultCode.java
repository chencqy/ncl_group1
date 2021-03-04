package uk.ac.ncl.rbac.common.results;

/**
 * Definition of ReturnCode
 */
public enum ResultCode {
    /* success */
    SUCCESS(200, "Success"),

    /* common fail */
    FAIL(900, "Fail"),

    /* parameter error：1000～1999 */
    PARAM_NOT_VALID(1001, "Parameter is invalid"),
    PARAM_IS_BLANK(1002, "Parameter is null"),

    /* User error */
    USER_NOT_LOGIN(2001, "User not logged in"),
    USER_CREDENTIALS_ERROR(2002, "Password incorrect"),
    USER_NOT_EXIST(2001, "The user does not exist"),

    /* permission error */
    NO_PERMISSION(3001, "Permission denied");

    private Integer code;
    private String message;

    ResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Get message according to code
     *
     * @param code
     * @return message
     */
    public static String getMessageByCode(Integer code) {
        for (ResultCode ele : values()) {
            if (ele.getCode().equals(code)) {
                return ele.getMessage();
            }
        }
        return null;
    }
}
