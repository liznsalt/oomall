package common.oomall.api;

import java.util.Date;

/**
 * @author /
 */
public class CommonResult<T> {
    private long errno;
    private String errmsg;
    private T data;

    protected CommonResult() {
    }

    protected CommonResult(long code, String message, T data) {
        this.errno = code;
        this.errmsg = message;
        this.data = data;
    }

    /**
     * 成功返回结果
     */
    public static <T> CommonResult<T> success() {
        return new CommonResult<T>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), null);
    }

    /**
     * 成功返回结果
     *
     * @param data 获取的数据
     */
    public static <T> CommonResult<T> success(T data) {
        return new CommonResult<T>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), data);
    }

    /**
     * 成功返回结果
     *
     * @param data 获取的数据
     * @param  message 提示信息
     */
    public static <T> CommonResult<T> success(T data, String message) {
        return new CommonResult<T>(ResultCode.SUCCESS.getCode(), message, data);
    }

    /**
     * 失败返回结果
     * @param errorCode 错误码
     */
    public static <T> CommonResult<T> failed(IErrorCode errorCode) {
        return new CommonResult<T>(errorCode.getCode(), errorCode.getMessage(), null);
    }

    /**
     * 失败返回结果
     * @param message 提示信息
     */
    public static <T> CommonResult<T> failed(String message) {
        return new CommonResult<T>(ResultCode.FAILED.getCode(), message, null);
    }

    /**
     * 失败返回结果
     */
    public static <T> CommonResult<T> failed() {
        return failed(ResultCode.FAILED);
    }

    /**
     * 参数验证失败返回结果
     */
    public static <T> CommonResult<T> badArgument() {
        return failed(ResultCode.BADARGUMENT);
    }
    public static <T> CommonResult<T> badArgument(String message) {
        return new CommonResult<T>(ResultCode.BADARGUMENT.getCode(), message, null);
    }

    public static <T> CommonResult<T> badArgumentValue() {
        return failed(ResultCode.BADARGUMENTVALUE);
    }
    public static <T> CommonResult<T> badArgumentValue(String message) {
        return new CommonResult<T>(ResultCode.BADARGUMENTVALUE.getCode(), message, null);
    }

    public static <T> CommonResult<T> unLogin() {
        return failed(ResultCode.UNLOGIN);
    }

    public static <T> CommonResult<T> serious() {
        return failed(ResultCode.SERIOUS);
    }

    public static <T> CommonResult<T> upSupport() {
        return failed(ResultCode.UNSUPPORT);
    }

    public static <T> CommonResult<T> codeError() {
        return failed(ResultCode.CODEERROR);
    }

    /**
     * @deprecated
     */
    public static <T> CommonResult<T> updatedDateExpired() {
        return failed(ResultCode.UPDATEDDATEEXPIRED);
    }
    /**
     * @deprecated
     */
    public static <T> CommonResult<T> updatedDateExpired(String message) {
        return new CommonResult<T>(ResultCode.UPDATEDDATEEXPIRED.getCode(), message, null);
    }

    public static <T> CommonResult<T> updatedDataFailed() {
        return failed(ResultCode.UPDATEDDATAFAILED);
    }
    public static <T> CommonResult<T> updatedDataFailed(String message) {
        return new CommonResult<T>(ResultCode.UPDATEDDATAFAILED.getCode(), message, null);
    }

    public static <T> CommonResult<T> illegal() {
        return failed(ResultCode.ILLEGAL);
    }
    public static <T> CommonResult<T> illegal(String message) {
        return new CommonResult<T>(ResultCode.ILLEGAL.getCode(), message, null);
    }

    /**
     * 未登录返回结果
     */
    public static <T> CommonResult<T> unauthorized() {
        return new CommonResult<T>(ResultCode.UNAUTHZ.getCode(), ResultCode.UNAUTHZ.getMessage(), null);
    }
    public static <T> CommonResult<T> unauthorized(T data) {
        return new CommonResult<T>(ResultCode.UNAUTHZ.getCode(), ResultCode.UNAUTHZ.getMessage(), data);
    }

    public long getErrno() {
        return errno;
    }

    public void setErrno(long errno) {
        this.errno = errno;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "{" +
                "errno=" + errno +
                ", errmsg='" + errmsg + '\'' +
                ", data=" + data +
                '}';
    }

    public static void main(String[] args) {
        System.out.println(CommonResult.success(new Date()));
//        String json = JacksonUtil.toJson(CommonResult.success(new Date()));
//        System.out.println(JacksonUtil.parseObject(json, "errno", Date.class));
    }
}
