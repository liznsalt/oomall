package common.oomall.api;

/**
 * 枚举了一些常用API操作码
 *
 * @author /
 */
public enum ResultCode implements IErrorCode {
    // 状态码
    SUCCESS(200, "操作成功"),
    BADARGUMENT(401, "参数不对"),
    BADARGUMENTVALUE(402, "参数值不对"),
    UNLOGIN(501, "请登录"),
    SERIOUS(502, "系统内部错误"),
    UNSUPPORT(503, "业务不支持"),
    UPDATEDDATEEXPIRED(504, "更新数据已经失效"),
    UPDATEDDATAFAILED(505, "更新数据失效"),
    UNAUTHZ(506, "无操作权限"),

    FAILED(500, "操作失败");
//    VALIDATE_FAILED(404, "参数检验失败"),
//    UNAUTHORIZED(401, "暂未登录或无权限"),
//    FORBIDDEN(403, "没有相关权限");

    private long code;
    private String message;

    private ResultCode(long code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public long getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
