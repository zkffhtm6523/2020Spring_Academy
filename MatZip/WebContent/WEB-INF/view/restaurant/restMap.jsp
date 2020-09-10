<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div id="sectionContainerCenter">
	<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=f8d334081337ab0adf6cf88a0fb13d55"></script>
	<div id="mapContainer" style="width: 100%; height: 100%;"></div>
	<script>
		const options = { //지도를 생성할 때 필요한 기본 옵션
			center: new kakao.maps.LatLng(36.714679, 128.049671), //지도의 중심좌표.
			level: 12 //지도의 레벨(확대, 축소 정도)
		};
		//지도 생성 및 객체 리턴
		var map = new kakao.maps.Map(mapContainer, options);
	</script>
</div>
