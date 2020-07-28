package exception;


/**
 * 接口层自定义异常
 *
 * @author iyut
 */
public class BusinessException extends BaseException {
	/**
	 * Constructors
	 *
	 * @param code   错误编码
	 * @param errMsg 异常信息
	 */
	public BusinessException(int code, String errMsg) {
		super(errMsg, null, code, errMsg);
	}

	/**
	 * Constructors
	 *
	 * @param cause  异常接口
	 * @param code   错误编码
	 * @param errMsg 异常信息
	 */
	public BusinessException(Throwable cause, int code, String errMsg) {
		super(errMsg, cause, code, errMsg);
	}
}
