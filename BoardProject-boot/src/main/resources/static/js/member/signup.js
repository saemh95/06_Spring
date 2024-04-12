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