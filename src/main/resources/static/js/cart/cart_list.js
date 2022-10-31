
//--------스크립트 실행과 동시에 변수 생성--------//

//제목줄 체크박스
const checkAll = document.querySelector('#checkAll')

//제목줄을 제외한 모든 체크박스
const chks = document.querySelectorAll('.chk');

//모달창 띄우기
const updateAmountModal = new bootstrap.Modal('#updateAmountModal');



//------------ 함수 정의 영역------------------//

//총 가격을 세팅하는 함수
function setFinalPrice(){
	//체크된 체크박스를 선택
	const checkedBoxes = document.querySelectorAll('.chk:checked');
	
	let finalPrice = 0;
	for(const checkedBox of checkedBoxes){
		const totalPrice = parseInt(checkedBox.closest('tr').querySelector('.totalPriceDiv').dataset.totalPrice);
		finalPrice = finalPrice + totalPrice;
	}
	document.querySelector('#finalPriceSpan').innerText = '￦' + finalPrice.toLocaleString();
	
}

//수량 변경 모달에서 취소버튼 클릭 시
function rollbackAmount(){
	
	//모든 체크박스를 돌면서 cartCode값을 가져온다.
	//수량을 롤백시켜야하는 cartCode와 비교
	const selectedCartCode = document.querySelector('#updateAmountBtn').dataset.cartCode;
	const originAmount = document.querySelector('#cancelBtn').dataset.originAmount;
	
	for(const chk of chks) {
		if(chk.value == selectedCartCode){
			chk.closest('tr').querySelector('input[type="number"]').value = originAmount;
		}
	}
	
}


//선택삭제 및 선택구매 버튼 클릭시 진행
function deleteOrBuy(selectedTag){
	const deleteForm = document.querySelector('#cartForm');
	
	//체크한 cartCode 다 들고온다.
	//전체 체크박스에서 체크된 체크박스를 가지고온다.
	const checkedChks = document.querySelectorAll('.chk:checked');
	
	if(checkedChks.length == 0){
		alert('상품을 먼저 선택하세요');
		return;
	}
	
	let cartCodes = '';

	for(const checkedChk of checkedChks){
		cartCodes = cartCodes + checkedChk.value + ',';
	}//"CART_001CART_002CART_003" 들어오기 때문에 , 로 연결해준다.
		
	deleteForm.querySelector('input').value = cartCodes;
		
		
	//form 태그 안에 있는 action의 속성 값을 바꾸기
	if(selectedTag.innerText == '선택삭제'){
		deleteForm.action = '/cart/cartDelete';
	}
	else{
		deleteForm.action = '/buy/buyInfo';
	}
		
	deleteForm.submit();

}






//-------------이벤트 정의 영역------------------//

//전체선택, 전체해제 실행시 진행되는 체크박스 이벤트
checkAll.addEventListener('click', function(){
	//제목줄에서 체크박스의 체크여부
	const isChecked = checkAll.checked;//true, false
	//장바구니 목록의 모든 체크박스
	const checkBoxes = document.querySelectorAll('.chk');

	//제목줄 체크박스가 체크되었다면
	if(isChecked){ 
		//모든 체크박스를 체크한다.
		for(const checkBox of checkBoxes){
			checkBox.checked = true;
		}
		
	}
	else{
		for(const checkBox of checkBoxes){
			checkBox.checked = false;
		}
	}
	
	setFinalPrice();
	
});


//장바구니 목록에 있는 체크박스 클릭 시 진행
for(const chk of chks){
	chk.addEventListener('click', function(){
		setFinalPrice();
	});
}




//장바구니 목록에 있는 변경 버튼 클릭 시 진행
function goUpdateAmount(selectedTag){
	
      updateAmountModal.show();
       
      //수량
      const cartAmount = selectedTag.closest('.row').querySelector('input[type="number"]').value;
      //cart_code
      const cartCode = selectedTag.closest('tr').querySelector('input[type="checkbox"]').value;
       
      //클릭한 버튼의 data-cart-code, data-cart-amount 속성의 값을 
	  //수량을 변경하고자 하는 데이터로 세팅
      document.querySelector('#updateAmountBtn').dataset.cartCode = cartCode;
      document.querySelector('#updateAmountBtn').dataset.cartAmount = cartAmount;

		//기존 장바구니 수량을 취소버튼에 전달
		const originAmount = selectedTag.closest('.row').querySelector('input[type="number"]').dataset.originAmount;
		document.querySelector("#cancelBtn").dataset.originAmount = originAmount;
}



//장바구니 목록의 수량변경 버튼 클릭 후 나타나는 모달창의 확인버튼 클릭 시 진행
function updateAmount(){
	
	const cartCode = document.querySelector('#updateAmountBtn').dataset.cartCode;
	const cartAmount = document.querySelector('#updateAmountBtn').dataset.cartAmount;
	
	//ajax start
	$.ajax({
	    url: '/cart/updateAmount', //요청경로
	    type: 'post',
	    data:{'cartCode':cartCode, 'cartAmount':cartAmount}, //필요한 데이터
	    success: function(result) {
			alert('성공');
			//모달창 띄우기
	       updateAmountModal.hide();
	       
			for(const chk of chks){
				if(chk.value == cartCode){
					chk.closest('tr').querySelector('input[type="number"]').dataset.originAmount = cartAmount;
				}
				
			}
			
	       
	    },
	    error: function(){
	       alert('실패');
	    }
	});
	//ajax end
	
	
}       
	
	
	     

	




