package com.zj.caoshangfei.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.apache.commons.lang.StringUtils;

/**
 * Created by jin.zhang@fuwo.com on 2017/8/26.
 */
@Data
public class RestResult {

	private String code = "10000";

	private String msg;

	private Object data;


	@JsonIgnore
	public boolean isSuccess() {
		return StringUtils.equals(this.code, ResultCodeEnum.SUCC.code) ? true : false;
	}

	public static RestResult isOk(Object data) {
		RestResult restResult = new RestResult();
		restResult.setCode(ResultCodeEnum.SUCC.code);
		restResult.setData(data);

		return restResult;
	}


	public enum ResultCodeEnum {

		SUCC("10000", "成功"),

		ERROR("10001", "未知错误"),

		PARA_ERR("1002", "表单参数错误"),

		NOT_EXIST_ERR("10003", "记录不存在"),

		EMPTY_FILE("20001", "文件为空"),

		DUPLICATE_REQUEST("20002", "重复的请求"),

		GOODS_NOT_EXISTS("20003", "商品不存在"),

		ORDER_PAYED("20004", "订单已付款"),

		USER_NOT_EXISTS("20005", "用户不存在")


		//
		;

		private String desc;

		private String code;

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}

		public String getDesc() {
			return desc;
		}

		public void setDesc(String desc) {
			this.desc = desc;
		}

		private ResultCodeEnum(String code, String desc) {
			this.desc = desc;
			this.code = code;
		}

	}


}
