package test.main;

public class SmartJXFStatus {
	//upload data name definition
		public final static String UP_STATUS = "status";
		public final static String UP_READING = "reading";
		public final static String UP_NET = "net";
		public final static String UP_V12 = "v12";
		public final static String UP_V24 = "v24";
		public final static String UP_BATTER_KEY = "Battery";
		public final static String UP_BATTER_VALUE = "batteryLevel";
		public final static String UP_SINGAL_KEY = "Meter";
		public final static String UP_SINGAL_VALUE = "signalStrength";
		//down data name definition
		public final static String CMD_DOOR = "cmd_door";
		public final static String CMD_POWER = "cmd_power";
		public final static String CMD_FAN = "cmd_fan";
		//
		public final static String UPLOAD = "upload";
		
		private Float batteryLevel;
		private Float singalLevel;
		private Byte leakage;
		private Byte lightning;
		
		private Byte door;
		private Byte airSwitch;
		private Byte fan;
		
		private Byte netS1;
		private Byte netS2;
		private Byte netS3;
		private Byte netS4;
		
		private Byte v12S1;
		private Byte v12S2;
		private Byte v12S3;
		private Byte v12S4;
		private Byte v12S5;
		private Byte v12S6;
		private Byte v12S7;
		private Byte v12S8;
		
		private Byte v24S1;
		private Byte v24S2;
		
		private Float temperature;
		private Float humidity;
		private Float voltage;
		private Float current;
		private Float power;
		
		private Float charger;
		private Float angle;
		
		
		public Float getBatteryLevel() {
			return batteryLevel;
		}
		public void setBatteryLevel(Float batteryLevel) {
			this.batteryLevel = batteryLevel;
		}
		public Float getSingalLevel() {
			return singalLevel;
		}
		public void setSingalLevel(Float singalLevel) {
			this.singalLevel = singalLevel;
		}
		public Byte getLeakage() {
			return leakage;
		}
		public void setLeakage(Byte leakage) {
			this.leakage = leakage;
		}
		public Byte getLightning() {
			return lightning;
		}
		public void setLightning(Byte lightning) {
			this.lightning = lightning;
		}
		public Byte getDoor() {
			return door;
		}
		public void setDoor(Byte door) {
			this.door = door;
		}
		public Byte getAirSwitch() {
			return airSwitch;
		}
		public void setAirSwitch(Byte airSwitch) {
			this.airSwitch = airSwitch;
		}
		public Byte getFan() {
			return fan;
		}
		public void setFan(Byte fan) {
			this.fan = fan;
		}
		public Float getAngle() {
			return angle;
		}
		public void setAngle(Float angle) {
			this.angle = angle;
		}
		public Float getTemperature() {
			return temperature;
		}
		public void setTemperature(Float temperature) {
			this.temperature = temperature;
		}
		public Float getHumidity() {
			return humidity;
		}
		public void setHumidity(Float humidity) {
			this.humidity = humidity;
		}
		public Float getVoltage() {
			return voltage;
		}
		public void setVoltage(Float voltage) {
			this.voltage = voltage;
		}
		public Float getCurrent() {
			return current;
		}
		public void setCurrent(Float current) {
			this.current = current;
		}
		public Float getPower() {
			return power;
		}
		public void setPower(Float power) {
			this.power = power;
		}
		public Float getCharger() {
			return charger;
		}
		public void setCharger(Float charger) {
			this.charger = charger;
		}
		public Byte getNetS1() {
			return netS1;
		}
		public void setNetS1(Byte netS1) {
			this.netS1 = netS1;
		}
		public Byte getNetS2() {
			return netS2;
		}
		public void setNetS2(Byte netS2) {
			this.netS2 = netS2;
		}
		public Byte getNetS3() {
			return netS3;
		}
		public void setNetS3(Byte netS3) {
			this.netS3 = netS3;
		}
		public Byte getNetS4() {
			return netS4;
		}
		public void setNetS4(Byte netS4) {
			this.netS4 = netS4;
		}
		public Byte getV12S1() {
			return v12S1;
		}
		public void setV12S1(Byte v12s1) {
			v12S1 = v12s1;
		}
		public Byte getV12S2() {
			return v12S2;
		}
		public void setV12S2(Byte v12s2) {
			v12S2 = v12s2;
		}
		public Byte getV12S3() {
			return v12S3;
		}
		public void setV12S3(Byte v12s3) {
			v12S3 = v12s3;
		}
		public Byte getV12S4() {
			return v12S4;
		}
		public void setV12S4(Byte v12s4) {
			v12S4 = v12s4;
		}
		public Byte getV12S5() {
			return v12S5;
		}
		public void setV12S5(Byte v12s5) {
			v12S5 = v12s5;
		}
		public Byte getV12S6() {
			return v12S6;
		}
		public void setV12S6(Byte v12s6) {
			v12S6 = v12s6;
		}
		public Byte getV12S7() {
			return v12S7;
		}
		public void setV12S7(Byte v12s7) {
			v12S7 = v12s7;
		}
		public Byte getV12S8() {
			return v12S8;
		}
		public void setV12S8(Byte v12s8) {
			v12S8 = v12s8;
		}
		public Byte getV24S1() {
			return v24S1;
		}
		public void setV24S1(Byte v24s1) {
			v24S1 = v24s1;
		}
		public Byte getV24S2() {
			return v24S2;
		}
		public void setV24S2(Byte v24s2) {
			v24S2 = v24s2;
		}
	
	public void ShowString() {
		System.out.println("leakage: " + leakage + " lightning: " + lightning +
				" batteryLevel: " + batteryLevel + " singalLevel: " + singalLevel + 
				" door: " + door + " airSwitch: " + airSwitch + " fan: " + fan + " netS1: " + netS1
				+ " netS2: " + netS2 + " netS3: " + netS3 + " netS4: " + netS4 + " v12S1: " + v12S1 + " v12S2: " + v12S2
				+ " v12S3: " + v12S3 + " v12S4: " + v12S5 + " v12S5: " + v12S5 + " v12S6: " + v12S6 + " v12S7: " + v12S7
				+ " v12S8: " + v12S8 + " v24S1: " + v24S1 + " v24S2: " + v24S2 + " temperature: " + temperature
				+ " humidity: " + humidity + " voltage: " + voltage + " current: " + current + " power: " + power
				+ " charger: " + charger + " angle: " + angle);
	}
}
