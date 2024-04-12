
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

const checkObj = {
    "memberEmail" : false,
    "memberPw" : false,
    "memberPwConfirm" : false,
    "memberNickname" : false,
    "memberTel" : false,
    "authKey" : false
}

const memberEmail = document.querySelector("#memberEmail");
const emailMessage = document.querySelector("#emailMessage");

memberEmail.addEventListener("input", e => {

    checkObj.authKey = false;
    document.querySelector("#authKeyMessage").innerText="";

    const inputEmail = e.target.value;
    // console.log(inputEmail);


    if (inputEmail.trim().length === 0) {
        emailMessage.innerText = "Please enter an email address where you can receive mail.";

        emailMessage.classList.remove('confirm', 'error');

        checkObj.memberEmail = false;

        memberEmail.value = "";

        return;
    }

    const regExp = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
    if (!regExp.test(inputEmail)) {
        emailMessage.innerText = "Use correct email";
        emailMessage.classList.add('error');
        emailMessage.classList.remove('confirm');
        checkObj.memberEmail = false;
        return;
    } 

    fetch("/member/checkEmail?memberEmail=" + inputEmail).then(resp => resp.text()).then(count=> {
        if (count==1) {
            emailMessage.innerText = "Email already in use";
            emailMessage.classList.add('error');
            emailMessage.classList.remove('confirm');
            checkObj.memberEmail = false;
            return;
        }
        
        emailMessage.innerText = "Email Confirmed";
        emailMessage.classList.add('confirm');
        emailMessage.classList.remove('error');
        checkObj.memberEmail = true;
    }).catch(error => {
        console.log(error);
    });
});

const sendAuthKeyBtn = document.querySelector("#sendAuthKeyBtn");
const authKey = document.querySelector("#authKey");
const checkAuthKeyBtn = document.querySelector("#checkAuthKeyBtn");
const authKeyMessage = document.querySelector("#authKeyMessage");

// timer -> setInterval
let authTimer;
// timer default (min)
const initMin = 4;
// timer default (sec)
const initSec = 59;
const initTime = "05:00";

// timer
let min = initMin;
let sec = initSec;

// auth key click
sendAuthKeyBtn.addEventListener("click", () => {
    checkObj.authKey = false;
    authKeyMessage.innerText = "";

    if(!checkObj.memberEmail) {
        alert("Please enter a correct email address");
        return;
    } 

    min = initMin;
    sec = initSec;

    clearInterval(authTimer);

    fetch("/email/signup", {
        method : "POST",
        headers : {"Content-Type" : "application/json"},
        body : memberEmail.value
    }).then( resp => resp.text()).then(result => {
        if(result == 1) console.log("Verification Sent");
        else console.log("Verification Sending Error");
    });
    
    authKeyMessage.innerText = initTime;
    authKeyMessage.classList.remove('confirm', 'error');

    alert("Verification Code Sent");

    // setInterver();
    authTimer = setInterval(() => {
        authKeyMessage.innerText = `${addZero(min)}:${addZero(sec)}`;
        // 0min 0sec (00:00)
        if(min == 0 && sec == 0) {
            checkObj.authKey = false;
            clearInterval(authTimer); // stop interval
            authKeyMessage.classList.add('error');
            authKeyMessage.classList.remove('confirm');
            return;
        }
        if(sec == 0) {
            sec = 60;
            min--;
        }

        sec--;

    }, 1000); // milisecond


});

function addZero(number) {
    if(number < 10 ) return "0" + number;
    else return number;
}

checkAuthKeyBtn.addEventListener("click", () => {

    if (min == 0 && sec == 0) {
        alert("Verification Failed");
        return;
    } 

    if (authKey.value.length < 6) {
        alert("Verification Number Incorrect");
        return;
    }

    const obj = {
        "email" : memberEmail.value,
        "authKey" : authKey.value
    }

    fetch("/email/checkAuthKey", {
        method : "POST",
        headers : {"Content-Type" : "application/json"},
        body : JSON.stringify(obj)
    }).then(resp => resp.text()).then(result => {
        if (result == 0) {
            alert("Verification Number Incorrect");
            checkObj.authKey = false;
            return;
        }

        clearInterval(authTimer);
        authKeyMessage.innerText = "Verfication Complete";
        authKeyMessage.classList.remove('error');
        authKeyMessage.classList.add('confirm');
        checkObj.authKey = true;
    }); 
});

