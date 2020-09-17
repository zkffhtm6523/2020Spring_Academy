package polymorphism;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

public class TvSpring {
	public static void main(String[] args) {
		//xml을 읽을 수 있는 생성자
		//아까 만들었던 bean 담겨 있는 곳을 찾는다.
		AbstractApplicationContext factory = new GenericXmlApplicationContext("applicationContext.xml");
		
		//beans에 있는 삼성의 주소값을 가져온다.
		//불러오지 않아도 beans에 있는 것을 자동으로 객체화한다.
		//기본으로 싱글톤 세팅이라서 같은 주소값이 올 것
		
		Tv tv = (Tv)factory.getBean("tv");
		tv.powerOn();
		tv.volumeUp();
		tv.volumeDown();
		tv.powerOff();
		//기본이 싱글톤이라서 주소값이 같다
	}
}
