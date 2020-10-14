package project;
import java.util.Random;
import java.util.Scanner;

public class LawOfLargeNumbers {
	public static void main(String[] args) {
		//랜덤 객체 생성
		Random rand = new Random();
		
		Scanner sc = new Scanner(System.in);
		
		int num = 0;
		//주사위 눈금 나온 갯수 저장
		int dice[] = new int[7];
		double dice_percent[] = new double[7];
		int count = 0;
		
		System.out.print("주사위 굴릴 횟수 입력 : ");
		count = sc.nextInt();
		
		for (int i = 0; i <= count; i++) {
			num = rand.nextInt(6) + 1;
			System.out.println("num : "+num);
			dice[num]++;
		}
		
		for (int i = 1; i <= 6; i++) {
			dice_percent[i] = ((double)dice[i]/count) * 100;
			System.out.printf("%d 나온 횟수 : %d\n", i, dice[i]);
		}
		for (int i = 1; i <= 6; i++) {
			System.out.printf("%d 나올 확률 : %.2f%%\n", i, dice_percent[i]);
		}
		
		sc.close();
	}
}
