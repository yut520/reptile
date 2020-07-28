package exception;

import lombok.Data;

/**
 * 自定义异常基类
 *
 * @author iyut
 */
@Data
public class BaseException extends RuntimeException {

	private int code;

	private String value;

	public BaseException(String message, Throwable cause, int code, String value) {
		super(message, cause);
		this.code = code;
		this.value = value;
	}
}