const memberPw = document.querySelector("#memberPw");
const memberPwConfirm = document.querySelector("#memberPwConfirm");
const pwMessage = document.querySelector("#pwMessage");

const checkPw = () => {
    if(memberPw.value === memberPwConfirm.value) {
        pwMessage.innerText = "Password Confirmed";
        pwMessage.classList.remove('error');
        pwMessage.classList.add('confirm');
        checkObj.memberPwConfirm = true;
        return;
    }
    
    pwMessage.innerText = "Password not a Match"
    pwMessage.classList.remove('confirm');
    pwMessage.classList.add('error');
    checkObj.memberPwConfirm = false;

};


memberPw.addEventListener("input", (e) => {
    
    checkObj.memberPw = false;

    const inputPw = e.target.value;
    if(inputPw.trim().length === 0) {
        pwMessage.innerText = "Please enter between 6 to 20 characters including letters, numbers, and special characters (!, @, #, -, _).";
        pwMessage.classList.remove('confirm', 'error');
        checkObj.memberPw = false;
        memberPw.value = ""; // first letter not a space
        return;
    }
    
    const regExp = /^[A-Za-z0-9!@#\-_]{6,20}$/;

    if(!regExp.test(inputPw)) {
        pwMessage.innerText = "Incorrect Password Form";
        pwMessage.classList.add('error');
        pwMessage.classList.remove('confirm');
        checkObj.memberPw = false;
        return;
    }
    
    pwMessage.innerText = "Password Confirmed";
    pwMessage.classList.remove('error');
    pwMessage.classList.add('confirm');
    checkObj.memberPw = true;

    if (memberPwConfirm.value.length > 0) {
        checkPw();
    }
});

memberPwConfirm.addEventListener("input", e => {
    if(checkObj.memberPw) {
        checkPw();
        return;
    }
    checkObj.memberPwConfirm = false;
});

const memberNickname = document.querySelector("#memberNickname");
const nickMessage = document.querySelector("#nickMessage");

memberNickname.addEventListener("input", e => {
    checkObj.memberNickname = false;
    const inputNickname = e.target.value;

    if (inputNickname.trim().length === 0 ) {
        checkObj.memberNickname = false;
        nickMessage.innerText = "only letters & numbers 2~10";
        nickMessage.classList.remove('error', 'confirm');
        memberNickname.value = "";
        return;
    }

    const regExp = /^[가-힣\w\d]{2,10}$/;
    if(!regExp.test(inputNickname)) {
        nickMessage.innerText = "Incorrect Nickname Form";
        nickMessage.classList.add('error');
        nickMessage.classList.remove('confirm');
        checkObj.memberNickname = false;
        return;
    }

    fetch("/member/checkNickname?memberNickname=" + inputNickname).then(resp => resp.text()).then(result => {
        if(result == 1) {
            checkObj.memberNickname = false;
            nickMessage.innerText = "Nickname already in use";
            nickMessage.classList.add('error');
            nickMessage.classList.remove('confirm');
            return;
        }
        nickMessage.innerText = "Nickname Confirmed";
        nickMessage.classList.add('confirm');
        nickMessage.classList.remove('error');
        checkObj.memberNickname = true;
    }).catch(err => console.log(err));

    
}); 

const memberTel = document.querySelector("#memberTel");
const telMessage = document.querySelector("#telMessage");

memberTel.addEventListener("input", e => {

    const inputTel = e.target.value;
    checkObj.memberTel = false;
    
    if (inputTel.trim().length === 0 ) {
        checkObj.memberTel = false;
        telMessage = "Phone Number (only number)";
        telMessage.classList.remove('error', 'confirm');
        return;
    }
    const regExp = /^01[0-9]{1}[0-9]{3,4}[0-9]{4}$/;
    if (!regExp.test(inputTel)) {
        telMessage.innerText = "Incorrect Number form";
        telMessage.classList.add('error');
        telMessage.classList.remove('confirm');
        checkObj.memberTel = false;
        return;
    }

    telMessage.innerText = "Phone Number Confirmed";
    telMessage.classList.add('confirm');
    telMessage.classList.remove('error');
    checkObj.memberTel = true;
    console.log(checkObj);
});

