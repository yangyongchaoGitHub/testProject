package test.main;

public class StringToFloat {

	public static void main(String[] args) {
		String fString = "123";
		float f = Float.parseFloat(fString);
		Float ff = 123.0f;
		System.out.println(f);
		System.out.println(f == ff.floatValue());
	}

}
