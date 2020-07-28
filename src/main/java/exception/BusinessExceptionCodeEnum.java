package exception;


/**
 * 接口层异常码枚举
 *
 * @author iyut
 */
public enum BusinessExceptionCodeEnum {
	/**
	 * 接口层业务异常
	 */
	BUSINESS_EXCEPTION(700, "接口层业务异常"),
	/**
	 * 请求参数值非法
	 */
	PARAMS_TYPE_INCORRECT(701, "请求参数值非法"),
	/**
	 * 请求参数为空
	 */
	PARAMS_MUST_BLANK(702, "请求参数为空"),
	/**
	 * 缺少请求参数
	 */
	PARAMS_MUST_MISSING(703, "缺少请求参数");

	/**
	 * 错误码
	 */
	private int code;
	/**
	 * 错误描述
	 */
	private String desc;

	BusinessExceptionCodeEnum(int code, String desc) {
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
