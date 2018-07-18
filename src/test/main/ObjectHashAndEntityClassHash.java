package test.main;

public class ObjectHashAndEntityClassHash {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(handle().hashCode());
		
		/**
		 * int 类型判断
		 */
		int m = 0;
		Object object = m;
		if (object instanceof Integer) {
			System.out.println("is");
		}
	}
	
	public static Object handle() {
		Man man = new Man();
		man.id = 1;
		System.out.println(man.hashCode());
		Man man2 = new  Man();
		System.out.println(man2.hashCode());
		return man;
	}
}

class Man {
	public int id = 0;
	public String nameString = "no name";
}