package test.main;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.annotation.PostConstruct;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeType;

public class EasyIotJsonTest2 {
	public static SimpleDateFormat sdf = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");	
	//upload data name definition
	public final static String KEY_STATUS = "status";
	public final static String KEY_READING = "reading";
	public final static String KEY_NET = "net";
	public final static String KEY_V12 = "v12";
	public final static String KEY_V24 = "v24";
	public final static String KEY_BATTER = "Battery";
	public final static String KEY_BATTER_LEVEL = "batteryLevel";
	public final static String KEY_SINGAL = "Meter";
	public final static String KEY_SINGA_STRENGTH = "signalStrength";
	public final static String KEY_UPLOAD = "upload";
	
	//down data name definition
	public final static String CMD_DOOR = "cmd_door";
	public final static String CMD_POWER = "cmd_power";
	public final static String CMD_FAN = "cmd_fan";
	
	public SmartJXFStatus parseToPersistent(String json) throws Exception  {		
		JsonNode node_root = new ObjectMapper().readValue(json, JsonNode.class);		
		
		if (node_root.size() == 0) {
			return null;
		}
		
		SmartJXFStatus jxfStatus = new SmartJXFStatus();		
		String code = node_root.findValue("devSerial").asText();

		//jxfStatus.setTs(new Date(System.currentTimeMillis()));
		
		JsonNode node_serviceData = node_root.path("serviceData");
		if (node_serviceData == null || node_serviceData.getNodeType() != JsonNodeType.ARRAY) {
			return null;
		}
	
		for (int i = 0; i < node_serviceData.size(); i++) {
			JsonNode node_id = node_serviceData.get(i);
			JsonNode node_data = node_id.path("serviceData");
			String serviceId = node_id.findValue("serviceId").asText();

			if (KEY_BATTER.equals(serviceId)) {
				float batter = Float.parseFloat(node_data.findValue(
						KEY_BATTER_LEVEL).asText());

				jxfStatus.setBatteryLevel(batter);
			} else if (KEY_SINGAL.equals(serviceId)) {
				String singal = node_data.findValue(KEY_SINGA_STRENGTH)
						.asText();
				if (singal != null) {
					jxfStatus.setSingalLevel(Float.parseFloat(singal));
				}
			} else if (KEY_UPLOAD.equals(serviceId)) {
				String net = getUploadData(node_data, KEY_NET);
				String reading = getUploadData(node_data, KEY_READING);
				String status = getUploadData(node_data, KEY_STATUS);
				String v12 = getUploadData(node_data, KEY_V12);
				String v24 = getUploadData(node_data, KEY_V24);

				if (net != null) {
					/*
					 * char[] c = net.toLowerCase().toCharArray(); byte n =
					 * (byte) ((hexString.indexOf(c[0]) << 4) |
					 * hexString.indexOf(c[1]));
					 */
					int n = Integer.parseInt(net);
					jxfStatus.setNetS1(bit(n, 0));
					jxfStatus.setNetS2(bit(n, 1));
					jxfStatus.setNetS3(bit(n, 2));
					jxfStatus.setNetS4(bit(n, 3));

				}

				if (reading != null && reading.length() == 10) {
					byte[] readingByte = stringHexToByte(reading);

					jxfStatus.setTemperature((float) (readingByte[0] & 0xff));
					jxfStatus.setHumidity((float) (readingByte[1] & 0xff));
					jxfStatus.setVoltage((float) (readingByte[2] & 0xff));
					jxfStatus.setCurrent((float) (readingByte[3] & 0xff));
					jxfStatus.setPower((float) (readingByte[4] & 0xff));
				}

				if (status != null) {
					int s = Integer.parseInt(status);

					jxfStatus.setDoor(bit(s, 0));
					jxfStatus.setAirSwitch(bit(s, 1));
					jxfStatus.setLeakage(bit(s, 2));
					jxfStatus.setLightning(bit(s, 3));
					jxfStatus.setFan(bit(s, 4));
				}

				if (v12 != null) {
					int v12_ = Integer.parseInt(v12);

					jxfStatus.setV12S1(bit(v12_, 0));
					jxfStatus.setV12S2(bit(v12_, 1));
					jxfStatus.setV12S3(bit(v12_, 2));
					jxfStatus.setV12S4(bit(v12_, 3));
					jxfStatus.setV12S5(bit(v12_, 4));
					jxfStatus.setV12S6(bit(v12_, 5));
					jxfStatus.setV12S7(bit(v12_, 6));
					jxfStatus.setV12S8(bit(v12_, 7));
				}

				if (v24 != null) {
					int v24_ = Integer.parseInt(v24);

					jxfStatus.setV24S1(bit(v24_, 0));
					jxfStatus.setV24S2(bit(v24_, 1));
				}
			}
		}
		return jxfStatus;
	}

	private String getUploadData(JsonNode node, String name) {
		JsonNode value = node.findValue(name);
		return value == null ? null : value.asText();
	}

	public byte c2b(char c) {
		if (c >= '0' && c <= '9') 
			return (byte) (c - '0');
		if (c >= 'a' && c <= 'f') 
			return (byte) (c - 'a');
		if (c >= 'A' && c <= 'F') 
			return (byte) (c - 'A');
		
		return 0;
	}
	public byte[] stringHexToByte(String str) {
		if (str != null) {
			char[] c = str.toLowerCase().toCharArray();
			byte[] b = new byte[str.length() / 2];
	
			for (int i = 0; i < c.length; i = i + 2) {
				b[i>>1] = (byte) (c2b(c[i]) << 4 | c2b(c[i+1]));	
			}
			
			return b;
		}
		
		return null;
	}
	
	private byte bit(int v, int i){
		return (byte) ((v>>i) & 0x1);
	}

	public static void main(String[] args) {
		
		EasyIotJsonTest2 test2 = new EasyIotJsonTest2();
		String jsonString = "{\"createTime\": \"2018-01-19 11:44:05\", \"lastMessageTime\": \"2018-01-22 18:29:26\", \"devSerial\": \"863703035109391\", \"serviceData\": [{\"serviceId\": \"Battery\", \"serviceData\": {\"batteryLevel\": 84}}, {\"serviceId\": \"Meter\", \"serviceData\": {\"signalStrength\": -85}}, {\"serviceId\": \"upload\", \"serviceData\": {\"v24\": -128, \"status\": -128, \"reading\": \"0402050103\", \"net\": -128, \"v12\": -1}}]}";
		try {
			test2.parseToPersistent(jsonString).ShowString();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
