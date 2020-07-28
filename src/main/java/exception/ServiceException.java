package exception;

/**
 * Service层自定义异常
 *
 * @author iyut
 */
public class ServiceException extends BaseException {
	/**
	 * Constructors
	 *
	 * @param code   错误编码
	 * @param errMsg 异常信息
	 */
	public ServiceException(int code, String errMsg) {
		super(errMsg, null, code, errMsg);
	}

	/**
	 * Constructors
	 *
	 * @param cause 异常接口
	 * @param code  错误编码
	 * @param value 异常信息
	 */
	public ServiceException(Throwable cause, int code, String value) {
		super(value, cause, code, value);
	}
}
