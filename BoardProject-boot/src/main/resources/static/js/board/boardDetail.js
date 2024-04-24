// 1) loginmember Number
// 2) current board number
// 3) like yes or no

const icon = document.querySelector("#boardLike");

icon.addEventListener("click", (e) => {
    if (loginMemberNo == null) {
        alert("First Login");
        return;
    }

    const obj = {
        "memberNo" : loginMemberNo,
        "boardNo" : boardNo,
        "likeCheck" : likeCheck
    };

    fetch("/board/like", {
        method : "POST",
        headers : {"Content-Type" : "application/json"},
        body : JSON.stringify(obj)
    }).then(resp => resp.text()).then(count => {
        
        if(count == -1) {
            console.log("Like error");
            return;
        }

        // each click -> INSERT/DELETE
        likeCheck = likeCheck == 0 ? 1 : 0;

        e.target.classList.toggle("fa-regular");
        e.target.classList.toggle("fa-solid");

        e.target.nextElementSibling.innerText = count;
    });
});
