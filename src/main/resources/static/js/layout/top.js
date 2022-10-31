
//-------------------페이지 열림과 동시에 실행되는 코드----------------------//

isLoginFail();

//--------------------------변수--------------------------------------------//

//회원가입 모달
const join_modal = document.querySelector('#join_modal');

//로그인 모달
const login_modal = document.querySelector('#login_modal');


//const myModalAlternative = new bootstrap.Modal('#login_modal');
//myModalAlternative.show();//페이지 열리면 모달창 실행된다.
//myModalAlternative.hide(); 모달을 닫는다.


//-------------------------함수 정의---------------------------------------//

//회원가입에서 search 버튼 클릭 시 진행
function searchAddr(){
		new daum.Postcode({
	        oncomplete: function(data) {
	        	// 도로명 주소 
	        	const roadAddr = data.roadAddress; 
	        	document.querySelector('#memberAddr').value = roadAddr;
	        
	        }
	    }).open();
	}
	
//Ajax를 사용하여 로그인기능하는 함수

function login(){
	const member_id = login_modal.querySelector('#memberId').value;
	const member_pw = login_modal.querySelector('#memberPw').value;
	
	//ajax start
	$.ajax({
		url: '/member/ajaxLogin', //요청경로
		type: 'post',
		data: {'memberId':member_id, 'memberPw':member_pw}, //필요한 데이터
		success: function(result) {
			if(result){
				alert('로그인 성공');
				location.href='/item/list'; 
			}
			else{
				alert('로그인 실패');
			}			
		},
		error: function() {
			alert('로그인 실패');
		}
	});
	}//ajax end


//로그인 실패 여부로 모달창을 띄워주는 함수
function isLoginFail(){ //모달창 실행 -> 로그인 실패했을때만 띄운다.
	const isLoginFail = document.querySelector('#isLoginFail').value;

	if(isLoginFail == 'true'){
		const myModalAlternative = new bootstrap.Modal('#login_modal');
		myModalAlternative.show();
	}
}

	
	
//-------------------------이벤트 정의------------------------------------//

//회원가입 모달이 닫히면 실행되는 이벤트	
join_modal.addEventListener('hidden.bs.modal', function(event){ //매개변수로 event작성해준다(event:변수로의 기능-이름 상관x)

//#1번째 방법	
//	const inputs = join_modal.querySelectorAll('input:not([type="button"])');
	//inputs.value=''; //value는 하나만 선택할 때 사용함 -> for문 사용
	
//	for(const inputTag of inputs){
//		inputTag.value = '';
//	}
	
//#2번째 방법
	join_modal.querySelector('form').reset();


});


//로그인 모달이 닫히면 실행되는 이벤트
login_modal.addEventListener('hidden.bs.modal', function(event)	{
	
	
	login_modal.querySelector('form').reset();
	//form을 top.html의 login 모달 안에 작성해서 아이디, 비번 가지고 오게 만들어준다.
});
	
	

	
