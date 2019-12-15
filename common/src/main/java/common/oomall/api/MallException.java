package common.oomall.api;

/**
 * @author hyt
 */
public class MallException extends Exception {
    private ResultCode resultCode;

    public MallException(ResultCode code) {
        this.resultCode = code;
    }

    public long getCode() {
        return resultCode.getCode();
    }
}
