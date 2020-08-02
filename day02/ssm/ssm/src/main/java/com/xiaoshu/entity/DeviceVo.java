package com.xiaoshu.entity;

public class DeviceVo {
private String name;
private String value;
public String getName() {
	return name;
}
public DeviceVo() {
	super();
	// TODO Auto-generated constructor stub
}
public DeviceVo(String name, String value) {
	super();
	this.name = name;
	this.value = value;
}
public void setName(String name) {
	this.name = name;
}
public String getValue() {
	return value;
}
public void setValue(String value) {
	this.value = value;
}

}
