package com.watchtek.watchall.vo;


/**
 * 서비스에서 테이블 데이터 조회시 결과를 반환할때 사용하는 VO 
 *
 * @author JIHEE
 * @version 1.0
 */

public class DeviceVO {
	
	
	private String objName;
	private	String objId;
	private String objtypeName;
	private String objDefineName;
	private String ipaddressIpv4;
	private String objProductId;
	private String managegroupName;
	private String objCompanyName;
	
	public String getObjName() {
		return objName;
	}
	public void setObjName(String objName) {
		this.objName = objName;
	}
	public String getObjId() {
		return objId;
	}
	public void setObjId(String objId) {
		this.objId = objId;
	}
	public String getObjtypeName() {
		return objtypeName;
	}
	public void setObjtypeName(String objtypeName) {
		this.objtypeName = objtypeName;
	}
	public String getObjDefineName() {
		return objDefineName;
	}
	public void setObjDefineName(String objDefineName) {
		this.objDefineName = objDefineName;
	}
	public String getIpaddressIpv4() {
		return ipaddressIpv4;
	}
	public void setIpaddressIpv4(String ipaddressIpv4) {
		this.ipaddressIpv4 = ipaddressIpv4;
	}
	public String getObjProductId() {
		return objProductId;
	}
	public void setObjProductId(String objProductId) {
		this.objProductId = objProductId;
	}
	public String getManagegroupName() {
		return managegroupName;
	}
	public void setManagegroupName(String managegroupName) {
		this.managegroupName = managegroupName;
	}
	public String getObjCompanyName() {
		return objCompanyName;
	}
	public void setObjCompanyName(String objCompanyName) {
		this.objCompanyName = objCompanyName;
	}


}
