package cn.giit.platform.common;

/**
 * @author phw
 * @date 04-08-2018
 * @description 所有请求返回的响应类
 */
public class JsonResult {

    public static final int CODE_SUCCESS = 200;
    public static final int CODE_ERR = 500;

    private Integer code = 0;

    private String msg;

    private Object data;

    private JsonResult(Integer code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    private JsonResult(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private JsonResult(Integer code) {
        this.code = code;
    }

    private JsonResult(Integer code, Object vars) {
        this.code = code;
        this.data = vars;
    }


    /**
     * 成功提示
     * @param msg 提示消息
     * @return json result
     */
    public static JsonResult success(String msg) {
        return new JsonResult(AppConst.RESULT_SUCCESS, msg);
    }

    /**
     * 附带数据的成功提示
     * @param msg 提示消息
     * @param data 返回的数据
     * @return json result
     */
    public static JsonResult success(String msg, Object data) {
        return new JsonResult(CODE_SUCCESS, msg, data);
    }

    /**
     * 成功提示
     * @return json result
     */
    public static JsonResult success() {
        return new JsonResult(CODE_SUCCESS, "request ok");
    }

    /**
     * 成功提示
     * @param data 变量
     * @return json result
     */
    public static JsonResult success(Object data) {
        return new JsonResult(CODE_SUCCESS, "request ok", data);
    }

    /**
     * 错误提示
     * @param msg 提示消息
     * @return json result
     */
    public static JsonResult error(String msg) {
        return new JsonResult(AppConst.RESULT_ERROR, msg);
    }

    /**
     * 错误提示
     * @param code 错误码
     * @param msg 提示消息
     * @return json result
     */
    public static JsonResult error(Integer code, String msg) {
        return new JsonResult(code, msg);
    }

    /**
     * 错误提示
     * @return 错误码
     */
    public static JsonResult error() {
        return new JsonResult(CODE_ERR, "request fail");
    }

    /**
     * 警告信息
     * @param msg 提示消息
     * @return json result
     */
    public static JsonResult warn(String msg) {
        return new JsonResult(201, msg);
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
