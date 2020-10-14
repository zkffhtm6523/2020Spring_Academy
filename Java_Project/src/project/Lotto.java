package project;

import java.util.Random;

public class Lotto {
	public static void main(String[] args) {
		//랜덤 객체 생성
		Random rand = new Random();
		
		//로또 복권 번호 생성
		int lotto[] = new int[7];
		
		//1~45
		int num = 0;
		
		//flag
		//깃발  - 표시
		//시작 전에 중복이 없다고 가정(false : 중복 없음, true : 중복 있음)
		boolean is_duplicate = false;
		
		for (int i = 0; i < 6; i++) {
			num = rand.nextInt(45) + 1;
			
			for (int j = 1; j < i; j++) {
				if(num == lotto[j]) {
					is_duplicate = true;
					break;
				}else {
					
				}
			}
			if(is_duplicate == false) {
				lotto[i] = num;
			}else if(is_duplicate == true) {
				
			}
			is_duplicate = false;
			
			System.out.print(num + " ");
		}
	}
}
