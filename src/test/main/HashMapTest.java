package test.main;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class HashMapTest {

	public static void main(String[] args) {
		HashMap<Short, Integer> testMap = new HashMap<>();
		Short s = new Short((short) 1);
		Short s2 = new Short((short) 1);
		Short short1 = 1;
		Short short2 = 1;
		System.out.println(short1.hashCode());
		testMap.put(short1, 1);
		System.out.println(short2.hashCode());
		testMap.put(short2, 2);
		testMap.put(s, 2);
		testMap.put(s2, 2);
		System.out.println(testMap.size());
		
		System.out.println("----------------------------------");
		
		testMap.put((short)2, 2);
		testMap.put((short)3, 3);
		testMap.put((short)4, 5);

		Iterator<Entry<Short, Integer>> iterator = testMap.entrySet().iterator();
		
		//open will error
		//testMap.put((short)5, 6);
		//testMap.put((short)6, 7);
		//testMap.put((short)7, 7);
		
		while(iterator.hasNext()) {
			Entry<Short, Integer> entry = iterator.next();
			System.out.println(" " + entry.getKey() + " " + entry.getValue());
		}
		
		System.out.println("----------------------------------");
		
		Map<String, Integer> map_str = new HashMap<>();
		map_str.put("s1", 123);
		String sss = new String("s1".getBytes());
		map_str.put(sss, 456);
		
		System.out.println(map_str.size());
		
		Iterator<Entry<String, Integer>> iterator_str = map_str.entrySet().iterator();
		while(iterator_str.hasNext()) {
			Entry<String, Integer> entry = iterator_str.next();
			System.out.println(" " + entry.getKey() + " " + entry.getValue());
		}
	}
}
