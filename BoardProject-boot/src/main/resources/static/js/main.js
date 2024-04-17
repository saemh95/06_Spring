// console.log("HI")

// cookie "K=V; K=V;"

const getCookie = (key) => {

    // document.cookie="test"+"="+"user01";
    // arr.map(param) => new arr of param
    const cookies = document.cookie;
    const cookieList = cookies.split("; ") // ["K=V", "K=V"]
                                          .map(el => el.split("=")); // ["K","V"]
    // console.log(cookieList);

    const obj = {};

    for (let i=0; i < cookieList.length; i++) {
        const k = cookieList[i][0];
        const v = cookieList[i][1];
        obj[k]=v;
    }
    return obj[key];
    // console.log(cookies); //== saveId=email
}
// console.log(getCookie("saveId"));
const loginEmail = document.querySelector("#loginForm input[name='memberEmail']");

if(loginEmail != null) { // loginform is shown
    // cookie k = "saveId" : value
    const saveId = getCookie("saveId");
    
    if(saveId != undefined) {
        loginEmail.value = saveId;

        document.querySelector("input[name='saveId']").checked= true;

    }
}

// email, password == null
const loginForm = document.querySelector("#loginForm");
const loginPw = document.querySelector("#loginForm input[name='memberPw']");

if(loginForm != null) {
    loginForm.addEventListener("submit", e => {
        if(loginEmail.value.trim().length === 0) {
            alert("Email is empty");
            e.preventDefault();
            loginEmail.focus();
            return;
        }
        // console.log(loginEmail.value);
        
        if(loginPw.value.trim().length === 0) {
            alert("Password is empty");
            e.preventDefault();
            loginPw.focus();
            return;
        }
        // console.log(loginPw.value);
    });
}
// console.log(loginEmail.value);

const quickLogin = document.querySelectorAll(".quick-login");

quickLogin.forEach((items, index) => {

    items.addEventListener("click", () => {
        const email = items.innerText;
        location.href = "/member/quickLogin?memberEmail=" + email;
    });
});

const selectMemberList = document.querySelector("#selectMemberList");
const memberList = document.querySelector("#memberList");

selectMemberList.addEventListener("click", () => {
    fetch("/member/selectMemberList").then(resp => resp.json()).then(list => {
        memberList.innerHTML="";

        for (let member of list) {
            const tr = document.createElement("tr");
            const arr = ['memberNo', 'memberEmail', 'memberNickname', 'memberDelFl'];

            for(let key of arr) {
                const td = document.createElement("td");
                
                td.innerText = member[key];
                tr.append(td);    
            }
            memberList.append(tr);
        }
    });
});


const resetPw = document.querySelector("#resetPw");



resetPw.addEventListener("click", () => {
    const resetMemberNo = document.querySelector("#resetMemberNo");
    
    fetch("/member/resetPw", {
        method : "PUT",
        headers : {"Content-Type" : "application/json"},
        body : resetMemberNo.value
    }).then(resp => resp.text()).then(result => {
        if(result>0) {
            alert("Password Reset");
            resetMemberNo.value = "";
        } else {
            alert("Password Reset Error");
        }
    }).catch(error => console.log(error));
});

const restorationBtn = document.querySelector("#resporationBtn");

restorationBtn.addEventListener("click", () => {

    const restorationMemberNo = document.querySelector("#restorationMemberNo");
    fetch("/member/restorationMember", {
        method : "PUT",
        headers : {"Content-Type" : "application/json"},
        body : restorationMemberNo.value
    }).then(resp => resp.text()).then(result => {

        if(result>0) {
            alert("User Restored");
            restorationMemberNo.value = "";
        } else {
            alert("Restoration Error");
        }
    });
});