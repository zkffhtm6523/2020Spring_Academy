package polymorphism;

public class SamsungTV implements Tv {
	private Speaker speaker;
	
	public void setSpeaker(Speaker speaker) {
		this.speaker = speaker;
	}
	public SamsungTV() {
		System.out.println("SamsungTV 객체화 !!!");
	}
	public SamsungTV(Speaker speaker) {
		System.out.println("SamsungTV 안에 Speaker 주소 넣어주는 곳");
		this.speaker = speaker;
	}
	
	@Override
	public void powerOn() {
		System.out.println("SamsungTV -- 전원 켠다.");
	}
	@Override
	public void powerOff() {
		System.out.println("SamsungTV -- 전원 끈다.");
	}
	@Override
	public void volumeUp() {
		speaker.volumeUp();
	}
	@Override
	public void volumeDown() {
		speaker.volumeDown();
	}
}
