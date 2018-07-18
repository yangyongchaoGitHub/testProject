package com.wenyun.hcms.ble;

import java.util.Date;

public class HeatmeterCollectorData {
    public String hwaddr;
    public int status;
    public Date ts;
    public Float flowRate;
    public Integer flowAmount;
    public Float TempWaterSupply;
    public Float TempWaterReturn;
    public Integer heatAmount;
    public Integer coldAmount;
    public Integer uptime;
    public Float power;
    public String querySoures = "";
    public String responseSoures = "";
	public String getHwaddr() {
		return hwaddr;
	}
	public void setHwaddr(String hwaddr) {
		this.hwaddr = hwaddr;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Date getTs() {
		return ts;
	}
	public void setTs(Date ts) {
		this.ts = ts;
	}
	public Float getFlowRate() {
		return flowRate;
	}
	public void setFlowRate(Float flowRate) {
		this.flowRate = flowRate;
	}
	public Integer getFlowAmount() {
		return flowAmount;
	}
	public void setFlowAmount(Integer flowAmount) {
		this.flowAmount = flowAmount;
	}
	public Float getTempWaterSupply() {
		return TempWaterSupply;
	}
	public void setTempWaterSupply(Float tempWaterSupply) {
		TempWaterSupply = tempWaterSupply;
	}
	public Float getTempWaterReturn() {
		return TempWaterReturn;
	}
	public void setTempWaterReturn(Float tempWaterReturn) {
		TempWaterReturn = tempWaterReturn;
	}
	public Integer getHeatAmount() {
		return heatAmount;
	}
	public void setHeatAmount(Integer heatAmount) {
		this.heatAmount = heatAmount;
	}
	public Integer getColdAmount() {
		return coldAmount;
	}
	public void setColdAmount(Integer coldAmount) {
		this.coldAmount = coldAmount;
	}
	public Integer getUptime() {
		return uptime;
	}
	public void setUptime(Integer uptime) {
		this.uptime = uptime;
	}
	public Float getPower() {
		return power;
	}
	public void setPower(Float power) {
		this.power = power;
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
