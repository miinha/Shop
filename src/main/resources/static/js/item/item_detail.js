
//장바구니 버튼 클릭 시 진행
function addCart(){
	//innerHTML : 선택한 태그 안에 있는 태그 내용을 그대로 가져옴(해석x-그대로)
	//innerText : 선택한 태그 안에 있는 태그 내용을 해석해서 가져옴
	//const str = document.querySelector('#test').innerHTML;
	const check_login = document.querySelector('#test').innerText;
	//alert(check_login); // "" : 공백글자를 가져옴(로그인x), 로그인 시 : "1"
	//alert(check_login.trim()); 
	
	if(check_login.trim() == ''){ //로그인 하지 않았을 경우
		alert('로그인이 필요합니다.');
		return ; //더 이상 메소드 실행하지 않고 끝난다.
	}	
	
	addCartAjax();
	
	
	//장바구니 등록 기능
	//(CART_CODE, ITME_CODE, MEMBER_ID, CART_AMOUNT, REG_DATE, TOTAL_PRICE)
	//자바스크립트에서 submit 실행방법
	//1. form 태그를 선택한다.
	//2. 선택한 form태그에서 submit()함수를 실행시킨다. 
	//document.querySelector('#regCartForm').submit();
	
}


//장바구니 등록 시 실행되는 ajax
function addCartAjax(){
			//ajax start
			$.ajax({
			   url: '/cart/regCart', //요청경로
			    type: 'post',
			    data:{'itemCode' : document.querySelector('#itemCode').value,
			    	  'cartAmount' : document.querySelector('#cartAmount').value }, //필요한 데이터
			    success: function(result) {
				
				  //모달창 띄우기
			       const modal = new bootstrap.Modal('#regCartModal');
			       modal.show();
				
			    //  result = confirm('장바구니에 상품을 등록했습니다.\n장바구니 페이지로 이동하시겠습니까?');
			    //  if(result){
				//	location.href = '/cart/cartList';
				//  }
			      
			      
			    },
			    error: function(){
			       alert('실패');
			    }
				});
			//ajax end
	
	}
	
	
//바로 구매 버튼 클릭 시
function buy(){
	const buyAmount = document.querySelector('#cartAmount').value;
	document.querySelector('#buyAmountInput').value = buyAmount;
	document.querySelector('#buyForm').submit();
	
}	






