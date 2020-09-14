<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<style type="text/css">
table, th, td{border: 1px solid black; border-collapse: collapse; }
th, td{padding: 20px;}
table{ width: 400px; margin: 15px auto;}
.restaurant-detail{width: 80%; margin: 0 auto; height: 300px;}
#sectionContainerCenter{display: block;}
#topMenu{width: 80%; margin: 15px auto;}
#topMenu h1{text-align: center; width: 100%; }
#topMenu button {
	float: right;
}
#middleMenu{width: 80%; margin: 15px auto;}
.reatuarant_name{text-align: center;}
.frmList{text-align: center;}
#addBtn{text-align: right;}
</style>
 	<div id="sectionContainerCenter">
 		<div id = "topMenu">
		<h1>레스토랑</h1>
 		<c:if test="${loginUser.i_user eq data.i_user}">
 		</c:if>
 		</div>
 		<div id = "middleMenu" >
 				<a href="/restaurant/restMod?i_rest=${data.i_rest}"><button>수정</button></a>
 				<button onclick="isDel()">삭제</button>
 			가게 사진들
 		</div>
 		<div class="restaurant-detail">
 			<div id="detail-header">
				<div class="restaurant_title_wrap">
					<span class="title">
						<h1 class="reatuarant_name">${data.nm}</h1>
						<strong class="rate-point">ㅋㅋㅋ</strong>
					</span>
				</div>
				<div class="status branch_none">
					<span class="cnt hit">조회수</span>
					<span class="cnt favorite">좋아요</span>
				</div>
				<form action="/restaurant/addRecMenus" enctype="multipart/form-data" method="post">
					<div id="addBtn"><button type="button" onclick="addRecMenu()">메뉴 추가</button></div>
					<input type="hidden" name="i_rest" value="${data.i_rest}">
					<div id="recItem">
						<div class="frmList">
							메뉴 : <input type="text" name="menu_nm">
							가격 : <input type="number" name="menu_price">
							사진 : <input type="file" name="menu_nm">
							<input type="submit" value="등록">
						</div>
					</div>
				</form>
 			</div>
 			<div>
 				<table>
 					<caption><h2>레스토랑 상세 성보</h2></caption>
 					<tbody>
 						<tr>
 							<th>주소</th>
 							<th>${data.addr}</th>
 						</tr>
 						<tr>
 							<th>카테고리</th>
 							<th>${data.cd_category_nm}</th>
 						</tr>
 					</tbody>
 				</table>
 			</div>
 		</div>
 	</div>
<script type="text/javascript">
function isDel() {
if(confirm('삭제하시겠습니까?')){
	location.href = '/restaurant/restDel?i_rest=${data.i_rest}'
	}
}
function addRecMenu() {
	var div = document.createElement('div')
	div.classList = 'frmList'
	var inputNm = document.createElement('input')
	inputNm.setAttribute('type','text')
	inputNm.setAttribute('name','menu_nm')
	var inputPrice = document.createElement('input')
	inputPrice.setAttribute('type','text')
	inputPrice.setAttribute('name','menu_price')
	var inputPic = document.createElement('input')
	inputPic.setAttribute('type','file')
	inputPic.setAttribute('name','menu_pic')
	
	div.append('메뉴 : ')
	div.append(inputNm)
	div.append(' 가격 : ')
	div.append(inputPrice)
	div.append(' 사진 : ')
	div.append(inputPic)
	
	recItem.append(div)
}
</script>
