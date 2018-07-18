package com.wenyun.hcms.ble;

import java.util.Date;

public class ActuatorCollectorData {
	private String hwaddr;
    private Integer status;
    private Date ts;
    private Integer tOpen;
    private Integer tClose;
    private boolean switc;
    private Float tempIndoor;
    private Float tempTarget;
    private String querySoures = "";
    private String responseSoures = "";
    
	public String getHwaddr() {
		return hwaddr;
	}
	public void setHwaddr(String hwaddr) {
		this.hwaddr = hwaddr;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	public Date getTs() {
		return ts;
	}
	public void setTs(Date ts) {
		this.ts = ts;
	}
	public Integer gettOpen() {
		return tOpen;
	}
	public void settOpen(Integer tOpen) {
		this.tOpen = tOpen;
	}
	public Integer gettClose() {
		return tClose;
	}
	public void settClose(Integer tClose) {
		this.tClose = tClose;
	}

	public boolean isSwitc() {
		return switc;
	}
	public void setSwitc(boolean switc) {
		this.switc = switc;
	}
	public Float getTempIndoor() {
		return tempIndoor;
	}
	public void setTempIndoor(Float tempIndoor) {
		this.tempIndoor = tempIndoor;
	}
	public Float getTempTarget() {
		return tempTarget;
	}
	public void setTempTarget(Float tempTarget) {
		this.tempTarget = tempTarget;
	}
	public String getQuerySoures() {
		return querySoures;
	}
	public void setQuerySoures(String querySoures) {
		this.querySoures = querySoures;
	}
	public String getResponseSoures() {
		return responseSoures;
	}
	public void setResponseSoures(String responseSoures) {
		this.responseSoures = responseSoures;
	}
    
    
}
