function execDaumPostcode() {
    new daum.Postcode({
        oncomplete: function(data) {
            // 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

            // 각 주소의 노출 규칙에 따라 주소를 조합한다.
            // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
            var addr = ''; // 주소 변수

            //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
            if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                addr = data.roadAddress;
            } else { // 사용자가 지번 주소를 선택했을 경우(J)
                addr = data.jibunAddress;
            }

            // 우편번호와 주소 정보를 해당 필드에 넣는다.
            document.getElementById('postcode').value = data.zonecode;
            document.getElementById("address").value = addr;
            // 커서를 상세주소 필드로 이동한다.
            document.getElementById("detailAddress").focus();
        }
        
    }).open();
}

document.querySelector("#searchAddress").addEventListener("click", execDaumPostcode);
const updateInfo = document.querySelector("#updateInfo");

// #updateInfo 요소가 존재 할 때만 수행 
if(updateInfo != null){

    // form 제출 시 
    updateInfo.addEventListener("submit", e=>{

        const memberNickname = document.querySelector("#memberNickname");
        const memberTel = document.querySelector("#memberTel");
        const memberAddress = document.querySelectorAll("[name='memberAddress']");

        // 닉네임 유효성 검사 
        if(memberNickname.value.trim().length === 0){
            alert("닉네임을 입력해주세요.");
            e.preventDefault(); // 제출 막기 
            return;
        }

        // 닉네임 정규식 
        let regExp = /^[가-힣\w\d]{2,10}$/;
        
        // 닉네임이 정규식에 맞지 않으면 
        if( !regExp.test(memberNickname.value)){
            alert("닉네임이 유효하지 않습니다.");
            e.preventDefault(); // 제출 막기 
            return;
        }

        // ************ 닉네임 중복 검사는 개별적으로 
        // 테스트 시 닉네임 중복 안되게 조심 하기!
        // ************
        
        // 전화번호 유효성 검사 

        if(memberTel.value.trim().length === 0){
            alert("전화번호를 입력해 주세요");
            e.preventDefault();
            return;
        }

        regExp = /^01[0-9]{1}[0-9]{3,4}[0-9]{4}$/;

        if( !regExp.test(memberTel.value)){
            alert("전화번호가 유효하지 않습니다.");
            e.preventDefault();
            return;
        }

        // 주소 유효성 검사 
        // 입력을 안하면 전부 안해야되고 
        // 입력하면 전부 해야된다 

        const addr0 = memberAddress[0].value.trim().length == 0; // 0이 맞다면 true, 0이 아니면 false
        const addr1 = memberAddress[1].value.trim().length == 0; // 0이 맞다면 true, 0이 아니면 false
        const addr2 = memberAddress[2].value.trim().length == 0; // 0이 맞다면 true, 0이 아니면 false

        // 모두 true 인 경우만 true 저장 

        const result1 = addr0 && addr1 && addr2; // 아무것도 입력 x 

        // 모두 false 인 경우만 true 저장 
        const result2 = !(addr0 || addr1 || addr2); // 모두 다 입력 

        // 모두 입력 또는 모두 미입력이 아니면 
        if( !(result1 || result2) ) {
            alert("주소를 모두 작성 또는 미작성 해주세요");
            e.preventDefault();
            return;
        }
    });
}

// --- changePw

if (changePw != null) {
    changePw.addEventListener("submit", e => {
        const currentPw = document.querySelector("#currentPw");
        const newPw = document.querySelector("#newPw");
        const newPwConfirm = document.querySelector("#newPwConfirm");

        let str;
        if (currentPw == 0) str ="Fill in current Password";
        else if ( newPw == 0 ) str = "Fill in New Password";

        if (str != undefined) {
            alert(str);
            e.preventDefault();
            return;
        }
        // 유효성검사
        const regExp = /^[A-Za-z0-9!@#\-_]{6,20}$/;
        if(!regExp.test(newPw.value)) {
            alert("Please fill out password with guide");
            e.preventDefault;
            return;
        }
        if(newPw.value != newPwConfirm.value){
            alert("Password is not a match");
            e.preventDefault();
            return;
        }
    });
}
const secession = document.querySelector("#secession");

if(secession != null) {
    secession.addEventListener("submit", e => {
        const memberPw = document.querySelector("#memberPw");
        const agree = document.querySelector("#agree");
        // 비밀번호 미작성시
        if(memberPw.value.trim().length == 0) {
            alert("Enter Password");
            e.preventDefault();
            return;
        }
        // 동의서 체크여부 (O/X)
        if(!agree.checked) {
            alert("Agree to terms");
            e.preventDefault();
            return;
        }
        // 탈퇴 확인 얼러트
        if(!confirm("Confirm to delete")) {
            alert("Canceled");
            e.preventDefault();
            return;
        }

    });
}

// ------------------------------------------------------------------

/* 비밀번호 수정 */


// -----------------------------------------------------------

/* 탈퇴 유효성 검사 */

// 탈퇴 form 태그 
