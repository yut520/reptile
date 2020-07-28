package exception;

/**
 * Service层异常码枚举
 * @author iyut
 */
public enum ServiceExceptionCodeEnum {
	/**
	 * 业务异常
	 */
	SERVICE_EXCEPTION(800, "Service层业务异常");

	/**
	 * 错误码
	 */
	private int code;
	/**
	 * 错误描述
	 */
	private String desc;

	ServiceExceptionCodeEnum(int code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public int getCode() {
		return code;
	}


	public String getDesc() {
		return desc;
	}

}
