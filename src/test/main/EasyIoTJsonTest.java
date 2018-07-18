package test.main;

import java.text.SimpleDateFormat;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeType;

public class EasyIoTJsonTest {
	public static SimpleDateFormat sdf = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");
	private static String hexString = "0123456789abcdef";
	private static boolean bChange = false;

	public static void main(String[] args) {
		try {
			String jsonString = "{\"createTime\": \"2018-01-19 11:44:05\", \"lastMessageTime\": \"2018-01-22 18:29:26\", \"devSerial\": \"863703035109391\", \"serviceData\": [{\"serviceId\": \"Battery\", \"serviceData\": {\"batteryLevel\": 84}}, {\"serviceId\": \"Meter\", \"serviceData\": {\"signalStrength\": -85}}, {\"serviceId\": \"upload\", \"serviceData\": {\"v24\": 0, \"status\": 25, \"reading\": \"0402050103\", \"net\": 27, \"v12\": 0}}]}";

			//String jsonString = "123456";
			JsonNode node_root = new ObjectMapper().readValue(jsonString,
					JsonNode.class);
			
			String code = node_root.findValue("devSerial").asText();
			SmartJXFStatus jxfStatus = new SmartJXFStatus();

			JsonNode node_serviceData = node_root.path("serviceData");
			if (node_serviceData.getNodeType() == JsonNodeType.ARRAY) {
				
				for (int i = 0; i < node_serviceData.size(); i++) {

					JsonNode node_id = node_serviceData.get(i);
					JsonNode node_data = node_id.path("serviceData");

					String serviceId = node_id.findValue("serviceId").asText();

					if (SmartJXFStatus.UP_BATTER_KEY.equals(serviceId)) {
						System.out.println("UP_BATTER");
						
						String batter = getUpData(node_data, SmartJXFStatus.UP_BATTER_VALUE);
						if (batter != null) {
							jxfStatus.setBatteryLevel(check(jxfStatus.getBatteryLevel(), Float.parseFloat(batter)));	
						}
						
					} else if (SmartJXFStatus.UP_SINGAL_KEY.equals(serviceId)) {
						System.out.println("UP_SINGAL");
					
						String singal = getUpData(node_data, SmartJXFStatus.UP_SINGAL_VALUE);
						if (singal != null) {
							jxfStatus.setSingalLevel(check(jxfStatus.getSingalLevel(), Float.parseFloat(singal)));
						}
						
					} else if (SmartJXFStatus.UPLOAD.equals(serviceId)) {
						System.out.println("UPLOAD");
						
						String net = getUpData(node_data, SmartJXFStatus.UP_NET);

						if (net != null) {
							System.out.println("target net " + net);
							/*
							 * char[] c = net.toLowerCase().toCharArray(); byte n =
							 * (byte) ((hexString.indexOf(c[0]) << 4) |
							 * hexString.indexOf(c[1]));
							 */
							int n = Integer.parseInt(net);
							jxfStatus.setNetS1(check(jxfStatus.getNetS1(), (byte) (n & 0x01)));
							jxfStatus.setNetS2(check(jxfStatus.getNetS2(), (byte) (byte) ((n >> 1) & 0x01)));
							jxfStatus.setNetS3(check(jxfStatus.getNetS3(), (byte) (byte) ((n >> 2) & 0x01)));
							jxfStatus.setNetS4(check(jxfStatus.getNetS4(), (byte) (byte) ((n >> 3) & 0x01)));
							
						}

						String reading = getUpData(node_data,
								SmartJXFStatus.UP_READING);
						if (reading != null && reading.length() == 10) {
							System.out.println("target reading " + reading);
							byte[] readingByte = stringHexToBytes(reading);
							
							for (int j = 0; j < readingByte.length; j++) {
								System.out.println(readingByte[j] & 0xff);
							}
							System.out.println();
							
							jxfStatus.setTemperature(check(jxfStatus.getTemperature(), readingByte[0] & 0xff));
							jxfStatus.setHumidity(check(jxfStatus.getHumidity(), readingByte[1] & 0xff));
							jxfStatus.setVoltage(check(jxfStatus.getVoltage(), readingByte[2] & 0xff));
							jxfStatus.setCurrent(check(jxfStatus.getCurrent(), readingByte[3] & 0xff));
							jxfStatus.setPower(check(jxfStatus.getPower(), readingByte[4] & 0xff));
						}

						String status = getUpData(node_data, SmartJXFStatus.UP_STATUS);
						if (status != null) {
							System.out.println("target status " + status);
							int s = Integer.parseInt(status);
							
							jxfStatus.setDoor(check(jxfStatus.getDoor(), (byte) (s & 0x01)));
							jxfStatus.setAirSwitch(check(jxfStatus.getAirSwitch(), (byte) ((s >> 1) & 0x01)));
							jxfStatus.setLeakage(check(jxfStatus.getLeakage(), (byte) ((s >> 2) & 0x01)));
							jxfStatus.setLightning(check(jxfStatus.getLightning(), (byte) ((s >> 3) & 0x01)));
							jxfStatus.setFan(check(jxfStatus.getFan(), (byte) ((s >> 4) & 0x01)));
						}

						String v12 = getUpData(node_data, SmartJXFStatus.UP_V12);
						if (v12 != null) {
							System.out.println("target v12 " + v12);
							int v12_ = Integer.parseInt(v12);
							
							jxfStatus.setV12S1(check(jxfStatus.getV12S1(), (byte) (v12_ & 0x01)));
							jxfStatus.setV12S2(check(jxfStatus.getV12S2(), (byte) ((v12_ >> 1) & 0x01)));
							jxfStatus.setV12S3(check(jxfStatus.getV12S3(), (byte) ((v12_ >> 2) & 0x01)));
							jxfStatus.setV12S4(check(jxfStatus.getV12S4(), (byte) ((v12_ >> 3) & 0x01)));
							jxfStatus.setV12S5(check(jxfStatus.getV12S5(), (byte) ((v12_ >> 4) & 0x01)));
							jxfStatus.setV12S6(check(jxfStatus.getV12S6(), (byte) ((v12_ >> 5) & 0x01)));
							jxfStatus.setV12S7(check(jxfStatus.getV12S7(), (byte) ((v12_ >> 6) & 0x01)));
							jxfStatus.setV12S8(check(jxfStatus.getV12S8(), (byte) ((v12_ >> 7) & 0x01)));

						}

						String v24 = getUpData(node_data, SmartJXFStatus.UP_V24);
						if (v24 != null) {
							System.out.println("target v24 " + v24);
							int v24_ = Integer.parseInt(v24);
							
							jxfStatus.setV24S1(check(jxfStatus.getV24S1(), (byte) (v24_ & 0x01)));
							jxfStatus.setV24S2(check(jxfStatus.getV24S2(), (byte) ((v24_ >> 1) & 0x01)));
						}
					}
				}
			}
			
			System.out.println(bChange);
			jxfStatus.ShowString();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	private static byte check(Byte oldValue, byte newValue) {
		if (oldValue == null || newValue != oldValue) {
			bChange = true;
			return newValue;
		}
		return oldValue;
	}
	
	private static float check(Float oldValue, float newValue) {
		if (oldValue == null || newValue != oldValue.floatValue()) {
			bChange = true;
			return newValue;
		}
		return oldValue;
	}

	private static String getUpData(JsonNode node, String name) {
		JsonNode node_value = node.findValue(name);
		System.out.println(node_value.getNodeType() + " name " + name);
		return node_value == null ? null : node_value.asText();
	}

	public static byte[] stringHexToBytes(String str) {
		if (str == null) {
			return null;
		}

		char[] c = str.toLowerCase().toCharArray();
		byte[] b = new byte[str.length() / 2];

		for (int i = 0; i < c.length; i = i + 2) {
			b[i / 2] = (byte) ((hexString.indexOf(c[i]) << 4) | hexString
					.indexOf(c[i + 1]));
		}
		return b;
	}
}
