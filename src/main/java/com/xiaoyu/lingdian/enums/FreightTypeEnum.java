package com.xiaoyu.lingdian.enums;

public enum FreightTypeEnum {

	ST("申通"),
	YZ("邮政"),
	EMS("EMS"),
	SF("顺丰");

	private String description;

	FreightTypeEnum(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

}
