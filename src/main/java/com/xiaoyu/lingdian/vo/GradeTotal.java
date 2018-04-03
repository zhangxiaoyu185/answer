package com.xiaoyu.lingdian.vo;

public class GradeTotal {

	/**
	 * 列
	 */
	private String columnObj;
	
	/**
	 * 行未做Key
	 */
	private String hwzKey;
	
	/**
	 * 行未做Value
	 */
	private String hwzValue;
	
	/**
	 * 行已做未满分Key
	 */
	private String hyzwmfKey;
	
	/**
	 * 行已做未满分Value
	 */
	private String hyzwmfValue;
	
	/**
	 * 行已做满分Key
	 */
	private String hyzmfKey;
	
	/**
	 * 行已做满分Value
	 */
	private String hyzmfValue;

	/**
	 * 行总分
	 */
	private String totalKey;

	public String getColumnObj() {
		return columnObj;
	}

	public void setColumnObj(String columnObj) {
		this.columnObj = columnObj;
	}

	public String getHwzKey() {
		return hwzKey;
	}

	public void setHwzKey(String hwzKey) {
		this.hwzKey = hwzKey;
	}

	public String getHwzValue() {
		return hwzValue;
	}

	public void setHwzValue(String hwzValue) {
		this.hwzValue = hwzValue;
	}

	public String getHyzwmfKey() {
		return hyzwmfKey;
	}

	public void setHyzwmfKey(String hyzwmfKey) {
		this.hyzwmfKey = hyzwmfKey;
	}

	public String getHyzwmfValue() {
		return hyzwmfValue;
	}

	public void setHyzwmfValue(String hyzwmfValue) {
		this.hyzwmfValue = hyzwmfValue;
	}

	public String getHyzmfKey() {
		return hyzmfKey;
	}

	public void setHyzmfKey(String hyzmfKey) {
		this.hyzmfKey = hyzmfKey;
	}

	public String getHyzmfValue() {
		return hyzmfValue;
	}

	public void setHyzmfValue(String hyzmfValue) {
		this.hyzmfValue = hyzmfValue;
	}

	public String getTotalKey() {
		return totalKey;
	}

	public void setTotalKey(String totalKey) {
		this.totalKey = totalKey;
	}
}