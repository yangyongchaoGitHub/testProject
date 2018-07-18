package test.main;

import java.util.ArrayList;
import java.util.List;

public class ArrayTest {

	public static void main(String[] args) {
		List<Integer> arr = new ArrayList<Integer>();
		arr.add(1);
		arr.add(2);
		arr.add(3);
		arr.add(arr.size(), 4);
		for (int i = 0; i < arr.size(); i++) {
			System.out.println(arr.get(i));
		}
		
		Integer in = arr.get(0);
		arr.remove(in);
		arr.add(in);
		System.out.println("-----");
		for (int i = 0; i < arr.size(); i++) {
			System.out.println(arr.get(i));
		}
	}

}
