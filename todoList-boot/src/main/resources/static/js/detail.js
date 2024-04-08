const goToList = document.querySelector("#goToList");

goToList.addEventListener("click", () => {
    location.href="/";
});

const completeBtn = document.querySelector(".complete-btn");

completeBtn.addEventListener("click", function(e) {
    const todoNo = e.target.dataset.todoNo;
    // console.log(todoNo);
    let complete = e.target.innerText;
    // this.style.backgroundColor = "red";
    complete = (complete === 'Y') ? 'N' : 'Y';
    location.href = `/todo/changeComplete?todoNo=${todoNo}&complete=${complete}`;
});

const updateBtn = document.querySelector("#updateBtn");

updateBtn.addEventListener("click", (e) => {

    const todoNo = e.target.dataset.todoNo;

    location.href = `/todo/update?todoNo=${todoNo}`;
});

const deleteBtn = document.querySelector("#deleteBtn");

deleteBtn.addEventListener("click", (e) => {
    if(confirm("Confirm Delete")) {
        location.href = `/todo/delete?todoNo=${e.target.dataset.todoNo}`;
    }
});