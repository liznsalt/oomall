package common.oomall.api;

/**
 * 封装API的错误码
 *
 * @author /
 */
public interface IErrorCode {
    /**
     * 获取code
     * @return 错误码
     */
    long getCode();

    /**
     * 获取错误信息
     * @return 错误信息
     */
    String getMessage();
}
