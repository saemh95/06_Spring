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