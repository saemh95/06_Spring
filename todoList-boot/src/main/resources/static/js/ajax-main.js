const totalCount = document.getElementById("totalCount");
const completeCount = document.getElementById("completeCount");
const reloadBtn = document.getElementById("reloadBtn");
const todoTitle = document.getElementById("todoTitle");
const todoContent = document.getElementById("todoContent");
const addBtn = document.getElementById("addBtn");

const tbody = document.querySelector("#tbody");

const popupLayer = document.getElementById("popupLayer");
const popupTodoNo = document.querySelector("#popupTodoNo");
const popupTodoTitle = document.querySelector("#popupTodoTitle");
const popupComplete = document.getElementById("popupComplete");
const popupRegDate = document.querySelector("#popupRegDate");
const popupTodoContent = document.querySelector("#popupTodoContent");
const popupClose = document.getElementById("popupClose");

const changeComplete = document.querySelector("#changeComplete");
const deleteBtn = document.getElementById("deleteBtn");
const updateView = document.getElementById("updateVIew");

// Promise
function getTotalCount() {
    fetch("/ajax/totalCount").then(response => {
        // console.log(response);
        return response.text();
    }).then(result => {
        // console.log(result);
        totalCount.innerText = result;
    });
}

function getCompletCount() {

    fetch("/ajax/completeCount").then(response => {
        return response.text();
    }).then(result => {
        completeCount.innerText = result;
    });

}

reloadBtn.addEventListener("click", () => {
    getTotalCount();
    getCompletCount();
});

addBtn.addEventListener("click", () => {
    const param = {
        "todoTitle" : todoTitle.value,
        "todoContent" : todoContent.value
    };
    fetch("/ajax/add", {
        method : "POST",
        headers : {"Content-Type" : "application/json"},
        body : JSON.stringify(param)
    }).then(response => response.text()).then(
        temp => {
            if(temp>0) {
                alert("Add Todo Complete");
                todoTitle.value = "";
                todoContent.value = "";
                getTotalCount();
                selectTodoList();
            } else {
                alert("Add Todo Error")
            }
        }
    )
    
    ;
});

const selectTodo = (url) => {
    fetch(url).then(resp => resp.json()).then(todo => {
        // const todo = JSON.parse(result);
        console.log(todo);
        
        popupTodoNo.innerText = todo.todoNo;
        popupTodoTitle.innerText = todo.todoTitle;
        popupComplete.innerText = todo.complete;
        popupRegDate.innerText = todo.regDate;
        popupTodoContent.innerText = todo.todoContent;

        popupLayer.classList.remove("popup-hidden");
    });
}

popupClose.addEventListener("click", () => {
    popupLayer.classList.add("popup-hidden");
});

const selectTodoList = () => {
    fetch("/ajax/selectList").then(resp => resp.text()).then(result => {
        // console.log(result);
        // console.log(typeof result);
        const todoList = JSON.parse(result);
        // console.log(todoList);
        tbody.innerHTML="";

        for (let todo of todoList) {
            const tr = document.createElement("tr");
            const arr = ['todoNo', 'todoTitle', 'complete', 'regDate'];

            for (let key of arr) {
                const td = document.createElement("td");
                if(key === 'todoTitle') {
                    const a = document.createElement("a");
                    a.innerText = todo[key];
                    a.href = "/ajax/detail?todoNo=" + todo.todoNo;
                    td.append(a);
                    tr.append(td);

                    a.addEventListener("click", (e) => {
                        e.preventDefault();
                        
                        selectTodo(e.target.href);
                    });
                    continue;
                    
                }
                td.innerText = todo[key];
                tr.append(td);
            }
            tbody.append(tr);   
        }
    });
}

deleteBtn.addEventListener("click", () => {
    
    if(!confirm("Confirm Delete")) {return;}
    
    const todoNo = popupTodoNo.innerText;

    fetch("/ajax/delete", {
        method : "DELETE",
        headers : {"Content-type" : "application/json"},
        body : todoNo
    }).then(resp => resp.text()).then(result => {
        if(result>0) {
            alert("Delete Completed");
            popupLayer.classList.add("popup-hidden");
            getTotalCount();
            selectTodoList();
            getCompletCount();
        } else {
            alert("Delete Canceled");
        }
    });

});

changeComplete.addEventListener("click", () => {

    let currentComplete = popupComplete.innerText == 'Y' ? 'N' : 'Y';
    const param = {
        "todoNo" : popupTodoNo.innerText,
        "complete" : currentComplete
    };

    fetch("/ajax/change", {
        method : "PUT",
        headers : {"Content-type" : "application/json"},
        body : JSON.stringify(param)
    }).then(response => response.text()).then(result=> {
        //console.log(popupComplete.innerText);

        if(result > 0) {
            // currentComplete = (currentComplete == 'Y' ? 'Y' : 'N' );
            // console.log("teest::", currentComplete);
            popupComplete.innerText = currentComplete;
            selectTodoList();
            getTotalCount();
            getCompletCount();
        }
    });
});

selectTodoList();
getTotalCount();
getCompletCount();

