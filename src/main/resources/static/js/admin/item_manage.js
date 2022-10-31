
//상품 재고변경 버튼 클릭시 실행
function updateStock(itemCode, selectedTag){
	
   //parentElement : 부모태그 찾아 감.
   //children : 자식 태그 찾아 감.
   //previousElementSibling : 이전 형제 노드를 찾아 감.
   //nextElementSibling : 다음 형제 노드를 찾아 감.
   //closest() : 감싸고 있는 가장 가까운 상위태그를 찾아 감
	
	//const itemStock = selectedTag.parentElement.previousElementSibling.children[0].value;// 밑줄과 동일
	const itemStock = selectedTag.closest('td').querySelector('.stockInput').value; 
	
		//ajax start
			$.ajax({
			    url: '/admin/updateStock', //요청경로
			    type: 'post',
			    data:{'itemCode' : itemCode, 'itemStock' : itemStock}, //필요한 데이터
			    success: function(result) {
			       //alert('수량을 변경했습니다.');
			       
			       //모달창 띄우기
			       const modal = new bootstrap.Modal('#updateStockModal');
			       modal.show();
			       
				},
			    error: function(){
			       alert('실패');
			    }
			});
		}//ajax end
	
	
//판매중, 매진 라디오 버튼 클릭 시 진행.	
//function change
	
	
	
	
	
	
	
	
