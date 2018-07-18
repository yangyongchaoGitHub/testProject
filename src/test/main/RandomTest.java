package test.main;

import java.util.Random;

public class RandomTest {

	public static void main(String[] args) {
		Random ran1 = new Random();
        System.out.println("使用种子为10的Random对象生成[0,50)内随机整数序列: ");
        for (int i = 0; i < 10; i++) {
            System.out.print(ran1.nextInt(50) + " ");
        }
	}

}
