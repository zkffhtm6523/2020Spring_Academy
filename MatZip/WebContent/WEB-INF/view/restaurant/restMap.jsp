<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div id="sectionContainerCenter">
	<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=f8d334081337ab0adf6cf88a0fb13d55"></script>
	<div id="mapContainer" style="width: 100%; height: 100%;"></div>
	<script src="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js"></script>
	<script>
		const options = { //지도를 생성할 때 필요한 기본 옵션
			center: new kakao.maps.LatLng(35.8641294, 128.5942331), //지도의 중심좌표.
			level: 5 //지도의 레벨(확대, 축소 정도)
		};
		//지도 생성 및 객체 리턴
		const map = new kakao.maps.Map(mapContainer, options);
		
		function getRestaurantList() {
			axios.get('/restaurant/ajaxGetList').then(function(res) {
				console.log(res.data)
				
				res.data.forEach(function(item){
					
					
					var na = {
							
							'Ga' : item.lng,
							'Ha' : item.lat
					}
					var marker = new kakao.maps.Marker({
						position: na
					});
					marker.setMap(map)
				})
			})
		}
		getRestaurantList();
	</script>
</div>
