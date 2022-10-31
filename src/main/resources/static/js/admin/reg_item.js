
	
//사용, 미사용 라디오 버튼 클릭 시 진행.
function changeStatus(cateCode, status){
	
	const result = confirm('상품의 상태를 변경하시겠습니까?');
	
	if(result){
		//ajax start
			$.ajax({
			   url: '/admin/changeStatusAjax', //요청경로
			    type: 'post',
			    data:{'cateStatus' : status, 'cateCode' : cateCode}, //필요한 데이터
			    success: function(result) {
			      alert('상태를 변경했습니다~');
			      
			      //셀렉트 박스 목록을 다시 조회
			      selectCategoryListInUseAjax();
			      
			    },
			    error: function(){
			       alert('실패');
			    }
			});
		}//ajax end
			
	}
	
//카테고리 사용여부 변경 후 실행되는 ajax 함수
//해당 함수가 실행되면 사용중인 카테고리 목록을 다시 조회한다.
function selectCategoryListInUseAjax(){
	//ajax start
		$.ajax({
		    url: '/admin/selectCategoryListInUseAjax', //요청경로
		    type: 'post',
		    data:{}, //필요한 데이터
		    success: function(result) {
		      const selectBox = document.querySelector("#cateCode");
		      selectBox.innerHTML = '';
		      
		      let str = '';
		      
		      for(const categoryInfo of result){
			
		     	 str += `<option value="${categoryInfo.cateCode}">${categoryInfo.cateName}</option>`; //조회된 데이터의 개수만큼 만들어져야 한다.
		      }
		      
		      selectBox.insertAdjacentHTML('afterbegin', str);
		      
		    },
		    error: function(){
		       alert('실패');
		    }
		});
	//ajax end
	
}







	
	

	
