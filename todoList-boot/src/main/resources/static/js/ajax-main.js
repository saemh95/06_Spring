const totalCount = document.getElementById("totalCount");
const completeCount = document.getElementById("completeCount");
const reloadBtn = document.getElementById("reloadBtn");
const todoTitle = document.getElementById("todoTitle");
const todoContent = document.getElementById("todoContent");
const addBtn = document.getElementById("addBtn");
const tbody = document.querySelector("#tbody");

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
                        
                        // selectTodo(e.target.href);
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

selectTodoList();
getTotalCount();
getCompletCount();

